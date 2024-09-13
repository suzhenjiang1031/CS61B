import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.HashSet;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private Node root; // BST 根节点
    private int size; // 节点数

    // 节点类定义
    private class Node {
        private K key;
        private V value;
        private Node left, right;

        Node(K k, V v) {
            this.key = k;
            this.value = v;
        }
    }

    public BSTMap() {
        root = null;
        size = 0;
    }

    // 插入键值对，如果key已存在则更新值
    @Override
    public void put(K key, V value) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");
        root = put(root, key, value);
    }

    private Node put(Node node, K key, V value) {
        if (node == null) { // 如果到达空节点，插入新节点
            size++;
            return new Node(key, value);
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = put(node.left, key, value);
        } else if (cmp > 0) {
            node.right = put(node.right, key, value);
        } else {
            node.value = value; // key 已存在，更新 value
        }
        return node;
    }

    // 获取给定key对应的值
    @Override
    public V get(K key) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");
        return get(root, key);
    }

    private V get(Node node, K key) {
        if (node == null) return null; // 没找到
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            return get(node.left, key);
        } else if (cmp > 0) {
            return get(node.right, key);
        } else {
            return node.value;
        }
    }

    // 检查是否包含指定 key
    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    // 返回当前树的节点数
    @Override
    public int size() {
        return size;
    }

    // 清空树
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    // 返回所有 key 的集合
    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        inOrderKeys(root, keys);
        return keys;
    }

    private void inOrderKeys(Node node, Set<K> keys) {
        if (node == null) return;
        inOrderKeys(node.left, keys);
        keys.add(node.key);
        inOrderKeys(node.right, keys);
    }

    // 删除指定的key
    @Override
    public V remove(K key) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");
        V value = get(key);
        if (value != null) {
            root = remove(root, key);
            size--;
        }
        return value;
    }

    private Node remove(Node node, K key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = remove(node.left, key);
        } else if (cmp > 0) {
            node.right = remove(node.right, key);
        } else {
            // 找到了节点，处理三种删除情况
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            // 处理有两个子节点的情况
            Node t = node;
            node = min(t.right);
            node.right = deleteMin(t.right);
            node.left = t.left;
        }
        return node;
    }

    private Node min(Node node) {
        if (node.left == null) return node;
        return min(node.left);
    }

    private Node deleteMin(Node node) {
        if (node.left == null) return node.right;
        node.left = deleteMin(node.left);
        return node;
    }

    // 返回一个键值对的迭代器 (中序遍历)
    @Override
    public Iterator<K> iterator() {
        return new Iterator<>() {
            private Iterator<K> keyIterator = keySet().iterator();

            @Override
            public boolean hasNext() {
                return keyIterator.hasNext();
            }

            @Override
            public K next() {
                if (!hasNext()) throw new NoSuchElementException();
                return keyIterator.next();
            }
        };
    }
}
