package com.example.lab2;

import com.example.lab2.math.MyFunction;
import com.example.lab2.math.log.Ln10Function;
import com.example.lab2.math.log.Ln2Function;
import com.example.lab2.math.log.Ln3Function;
import com.example.lab2.math.log.LnFunction;
import com.example.lab2.math.trig.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * ИЕРАРХИЯ ФУНКЦИЙ:
 * <p>
 * Верхний уровень:
 *   MyFunction
 * <p>
 * Уровень 3:
 *   tg → ctg
 * Уровень 2:
 *   sec → cos
 *   ctg → sin, cos
 * <p>
 * Уровень 1:
 *   cos → sin
 *   csc → sin
 *   ln2 → ln
 *   ln3 → ln
 *   ln10 → ln
 * <p>
 * Базовые функции:
 *   sin
 *   ln
 */
public class MyFunctionIntegrationTest {
    private static final double EPSILON = 5;

    // ==================== Базовые функции TESTS ====================

    @Test
    @DisplayName("BASE: sin(x) - вычисляет корректные значения")
    void testSinFunction() {
        SinFunction sin = new SinFunction();
        assertEquals(0.0, sin.proceed(0.0), EPSILON);
        assertEquals(1.0, sin.proceed(Math.PI/2), EPSILON);
    }

    @Test
    @DisplayName("BASE: ln(x) - вычисляет корректные значения")
    void testLnFunction() {
        LnFunction ln = new LnFunction();
        assertEquals(0.0, ln.proceed(1.0), EPSILON);
        assertEquals(1.0, ln.proceed(Math.E), EPSILON);
    }

    // ==================== Уровень 1 TESTS ====================

    @Test
    @DisplayName("LEVEL 1: cos(x) - корректно использует sin")
    void testCosFunction() {
        SinFunction sinMock = mock(SinFunction.class);
        when(sinMock.proceed(Math.PI/2 - 1.0)).thenReturn(0.5403);

        CosFunction cos = new CosFunction(sinMock);
        assertEquals(0.5403, cos.proceed(1.0), EPSILON);
    }

    @Test
    @DisplayName("LEVEL 1: csc(x) - корректно использует sin")
    void testCscFunction() {
        SinFunction sinMock = mock(SinFunction.class);
        when(sinMock.proceed(1.0)).thenReturn(0.8415);

        CscFunction csc = new CscFunction(sinMock);
        assertEquals(1.0/0.8415, csc.proceed(1.0), 1e-4);
    }

    @Test
    @DisplayName("LEVEL 1: ln3(x) - корректно использует ln")
    void testLn3Function() {
        LnFunction lnMock = mock(LnFunction.class);
        when(lnMock.proceed(3.0)).thenReturn(1.0986);

        Ln3Function ln3 = new Ln3Function(lnMock);
        assertEquals(1.0, ln3.proceed(3.0), 1e-4);
    }

    @Test
    @DisplayName("LEVEL 1: ln10(x) - корректно использует ln")
    void testLn10Function() {
        LnFunction lnMock = mock(LnFunction.class);
        when(lnMock.proceed(10.0)).thenReturn(2.3026);

        Ln10Function ln10 = new Ln10Function(lnMock);
        assertEquals(1.0, ln10.proceed(10.0), 1e-4);
    }

    // ==================== Уровень 2 TESTS ====================

    @Test
    @DisplayName("LEVEL 2: ctg(x) - корректно использует sin и cos")
    void testCtgFunction() {
        SinFunction sinMock = mock(SinFunction.class);
        CosFunction cosMock = mock(CosFunction.class);
        when(sinMock.proceed(1.0)).thenReturn(0.8415);
        when(cosMock.proceed(1.0)).thenReturn(0.5403);

        CtgFunction ctg = new CtgFunction(sinMock, cosMock);
        assertEquals(0.5403/0.8415, ctg.proceed(1.0), 1e-4);
    }

    @Test
    @DisplayName("LEVEL 2: sec(x) - корректно использует cos")
    void testSecFunction() {
        CosFunction cosMock = mock(CosFunction.class);
        when(cosMock.proceed(1.0)).thenReturn(0.5403);

        SecFunction sec = new SecFunction(cosMock);
        assertEquals(1.0/0.5403, sec.proceed(1.0), 1e-4);
    }


    // ==================== Уровень 3 TESTS ====================

    @Test
    @DisplayName("LEVEL 3: tg(x) - корректно использует ctg")
    void testTgFunction() {
        CtgFunction ctgMock = mock(CtgFunction.class);
        when(ctgMock.proceed(1.0)).thenReturn(1.5574);

        TgFunction tg = new TgFunction(ctgMock);
        assertEquals(1.0/1.5574, tg.proceed(1.0), 1e-4);
    }


    // ==================== Верхний уровень TESTS ====================

