package com.example.dayonetest;

import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class MyCalculatorRepeatableTest {
  @DisplayName("덧셈을 단순 반복하는 테스트")
  @RepeatedTest(5)
  public void repeatedAddTest() {
    // arrange - 준비
    MyCalculator myCalculator = new MyCalculator();
    // act - 행동
    myCalculator.add(10.0);
    // assert - 검증
    Assertions.assertEquals(10.0, myCalculator.getResult());
  }

  @DisplayName("덧셈을 5번 반복하는 테스트")
  @ParameterizedTest
  @MethodSource("parameterizedTestParameters")
  public void parameterizedTest(Double addValue, Double expectValue) {
    // arrange - 준비
    MyCalculator myCalculator = new MyCalculator();
    // act - 행동
    myCalculator.add(addValue);
    // assert - 검증
    Assertions.assertEquals(expectValue, myCalculator.getResult());
  }

  public static Stream<Arguments> parameterizedTestParameters() {
    return Stream.of(
        Arguments.of(10.0, 10.0),
        Arguments.of(8.0, 8.0),
        Arguments.of(4.0, 4.0),
        Arguments.of(16.0, 16.0));
  }

  @DisplayName("파라미터를 넣으며 사친연산 2번 반복 테스 ")
  @ParameterizedTest
  @MethodSource("parameterizedComplicatedCalculateTestParameters")
  public void parameterizedComplicatedCalculateTest(
      Double addValue,
      Double minusValue,
      Double multiplyValue,
      Double divideValue,
      Double expectValue) {
    MyCalculator myCalculator = new MyCalculator(0.0);

    // when
    Double result =
        myCalculator
            .add(addValue)
            .minus(minusValue)
            .multiply(multiplyValue)
            .divide(divideValue)
            .getResult();

    // then
    Assertions.assertEquals(expectValue, result);
  }

  public static Stream<Arguments> parameterizedComplicatedCalculateTestParameters() {
    return Stream.of(Arguments.of(10.0, 4.0, 2.0, 3.0, 4.0), Arguments.of(4.0, 2.0, 4.0, 4.0, 2.0));
  }
}
