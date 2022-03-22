package main.java.geektime.tdd.args.exceptions;

public class IllegalOptionsException extends RuntimeException{

    private String parameter;

    public IllegalOptionsException(String option) {
        this.parameter = option;
    }

    public String getParameter() {
        return parameter;
    }
}