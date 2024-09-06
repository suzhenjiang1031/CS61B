import java.util.ArrayList;
import java.util.List;

public class ArrayDeque61B<T> implements Deque61B<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque61B() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }


    @Override
    public void addFirst(T x) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        items[nextFirst] = x;
        nextFirst = (nextFirst - 1 + items.length) % items.length;
        size++;

    }

    @Override
    public void addLast(T x) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        items[nextLast] = x;
        nextLast = (nextLast + 1) % items.length;
        size++;

    }

    @Override
    public List<T> toList() {
        List<T> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(get(i));
        }
        return list;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        nextFirst = (nextFirst + 1) % items.length;
        T removedItem = items[nextFirst];
        items[nextFirst] = null;
        size--;
        if (size > 0 && size == items.length / 4) {
            resize(items.length / 2);
        }
        return removedItem;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        nextLast = (nextLast - 1 + items.length) % items.length;
        T removedItem = items[nextLast];
        items[nextLast] = null;
        size--;
        if (size > 0 && size == items.length / 4) {
            resize(items.length / 2);
        }
        return removedItem;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= items.length) {
            return null;
        }
        return items[nextFirst + 1 + index % items.length];
    }

    @Override
    public T getRecursive(int index) {
        return get(index);
    }

    public void resize(int capacity) {
        T[] newItems = (T[]) new Object[capacity];
        int start = (nextFirst + 1) % items.length;
        for (int i = 0; i < size; i++) {
            newItems[i] = items[(start + i) % items.length];
        }
        items = newItems;
        nextFirst = capacity - 1;
        nextLast = size;
    }
}
