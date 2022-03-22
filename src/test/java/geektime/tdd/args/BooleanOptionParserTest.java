package test.java.geektime.tdd.args;

import main.java.geektime.tdd.args.OptionParsers;
import main.java.geektime.tdd.args.exceptions.TooManyArgumentsException;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static test.java.geektime.tdd.args.OptionParsersTest.option;

public class BooleanOptionParserTest {
    // sad path:
    @Test
    public void should_not_accept_extra_argument_for_boolean_option() {
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class,
                () -> OptionParsers.bool().parse(asList("-l", "t"), option("l")));
        assertEquals("l", e.getOption());
    }

    // default:
    @Test
    public void should_set_default_value_to_false_if_option_not_present() {
        assertFalse(OptionParsers.bool().parse(asList(), option("l")));
    }

    @Test//happy path
    public void should_set_value_to_true_if_option_present() {
        assertTrue(OptionParsers.bool().parse(asList("-l"), option("l")));
    }

}
