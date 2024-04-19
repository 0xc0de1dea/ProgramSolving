import java.util.HashMap;

/**
 * Written by 0xc0de1dea
 * Email : 0xc0de1dea@gmail.com
 */

 public class Main {
    public static int upperbound(int x, int v, double target, int left, int right){
        while (left < right){
            int m = (left + right) / 2;
            double elapsedTime = 1 + (x - m) / (double)v;

            if (elapsedTime < target){
                right = m;
            } else {
                left = m + 1;
            }
        }

        return left <= target ? 0 : left;
    }

    public static void main(String[] args) throws Exception {
        //System.setIn(new java.io.FileInputStream("input.in"));
        Reader in = new Reader();
        StringBuilder sb = new StringBuilder();
        
        HashMap<Integer, Integer> rowID = new HashMap<>(3000);
        int n = in.nextInt();
        int m = in.nextInt();
        int q = in.nextInt();

        for (int i = 0; i < n; i++){
            rowID.put(i, i);
        }

        int[][] board = new int[n][m];

        for (int i = 0; i < n; i++){
            for (int j = 0; j < m; j++){
                board[i][j] = in.nextInt();
            }
        }

        for (int i = 0; i < q; i++){
            int query = in.nextInt();

            if (query == 0){
                int r = in.nextInt();
                int c = in.nextInt();
                int k = in.nextInt();
                board[rowID.get(r)][c] = k;
            } else {
                int r1 = in.nextInt();
                int r2 = in.nextInt();
                int tmp = rowID.get(r1);
                rowID.put(r1, rowID.get(r2));
                rowID.put(r2, tmp);
            }
        }

        for (int i = 0; i < n; i++){
            for (int j = 0; j < m; j++){
                sb.append(board[rowID.get(i)][j]).append(' ');
            }

            sb.append('\n');
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
        while ((c = read()) < 32) { if (size < 0) return "endLine"; }
        do sb.appendCodePoint(c);
        while ((c = read()) > 32); // SPACE 분리라면 >로, 줄당 분리라면 >=로
        return sb.toString();
    }

    char nextChar() throws Exception {
        byte c;
        while ((c = read()) <= 32); // SPACE 분리라면 <=로, SPACE 무시라면 <로
        return (char)c;
    }
    
    int nextInt() throws Exception {
        int n = 0;
        byte c;
        boolean isMinus = false;
        while ((c = read()) <= 32) { if (size < 0) return -1; }
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
        while ((c = read()) <= 32) { if (size < 0) return -12345; }
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