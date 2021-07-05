## JMH 简介

JMH(Java Microbenchmark Harness)是用于代码微基准测试的工具套件，主要是基于方法层面的基准测试，精度可以达到纳秒级。该工具是由 Oracle 内部实现 JIT 的大牛们编写的，他们应该比任何人都了解 JIT 以及 JVM 对于基准测试的影响。

当你定位到热点方法，希望进一步优化方法性能的时候，就可以使用 JMH 对优化的结果进行量化的分析。

JMH 比较典型的应用场景如下：

1. 想准确地知道某个方法需要执行多长时间，以及执行时间和输入之间的相关性
2. 对比接口不同实现在给定条件下的吞吐量
3. 查看多少百分比的请求在多长时间内完成

## JMH使用

以测试字符串拼接性能为例子。

方式一：IDEA直接测试

### 2.1 引入依赖

```xml
		<dependency>
            <groupId>org.openjdk.jmh</groupId>
            <artifactId>jmh-core</artifactId>
            <version>1.32</version>
        </dependency>
        <dependency>
            <groupId>org.openjdk.jmh</groupId>
            <artifactId>jmh-generator-annprocess</artifactId>
            <version>1.32</version>
        </dependency>
```

### 2.2 测试代码

```java
package co.dianjiu.jhm.demo;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @author DianJiu
 * @website https://dianjiu.co
 * @email dianjiu@dianjiu.cc
 * @date 2021/7/2 15:49
 * @desc TODO
 */
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 5)
@Threads(4)
@Fork(1)
@State(value = Scope.Benchmark)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class StringConnectTest {

    @Param(value = {"10", "50", "100"})
    private int length;

    @Benchmark
    public void testStringAdd(Blackhole blackhole) {
        String a = "";
        for (int i = 0; i < length; i++) {
            a += i;
        }
        blackhole.consume(a);
    }

    @Benchmark
    public void testStringBuilderAdd(Blackhole blackhole) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(i);
        }
        blackhole.consume(sb.toString());
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(StringConnectTest.class.getSimpleName())
                .result("result.json")
                .resultFormat(ResultFormatType.JSON).build();
        new Runner(opt).run();
    }
}
```

### 2.3 执行测试

环境信息

> JMH version: 1.32
>
> VM version: JDK 11.0.10, OpenJDK 64-Bit Server VM, 11.0.10+9-LTS
>
> VM invoker: C:\Program Files\Zulu\zulu-11\bin\java.exe
>
> VM options: -javaagent:C:\Program Files\JetBrains\IntelliJ IDEA 2021.1\lib\idea_rt.jar=55934:C:\Program Files\JetBrains\IntelliJ IDEA 2021.1\bin -Dfile.encoding=UTF-8
>
> Blackhole mode: full + dont-inline hint
>
> Warmup: 3 iterations, 1 s each
>
> Measurement: 5 iterations, 5 s each
>
> Timeout: 10 min per iteration
>
> Threads: 4 threads, will synchronize iterations
>
> Benchmark mode: Average time, time/op
>
> Benchmark: co.dianjiu.jhm.demo.StringConnectTest.testStringAdd
>
> Parameters: (length = 10)

预热测试

> Run progress: 0.00% complete, ETA 00:02:48
>
> Fork: 1 of 1
>
> Warmup Iteration   1: 482.494 ±(99.9%) 162.805 ns/op
>
> Warmup Iteration   2: 334.590 ±(99.9%) 89.881 ns/op
>
> Warmup Iteration   3: 349.968 ±(99.9%) 85.414 ns/op

测量迭代

> Iteration   1: 395.861 ±(99.9%) 25.649 ns/op
> Iteration   2: 409.620 ±(99.9%) 26.549 ns/op
> Iteration   3: 389.641 ±(99.9%) 43.061 ns/op
> Iteration   4: 265.158 ±(99.9%) 31.658 ns/op
> Iteration   5: 278.007 ±(99.9%) 12.114 ns/op

测试结果

> Benchmark                              								 (length)  	Mode  	Cnt     		Score      		Error  Units
> StringConnectTest.testStringAdd               		10  				avgt    	5  			 347.657 ±  269.428  ns/op
> StringConnectTest.testStringAdd               		50  				avgt    	5  			 1670.355 ±  136.749  ns/op
> StringConnectTest.testStringAdd              		100  			   avgt    	5  			 5201.457 ±  310.844  ns/op
> StringConnectTest.testStringBuilderAdd        10  				 avgt    	5   			148.904 ±    5.196  ns/op
> StringConnectTest.testStringBuilderAdd        50  				 avgt    	5  			 1371.516 ± 1243.762  ns/op
> StringConnectTest.testStringBuilderAdd       100 				 avgt    	5  			1149.044 ±  243.874  ns/op

