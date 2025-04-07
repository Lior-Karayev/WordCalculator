// Lior Karayev 214484222
// Idan Tzabari 300370525

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String equation;
        String[] parameters;
        int result = 0;

        Scanner input = new Scanner(System.in);

        System.out.print("Enter expression: ");
        equation = input.nextLine();

        result = calculateEquation(equation);

        System.out.println("THe value of expression '" + equation + "' is: " + result);

    }

    public static int wordToNumber(String word) {
        switch (word.toLowerCase()) {
            case "zero":
                return 0;
            case "one":
                return 1;
            case "two":
                return 2;
            case "three":
                return 3;
            case "four":
                return 4;
            case "five":
                return 5;
            case "six":
                return 6;
            case "seven":
                return 7;
            case "eight":
                return 8;
            case "nine":
                return 9;
            default:
                return 0;
        }
    }

    public static int calculateEquation(String equation) {
        if (equation == null || equation.isEmpty()) return 0;

        equation = equation.replace("divided by", "divided");
        equation = equation.replace("devided by", "divided");
        equation = equation.replace("devided by", "divided");
        equation = equation.replace("devided by", "divided");
        equation = equation.replace("devided by", "divided");

        while (equation.contains("(")) {
            int startIndex = equation.lastIndexOf("(");
            int endIndex = equation.indexOf(")", startIndex);


            String inner = equation.substring(startIndex + 1, endIndex);
            int innerResult = calculateEquation(inner);

            equation = equation.substring(0, startIndex) + innerResult + equation.substring(endIndex + 1);
        }

        String[] tokens = equation.toLowerCase().split(" ");

        if(equation.contains("times") || equation.contains("divided"))
            tokens = handleMultiplicationAndDivision(tokens);

        return handleAdditionAndSubtraction(tokens);
    }

    private static String[] handleMultiplicationAndDivision(String[] tokens) {
        int i = 0;

        while (i < tokens.length) {
            if (tokens[i].equals("times") || tokens[i].equals("divided")) {
                int left = getNumber(tokens[i - 1]);
                int right = getNumber(tokens[i + 1]);
                int result;

                if (tokens[i].equals("times")) {
                    result = left * right;
                } else {
                    result = left / right;
                }

                tokens[i - 1] = String.valueOf(result);
                tokens[i] = "";
                tokens[i + 1] = "";
                tokens = compactArray(tokens);
                i--;
            }
            i++;
        }

        return tokens;
    }

    private static int handleAdditionAndSubtraction(String[] tokens) {
        int result = getNumber(tokens[0]);

        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            if (token.equals("plus")) {
                result += getNumber(tokens[i + 1]);
            } else if(token.equals("minus")) {
                result -= getNumber(tokens[i + 1]);
            }
        }

        return result;
    }

    private static int getNumber(String token) {
        try {
            return Integer.parseInt(token);
        } catch (NumberFormatException e) {
            return wordToNumber(token);
        }
    }

    private static String[] compactArray(String[] tokens) {
        int count = 0;
        for (String token : tokens) {
            if (!token.isEmpty()) count++;
        }

        String[] newTokens = new String[count];
        int index = 0;
        for (String token : tokens) {
            if (!token.isEmpty()) newTokens[index++] = token;
        }

        return newTokens;
    }
}