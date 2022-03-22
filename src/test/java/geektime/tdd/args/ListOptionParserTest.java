package test.java.geektime.tdd.args;

import main.java.geektime.tdd.args.exceptions.IllegalValueException;
import main.java.geektime.tdd.args.OptionParsers;
import org.junit.Test;

import java.util.function.Function;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static test.java.geektime.tdd.args.OptionParsersTest.option;

public class ListOptionParserTest {
    //TODO: -g "this" "is" {"this", is"}
    @Test
    public void should_parse_list_value() {
        assertArrayEquals(new String[]{"this", "is"}, OptionParsers.list(String[]::new, String::valueOf).parse(asList("-g", "this", "is"), option("g")));
    }

    @Test
    public void should_not_treat_negative_int_as_flag() {
        assertArrayEquals(new Integer[]{-1, -2}, OptionParsers.list(Integer[]::new, Integer::parseInt).parse(asList("-g", "-1", "-2"), option("g")));
    }

    // TODO: default value []
    @Test
    public void should_use_empty_array_as_default_value() {
        String[] value = OptionParsers.list(String[]::new, String::valueOf).parse(asList(), option("g"));
        assertEquals(0, value.length);
    }

    // TODO: -d a throw exception
    @Test
    public void should_throw_exception_if_value_parser_cant_parse_value() {
        Function<String, String> parser = (it) -> {
            throw new RuntimeException();
        };
        IllegalValueException e = assertThrows(IllegalValueException.class, () -> OptionParsers.list(String[]::new, parser)
                .parse(asList("-g", "this", "is"), option("g")));
        assertEquals("g", e.getOption());
        assertEquals("this", e.getValue());
    }
}