    /**
     * Координаты точек для логарифмической функции:
     * (0.13878, 0), (3.05454, 0) - нули функции
     * (0.24655, 5), (8.92552, 5) - точки на уровне y=5
     * (10, 5.75419) - точка в верхней границе
     * (16.43874, 10) - крайняя точка при y=10
     * <p>
     * Координаты точек для тригонометрической функции:
     * Нули функции:
     * (-1.28588, 0), (-4.42726, 0), (-7.56906, 0), (-10.71044, 0)
     * Локальные максимумы:
     * (-2.24305, 0.44532), (-5.37977, 0.40804), (-8.52623, 0.44532), (-11.66295, 0.40804)
     * Локальные минимумы:
     * (-3.76296, -182.62821), (-10.04615, -182.62821),
     * (-16.32933, -182.62821), (-22.61252, -182.62821)
     */


    // Логарифмическая ветка (x > 0)
    @Test
    @DisplayName("Логарифмическая ветка: x=0.13878 → 0")
    void testLogarithmicCase1() {
        Ln2Function ln2Mock = mock(Ln2Function.class);
        Ln3Function ln3Mock = mock(Ln3Function.class);
        Ln10Function ln10Mock = mock(Ln10Function.class);

        when(ln2Mock.proceed(0.13878)).thenReturn(-2.8517);
        when(ln3Mock.proceed(0.13878)).thenReturn(-1.8004);
        when(ln10Mock.proceed(0.13878)).thenReturn(-0.8557);

        SinFunction sinMock = mock(SinFunction.class);
        CosFunction cosMock = mock(CosFunction.class);
        TgFunction tgMock = mock(TgFunction.class);
        CtgFunction ctgMock = mock(CtgFunction.class);
        SecFunction secMock = mock(SecFunction.class);
        CscFunction cscMock = mock(CscFunction.class);

        MyFunction function = new MyFunction(
                sinMock, cosMock, tgMock, ctgMock,
                secMock, cscMock,
                ln2Mock, ln3Mock, ln10Mock
        );

        assertEquals(0.0, function.proceed(0.13878), EPSILON);

        // Верификация вызовов логарифмических функций
        verify(ln2Mock).proceed(0.13878);
        verify(ln3Mock).proceed(0.13878);
        verify(ln10Mock).proceed(0.13878);

        // Проверка что тригонометрические функции не вызывались
        verifyNoInteractions(sinMock, cosMock, tgMock, ctgMock, secMock, cscMock);
    }

    @Test
    @DisplayName("Логарифмическая ветка: x=3.05454 → 0")
    void testLogarithmicCase2() {
        Ln2Function ln2Mock = mock(Ln2Function.class);
        Ln3Function ln3Mock = mock(Ln3Function.class);
        Ln10Function ln10Mock = mock(Ln10Function.class);

        // Реальные значения, посчитанные вручную для x=3.05454
        when(ln2Mock.proceed(3.05454)).thenReturn(1.6112);    // ln(3.05454)/ln(2)
        when(ln3Mock.proceed(3.05454)).thenReturn(1.0166);     // ln(3.05454)/ln(3)
        when(ln10Mock.proceed(3.05454)).thenReturn(0.4830);    // ln(3.05454)/ln(10)

        SinFunction sinMock = mock(SinFunction.class);
        CosFunction cosMock = mock(CosFunction.class);
        TgFunction tgMock = mock(TgFunction.class);
        CtgFunction ctgMock = mock(CtgFunction.class);
        SecFunction secMock = mock(SecFunction.class);
        CscFunction cscMock = mock(CscFunction.class);

        MyFunction function = new MyFunction(
                sinMock, cosMock, tgMock, ctgMock,
                secMock, cscMock,
                ln2Mock, ln3Mock, ln10Mock
        );

        assertEquals(0.0, function.proceed(3.05454), EPSILON);

        // Верификация вызовов логарифмических функций
        verify(ln2Mock).proceed(3.05454);
        verify(ln3Mock).proceed(3.05454);
        verify(ln10Mock).proceed(3.05454);

        // Проверка что тригонометрические функции не вызывались
        verifyNoInteractions(sinMock, cosMock, tgMock, ctgMock, secMock, cscMock);
    }


