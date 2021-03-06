package org.zhdd99.homework07;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;
import java.sql.*;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 6. (必做)研究一下 JDBC 接口和数据库连接池，掌握它们的设计和用法:
 * 1)使用 JDBC 原生接口，实现数据库的增删改查操作。
 * 2)使用事务，PrepareStatement 方式，批处理方式，改进上述操作。
 * 3)配置 Hikari 连接池，改进上述操作。
 */
public class JdbcConnectionTest {

    public static ExecutorService executorService = Executors.newFixedThreadPool(100);

    public static void main(String[] args) throws Exception {
        //方法1 拼接sql
        long solutionSql = solutionSql();
        //方法2 PrepareStatement batch
        long solutionPrepareStatement = solutionPrepareStatement();
        //方法三 文件dump
        long solutionCsv = solutionCsv();
        //末尾打印四个方法的耗时
        System.out.println("solutionSql: " + solutionSql);
        System.out.println("solutionPrepareStatement: " + solutionPrepareStatement);
        System.out.println("solutionCsv: " + solutionCsv);
        executorService.shutdown();
    }

    private static long solutionSql() throws Exception {
        long start = System.currentTimeMillis();
        final String nowDateString = "\'2020-03-06 00:00:00\'";
        ApplicationContext context = new AnnotationConfigApplicationContext(BeanConfigration.class);
        DataSource dataSource = (DataSource)context.getBean("hikariDataSource");
        int concurrent = 100;
        CompletableFuture<Void>[] wait = new CompletableFuture[concurrent];
        while (concurrent-- > 0) {
            final CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
                try (Connection connection = dataSource.getConnection();
                     Statement statement = connection.createStatement())
                {
                    StringBuilder stringBuilder = new StringBuilder("insert into platform_order (gmt_create, gmt_modified, order_id, user_id, amount, status, gmt_pay) values ");
                    for (int i = 1; i <= 10000; i ++ ) {
                        if(i > 1){
                            stringBuilder.append(",");
                        }
                        stringBuilder.append("(");
                        stringBuilder.append(nowDateString).append(",");
                        stringBuilder.append(nowDateString).append(",");
                        stringBuilder.append("'").append(UUID.randomUUID().toString()).append("\',");
                        stringBuilder.append("'").append("defaultUser").append("\',");
                        stringBuilder.append(10000).append(",");
                        stringBuilder.append(0).append(",");
                        stringBuilder.append(nowDateString);
                        stringBuilder.append(")");
                    }
                    statement.execute(stringBuilder.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, executorService);
            wait[concurrent] = completableFuture;
        }
        CompletableFuture.allOf(wait).join();
        return System.currentTimeMillis() - start;
    }

    private static long solutionPrepareStatement() throws Exception {
        long start = System.currentTimeMillis();
        ApplicationContext context = new AnnotationConfigApplicationContext(BeanConfigration.class);
        DataSource dataSource = (DataSource)context.getBean("hikariDataSource");
        int concurrent = 100;
        CompletableFuture<Void>[] wait = new CompletableFuture[concurrent];
        while (concurrent-- > 0) {
            final CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
                try (Connection connection = dataSource.getConnection();
                     PreparedStatement preparedStatement = connection.prepareStatement("insert into platform_order (gmt_create, gmt_modified, order_id, user_id, amount, status, gmt_pay) values (?, ?, ?, ?, ?, ?, ?)"))
                {
                    connection.setAutoCommit(false);
                    for (int i = 1; i <= 10000; i ++ ) {
                        Date date = new Date(start);
                        preparedStatement.setDate(1, date);
                        preparedStatement.setDate(2, date);
                        preparedStatement.setString(3, UUID.randomUUID().toString());
                        preparedStatement.setString(4, "defaultUser");
                        preparedStatement.setLong(5, 10000L);
                        preparedStatement.setInt(6, 0);
                        preparedStatement.setDate(7, date);
                        preparedStatement.addBatch();
                    }
                    preparedStatement.executeBatch();
                    connection.commit();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, executorService);
            wait[concurrent] = completableFuture;
        }
        CompletableFuture.allOf(wait).join();
        return System.currentTimeMillis() - start;
    }


    private static long solutionCsv() throws Exception {
        long start = System.currentTimeMillis();
        ApplicationContext context = new AnnotationConfigApplicationContext(BeanConfigration.class);
        DataSource dataSource = (DataSource)context.getBean("hikariDataSource");
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement())
        {
            connection.setAutoCommit(true);
            statement.execute("LOAD DATA INFILE '/platform_order.csv' " +
                    "INTO TABLE platform_order " +
                    "FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '\"' ESCAPED BY '\"'\n" +
                    "LINES TERMINATED BY '\\n'");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return System.currentTimeMillis() - start;
    }

}
