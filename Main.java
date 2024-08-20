package bullscows;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input the length of the secret:");
        System.out.print("> ");
        if (!scanner.hasNextInt()) {
            System.out.println("Error: the lenght isn't a valid number.");
            scanner.close();
            return;
        }
        int numberLength = scanner.nextInt();

        if (numberLength <= 0) {
            System.out.println("Error: the length of the secret code must be a positive number.");
            scanner.close();
            return;
        }

        if (numberLength > 36) {
            System.out.println("Error: can't generate a secret number with a length of " + numberLength
                    + " because there aren't enough unique digits.");
            scanner.close();
            return;
        }

        System.out.println("Input the number of possible symbols in the code:");
        System.out.print("> ");
        if (!scanner.hasNextInt()) {
            System.out.println("Error: the number of possible symbols isn't a valid number.");
            scanner.close();
            return;
        }
        int numberOfPossibleSymbols = scanner.nextInt();

        if (numberOfPossibleSymbols < numberLength) {
            System.out.println("Error: it's not possible to generate a code with a length of " + numberLength
                    + " with " + numberOfPossibleSymbols + " unique symbols.");
            scanner.close();
            return;
        }

        if (numberOfPossibleSymbols > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            scanner.close();
            return;
        }

        String possibleSymbols = "0123456789abcdefghijklmnopqrstuvwxyz";
        possibleSymbols = possibleSymbols.substring(0, numberOfPossibleSymbols);

        String secretCode = generateSecretCode(numberLength, possibleSymbols);

        String range;
        if (numberOfPossibleSymbols <= 10) {
            range = String.format("0-%d", numberOfPossibleSymbols - 1);
        } else if (numberOfPossibleSymbols == 11) {
            range = "0-9, a";
        } else {
            range = String.format("0-9, a-%c", possibleSymbols.charAt(numberOfPossibleSymbols - 1));
        }

        System.out.printf("The secret is prepared: %s (%s).\n", "*".repeat(numberLength), range);

        System.out.println("Okay, let's start a game!");

        int turn = 1;
        boolean guessed = false;

        while (!guessed) {
            String guessedCode;

            System.out.println("Turn " + turn + ":");
            System.out.print("> ");
            guessedCode = scanner.next();

            int cows = 0;
            int bulls = 0;

            for (int i = 0; i < secretCode.length(); i++) {
                if (secretCode.charAt(i) == guessedCode.charAt(i)) {
                    bulls++;
                } else {
                    if (secretCode.indexOf(guessedCode.charAt(i)) != -1) {
                        cows++;
                    }
                }
            }

            if (bulls == secretCode.length()) {
                guessed = true;
                System.out.println("Grade: " + bulls + " bulls");
                System.out.println("Congratulations! You guessed the secret code.");
                break;
            }

            if (bulls != 0 && cows != 0) {
                System.out.println("Grade: " + bulls + " bull(s) and " + cows + " cow(s)");
            } else if (bulls != 0) {
                System.out.println("Grade: " + bulls + " bull(s).");
            } else if (cows != 0) {
                System.out.println("Grade: " + cows + " cow(s).");
            } else {
                System.out.println("Grade: None");
            }

            turn++;
        }

        scanner.close();
    }

    private static String generateSecretCode(int numberLength, String possibleSymbols) {
        Random random = new Random();
        StringBuilder secretCodeString = new StringBuilder();

        while (secretCodeString.length() < numberLength) {
            int randomIndex = random.nextInt(possibleSymbols.length());
            char newChar = possibleSymbols.charAt(randomIndex);

            if (secretCodeString.toString().contains(String.valueOf(newChar))) {
                continue;
            }

            secretCodeString.append(newChar);
        }

        return secretCodeString.toString();
    }
}
