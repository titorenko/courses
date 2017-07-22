


public class Subset {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> q = new RandomizedQueue<>();
        int i = 0;
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            int r = StdRandom.uniform(i+1);
            if (i < k) {
                q.enqueue(s);
            } else if (r < k) {
                q.dequeue();
                q.enqueue(s);
            }
            i++;
        }
        
        for (String s : q) {
            StdOut.println(s);
        }
    }
}