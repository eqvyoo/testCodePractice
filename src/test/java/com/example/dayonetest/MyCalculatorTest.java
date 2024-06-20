package com.example.dayonetest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MyCalculatorTest {

    @Test
    @DisplayName("My Calculator 덧셈 테스트")
    void addTest() {
        // arrange - 준비
        MyCalculator myCalculator = new MyCalculator();
        // act - 행동
        myCalculator.add(10.0);
        // assert - 검증
        Assertions.assertEquals(10.0, myCalculator.getResult());
    }

    @Test
    @DisplayName("My Calculator 뺄셈 테스트")
    void minusTest() {
        // gwt
        //given 준비
        MyCalculator myCalculator = new MyCalculator(10.0);
        // when 행동
        myCalculator.minus(5.0);
        // then 검증/비교
        Assertions.assertEquals(5.0, myCalculator.getResult());
    }

    @Test
    @DisplayName("My Calculator 곱셈 테스트")
    void multiply() {
        MyCalculator myCalculator = new MyCalculator(2.0);
        myCalculator.multiply(2.0);
        Assertions.assertEquals(4.0, myCalculator.getResult());
    }

    @Test
    @DisplayName("My Calculator 나눗셈 테스트")
    void divide() {
        MyCalculator myCalculator = new MyCalculator(10.0);
        myCalculator.divide(2.0);
        Assertions.assertEquals(5.0, myCalculator.getResult());
    }

    @Test
    @DisplayName("My Calculator 사칙연산 테스트")
    void complicatedCalculateTest(){
        // given
        MyCalculator myCalculator = new MyCalculator(0.0);

        // when
        Double result = myCalculator
                .add(10.0)
                .minus(4.0)
                .multiply(2.0)
                .divide(3.0)
                .getResult();

        // then
        Assertions.assertEquals(4.0, result);
    }

    @Test
    @DisplayName("My Calculator 0으로 나누었을 때에는 ZeroDivisionException을 발생시켜야함")
    void divideZeroTest(){
        // given
        MyCalculator myCalculator = new MyCalculator(10.0);

        // when & then
        Assertions.assertThrows(MyCalculator.ZeroDivisionException.class, () -> myCalculator.divide(0.0));
    }
}