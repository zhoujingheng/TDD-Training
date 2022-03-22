package main.java.geektime.tdd.args;

import main.java.geektime.tdd.args.exceptions.IllegalOptionsException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Args {
    public static <T> T parse(Class<T> optionsClass, String... args) {
        try {
            List<String> arguments = Arrays.asList(args);
            Constructor<?> constructor = optionsClass.getDeclaredConstructors()[0];
            constructor.setAccessible(true);

            Object[] values = Arrays.stream(constructor.getParameters()).map(it -> parseOptions(arguments, it)).toArray();
            return (T) constructor.newInstance(values);
        } catch (IllegalOptionsException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Object parseOptions(List<String> arguments, Parameter parameter) {
        if (!parameter.isAnnotationPresent(Option.class)) throw new IllegalOptionsException(parameter.getName());
        return PARSERS.get(parameter.getType()).parse(arguments, parameter.getAnnotation(Option.class));
    }

    private static Map<Class<?>, OptionParser> PARSERS = Map.of(
            boolean.class, OptionParsers.bool(),
            int.class, OptionParsers.unary(0, Integer::parseInt),
            String.class, OptionParsers.unary("", String::valueOf),
            String[].class,OptionParsers.list(String[]::new,String::valueOf),
            Integer[].class,OptionParsers.list(Integer[]::new,Integer::parseInt));

}
