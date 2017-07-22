package week3


 class UF(var count: Int) {
    val id = new Array[Int](count)// id[i] = parent of i
    val rank = new Array[Byte](count)// rank[i] = rank of subtree rooted at i (cannot be more than 31)

    /**
     * Initializes an empty union-find data structure with <tt>N</tt>
     * isolated components <tt>0</tt> through <tt>N-1</tt>
     * @throws java.lang.IllegalArgumentException if <tt>N &lt; 0</tt>
     * @param N the number of sites
     */
    //public UF(int N) {
        0 until count foreach {i =>
            id(i) = i;
            rank(i) = 0;
        }
    //}

    /**
     * Returns the component identifier for the component containing site <tt>p</tt>.
     * @param p the integer representing one object
     * @return the component identifier for the component containing site <tt>p</tt>
     */
    def find(p: Int): Int = {
        var cp = p
        while (cp != id(cp)) {
            id(cp) = id(id(cp));    // path compression by halving
            cp = id(cp);
        }
        return cp
    }
  
    /**
     * Are the two sites <tt>p</tt> and <tt>q</tt> in the same component?
     * @param p the integer representing one site
     * @param q the integer representing the other site
     * @return true if the two sites <tt>p</tt> and <tt>q</tt> are in the same component; false otherwise
     */
    def connected(p: Int, q: Int): Boolean = {
        return find(p) == find(q);
    }

  
    /**
     * Merges the component containing site <tt>p</tt> with the 
     * the component containing site <tt>q</tt>.
     * @param p the integer representing one site
     * @param q the integer representing the other site
     * @throws java.lang.IndexOutOfBoundsException unless
     *      both <tt>0 &le; p &lt; N</tt> and <tt>0 &le; q &lt; N</tt>
     */
    def union(p: Int, q: Int) {
        val i = find(p);
        val j = find(q);
        if (i == j) return;

        // make root of smaller rank point to root of larger rank
        if      (rank(i) < rank(j)) id(i) = j;
        else if (rank(i) > rank(j)) id(j) = i;
        else {
            id(j) = i;
            rank(i) = (rank(i) + 1).toByte// + (1.toByte);
        }
        count = count - 1;
    }
   
}