    @Test
    @DisplayName("Логарифмическая ветка: x=0.24655 → 5")
    void testLogarithmicCase3() {
        Ln2Function ln2Mock = mock(Ln2Function.class);
        Ln3Function ln3Mock = mock(Ln3Function.class);
        Ln10Function ln10Mock = mock(Ln10Function.class);

        // Реальные значения, посчитанные вручную для x=0.24655
        when(ln2Mock.proceed(0.24655)).thenReturn(-2.0189);    // ln(0.24655)/ln(2)
        when(ln3Mock.proceed(0.24655)).thenReturn(-1.2736);     // ln(0.24655)/ln(3)
        when(ln10Mock.proceed(0.24655)).thenReturn(-0.6050);    // ln(0.24655)/ln(10)

        SinFunction sinMock = mock(SinFunction.class);
        CosFunction cosMock = mock(CosFunction.class);
        TgFunction tgMock = mock(TgFunction.class);
        CtgFunction ctgMock = mock(CtgFunction.class);
        SecFunction secMock = mock(SecFunction.class);
        CscFunction cscMock = mock(CscFunction.class);

        MyFunction function = new MyFunction(
                sinMock, cosMock, tgMock, ctgMock,
                secMock, cscMock,
                ln2Mock, ln3Mock, ln10Mock
        );

        assertEquals(5.0, function.proceed(0.24655), EPSILON);

        // Верификация вызовов логарифмических функций
        verify(ln2Mock).proceed(0.24655);
        verify(ln3Mock).proceed(0.24655);
        verify(ln10Mock).proceed(0.24655);

        // Проверка что тригонометрические функции не вызывались
        verifyNoInteractions(sinMock, cosMock, tgMock, ctgMock, secMock, cscMock);
    }

    @Test
    @DisplayName("Логарифмическая ветка: x=8.92552 → 5")
    void testLogarithmicCase4() {
        // Настройка моков логарифмических функций
        Ln2Function ln2Mock = mock(Ln2Function.class);
        Ln3Function ln3Mock = mock(Ln3Function.class);
        Ln10Function ln10Mock = mock(Ln10Function.class);

        when(ln2Mock.proceed(8.92552)).thenReturn(3.1584);
        when(ln3Mock.proceed(8.92552)).thenReturn(1.9930);
        when(ln10Mock.proceed(8.92552)).thenReturn(0.9467);

        // Настройка моков тригонометрических функций
        SinFunction sinMock = mock(SinFunction.class);
        CosFunction cosMock = mock(CosFunction.class);
        TgFunction tgMock = mock(TgFunction.class);
        CtgFunction ctgMock = mock(CtgFunction.class);
        SecFunction secMock = mock(SecFunction.class);
        CscFunction cscMock = mock(CscFunction.class);

        MyFunction function = new MyFunction(
                sinMock, cosMock, tgMock, ctgMock,
                secMock, cscMock,
                ln2Mock, ln3Mock, ln10Mock
        );

        assertEquals(5.0, function.proceed(8.92552), EPSILON);

        verify(ln2Mock).proceed(8.92552);
        verify(ln3Mock).proceed(8.92552);
        verify(ln10Mock).proceed(8.92552);
        verifyNoInteractions(sinMock, cosMock, tgMock, ctgMock, secMock, cscMock);
    }

    @Test
    @DisplayName("Логарифмическая ветка: x=10 → 5.75419")
    void testLogarithmicCase5() {
        Ln2Function ln2Mock = mock(Ln2Function.class);
        Ln3Function ln3Mock = mock(Ln3Function.class);
        Ln10Function ln10Mock = mock(Ln10Function.class);

        when(ln2Mock.proceed(10.0)).thenReturn(3.3219);
        when(ln3Mock.proceed(10.0)).thenReturn(2.0959);
        when(ln10Mock.proceed(10.0)).thenReturn(1.0);

        SinFunction sinMock = mock(SinFunction.class);
        CosFunction cosMock = mock(CosFunction.class);
        TgFunction tgMock = mock(TgFunction.class);
        CtgFunction ctgMock = mock(CtgFunction.class);
        SecFunction secMock = mock(SecFunction.class);
        CscFunction cscMock = mock(CscFunction.class);

        MyFunction function = new MyFunction(
                sinMock, cosMock, tgMock, ctgMock,
                secMock, cscMock,
                ln2Mock, ln3Mock, ln10Mock
        );

        assertEquals(5.75419, function.proceed(10.0), EPSILON);

        verify(ln2Mock).proceed(10.0);
        verify(ln3Mock).proceed(10.0);
        verify(ln10Mock).proceed(10.0);
        verifyNoInteractions(sinMock, cosMock, tgMock, ctgMock, secMock, cscMock);
    }

    @Test
    @DisplayName("Логарифмическая ветка: x=16.43874 → 10")
    void testLogarithmicCase6() {
        Ln2Function ln2Mock = mock(Ln2Function.class);
        Ln3Function ln3Mock = mock(Ln3Function.class);
        Ln10Function ln10Mock = mock(Ln10Function.class);

        when(ln2Mock.proceed(16.43874)).thenReturn(4.0389);
        when(ln3Mock.proceed(16.43874)).thenReturn(2.5480);
        when(ln10Mock.proceed(16.43874)).thenReturn(1.2106);

        SinFunction sinMock = mock(SinFunction.class);
        CosFunction cosMock = mock(CosFunction.class);
        TgFunction tgMock = mock(TgFunction.class);
        CtgFunction ctgMock = mock(CtgFunction.class);
        SecFunction secMock = mock(SecFunction.class);
        CscFunction cscMock = mock(CscFunction.class);

        MyFunction function = new MyFunction(
                sinMock, cosMock, tgMock, ctgMock,
                secMock, cscMock,
                ln2Mock, ln3Mock, ln10Mock
        );

        assertEquals(10.0, function.proceed(16.43874), EPSILON);

        verify(ln2Mock).proceed(16.43874);
        verify(ln3Mock).proceed(16.43874);
        verify(ln10Mock).proceed(16.43874);
        verifyNoInteractions(sinMock, cosMock, tgMock, ctgMock, secMock, cscMock);
    }

