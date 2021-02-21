package spell;

public class Node implements INode {

  public int frequencyCount = 0;
  public Node[] children = new Node[26]; //Each node has an array of 26 character nodes.


  /**
   * Returns the frequency count for the word represented by the node.
   *
   * @return the frequency count for the word represented by the node.
   */
  public int getValue() {
    return frequencyCount;
  }

  /**
   * Increments the frequency count for the word represented by the node.
   */
  public void incrementValue() {
    frequencyCount++;
  }

  /**
   * Returns the child nodes of this node.
   *
   * @return the child nodes.
   */
  public Node[] getChildren() {
    return children;
  }
}
