/**
 * Written by 0xc0de1dea
 * Email : 0xc0de1dea@gmail.com
 */

//import java.io.FileInputStream;

import java.util.HashMap;

public class Main {
    static int[] parent, rank, counting;

    static int find(int node){
        if (parent[node] == node) return node;
        return parent[node] = find(parent[node]);
    }

    static void union(int x, int y){
        x = find(x); y = find(y);

        if (x != y){
            if (rank[x] > rank[y]){
                parent[y] = x;
                counting[x] += counting[y];
            }
            else {
                parent[x] = y;
                counting[y] += counting[x];
            }

            if (rank[x] == rank[y]) rank[y]++;
        }
    }

    public static void main(String[] argu) throws Exception {
        //System.setIn(new FileInputStream("input.in"));
        Reader in = new Reader();
        StringBuilder sb = new StringBuilder();

        int t = in.nextInt();
        HashMap<String, Integer> id = new HashMap<>(200_000);

        while (t-- > 0){
            int f = in.nextInt();
            parent = new int[f << 1];
            rank = new int[f << 1];
            counting = new int[f << 1];

            for (int i = 0; i < f << 1; i++){
                parent[i] = i;
                counting[i] = 1;
            }

            int num = -1;

            for (int i = 0; i < f; i++){
                String a = in.nextString();
                String b = in.nextString();

                if (!id.containsKey(a)) id.put(a, ++num);
                if (!id.containsKey(b)) id.put(b, ++num);

                int idA = id.get(a);
                int idB = id.get(b);

                union(idA, idB);

                sb.append(counting[find(idB)]).append('\n');
            }
        }

        System.out.print(sb);
    }
}

class Reader {
    final int SIZE = 1 << 13;
    byte[] buffer = new byte[SIZE];
    int index, size;

    String nextString() throws Exception {
        StringBuilder sb = new StringBuilder();
        byte c;
        while ((c = read()) <= 32);
        do sb.appendCodePoint(c);
        while (isAlphabet(c = read()));
        return sb.toString();
    }

    char nextChar() throws Exception {
        char ch = ' ';
        byte c;
        while ((c = read()) <= 32);
        do ch = (char)c;
        while (isAlphabet(c = read()));
        return ch;
    }
    
    int nextInt() throws Exception {
        int n = 0;
        byte c;
        boolean isMinus = false;
        while ((c = read()) <= 32); //{ if (size < 0) return -1; }
        if (c == 45) { c = read(); isMinus = true; }
        do n = (n << 3) + (n << 1) + (c & 15);
        while (isNumber(c = read()));
        return isMinus ? ~n + 1 : n;
    }

    long nextLong() throws Exception {
        long n = 0;
        byte c;
        boolean isMinus = false;
        while ((c = read()) <= 32);
        if (c == 45) { c = read(); isMinus = true; }
        do n = (n << 3) + (n << 1) + (c & 15);
        while (isNumber(c = read()));
        return isMinus ? ~n + 1 : n;
    }

    double nextDouble() throws Exception {
        double n = 0, div = 1;
        byte c;
        boolean isMinus = false;
        while ((c = read()) <= 32);
        if (c == 45) { c = read(); isMinus = true; }
        else if (c == 46) { c = read(); }
        do n = (n * 10) + (c & 15);
        while (isNumber(c = read()));
        if (c == 46) { while (isNumber(c = read())){ n += (c - 48) / (div *= 10); }}
        return isMinus ? -n : n;
    }

    boolean isNumber(byte c) {
        return 47 < c && c < 58;
    }

    boolean isAlphabet(byte c){
        return (64 < c && c < 91) || (96 < c && c < 123);
    }

    byte read() throws Exception {
        if (index == size) {
            size = System.in.read(buffer, index = 0, SIZE);
            if (size < 0) buffer[0] = -1;
        }
        return buffer[index++];
    }
}