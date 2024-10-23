package deque;

import java.util.*;

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

    public boolean contains(T x) {
        Node currentNode = sentinel.next;
        while (currentNode != sentinel) {
            if (currentNode.item.equals(x)) {
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !(o instanceof LinkedListDeque61B<?>)) {
            return false;
        }

        LinkedListDeque61B<T> otherDeque = (LinkedListDeque61B<T>) o;

        if (this.size != otherDeque.size) {
            return false;
        }

        Node currentNode = this.sentinel.next;
        Node otherNode = otherDeque.sentinel.next;

        while (currentNode != this.sentinel && otherNode != otherDeque.sentinel) {
            if (!currentNode.item.equals(otherNode.item)) {
                return false;
            }
            currentNode = currentNode.next;
            otherNode = otherNode.next;
        }
        return true;
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

    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }

    private class LinkedListDequeIterator implements Iterator<T> {
        private Node currentNode;

        public LinkedListDequeIterator() {
            currentNode = sentinel.next;
        }

        @Override
        public boolean hasNext() {
            return currentNode != sentinel;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T item = currentNode.item;
            currentNode = currentNode.next;
            return item;
        }
    }

    /**
     * Returns {@code true} if the iteration has more elements.
     * (In other words, returns {@code true} if {@link #next} would
     * return an element rather than throwing an exception.)
     *
     * @return {@code true} if the iteration has more elements
     */
    @Override
    public boolean hasNext() {
        return iterator().hasNext();
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    @Override
    public T next() {
        return iterator().next();
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[");

        Node currentNode = sentinel.next;
        while (currentNode != sentinel) {
            sb.append(currentNode.item);
            currentNode = currentNode.next;
            if (currentNode != sentinel) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}