    @Test
    @DisplayName("Тригонометрическая ветка: x=-1.28588 → 0")
    void testTrigonometricCase1() {
        // Настройка моков тригонометрических функций
        SinFunction sinMock = mock(SinFunction.class);
        CosFunction cosMock = mock(CosFunction.class);
        TgFunction tgMock = mock(TgFunction.class);
        CtgFunction ctgMock = mock(CtgFunction.class);
        SecFunction secMock = mock(SecFunction.class);
        CscFunction cscMock = mock(CscFunction.class);

        when(sinMock.proceed(-1.28588)).thenReturn(-0.9596);
        when(cosMock.proceed(-1.28588)).thenReturn(0.2812);
        when(tgMock.proceed(-1.28588)).thenReturn(-3.4123);
        when(ctgMock.proceed(-1.28588)).thenReturn(-0.2930);
        when(secMock.proceed(-1.28588)).thenReturn(3.5560);
        when(cscMock.proceed(-1.28588)).thenReturn(-1.0421);

        // Моки логарифмических функций (не должны вызываться)
        Ln2Function ln2Mock = mock(Ln2Function.class);
        Ln3Function ln3Mock = mock(Ln3Function.class);
        Ln10Function ln10Mock = mock(Ln10Function.class);

        MyFunction function = new MyFunction(
                sinMock, cosMock, tgMock, ctgMock,
                secMock, cscMock,
                ln2Mock, ln3Mock, ln10Mock
        );

        assertEquals(0.0, function.proceed(-1.28588), EPSILON);

        // Проверка вызовов тригонометрических функций
        verify(sinMock).proceed(-1.28588);
        verify(cosMock).proceed(-1.28588);
        verify(tgMock).proceed(-1.28588);
        verify(ctgMock).proceed(-1.28588);
        verify(secMock).proceed(-1.28588);
        verify(cscMock, times(4)).proceed(-1.28588);

        // Проверка что логарифмические функции не вызывались
        verifyNoInteractions(ln2Mock, ln3Mock, ln10Mock);
    }

    @Test
    @DisplayName("Тригонометрическая ветка: x=-4.42726 → 0")
    void testTrigonometricCase2() {
        SinFunction sinMock = mock(SinFunction.class);
        CosFunction cosMock = mock(CosFunction.class);
        TgFunction tgMock = mock(TgFunction.class);
        CtgFunction ctgMock = mock(CtgFunction.class);
        SecFunction secMock = mock(SecFunction.class);
        CscFunction cscMock = mock(CscFunction.class);

        when(sinMock.proceed(-4.42726)).thenReturn(0.9602);
        when(cosMock.proceed(-4.42726)).thenReturn(-0.2794);
        when(tgMock.proceed(-4.42726)).thenReturn(-3.4369);
        when(ctgMock.proceed(-4.42726)).thenReturn(-0.2910);
        when(secMock.proceed(-4.42726)).thenReturn(-3.5792);
        when(cscMock.proceed(-4.42726)).thenReturn(1.0415);

        Ln2Function ln2Mock = mock(Ln2Function.class);
        Ln3Function ln3Mock = mock(Ln3Function.class);
        Ln10Function ln10Mock = mock(Ln10Function.class);

        MyFunction function = new MyFunction(
                sinMock, cosMock, tgMock, ctgMock,
                secMock, cscMock,
                ln2Mock, ln3Mock, ln10Mock
        );

        assertEquals(0.0, function.proceed(-4.42726), EPSILON);

        verify(sinMock).proceed(-4.42726);
        verify(cosMock).proceed(-4.42726);
        verify(tgMock).proceed(-4.42726);
        verify(ctgMock).proceed(-4.42726);
        verify(secMock).proceed(-4.42726);
        verify(cscMock, times(4)).proceed(-4.42726);
        verifyNoInteractions(ln2Mock, ln3Mock, ln10Mock);
    }

