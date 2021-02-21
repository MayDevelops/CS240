package spell;

import java.io.IOException;
import java.io.*;
import java.util.*;

public class SpellCorrector implements ISpellCorrector {

  Trie trie = new Trie();


  /**
   * Tells this <code>SpellCorrector</code> to use the given file as its dictionary
   * for generating suggestions.
   *
   * @param dictionaryFileName the file containing the words to be used
   * @throws IOException If the file cannot be read
   * @pre SpellCorrector will have had empty-param constructor called, but dictionary has nothing in it.
   * @post SpellCorrector will have dictionary filled and be ready to suggestSimilarWord any number of times.
   */
  @Override
  public void useDictionary(String dictionaryFileName) throws IOException {
    System.out.printf("Adding words to Trie\n");
    Scanner scanner = new Scanner(new File(dictionaryFileName));
    while (scanner.hasNext()) {
      String line = scanner.next();
      line = line.toLowerCase();
      trie.add(line);
    }
    System.out.printf("There are %d words in the Trie.\n", trie.getWordCount());
  }

  /**
   * Suggest a word from the dictionary that most closely matches
   * <code>inputWord</code>.
   *
   * @param inputWord the word we are trying to find or find a suggestion for
   * @return the suggestion or null if there is no similar word in the dictionary
   */
  @Override
  public String suggestSimilarWord(String inputWord) {
    inputWord = inputWord.toLowerCase();
    if (trie.find(inputWord) != null) {
      return inputWord;
    }

    Set<String> tempWordStorage;
    Set<String> candidateWords = new TreeSet<>();
    System.out.printf("Getting Candidate Words from DeletionDistance...%s\n", inputWord);
    tempWordStorage = DeletionDistance(inputWord);
    candidateWords.addAll(tempWordStorage);
    System.out.printf("Getting Candidate Words from TranspositionDistance...%s\n", inputWord);
    tempWordStorage = TranspositionDistance(inputWord);
    candidateWords.addAll(tempWordStorage);
    System.out.printf("Getting Candidate Words from AlterationDistance...%s\n", inputWord);
    tempWordStorage = AlterationDistance(inputWord);
    candidateWords.addAll(tempWordStorage);
    System.out.printf("Getting Candidate Words from InsertionDistance...%s\n", inputWord);
    tempWordStorage = InsertionDistance(inputWord);
    candidateWords.addAll(tempWordStorage);

    if (DoesWordExist(candidateWords) == null) {
      tempWordStorage = AllTwoDistanceMethods(candidateWords);
      candidateWords.addAll(tempWordStorage);
    }

    return DoesWordExist(candidateWords);
  }

  public Set<String> DeletionDistance(String inputWord) {

    Set<String> candidates = new TreeSet<>();
    Vector<Character> characters = new Vector<>();

    for (int i = 0; i < inputWord.length(); i++) {
      for (int j = 0; j < inputWord.length(); j++) {
        if (j != i) {
          characters.add(inputWord.charAt(j));
        }
      }
      String s = StringBuilder(characters);
      characters.clear();
      candidates.add(s);
    }

    return candidates;
  }

  private Set<String> TranspositionDistance(String inputWord) {
    Set<String> candidates = new TreeSet<>();

    for (int i = 0; i < inputWord.length(); i++) {
      char[] characters = inputWord.toCharArray();
      if (i != inputWord.length() - 1) {
        int j = (i + 1);
        char temp = characters[i];
        characters[i] = characters[j];
        characters[j] = temp;
      }
      String s = new String(characters);
      candidates.add(s);
    }

    return candidates;
  }

  private Set<String> AlterationDistance(String inputWord) {
    Set<String> candidates = new TreeSet<>();

    for (int i = 0; i < inputWord.length(); i++) {
      char[] characters = inputWord.toCharArray();
      for (int j = 0; j < 26; j++) {
        int temp = j + 97;
        characters[i] = (char) temp;
        String s = new String(characters);
        candidates.add(s);
      }

    }
    return candidates;
  }

  private Set<String> InsertionDistance(String inputWord) {
    Set<String> candidates = new TreeSet<>();
    char[] updatedArr = new char[inputWord.length() + 1];

    for (int i = 0; i < inputWord.length() + 1; i++) {
      inputWord.getChars(0, i, updatedArr, 0);
      for (int j = 0; j < 26; j++) {
        int temp = j + 97;
        updatedArr[i] = (char) temp;
        inputWord.getChars(i, inputWord.length(), updatedArr, i + 1);

        candidates.add(new String(updatedArr));
      }

    }
    return candidates;
  }

  private Set<String> AllTwoDistanceMethods(Set<String> oneDistanceCandidates) {
    Set<String> tempWordStorage;
    Set<String> twoDistanceCandidates = new TreeSet<>();
    for (String s : oneDistanceCandidates) {
      tempWordStorage = DeletionDistance(s);
      twoDistanceCandidates.addAll(tempWordStorage);
      tempWordStorage = TranspositionDistance(s);
      twoDistanceCandidates.addAll(tempWordStorage);
      tempWordStorage = AlterationDistance(s);
      twoDistanceCandidates.addAll(tempWordStorage);
      tempWordStorage = InsertionDistance(s);
      twoDistanceCandidates.addAll(tempWordStorage);
    }
    return twoDistanceCandidates;
  }


  private String StringBuilder(Vector<Character> v) {
    StringBuilder sb = new StringBuilder();
    for (Character c : v) {
      sb.append(c);
    }
    return sb.toString();
  }

  private String DoesWordExist(Set<String> candidateWords) {
    String currentWord = null;
    int frequency = 0;

    for (String s : candidateWords) {
      INode temp = trie.findNode(s);
      if (temp != null) {
        if (temp.getValue() > frequency) {
          currentWord = s;
          frequency = temp.getValue();
        }
      }
    }

    System.out.printf("Recommended Word: %s\n", currentWord);
    return currentWord;
  }


}
