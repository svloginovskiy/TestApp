package ru.loginovskiy;

public class UnknownOperationException extends Exception{
    String operation;
    public UnknownOperationException(String operation) {
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }
}