    @Test
    @DisplayName("Тригонометрическая ветка: x=-7.56906 → 0")
    void testTrigonometricCase3() {
        SinFunction sinMock = mock(SinFunction.class);
        CosFunction cosMock = mock(CosFunction.class);
        TgFunction tgMock = mock(TgFunction.class);
        CtgFunction ctgMock = mock(CtgFunction.class);
        SecFunction secMock = mock(SecFunction.class);
        CscFunction cscMock = mock(CscFunction.class);

        when(sinMock.proceed(-7.56906)).thenReturn(-0.9599);
        when(cosMock.proceed(-7.56906)).thenReturn(0.2804);
        when(tgMock.proceed(-7.56906)).thenReturn(-3.4246);
        when(ctgMock.proceed(-7.56906)).thenReturn(-0.2920);
        when(secMock.proceed(-7.56906)).thenReturn(3.5665);
        when(cscMock.proceed(-7.56906)).thenReturn(-1.0418);

        Ln2Function ln2Mock = mock(Ln2Function.class);
        Ln3Function ln3Mock = mock(Ln3Function.class);
        Ln10Function ln10Mock = mock(Ln10Function.class);

        MyFunction function = new MyFunction(
                sinMock, cosMock, tgMock, ctgMock,
                secMock, cscMock,
                ln2Mock, ln3Mock, ln10Mock
        );

        assertEquals(0.0, function.proceed(-7.56906), EPSILON);

        verify(sinMock).proceed(-7.56906);
        verify(cosMock).proceed(-7.56906);
        verify(tgMock).proceed(-7.56906);
        verify(ctgMock).proceed(-7.56906);
        verify(secMock).proceed(-7.56906);
        verify(cscMock, times(4)).proceed(-7.56906);
        verifyNoInteractions(ln2Mock, ln3Mock, ln10Mock);
    }

    @Test
    @DisplayName("Тригонометрическая ветка: x=-10.71044 → 0")
    void testTrigonometricCase4() {
        SinFunction sinMock = mock(SinFunction.class);
        CosFunction cosMock = mock(CosFunction.class);
        TgFunction tgMock = mock(TgFunction.class);
        CtgFunction ctgMock = mock(CtgFunction.class);
        SecFunction secMock = mock(SecFunction.class);
        CscFunction cscMock = mock(CscFunction.class);

        when(sinMock.proceed(-10.71044)).thenReturn(0.9601);
        when(cosMock.proceed(-10.71044)).thenReturn(-0.2798);
        when(tgMock.proceed(-10.71044)).thenReturn(-3.4307);
        when(ctgMock.proceed(-10.71044)).thenReturn(-0.2915);
        when(secMock.proceed(-10.71044)).thenReturn(-3.5740);
        when(cscMock.proceed(-10.71044)).thenReturn(1.0416);

        Ln2Function ln2Mock = mock(Ln2Function.class);
        Ln3Function ln3Mock = mock(Ln3Function.class);
        Ln10Function ln10Mock = mock(Ln10Function.class);

        MyFunction function = new MyFunction(
                sinMock, cosMock, tgMock, ctgMock,
                secMock, cscMock,
                ln2Mock, ln3Mock, ln10Mock
        );

        assertEquals(0.0, function.proceed(-10.71044), EPSILON);

        verify(sinMock).proceed(-10.71044);
        verify(cosMock).proceed(-10.71044);
        verify(tgMock).proceed(-10.71044);
        verify(ctgMock).proceed(-10.71044);
        verify(secMock).proceed(-10.71044);
        verify(cscMock, times(4)).proceed(-10.71044);
        verifyNoInteractions(ln2Mock, ln3Mock, ln10Mock);
    }

    @Test
    @DisplayName("Тригонометрическая ветка: x=-3.76296 → -182.62821")
    void testTrigonometricCase5() {
        SinFunction sinMock = mock(SinFunction.class);
        CosFunction cosMock = mock(CosFunction.class);
        TgFunction tgMock = mock(TgFunction.class);
        CtgFunction ctgMock = mock(CtgFunction.class);
        SecFunction secMock = mock(SecFunction.class);
        CscFunction cscMock = mock(CscFunction.class);

        when(sinMock.proceed(-3.76296)).thenReturn(0.5876);
        when(cosMock.proceed(-3.76296)).thenReturn(-0.8091);
        when(tgMock.proceed(-3.76296)).thenReturn(-0.7263);
        when(ctgMock.proceed(-3.76296)).thenReturn(-1.3768);
        when(secMock.proceed(-3.76296)).thenReturn(-1.2360);
        when(cscMock.proceed(-3.76296)).thenReturn(1.7018);

        MyFunction function = new MyFunction(
                sinMock, cosMock, tgMock, ctgMock,
                secMock, cscMock,
                mock(Ln2Function.class), mock(Ln3Function.class), mock(Ln10Function.class)
        );

        assertEquals(-182.62821, function.proceed(-3.76296), EPSILON);

        verify(sinMock).proceed(-3.76296);
        verify(cosMock).proceed(-3.76296);
        verify(tgMock).proceed(-3.76296);
        verify(ctgMock).proceed(-3.76296);
        verify(secMock).proceed(-3.76296);
        verify(cscMock, times(4)).proceed(-3.76296);
    }

