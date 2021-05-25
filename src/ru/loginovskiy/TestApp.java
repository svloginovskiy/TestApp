package ru.loginovskiy;

import java.io.*;
import java.util.Locale;

public class TestApp {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("You need to enter two arguments: input filename(or - for console input) and output filename(or - for console output)");
            return;
        }
        try (InputStream inputStream = args[0].equals("-") ? System.in : new FileInputStream(args[0]);
             OutputStream outputStream = args[1].equals("-") ? System.out : new FileOutputStream(args[1])) {
            Calculator calculator = new Calculator(inputStream, outputStream);
            calculator.parseAndCalculate();
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
