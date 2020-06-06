package com.demo.serialcode.service;

import com.demo.serialcode.moduleinfo.SerailCodeModuleBeanNames;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 测试：算法生成器
 *
 * @author shenhongjun
 * @since 2020/5/26
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SerialCodeGeneratorTest {

    /**
     * 序列号缓存器redis 实现
     *
     * 序列号redis缓存器
     */
    @Resource(name = SerailCodeModuleBeanNames.SerialCodeModuleSerialCodeCacheServiceRedisImpl)
    private AbstractSerialCodeCacheService redisSerialCodeGenerator;

    private int length = 0;
    private int waitMin = 0;

    /**
     * 前置执行器
     */
    @Before
    public void before() {
        this.length = 100;
        this.waitMin = 5;
    }

    /**
     * 后置执行器
     */
    @After
    public void after() {

    }

    /*
     * 单线程
     * 串行循环{length}次执行3个规则:
     * 1、Normal 常规序列号的生成器,格式:{codeType}-{infix}-{自动生成策略},例如:DRV-SHJ-000000002w
     * 2、CalendarInfix 指定日期格式作为自定义中缀的序列号生成器,格式:{codeType}-{infix}-{自动生成策略},例如:ORD-20200602-0000000032
     * 3、CalendarInfixTimestampSufix 日期格式作为自定义中缀，使用（生成序列号时的毫秒时间值 ×1000 + 自增3位数字）,格式:{codeType}-{infix}-{自动生成策略},例如:PRO-20200602-fnzhhz580a
     */

    /**
     * normal 串行-常规序列号的生成器
     * 串行程循环{length}次，每次获取1个序列号
     * 执行的规则为：Normal，详细参见redis的key为TYP:DRV
     * 输出的内容有：
     * 1、序列号
     * 2、序列号数量
     * 3、不重复数量
     * 返回样例:DRV-SHJ-000000002w
     */
    @Ignore
    @Test
    public void testNormalSingle() {
        List<String> stringLinkedList = new LinkedList<>();
        String singleSerialCode = "";

        for (int i = 0; i < this.length; i++) {
            singleSerialCode = redisSerialCodeGenerator.fetchSerialCode("DRV");
            stringLinkedList.add(singleSerialCode);
        }

        System.out.println("序列号：" + stringLinkedList);
        System.out.println("序列号数量：" + stringLinkedList.size());
        // 查重
        System.out.println("不重复数量：" + new HashSet<String>(stringLinkedList).size());

        assert stringLinkedList.size() == this.length;
    }

    /**
     * calendarInfix 串行-指定日期格式作为自定义中缀的序列号生成器
     * 串行程循环{length}次，每次获取1个序列号
     * 执行的规则为：CalendarInfix，详细参见redis的key为TYP:ORD
     * 输出的内容有：
     * 1、序列号
     * 2、序列号数量
     * 3、不重复数量
     * 返回样例:ORD-20200602-0000000032
     */
    @Ignore
    @Test
    public void testCalendarInfixSingle() {
        List<String> stringLinkedList = new LinkedList<>();
        String singleSerialCode = "";

        for (int i = 0; i < this.length; i++) {
            singleSerialCode = redisSerialCodeGenerator.fetchSerialCode("ORD");
            stringLinkedList.add(singleSerialCode);
        }

        System.out.println("序列号：" + stringLinkedList);
        System.out.println("序列号数量：" + stringLinkedList.size());
        // 查重
        System.out.println("不重复数量：" + new HashSet<String>(stringLinkedList).size());

        assert stringLinkedList.size() == this.length;
    }

    /**
     * calendarInfixTimestampSufix 串行-日期格式作为自定义中缀，使用（生成序列号时的毫秒时间值 ×1000 + 自增3位数字）
     * 串行程循环{length}次，每次获取1个序列号
     * 执行的规则为：CalendarInfix，详细参见redis的key为TYP:PRO
     * 输出的内容有：
     * 1、序列号
     * 2、序列号数量
     * 3、不重复数量
     * 返回样例:PRO-20200602-fnzhhz580a
     */
    @Ignore
    @Test
    public void testCalendarInfixTimestampSufixSingle() {
        List<String> stringLinkedList = new LinkedList<>();
        String singleSerialCode = "";

        for (int i = 0; i < this.length; i++) {
            singleSerialCode = redisSerialCodeGenerator.fetchSerialCode("PRO");
            stringLinkedList.add(singleSerialCode);
        }

        System.out.println("序列号：" + stringLinkedList);
        System.out.println("序列号数量：" + stringLinkedList.size());
        // 查重
        System.out.println("不重复数量：" + new HashSet<String>(stringLinkedList).size());

        assert stringLinkedList.size() == this.length;
    }

    /*
     * 多线程
     * 并行起{length}个线程执行3个规则:
     * 1、Normal 常规序列号的生成器,格式:{codeType}-{infix}-{自动生成策略},例如:DRV-SHJ-000000002w
     * 2、CalendarInfix 指定日期格式作为自定义中缀的序列号生成器,格式:{codeType}-{infix}-{自动生成策略},例如:ORD-20200602-0000000032
     * 3、CalendarInfixTimestampSufix 日期格式作为自定义中缀，使用（生成序列号时的毫秒时间值 ×1000 + 自增3位数字）,格式:{codeType}-{infix}-{自动生成策略},例如:PRO-20200602-fnzhhz580a
     */

    /**
     * normal 并行-常规序列号的生成器
     * 并行起{length}个线程，每个线程获取1个序列号
     * 执行的规则为：Normal，详细参见redis的key为TYP:DRV
     * 输出的内容有：
     * 1、序列号
     * 2、序列号数量
     * 3、不重复数量
     * 返回样例:DRV-SHJ-000000002w
     */
//    @Ignore
    @Test
    public void testNormalMany() throws InterruptedException {
        List<String> stringLinkedList = Collections.synchronizedList(new LinkedList<>());

        final CountDownLatch countDownLatch = new CountDownLatch(this.length);
        ExecutorService executorService = Executors.newFixedThreadPool(this.length);
        for (int i = 0; i < this.length; i++) {
            executorService.execute(() -> {
                if (stringLinkedList.add(redisSerialCodeGenerator.fetchSerialCode("DRV"))) {
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await(this.waitMin, TimeUnit.MINUTES);
        System.out.println("序列号：" + stringLinkedList);
        System.out.println("序列号数量：" + stringLinkedList.size());
        // 查重
        System.out.println("不重复数量：" + new HashSet<String>(stringLinkedList).size());
        assert stringLinkedList.size() == this.length;
    }

    /**
     * calendarInfix 并行-指定日期格式作为自定义中缀的序列号生成器
     * 并行起{length}个线程，每个线程获取1个序列号
     * 执行的规则为：CalendarInfix，详细参见redis的key为TYP:ORD
     * 输出的内容有：
     * 1、序列号
     * 2、序列号数量
     * 3、不重复数量
     * 返回样例:ORD-20200602-0000000032
     */
//    @Ignore
    @Test
    public void testCalendarInfixMany() throws InterruptedException {
        List<String> stringLinkedList = Collections.synchronizedList(new LinkedList<>());

        final CountDownLatch countDownLatch = new CountDownLatch(this.length);
        ExecutorService executorService = Executors.newFixedThreadPool(this.length);
        for (int i = 0; i < this.length; i++) {
            executorService.execute(() -> {
                if (stringLinkedList.add(redisSerialCodeGenerator.fetchSerialCode("ORD"))) {
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await(this.waitMin, TimeUnit.MINUTES);
        System.out.println("序列号：" + stringLinkedList);
        System.out.println("序列号数量：" + stringLinkedList.size());
        // 查重
        System.out.println("不重复数量：" + new HashSet<String>(stringLinkedList).size());
        assert stringLinkedList.size() == this.length;
    }

    /**
     * calendarInfixTimestampSufix 并行-日期格式作为自定义中缀，使用（生成序列号时的毫秒时间值 ×1000 + 自增3位数字）
     * 并行起{length}个线程，每个线程获取1个序列号
     * 执行的规则为：CalendarInfix，详细参见redis的key为TYP:PRO
     * 输出的内容有：
     * 1、序列号
     * 2、序列号数量
     * 3、不重复数量
     * 返回样例:PRO-20200602-fnzhhz580a
     */
//    @Ignore
    @Test
    public void testCalendarInfixTimestampSufixMany() throws InterruptedException {
        List<String> stringLinkedList = Collections.synchronizedList(new LinkedList<>());

        final CountDownLatch countDownLatch = new CountDownLatch(this.length);
        ExecutorService executorService = Executors.newFixedThreadPool(this.length);
        for (int i = 0; i < this.length; i++) {
            executorService.execute(() -> {
                if (stringLinkedList.add(redisSerialCodeGenerator.fetchSerialCode("PRO"))) {
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await(this.waitMin, TimeUnit.MINUTES);
        System.out.println("序列号：" + stringLinkedList);
        System.out.println("序列号数量：" + stringLinkedList.size());
        // 查重
        System.out.println("不重复数量：" + new HashSet<String>(stringLinkedList).size());
        assert stringLinkedList.size() == this.length;
    }

}
