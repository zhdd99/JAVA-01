学习笔记
1g，4g，8g 内存时不同GC环境下的运行结果：

1g：
G1收集器生成对象次数:15235
cms收集器生成对象次数:14707
并行收集器生成对象次数:15213

4g：
G1收集器生成对象次数:14080
cms收集器生成对象次数:12464
并行收集器生成对象次数:14667

8g：
G1收集器生成对象次数:16306
cms收集器生成对象次数:9380
并行收集器生成对象次数:7515

总结：
一、关于内存大小
    在java程序容量足够的情况下，给进程配置最小的内存，性能最好。
    
二、关于垃圾收集器的效率对比 
    1、并行收集器适用于对吞吐量有高要求，多CPU、对应用响应时间无要求的中、大型应用。
    2、并发收集器适用于对响应时间有高要求，多CPU、对应用响应时间有较高要求的中、大型应用。
    3、G1收集器适用于系统内存较大，同时考虑整体响应时间可Delayed控的大型应用
    
三、对于GCLogAnalysis脚本的建议
    1、脚本中对于数据的处理是单线程，考虑使用多线程达到最大CPU使用率，同时考察在最大CPU使用率的情况下系统的屯粗粮
    2、如果使用多线程 System.currentTimeMillis() 方法在高并发下性能较差，建议使用 Thread.sleep 的方式
    
    
注：不同GC和堆内存的总结写在 README.md 文件中，httpclient 项目为访问 http://localhost:8088/api/hello 的源代码
