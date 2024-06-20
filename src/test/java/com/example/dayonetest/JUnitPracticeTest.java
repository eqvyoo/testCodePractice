package com.example.dayonetest;

import org.junit.jupiter.api.*;

import java.util.List;

// @display name > @display name generation
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class JUnitPracticeTest {
    @Test
    @DisplayName("Assert equals 메소드 테스트")
    public void assert_equals_test(){
        String expect = "Something";
        String actual = "Something";
        Assertions.assertEquals(expect, actual);
    }

    @Test
    @DisplayName("assert Not Equals 메소드 테스트")
    public void assertNotEqualsTest(){
        String expect = "Something";
        String actual = "Hi";

        Assertions.assertNotEquals(expect, actual);
    }

    @Test
    @DisplayName("assert True 메소드 테스트")
    public void assetTrueTest(){
        Integer a = 10;
        Integer b = 10;

        Assertions.assertTrue(a.equals(b));
    }

    @Test
    @DisplayName("assert false 메소드 테스트")
    public void assertFalseTest(){
        Integer a = 10;
        Integer b = 20;

        Assertions.assertFalse(a.equals(b));
    }

    @Test
    @DisplayName("assert throws 메소드 테스트")
    public void assertThrowsTest(){
        Assertions.assertThrows(RuntimeException.class, () -> {
            throw new RuntimeException("임의 에러");
        });
    }

    @Test
    @DisplayName("assert not null 메소드 테스트")
    public void assertNotNullTest(){
        String value = "Hi";
        Assertions.assertNotNull(value);
    }

    @Test
    @DisplayName("assert iterable 메소드 테스트")
    public void assertIterableEquals(){
        List<Integer> list1 = List.of(1,2);
        List<Integer> list2 = List.of(1,2);

        Assertions.assertIterableEquals(list1, list2);
    }

    @Test
    @DisplayName("assert all 메소드 테스트")
    public void assertAllTest(){
        String expect = "Something";
        String actual = "Something";

        List<Integer> list1 = List.of(1,2);
        List<Integer> list2 = List.of(1,2);

        Assertions.assertAll("Assert all", List.of(
                () -> {Assertions.assertEquals(expect, actual);},
                () -> {Assertions.assertIterableEquals(list1, list2);}
        ));
    }

}
