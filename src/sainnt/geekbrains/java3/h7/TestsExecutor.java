package sainnt.geekbrains.java3.h7;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TestsExecutor {
    public static void main(String[] args) {
        try{
            start("sainnt.geekbrains.java3.h7.Arithmetics");
        } catch (ClassNotFoundException classNotFoundException) {
            throw new RuntimeException(classNotFoundException);
        }

    }

    private static void start(String className) throws ClassNotFoundException {
        Class<?> testedClass = Class.forName(className);
        ArrayList<Method> methods = new   ArrayList<> (Arrays.asList( testedClass.getDeclaredMethods()));
        performOnOneAnnotation(methods, BeforeSuite.class);
        methods.stream()
                .filter(t->t.isAnnotationPresent(Test.class))
                .sorted(Comparator.comparingInt(o -> o.getAnnotation(Test.class).priority()))
                .forEachOrdered(t->{
                    t.setAccessible(true);
                    try {
                        t.invoke(null, 1, 2);
                    }
                    catch (IllegalAccessException | InvocationTargetException e){
                        throw new RuntimeException(e);
                    }
                    t.setAccessible(false);
        });
        performOnOneAnnotation(methods,AfterSuite.class);
    }

    private static void performOnOneAnnotation(ArrayList<Method> methods, Class< ? extends  Annotation> annotation){
        List<Method> suitableMethods = methods.stream().
                filter(t -> t.isAnnotationPresent(annotation))
                .collect(Collectors.toList());
        if (suitableMethods.size() > 1)
            throw new RuntimeException();
        else if (suitableMethods.size() == 1)
        {
            Method beforeAnnotatedMethod = suitableMethods.get(0);
            beforeAnnotatedMethod.setAccessible(true);
            try{
                beforeAnnotatedMethod.invoke(null);
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            beforeAnnotatedMethod.setAccessible(false);
        }
    }

}
