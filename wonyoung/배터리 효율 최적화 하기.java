import java.util.Scanner;
import java.util.*;

public class Main {
    static int[][] board, checked;
    static List<int[]> points = new ArrayList<>();
    static int[][] dir = {{1,0},{-1,0},{0,1},{0,-1}};
    static int N, M, best = -1000*10;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        board = new int[N][M];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                board[i][j] = sc.nextInt();
                points.add(new int[]{i,j});
            }
        }

        int K = N*M;
        // 두 배터리 셀 무작위 생성
        for(int k = 0; k < K-1; k++){
            for(int l = k+1; l < K; l++) calcScore(k,l);
        }
        System.out.println(best);

    }

    static void calcScore(int k, int l){
        checked = new int[N][M];
        int sum = 0; 

        int[] A = points.get(k); 
        int[] B = points.get(l);

        checked[A[0]][A[1]] = 1;
        checked[B[0]][B[1]] = 1;

        sum += 2*board[A[0]][A[1]];
        sum += 2*board[B[0]][B[1]];

        dfs(2, 0, sum);
    }


    static void dfs(int module, int cnt, int sum) {
        if (cnt == 3) {
            if (!isConnected(module)) return;
            if (module == 2) dfs(3, 0, sum);       
            else best = Math.max(best, sum);       
            return;
        }
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                if (checked[r][c] != 0) continue;
                if (!cango(r, c, module)) continue;
                checked[r][c] = module;
                dfs(module, cnt + 1, sum + board[r][c]);
                checked[r][c] = 0;
            }
        }
    }

    static boolean isConnected(int module){
        boolean[][] visited = new boolean[N][M];
        int sr = -1, sc = -1;

        for (int i = 0; i < N; i++)
            for (int j = 0; j < M; j++)
                if (checked[i][j] == 1 || checked[i][j] == module) { 
                    sr = i; sc = j;
                    break; 
                }

        int cnt = 0;
        Deque<int[]> q = new ArrayDeque<>();
        q.add(new int[]{sr, sc});
        visited[sr][sc] = true;

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            cnt++;
            for (int d = 0; d < 4; d++) {
                int nr = cur[0] + dir[d][0], nc = cur[1] + dir[d][1];
                if (!inCell(nr, nc) || visited[nr][nc]) continue;
                if (checked[nr][nc] == 1 || checked[nr][nc] == module) {
                    visited[nr][nc] = true;
                    q.add(new int[]{nr, nc});
                }
            }
        }
        return cnt == 5;
    }

    static boolean cango(int r, int c, int module){
        for(int[] d: dir){
            int nr = r + d[0], nc = c + d[1];
            if(inCell(nr, nc) && (checked[nr][nc] == 1 || checked[nr][nc] == module)) return true;
        }
        return false;
    }

    static boolean inCell(int r, int c){
        return 0 <= r && r < N && 0 <= c && c < M;
    }
}