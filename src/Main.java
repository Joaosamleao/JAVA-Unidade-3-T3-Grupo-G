import java.io.FileInputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    static int n;
    static int[][] cap;

    public static int bfs(int s, int t, int[] parent) {
        Arrays.fill(parent, -1);
        parent[s] = s;
        Queue<Integer> q = new LinkedList<>();
        q.add(s);

        int[] minCap = new int[n];
        minCap[s] = Integer.MAX_VALUE;

        while (!q.isEmpty()) {
            int cur = q.poll();

            for (int next = 0; next < n; next++) {
                if (parent[next] == -1 && cap[cur][next] > 0) {
                    parent[next] = cur;
                    minCap[next] = Math.min(minCap[cur], cap[cur][next]);

                    if (next == t) {
                        return minCap[t];
                    }
                    q.add(next);
                }
            }
        }
        return 0;
    }

    public static int edmondsKarp(int s, int t) {
        int maxFlow = 0;
        int[] parent = new int[n];
        int newFlow;

        while ((newFlow = bfs(s, t, parent)) > 0) {
            maxFlow += newFlow;
            int cur = t;

            while (cur != s) {
                int prev = parent[cur];
                cap[prev][cur] -= newFlow;
                cap[cur][prev] += newFlow;
                cur = prev;
            }
        }
        return maxFlow;
    }

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("dados/entrada_do_problema.txt"));

        Scanner sc = new Scanner(System.in);
        int network = 1;

        while (sc.hasNextInt()) {
            n = sc.nextInt();

            if (n == 0) {
                break;
            }

            int s = sc.nextInt() - 1;
            int t = sc.nextInt() - 1;
            int c = sc.nextInt();

            cap = new int[n][n];

            for (int i = 0; i < c; i++) {
                int u = sc.nextInt() - 1;
                int v = sc.nextInt() - 1;
                int bandwidth = sc.nextInt();

                cap[u][v] += bandwidth;
                cap[v][u] += bandwidth;
            }

            int maxBandwidth = edmondsKarp(s, t);

            System.out.println("Network " + network);
            System.out.println("The bandwidth is " + maxBandwidth + ".");
            System.out.println();

            network++;
        }

        sc.close();
    }
}