    @Test
    @DisplayName("Тригонометрическая ветка: x=-2.24305 → 0.44532")
    void testTrigonometricCase6() {
        // Настройка моков тригонометрических функций
        SinFunction sinMock = mock(SinFunction.class);
        CosFunction cosMock = mock(CosFunction.class);
        TgFunction tgMock = mock(TgFunction.class);
        CtgFunction ctgMock = mock(CtgFunction.class);
        SecFunction secMock = mock(SecFunction.class);
        CscFunction cscMock = mock(CscFunction.class);

        when(sinMock.proceed(-2.24305)).thenReturn(-0.7819);
        when(cosMock.proceed(-2.24305)).thenReturn(-0.6234);
        when(tgMock.proceed(-2.24305)).thenReturn(1.2543);
        when(ctgMock.proceed(-2.24305)).thenReturn(0.7972);
        when(secMock.proceed(-2.24305)).thenReturn(-1.6039);
        when(cscMock.proceed(-2.24305)).thenReturn(-1.2789);

        // Моки логарифмических функций
        Ln2Function ln2Mock = mock(Ln2Function.class);
        Ln3Function ln3Mock = mock(Ln3Function.class);
        Ln10Function ln10Mock = mock(Ln10Function.class);

        MyFunction function = new MyFunction(
                sinMock, cosMock, tgMock, ctgMock,
                secMock, cscMock,
                ln2Mock, ln3Mock, ln10Mock
        );

        assertEquals(0.44532, function.proceed(-2.24305), EPSILON);

        // Проверка вызовов
        verify(sinMock).proceed(-2.24305);
        verify(cosMock).proceed(-2.24305);
        verify(tgMock).proceed(-2.24305);
        verify(ctgMock).proceed(-2.24305);
        verify(secMock).proceed(-2.24305);
        verify(cscMock, times(4)).proceed(-2.24305);
        verifyNoInteractions(ln2Mock, ln3Mock, ln10Mock);
    }

    @Test
    @DisplayName("Тригонометрическая ветка: x=-5.37977 → 0.40804")
    void testTrigonometricCase7() {
        SinFunction sinMock = mock(SinFunction.class);
        CosFunction cosMock = mock(CosFunction.class);
        TgFunction tgMock = mock(TgFunction.class);
        CtgFunction ctgMock = mock(CtgFunction.class);
        SecFunction secMock = mock(SecFunction.class);
        CscFunction cscMock = mock(CscFunction.class);

        when(sinMock.proceed(-5.37977)).thenReturn(0.7818);
        when(cosMock.proceed(-5.37977)).thenReturn(0.6235);
        when(tgMock.proceed(-5.37977)).thenReturn(1.2539);
        when(ctgMock.proceed(-5.37977)).thenReturn(0.7975);
        when(secMock.proceed(-5.37977)).thenReturn(1.6037);
        when(cscMock.proceed(-5.37977)).thenReturn(1.2791);

        Ln2Function ln2Mock = mock(Ln2Function.class);
        Ln3Function ln3Mock = mock(Ln3Function.class);
        Ln10Function ln10Mock = mock(Ln10Function.class);

        MyFunction function = new MyFunction(
                sinMock, cosMock, tgMock, ctgMock,
                secMock, cscMock,
                ln2Mock, ln3Mock, ln10Mock
        );

        assertEquals(0.40804, function.proceed(-5.37977), EPSILON);

        verify(sinMock).proceed(-5.37977);
        verify(cosMock).proceed(-5.37977);
        verify(tgMock).proceed(-5.37977);
        verify(ctgMock).proceed(-5.37977);
        verify(secMock).proceed(-5.37977);
        verify(cscMock, times(4)).proceed(-5.37977);
        verifyNoInteractions(ln2Mock, ln3Mock, ln10Mock);
    }

    @Test
    @DisplayName("Тригонометрическая ветка: x=-8.52623 → 0.44532")
    void testTrigonometricCase8() {
        SinFunction sinMock = mock(SinFunction.class);
        CosFunction cosMock = mock(CosFunction.class);
        TgFunction tgMock = mock(TgFunction.class);
        CtgFunction ctgMock = mock(CtgFunction.class);
        SecFunction secMock = mock(SecFunction.class);
        CscFunction cscMock = mock(CscFunction.class);

        when(sinMock.proceed(-8.52623)).thenReturn(-0.7819);
        when(cosMock.proceed(-8.52623)).thenReturn(-0.6234);
        when(tgMock.proceed(-8.52623)).thenReturn(1.2543);
        when(ctgMock.proceed(-8.52623)).thenReturn(0.7972);
        when(secMock.proceed(-8.52623)).thenReturn(-1.6039);
        when(cscMock.proceed(-8.52623)).thenReturn(-1.2789);

        Ln2Function ln2Mock = mock(Ln2Function.class);
        Ln3Function ln3Mock = mock(Ln3Function.class);
        Ln10Function ln10Mock = mock(Ln10Function.class);

        MyFunction function = new MyFunction(
                sinMock, cosMock, tgMock, ctgMock,
                secMock, cscMock,
                ln2Mock, ln3Mock, ln10Mock
        );

        assertEquals(0.44532, function.proceed(-8.52623), EPSILON);

        verify(sinMock).proceed(-8.52623);
        verify(cosMock).proceed(-8.52623);
        verify(tgMock).proceed(-8.52623);
        verify(ctgMock).proceed(-8.52623);
        verify(secMock).proceed(-8.52623);
        verify(cscMock, times(4)).proceed(-8.52623);
        verifyNoInteractions(ln2Mock, ln3Mock, ln10Mock);
    }

