package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author Suzhenjiang
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    // You should probably define some more!
    private int size;
    private double loadFactor;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    /** Constructors */
    public MyHashMap() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        if (initialCapacity <= 0 || loadFactor <= 0) {
            throw  new IllegalArgumentException("Capacity and load factor must be greater than 0.");
        }
        this.buckets = createBucket(initialCapacity);
        this.size = 0;
        this.loadFactor = loadFactor;

    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *  Note that that this is referring to the hash table bucket itself,
     *  not the hash map itself.
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket(int capacity) {
        // TODO: Fill in this method.
        return (Collection<Node>[]) new Collection[capacity];
    }

    /*Return a data structure to be a hash table bucket.
      Hre, we use LinkedList by default.
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    private int hash(K key) {
        return Math.floorMod(key.hashCode(), buckets.length);
    }


    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!
    /**
     * Associates the specified value with the specified key in this map.
     * If the map already contains the specified key, replaces the key's mapping
     * with the value specified.
     *
     * @param key
     * @param value
     */
    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("keys cannot be null.");
        }

        int buckerIndex = hash(key);

        if (buckets[buckerIndex] == null) {
            buckets[buckerIndex] = createBucket();
        }

        for (Node node : buckets[buckerIndex]) {
            if (node.key.equals(key)) {
                node.value = value; // Update existing value
                return;
            }
        }

        // Add new key-value pair
        buckets[buckerIndex].add(new Node(key, value));
        size++;

        // Resize if load factor is exceeded
        if ((double) size / buckerIndex > loadFactor) {
            resize(buckets.length * 2);
        }
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key
     */
    @Override
    public V get(K key) {
        if (key == null) {
            return null;
        }

        int bucketIndex = hash(key);

        if (buckets[bucketIndex] == null) {
            return null;
        }

        for (Node node : buckets[bucketIndex]) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }
        return null;
    }

    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     * @param key
     */
    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Removes every mapping from this map.
     */
    @Override
    public void clear() {
        for (int i = 0; i < buckets.length; i++) {
            if (buckets[i] != null) {
                buckets[i].clear();
            }
        }
        size = 0;
    }

    private void resize(int newCapacity) {
        Collection<Node>[] newBuckets = createBuckets(newCapacity);
        for (Collection<Node> bucket : buckets) {
            if (bucket != null) {
                for (Node node : bucket) {
                int newBucketIndex = Math.floorMod(node.key.hashCode(), newCapacity);
                if (newBuckets[newBucketIndex] == null) {
                    newBuckets[newBucketIndex] = createBucket();
                }
                newBuckets[newBucketIndex].add(node);
                }
            }
        }
        buckets = newBuckets;
    }

    /**
     * Returns a Set view of the keys contained in this map. Not required for this lab.
     * If you don't implement this, throw an UnsupportedOperationException.
     */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException("keySet() is not implemented.");
    }

    /**
     * Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException("remove() is not implement.");
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException("iterator() is not implement.");
    }

}
