package hangman;

import java.io.*;
import java.util.*;

public class EvilHangman {

    public static void main(String[] args) throws IOException, EmptyDictionaryException, GuessAlreadyMadeException {

        File file = new File(args[0]);
        int wordLength = Integer.parseInt(args[1]);
        int guesses = Integer.parseInt(args[2]);
        Set <String> words = new TreeSet<>();

        EvilHangmanGame hangman = new EvilHangmanGame();
        hangman.startGame(file, wordLength);
        while (guesses != 0) {
            boolean guessException = true;
            System.out.printf("You have %d guesses left\n", guesses);
            System.out.printf("Used letters: %s\n", hangman.getGuessedLetters());
            System.out.printf("Word: %s\n", hangman.displayKey);
            System.out.print("Enter guess: ");

            Scanner scanner = new Scanner(System.in);
            String s = scanner.nextLine();

            while(CheckString(s)) {
                System.out.print("Invalid input! Enter guess: ");
                s = scanner.nextLine();
            }
            char userChar = s.charAt(0);

            while(guessException) {
                try {
                    words = hangman.makeGuess(userChar);
                    guessException = false;
                }
                catch (GuessAlreadyMadeException e) {

                    s = scanner.nextLine();
                    userChar = s.charAt(0);
                    guessException = true;
                }
            }

            if(hangman.keysDiffer) {
                System.out.printf("Yes, there is %d %s\n\n", hangman.numKeysDiffer, userChar);
            } else {
                System.out.printf("Sorry, there are no %c\n\n", userChar);
                guesses--;
            }

            if (words.size() <= 1 && !hangman.displayKey.contains("-")) {
                System.out.printf("You win! You guessed the word: %s\n", hangman.displayKey);
                return;
            }
        }
        System.out.printf("Sorry, you lost! The word was: %s", words.iterator().next());
    }

    public static boolean CheckString(String s) {
        if(s.length() <= 0) {
            return true;
        }
        char c = s.charAt(0);
        if(! ( (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') ) ) {
            return true;
        }
        return s.length() != 1 || s.contains(" ");
    }
}