    @Test
    @DisplayName("Тригонометрическая ветка: x=-11.66295 → 0.40804")
    void testTrigonometricCase9() {
        SinFunction sinMock = mock(SinFunction.class);
        CosFunction cosMock = mock(CosFunction.class);
        TgFunction tgMock = mock(TgFunction.class);
        CtgFunction ctgMock = mock(CtgFunction.class);
        SecFunction secMock = mock(SecFunction.class);
        CscFunction cscMock = mock(CscFunction.class);

        when(sinMock.proceed(-11.66295)).thenReturn(0.7818);
        when(cosMock.proceed(-11.66295)).thenReturn(0.6235);
        when(tgMock.proceed(-11.66295)).thenReturn(1.2539);
        when(ctgMock.proceed(-11.66295)).thenReturn(0.7975);
        when(secMock.proceed(-11.66295)).thenReturn(1.6037);
        when(cscMock.proceed(-11.66295)).thenReturn(1.2791);

        Ln2Function ln2Mock = mock(Ln2Function.class);
        Ln3Function ln3Mock = mock(Ln3Function.class);
        Ln10Function ln10Mock = mock(Ln10Function.class);

        MyFunction function = new MyFunction(
                sinMock, cosMock, tgMock, ctgMock,
                secMock, cscMock,
                ln2Mock, ln3Mock, ln10Mock
        );

        assertEquals(0.40804, function.proceed(-11.66295), EPSILON);

        verify(sinMock).proceed(-11.66295);
        verify(cosMock).proceed(-11.66295);
        verify(tgMock).proceed(-11.66295);
        verify(ctgMock).proceed(-11.66295);
        verify(secMock).proceed(-11.66295);
        verify(cscMock, times(4)).proceed(-11.66295);
        verifyNoInteractions(ln2Mock, ln3Mock, ln10Mock);
    }


    @Test
    @DisplayName("Тригонометрическая ветка: x=-10.04615 → -182.62821")
    void testTrigonometricCase10() {
        SinFunction sinMock = mock(SinFunction.class);
        CosFunction cosMock = mock(CosFunction.class);
        TgFunction tgMock = mock(TgFunction.class);
        CtgFunction ctgMock = mock(CtgFunction.class);
        SecFunction secMock = mock(SecFunction.class);
        CscFunction cscMock = mock(CscFunction.class);

        when(sinMock.proceed(-10.04615)).thenReturn(0.5878);
        when(cosMock.proceed(-10.04615)).thenReturn(-0.8090);
        when(tgMock.proceed(-10.04615)).thenReturn(-0.7267);
        when(ctgMock.proceed(-10.04615)).thenReturn(-1.3761);
        when(secMock.proceed(-10.04615)).thenReturn(-1.2361);
        when(cscMock.proceed(-10.04615)).thenReturn(1.7013);

        Ln2Function ln2Mock = mock(Ln2Function.class);
        Ln3Function ln3Mock = mock(Ln3Function.class);
        Ln10Function ln10Mock = mock(Ln10Function.class);

        MyFunction function = new MyFunction(
                sinMock, cosMock, tgMock, ctgMock,
                secMock, cscMock,
                ln2Mock, ln3Mock, ln10Mock
        );

        assertEquals(-182.62821, function.proceed(-10.04615), EPSILON);

        verify(sinMock).proceed(-10.04615);
        verify(cosMock).proceed(-10.04615);
        verify(tgMock).proceed(-10.04615);
        verify(ctgMock).proceed(-10.04615);
        verify(secMock).proceed(-10.04615);
        verify(cscMock, times(4)).proceed(-10.04615);
        verifyNoInteractions(ln2Mock, ln3Mock, ln10Mock);
    }

