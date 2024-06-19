package com.example.dayonetest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LombokTestDataTest {
    @Test
    public void testDatTest(){
        TestData testData = new TestData();
        testData.setName("yoojung");

        Assertions.assertEquals("yoojung", testData.getName());
    }
}
