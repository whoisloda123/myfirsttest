package com.liucan.boot;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {
    @BeforeClass
    public static void beforeClass() {
        System.out.println("junit test begin:");
    }

    @AfterClass
    public static void afterClass() {
        System.out.println("junit test end:");
    }

    @Before
    public void before() {
        System.out.println("test fun begin:");
    }

    @After
    public void after() {
        System.out.println("test fun end:");
    }

    @Test
    public void test() {
    }
}