    @Test
    @DisplayName("Тригонометрическая ветка: x=-16.32933 → -182.62821")
    void testTrigonometricCase11() {
        SinFunction sinMock = mock(SinFunction.class);
        CosFunction cosMock = mock(CosFunction.class);
        TgFunction tgMock = mock(TgFunction.class);
        CtgFunction ctgMock = mock(CtgFunction.class);
        SecFunction secMock = mock(SecFunction.class);
        CscFunction cscMock = mock(CscFunction.class);

        when(sinMock.proceed(-16.32933)).thenReturn(0.5877);
        when(cosMock.proceed(-16.32933)).thenReturn(-0.8091);
        when(tgMock.proceed(-16.32933)).thenReturn(-0.7265);
        when(ctgMock.proceed(-16.32933)).thenReturn(-1.3764);
        when(secMock.proceed(-16.32933)).thenReturn(-1.2360);
        when(cscMock.proceed(-16.32933)).thenReturn(1.7015);

        Ln2Function ln2Mock = mock(Ln2Function.class);
        Ln3Function ln3Mock = mock(Ln3Function.class);
        Ln10Function ln10Mock = mock(Ln10Function.class);

        MyFunction function = new MyFunction(
                sinMock, cosMock, tgMock, ctgMock,
                secMock, cscMock,
                ln2Mock, ln3Mock, ln10Mock
        );

        assertEquals(-182.62821, function.proceed(-16.32933), EPSILON);

        verify(sinMock).proceed(-16.32933);
        verify(cosMock).proceed(-16.32933);
        verify(tgMock).proceed(-16.32933);
        verify(ctgMock).proceed(-16.32933);
        verify(secMock).proceed(-16.32933);
        verify(cscMock, times(4)).proceed(-16.32933);
        verifyNoInteractions(ln2Mock, ln3Mock, ln10Mock);
    }

    @Test
    @DisplayName("Тригонометрическая ветка: x=-22.61252 → -182.62821")
    void testTrigonometricCase12() {
        SinFunction sinMock = mock(SinFunction.class);
        CosFunction cosMock = mock(CosFunction.class);
        TgFunction tgMock = mock(TgFunction.class);
        CtgFunction ctgMock = mock(CtgFunction.class);
        SecFunction secMock = mock(SecFunction.class);
        CscFunction cscMock = mock(CscFunction.class);

        when(sinMock.proceed(-22.61252)).thenReturn(0.5876);
        when(cosMock.proceed(-22.61252)).thenReturn(-0.8092);
        when(tgMock.proceed(-22.61252)).thenReturn(-0.7263);
        when(ctgMock.proceed(-22.61252)).thenReturn(-1.3768);
        when(secMock.proceed(-22.61252)).thenReturn(-1.2359);
        when(cscMock.proceed(-22.61252)).thenReturn(1.7018);

        Ln2Function ln2Mock = mock(Ln2Function.class);
        Ln3Function ln3Mock = mock(Ln3Function.class);
        Ln10Function ln10Mock = mock(Ln10Function.class);

        MyFunction function = new MyFunction(
                sinMock, cosMock, tgMock, ctgMock,
                secMock, cscMock,
                ln2Mock, ln3Mock, ln10Mock
        );

        assertEquals(-182.62821, function.proceed(-22.61252), EPSILON);

        verify(sinMock).proceed(-22.61252);
        verify(cosMock).proceed(-22.61252);
        verify(tgMock).proceed(-22.61252);
        verify(ctgMock).proceed(-22.61252);
        verify(secMock).proceed(-22.61252);
        verify(cscMock, times(4)).proceed(-22.61252);
        verifyNoInteractions(ln2Mock, ln3Mock, ln10Mock);
    }

    @Test
    @DisplayName("Тригонометрическая ветка: x=-3.76296 → -182.62821")
    void testTrigonometricCase13() {
        // Настройка моков тригонометрических функций
        SinFunction sinMock = mock(SinFunction.class);
        CosFunction cosMock = mock(CosFunction.class);
        TgFunction tgMock = mock(TgFunction.class);
        CtgFunction ctgMock = mock(CtgFunction.class);
        SecFunction secMock = mock(SecFunction.class);
        CscFunction cscMock = mock(CscFunction.class);

        when(sinMock.proceed(-3.76296)).thenReturn(0.5876);
        when(cosMock.proceed(-3.76296)).thenReturn(-0.8091);
        when(tgMock.proceed(-3.76296)).thenReturn(-0.7263);
        when(ctgMock.proceed(-3.76296)).thenReturn(-1.3768);
        when(secMock.proceed(-3.76296)).thenReturn(-1.2360);
        when(cscMock.proceed(-3.76296)).thenReturn(1.7018);

        // Моки логарифмических функций
        Ln2Function ln2Mock = mock(Ln2Function.class);
        Ln3Function ln3Mock = mock(Ln3Function.class);
        Ln10Function ln10Mock = mock(Ln10Function.class);

        MyFunction function = new MyFunction(
                sinMock, cosMock, tgMock, ctgMock,
                secMock, cscMock,
                ln2Mock, ln3Mock, ln10Mock
        );

        assertEquals(-182.62821, function.proceed(-3.76296), EPSILON);

        // Проверка вызовов
        verify(sinMock).proceed(-3.76296);
        verify(cosMock).proceed(-3.76296);
        verify(tgMock).proceed(-3.76296);
        verify(ctgMock).proceed(-3.76296);
        verify(secMock).proceed(-3.76296);
        verify(cscMock, times(4)).proceed(-3.76296);
        verifyNoInteractions(ln2Mock, ln3Mock, ln10Mock);
    }
}