方式二：Jar包测试

```xml
	<build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-shade-plugin</artifactId>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>shade</goal>
                        </goals>
                        <configuration>
                            <finalName>jmh-demo</finalName>
                            <transformers>
                                <transformer
                                        implementation="org.apache.maven.plugins.shade.resource.AppendingTransformer">
                                    <resource>META-INF/spring.handlers</resource>
                                </transformer>
                                <transformer
                                        implementation="org.springframework.boot.maven.PropertiesMergingResourceTransformer">
                                    <resource>META-INF/spring.factories</resource>
                                </transformer>
                                <transformer
                                        implementation="org.apache.maven.plugins.shade.resource.AppendingTransformer">
                                    <resource>META-INF/spring.schemas</resource>
                                </transformer>
                                <transformer
                                        implementation="org.apache.maven.plugins.shade.resource.ServicesResourceTransformer" />
                                <transformer
                                        implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                                    <mainClass>co.dianjiu.jhm.Application</mainClass>
                                </transformer>
                            </transformers>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
```

```bash
mvn clean install
java -jar target/jmh-demo.jar StringConnectTest
```

## JMH 注解

为了能够更好地使用 JMH 的各项功能，下面对 JMH 的基本概念进行讲解：

### @BenchmarkMode

用来配置 Mode 选项，可用于类或者方法上，这个注解的 value 是一个数组，可以把几种 Mode 集合在一起执行，如：`@BenchmarkMode({Mode.SampleTime, Mode.AverageTime})`，还可以设置为 `Mode.All`，即全部执行一遍。

1. Throughput：整体吞吐量，每秒执行了多少次调用，单位为 `ops/time`
2. AverageTime：用的平均时间，每次操作的平均时间，单位为 `time/op`
3. SampleTime：随机取样，最后输出取样结果的分布
4. SingleShotTime：只运行一次，往往同时把 Warmup 次数设为 0，用于测试冷启动时的性能
5. All：上面的所有模式都执行一次

### @State

通过 State 可以指定一个对象的作用范围，JMH 根据 scope 来进行实例化和共享操作。@State 可以被继承使用，如果父类定义了该注解，子类则无需定义。由于 JMH 允许多线程同时执行测试，不同的选项含义如下：

1. Scope.Benchmark：所有测试线程共享一个实例，测试有状态实例在多线程共享下的性能
2. Scope.Group：同一个线程在同一个 group 里共享实例
3. Scope.Thread：默认的 State，每个测试线程分配一个实例

### @OutputTimeUnit

为统计结果的时间单位，可用于类或者方法注解

### @Warmup

预热所需要配置的一些基本测试参数，可用于类或者方法上。一般前几次进行程序测试的时候都会比较慢，所以要让程序进行几轮预热，保证测试的准确性。参数如下所示：

1. iterations：预热的次数
2. time：每次预热的时间
3. timeUnit：时间的单位，默认秒
4. batchSize：批处理大小，每次操作调用几次方法

> **为什么需要预热？**
>  因为 JVM 的 JIT 机制的存在，如果某个函数被调用多次之后，JVM 会尝试将其编译为机器码，从而提高执行速度，所以为了让 benchmark 的结果更加接近真实情况就需要进行预热。
>

### @Measurement

实际调用方法所需要配置的一些基本测试参数，可用于类或者方法上，参数和 `@Warmup` 相同。

### @Threads

每个进程中的测试线程，可用于类或者方法上。

### @Fork

进行 fork 的次数，可用于类或者方法上。如果 fork 数是 2 的话，则 JMH 会 fork 出两个进程来进行测试。

### @Param

指定某项参数的多种情况，特别适合用来测试一个函数在不同的参数输入的情况下的性能，只能作用在字段上，使用该注解必须定义 @State 注解。

在介绍完常用的注解后，让我们来看下 JMH 有哪些陷阱。

## JMH 陷阱

在使用 JMH 的过程中，一定要避免一些陷阱。

比如 JIT 优化中的死码消除，比如以下代码：

```text
@Benchmark
public void testStringAdd(Blackhole blackhole) {
    String a = "";
    for (int i = 0; i < length; i++) {
        a += i;
    }
}
```

JVM 可能会认为变量 `a` 从来没有使用过，从而进行优化把整个方法内部代码移除掉，这就会影响测试结果。

JMH 提供了两种方式避免这种问题，一种是将这个变量作为方法返回值 return a，一种是通过 Blackhole 的 consume 来避免 JIT 的优化消除。

其他陷阱还有常量折叠与常量传播、永远不要在测试中写循环、使用 Fork 隔离多个测试方法、方法内联、伪共享与缓存行、分支预测、多线程测试等，感兴趣的可以阅读 `https://github.com/lexburner/JMH-samples` 了解全部的陷阱。