package hangman;

import java.io.*;
import java.util.*;

public class EvilHangmanGame implements IEvilHangmanGame {
  public Set<String> words = new TreeSet<>();
  public SortedSet<Character> guessedLetters = new TreeSet<>();
  public Map<String, Set<String>> availableWords = new HashMap<>();
  public String displayKey = "";
  public boolean keysDiffer = false;
  public int numKeysDiffer = 0;
  public char globalGuess;

  @Override
  public void startGame(File dictionary, int wordLength) throws IOException, EmptyDictionaryException {
    availableWords.clear();
    guessedLetters.clear();
    words.clear();
    char[] keyChar = new char[wordLength];
    Arrays.fill(keyChar,'-');
    displayKey = StringBuilder(keyChar);

    Scanner scanner = new Scanner(dictionary);

    while (scanner.hasNext()) {
      String word = scanner.next().toLowerCase();
      if (word.length() == wordLength) {
        words.add(word);
      }
    }

    if (words.size() == 0) {
      throw new EmptyDictionaryException();
    }

    //System.out.printf("There are %d words in the Initial Set.\n", words.size());
  }

  @Override
  public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
    availableWords.clear();
    keysDiffer = false;
    numKeysDiffer = 0;
    globalGuess = guess;

    if (guess >= 'A' && guess <= 'Z') {
      int i = guess + 32;
      char c = (char) i;
      if (guessedLetters.contains(c) || guessedLetters.contains(guess)) {
        System.out.println("Guess already made! Enter Guess: ");
        throw new GuessAlreadyMadeException();
      }
    } else if (guess >= 'a' && guess <= 'z') {
      int i = guess - 32;
      char c = (char) i;
      if (guessedLetters.contains(c) || guessedLetters.contains(guess)) {
        System.out.println("Guess already made! Enter Guess: ");
        throw new GuessAlreadyMadeException();
      }
    }


    guessedLetters.add(guess);
    for (String s : words) {
      String key = BuildKey(guess, s);

      if (key != null) {
        Set<String> temp = availableWords.get(key);
        if (temp == null) {
          temp = new TreeSet<>();
        }
        temp.add(s);
        availableWords.put(key, temp);
      }
    }
    words = MaxSetHelper(availableWords, guess);
    return words;
  }

  @Override
  public SortedSet<Character> getGuessedLetters() {
    return guessedLetters;
  }

  public String BuildKey(char c, String incomingString) {
    String s;
    char[] charArray = new char[incomingString.length()];
    Arrays.fill(charArray, '-');
    s = StringBuilder(charArray);

    for (int i = 0; i < incomingString.length(); i++) {
      if (incomingString.charAt(i) == c) {
        charArray[i] = c;
        s = StringBuilder(charArray);
      }
    }
    return s;
  }

  private String StringBuilder(char[] v) {
    StringBuilder sb = new StringBuilder();
    for (char c : v) {
      sb.append(c);
    }
    return sb.toString();
  }

  private Set<String> MaxSetHelper(Map<String, Set<String>> map, char guess) {
    Set<String> finalSet;
    Vector<String> keysToRemove = new Vector<>();
    String oldKey = displayKey;
    String workingKey = "";

    int minCount;
    int maxCount = 0;
    //get the one with the biggest set in the map
    for (Map.Entry<String, Set<String>> m : availableWords.entrySet()) {
      minCount = m.getValue().size();
      if (minCount > maxCount) {
        maxCount = minCount;
      }
    }
    for (Map.Entry<String, Set<String>> m : availableWords.entrySet()) {
      if (m.getValue().size() != maxCount) {
        keysToRemove.add(m.getKey());
      } else {
        workingKey = m.getKey();
      }
    }
    map.keySet().removeAll(keysToRemove);

    //choose the group with the least instances
    if (map.size() > 1) {
      int lowestInstance = 999, instances;
      for (Map.Entry<String, Set<String>> m : availableWords.entrySet()) {
        instances = 0;
        for (int i = 0; i < m.getKey().length(); i++) {
          if (m.getKey().charAt(i) == guess) {
            instances++;
          }
        }

        if (instances < lowestInstance) {
          lowestInstance = instances;
          workingKey = m.getKey();
        }
      }

      for (Map.Entry<String, Set<String>> n : availableWords.entrySet()) {
        instances = 0;
        for (int i = 0; i < n.getKey().length(); i++) {
          if (n.getKey().charAt(i) == guess) {
            instances++;
          }
        }
        if (instances != lowestInstance) {
          keysToRemove.add(n.getKey());
        } else {
          workingKey = n.getKey();
        }
      }
      map.keySet().removeAll(keysToRemove);
    }

    //get the one that has the rightmost value
    if (map.size() > 1) {
      int minPos, maxPos = 0;
      for (String s : map.keySet()) {
        minPos = 0;
        for (int i = 0; i < s.length(); i++) {
          if (s.charAt(i) == guess) {
            minPos++;
            minPos = minPos + i;
          }
        }
        if (minPos > maxPos) {
          maxPos = minPos;
          workingKey = s;
        }
      }
    }

    finalSet = map.get(workingKey);

    for (int i = 0; i < workingKey.length(); i++) {
      if(workingKey.charAt(i) != '-') {
        displayKey = MergeKeys(oldKey, workingKey);
      }
    }
    return finalSet;
  }

  private String MergeKeys(String oldKey, String key) {
    numKeysDiffer = 0;
    char[] rString = new char[oldKey.length()];

    if(! oldKey.equals(key)) {
      keysDiffer = true;
    }

    for(int i = 0; i < oldKey.length(); i++) {
      if (oldKey.charAt(i) != '-' && oldKey.charAt(i) != key.charAt(i)) {
        rString[i] = oldKey.charAt(i);
      } else {
        rString[i] = key.charAt(i);
      }
    }

    for(int i = 0; i < oldKey.length(); i++) {
      if (oldKey.charAt(i) != key.charAt(i) && key.charAt(i) == globalGuess) {
        numKeysDiffer++;
      }
    }
    return StringBuilder(rString);
  }
}
