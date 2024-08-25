import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T>{
    public class Node{
        public T item;
        public Node prev;
        public Node next;

        public Node(T i, Node p, Node n) {
            item = i;
            prev = p;
            next = n;
        }

    }

    private Node sentinel;
    private int size;

    public LinkedListDeque61B() {
        size = 0;
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
    }

    @Override
    public void addFirst(T x) {
        size += 1;
        Node oldNode = sentinel.next;
        Node newNode = new Node(x, sentinel, oldNode);
        sentinel.next = newNode;
        oldNode.prev = newNode;
    }

    @Override
    public void addLast(T x) {
        size += 1;
        Node oldNode = sentinel.prev;
        Node newNode = new Node(x, oldNode, sentinel);
        sentinel.prev = newNode;
        oldNode.next = newNode;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        Node nowNode = sentinel.next;
        if (isEmpty()) return null;
        while (nowNode != sentinel) {
            returnList.add(nowNode.item);
            nowNode = nowNode.next;
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return sentinel.next == sentinel;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) return null;
        T item = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size -= 1;
        return item;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) return null;
        T item = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size -= 1;
        return item;
    }

    @Override
    public T get(int index) {
        int pos = 1;
        Node nowNode = sentinel;

        if (size < index) {
            return null;
        }

        while (pos <= index) {
            pos += 1;
            nowNode = nowNode.next;
        }
        return nowNode.item;
    }

    @Override
    public T getRecursive(int index) {
        if (index > size || index < 0) {
            return null;
        }
        return getRecursiveHelp(sentinel, index);
    }

    private T getRecursiveHelp(Node node, int index) {
        if (index == 0) {
            return node.item;
        }
        return getRecursiveHelp(node.next, index - 1);
    }
}
