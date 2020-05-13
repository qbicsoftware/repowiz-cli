package life.qbic.repowiz.find.tree

class Node<T> {

    private List<Node<T>> children = new ArrayList<Node<T>>()
    private T data = null

    Node(T data) {
        this.data = data
    }

    List<Node<T>> getChildren() {
        return children
    }

    void addChild(T data) {
        Node<T> child = new Node<T>(data)
        children.add(child)
    }

    void addChild(Node<T> child) {
        children.add(child)
    }

    T getData() {
        data
    }

    boolean isLeaf() {
        this.children.size() == 0
    }

}