package com.homework.dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
//import java.util.logging.Level;
//import java.util.logging.Logger;

import javax.sql.DataSource;

import com.sun.istack.internal.logging.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import sun.util.logging.PlatformLogger.Level;

/**
 * 6. (必做)研究一下 JDBC 接口和数据库连接池，掌握它们的设计和用法:
 * 1)使用 JDBC 原生接口，实现数据库的增删改查操作。
 * 2)使用事务，PrepareStatement 方式，批处理方式，改进上述操作。
 * 3)配置 Hikari 连接池，改进上述操作。
 */
public class JdbcConnectionTest {

    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/spring_homework", "root",
            "123456")) {
            //方法1 jdbc
            long solutionJdbc = solutionJdbc(conn);
            //方法2 PrepareStatement
            long solutionPrepareStatement = solutionPrepareStatement(conn);
            //方法三 Hikari
            long solutionHikari = solutionHikari();

            //末尾打印三个方法的耗时
            System.out.println("solutionJdbc: " + solutionJdbc);
            System.out.println("solutionPrepareStatement: " + solutionPrepareStatement);
            System.out.println("solutionHikari: " + solutionHikari);
        }

    }

    private static long solutionJdbc(Connection conn) throws Exception {
        long start = System.currentTimeMillis();
        conn.setAutoCommit(true);
        int concurrent = 100;
        while (concurrent-- > 0) {
            try (Statement stmt = conn.createStatement()) {
                for (long i = 1; i <= 100L; i++) {
                    final long id = (concurrent * 1000L) + (i * 10) + 1;
                    String sql = String.format("insert into `student` (`id`, `name`) values (%d,'%s')", id,
                        "jdbc" + id);
                    stmt.execute(sql);
                }
            }
        }
        final long solutionJdbc = System.currentTimeMillis() - start;
        System.out.println("solutionJdbc: " + solutionJdbc);
        return solutionJdbc;
    }

    private static long solutionPrepareStatement(Connection conn) throws Exception {
        long start = System.currentTimeMillis();
        conn.setAutoCommit(false);
        int concurrent = 100;
        while (concurrent-- > 0) {
            try (PreparedStatement preparedStatement = conn.prepareStatement(
                "insert into `student` (`id`, `name`) values (?,?)")) {
                for (long i = 1; i <= 100L; i++) {
                    final long id = (concurrent * 1000L) + i * 10 + 2;
                    preparedStatement.setLong(1, id);
                    preparedStatement.setString(2, "PrepareStatement" + id);
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
            }
            conn.commit();
        }
        final long solutionPrepareStatement = System.currentTimeMillis() - start;
        System.out.println("solutionPrepareStatement: " + solutionPrepareStatement);
        return solutionPrepareStatement;
    }

    private static long solutionHikari() {
        long start = System.currentTimeMillis();
        ApplicationContext context = new AnnotationConfigApplicationContext(BeanConfigration.class);
        DataSource dataSource = (DataSource)context.getBean("hikariDataSource");
        int concurrent = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(concurrent);
        CompletableFuture<Void>[] wait = new CompletableFuture[concurrent];
        while (concurrent-- > 0) {
            long curr = concurrent;
            final CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
                try (Connection connection = dataSource.getConnection();
                     PreparedStatement preparedStatement = connection.prepareStatement("insert into `student` (`id`, `name`) values (?,?)"))
                {
                    for (long i = 1; i <= 100L; i++) {
                        final long id = (curr * 1000L) + i * 10 + 3;
                        preparedStatement.setLong(1, id);
                        preparedStatement.setString(2, "Hikari" + id);
                        preparedStatement.addBatch();
                    }
                    preparedStatement.executeBatch();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, executorService);
            wait[concurrent] = completableFuture;
        }
        CompletableFuture.allOf(wait).join();
        final long solutionHikari = System.currentTimeMillis() - start;
        System.out.println("solutionHikari: " + solutionHikari);
        executorService.shutdown();
        return solutionHikari;
    }

}
