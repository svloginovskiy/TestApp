package ru.loginovskiy;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.DoubleStream;

public class Calculator {

    private static final String DELIM = " ";
    private InputStream inputStream;
    private OutputStream outputStream;
    Calculator(InputStream in, OutputStream out) {
        inputStream = in;
        outputStream = out;
    }


    public void parseAndCalculate() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                String[] splitted = line.split(DELIM);
                try {
                    double result = calculate(splitted);
                    outputStream.write((String.valueOf(result) + "\n").getBytes(StandardCharsets.UTF_8));
                } catch (UnknownOperationException e) {
                    System.err.println("Unknown operation is used: " + e.getOperation());
                } catch (NumberFormatException e) {
                    System.err.println("One of arguments is not a number");
                } catch (NotEnoughArgumentsException e) {
                    System.err.println("Not enough arguments for operation");
                }
            }
        } catch (IOException e) {
            System.err.println("Error while parsing file:" + e.getMessage());
        }
    }

    private double calculate(String[] strings) throws UnknownOperationException, NumberFormatException, NotEnoughArgumentsException {
        DoubleStream arguments = Arrays.stream(strings).skip(1).mapToDouble(Double::parseDouble);
        long argumentsCount = strings.length - 1;
        if (argumentsCount < 2) {
            throw new NotEnoughArgumentsException();
        }
        switch(strings[0].toLowerCase()) {
            case "add":
                return arguments.sum();
            case "mul":
                return arguments.reduce(1, (x,y) -> x * y);
            case "mul&add":
                if (argumentsCount < 3) {
                    throw new NotEnoughArgumentsException();
                }
                double[] argumentsArray = arguments.toArray();
                return (argumentsArray[0] * argumentsArray[1]) + argumentsArray[2];
            default:
                throw new UnknownOperationException(strings[0].toLowerCase());
        }
    }
}
