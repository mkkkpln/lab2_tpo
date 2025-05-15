package com.example.lab2.aop;

import com.example.lab2.math.AFunction;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@Aspect
public class FunctionInterceptorAspect {

    // Добавляем флаг для отслеживания вложенных вызовов
    private static final ThreadLocal<Boolean> isNestedCall = ThreadLocal.withInitial(() -> false);


    @Pointcut(value = "execution(public Double com.example.lab2.math.AFunction+.proceed(Double)) && args(x) && target(function)", argNames = "x,function")
    public void proceedExecution(Double x, AFunction function) {}

    @Around(value = "proceedExecution(x, function)", argNames = "joinPoint,x,function")
    public Object intercept(ProceedingJoinPoint joinPoint, Double x, AFunction function) throws Throwable {
        // Если это вложенный вызов (например, Cos вызывает Sin), пропускаем логирование
        if (isNestedCall.get()) {
            return joinPoint.proceed();
        }

        // Устанавливаем флаг, чтобы избежать рекурсии
        isNestedCall.set(true);
        try {
            Object result = joinPoint.proceed();
            logToFile(function, x, (Double) result);

            // Логируем окрестности точки
            logNeighborhood(function, x);
            return result;
        } finally {
            // Сбрасываем флаг
            isNestedCall.set(false);
        }
    }


    private void logNeighborhood(AFunction function, double x) {
        try {
            isNestedCall.set(true);

            // Логируем точки слева (x - DELTA, x - 2*DELTA, x - 3*DELTA)
            for (int i = 1; i <= 3; i++) {
                double xLeft = x - i * function.delta();
                double yLeft = function.proceed(xLeft);
                logToFile(function, xLeft, yLeft);
            }

            // Логируем точки справа (x + DELTA, x + 2*DELTA, x + 3*DELTA)
            for (int i = 1; i <= 3; i++) {
                double xRight = x + i * function.delta();
                double yRight = function.proceed(xRight);
                logToFile(function, xRight, yRight);
            }

            isNestedCall.set(false);
        } catch (Throwable t) {
            System.err.println("Error calculating neighborhood for x=" + x + ": " + t.getMessage());
        }
    }

    private void logToFile(AFunction function, double x, double result) {
        String srcPath = "src/main/resources/csv/";
        String className = srcPath + function.getClass().getSimpleName().toLowerCase().replace("function", "") + ".csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(className, true))) {
            writer.println(x + "," + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
