public class UnionFind {
    private int[] parent;

    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        parent = new int[N];

        for (int i=0; i < N; i++) {
         parent[i] = -1;
        }
    }

    /* Returns the size of the setpa V belongs to. */
    public int sizeOf(int v) {
        return Math.abs(parent[v]);
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        return parent[v];
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        return find(v1) == find(v2);
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int p) {
        if (p < 0 || p >= parent.length) {
            throw new IllegalArgumentException("Invalid vertex: " + p);
        }
        while (parent[p]>=0){
            p = parent[p];
        }
        return p;
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing an item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        if(connected(v1,v2))
            return;

        int i = find(v1);
        int j = find(v2);
        //合并
        if(sizeOf(i)>=sizeOf(j)){

            parent[i]+=parent[j];
            parent[j]=i;
        }
        else{
            parent[j]+=parent[i];
            parent[i]=j;
        }
    }

}
