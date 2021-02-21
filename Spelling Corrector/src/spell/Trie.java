package spell;

import java.lang.*;

public class Trie implements ITrie {
  private final Node rootNode;
  private int nodeCount = 1;
  private int wordCount = 0;

  public Trie() {
    rootNode = new Node();
  }


  /**
   * Adds the specified word to the trie (if necessary) and increments the word's frequency count.
   *
   * @param word the word being added to the trie
   */
  public void add(String word) {
    Node currentNode = rootNode;

    for (int i = 0; i < word.length(); i++) {
      char currentChar = word.charAt(i);
      if (currentNode.children[currentChar - 'a'] == null) {
        currentNode.children[currentChar - 'a'] = new Node();
        nodeCount++;
      }
      currentNode = currentNode.children[currentChar - 'a'];
    }
    if (find(word) == null) {
      wordCount++;
      currentNode.incrementValue();
    }


  }


  /**
   * Searches the trie for the specified word.
   *
   * @param word the word being searched for.
   * @return a reference to the trie node that represents the word,
   * or null if the word is not in the trie
   */
  public INode find(String word) {
    Node currentNode = rootNode;

    for (int i = 0; i < word.length(); i++) {
      char currentChar = word.charAt(i);
      if ((currentNode.children[currentChar - 'a']) == null) {
        return null;
      } else {
        currentNode = currentNode.children[currentChar - 'a'];
      }
    }

    if (currentNode.getValue() != 0) {
      currentNode.incrementValue();
      return currentNode;
    } else {
      return null;
    }
  }

  public INode findNode(String word) {
    Node currentNode = rootNode;

    for (int i = 0; i < word.length(); i++) {
      char currentChar = word.charAt(i);
      if ((currentNode.children[currentChar - 'a']) == null) {
        return null;
      } else {
        currentNode = currentNode.children[currentChar - 'a'];
      }
    }

    if (currentNode.getValue() != 0) {
      return currentNode;
    } else {
      return null;
    }
  }

  /**
   * Returns the number of unique words in the trie.
   *
   * @return the number of unique words in the trie
   */
  public int getWordCount() {
    return wordCount;
  }

  /**
   * Returns the number of nodes in the trie.
   *
   * @return the number of nodes in the trie
   */
  public int getNodeCount() {
    return nodeCount;
  }

  /**
   * The toString specification is as follows:
   * For each word, in alphabetical order:
   * <word>\n
   * MUST BE RECURSIVE.
   */
  @Override
  public String toString() {
    StringBuilder currentWord = new StringBuilder();
    StringBuilder out = new StringBuilder();
    HelperToString(rootNode, currentWord, out);
    return out.toString();
  }

  /**
   * Returns the hashcode of this trie.
   * MUST be constant time.
   *
   * @return a uniform, deterministic identifier for this trie.
   */
  @Override
  public int hashCode() {
    for (int i = 0; i < 26; i++) {
      if (rootNode.children[i] != null) {
        return (i * getNodeCount() * getWordCount());
      }
    }
    return 0;
  }

  /**
   * Checks if an object is equal to this trie.
   * MUST be recursive.
   *
   * @param o Object to be compared against this trie
   * @return true if o is a spell.Trie with same structure and node count for each node
   * false otherwise
   */
  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
    if (this == o) {
      return true;
    }
    if (o.getClass() != this.getClass()) {
      return false;
    }
    Trie trie = (Trie) o;
    if (trie.wordCount != this.wordCount || trie.rootNode.getValue() != this.rootNode.getValue() || trie.nodeCount != this.nodeCount) {
      return false;
    }

    Node first = trie.rootNode;
    Node second = this.rootNode;
    return EqualsHelper(first, second);
  }

  public void HelperToString(Node currentNode, StringBuilder currentWord, StringBuilder out) {
    if (currentNode.getValue() > 0) {
      out.append(currentWord.toString() + "\n");
    }
    Node[] node = currentNode.getChildren();

    for (int i = 0; i < 26; i++) {
      Node child = node[i];
      if (child != null) {
        char c = (char) (i + 'a');
        currentWord.append(c);
        HelperToString(child, currentWord, out);
        currentWord.deleteCharAt(currentWord.length() - 1);
      }
    }
  }

  public boolean EqualsHelper(Node first, Node second) {
    for (int i = 0; i < 26; i++) {
      if (first.children[i] != null && second.children[i] == null) {
        return false;
      } else if (first.children[i] == null && second.children[i] != null) {
        return false;
      } else if (first.children[i] == null && second.children[i] == null) {
        continue;
      }
      if (first.children[i].getValue() == second.children[i].getValue()) {
        Node temp1 = first.children[i];
        Node temp2 = second.children[i];
        if (! EqualsHelper(temp1, temp2)) {
          return false;
        }
      } else {
        return false;
      }
    }
    return true;
  }
}
