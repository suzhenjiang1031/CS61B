public class UnionFind {
    private int[] parent;
    private int[] size;

    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        parent = new int[N];
        size = new int[N];

        for (int i=0; i < N; i++) {
         parent[i] = i;
         size[i] = 1;
        }
    }

    /* Returns the size of the setpa V belongs to. */
    public int sizeOf(int v) {
        int root = find(v);
        return Math.abs(size[root]);
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        int root = find(v);
        return root == v ? -size[root] : parent[root];
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        return find(v1) == find(v2);
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        if (v < 0 || v >= parent.length) {
            throw new IllegalArgumentException("Invalid vertex: " + v);
        }
        if (v != parent[v]) {
            parent[v] = find(parent[v]);
        }
        return parent[v];
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing an item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        int i = parent(v1);
        int j = parent(v2);

        if(i == j) return;

        if (size[i] < size[j]) {
            parent[i] = j;
            size[j] += size[i];
        } else {
            parent[j] = i;
            size[i] += size[j];
        }
    }

}
