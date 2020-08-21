package life.qbic.repowiz.find.tree

/**
 * This class is a DTO to represent nodes in a {@link DecisionTree}
 *
 * This class should be used whenever a node for the decision tree is required or when a tree needs to be build
 *
 *  @since: 1.0.0
 *  @author: Jennifer Bödker
 *
 */
class Node<T> {

    private List<Node<T>> children = new ArrayList<Node<T>>()
    private T data = null

    /**
     * Creates a node with the provided data
     * @param data of the defined format e.g a String
     */
    Node(T data) {
        this.data = data
    }

    /**
     * Returns a list of children of the current node
     * @return children of the node
     */
    List<Node<T>> getChildren() {
        return children
    }

    /**
     * Adds a child to the current node by creating a new node
     * @param data which is associated with the child
     */
    void addChild(T data) {
        Node<T> child = new Node<T>(data)
        children.add(child)
    }

    /**
     * Adds a child to the current node
     * @param child is a node itself
     */
    void addChild(Node<T> child) {
        children.add(child)
    }

    /**
     * Returns the data of the node
     * @return data associated with the node
     */
    T getData() {
        data
    }

    /**
     * Determines if the current node is a leaf node.
     * A node is a leaf node if it has no children
     * @return a boolean which states if the node is a leaf node
     */
    boolean isLeaf() {
        this.children.size() == 0
    }

}