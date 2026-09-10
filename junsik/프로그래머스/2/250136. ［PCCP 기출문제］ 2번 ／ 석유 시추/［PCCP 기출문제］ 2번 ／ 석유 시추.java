import java.util.*;

class Oil {
    int r, c;
    Oil (int r, int c) {
        this.r = r; this.c = c;
    }
}

class Solution {
    int N, M;
    int[][] drc = {{-1, 0}, {0, -1}, {0, 1}, {1, 0}};
    int[] oils; // 열 당 석유량
    
    public int solution(int[][] land) {
        N = land.length; M = land[0].length;
        boolean[][] visited = new boolean[N][M];
        oils = new int[M];
        
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                if (land[r][c] == 0 || visited[r][c]) continue;
                findOil(r, c, land, visited);
            }
        }
        
        int answer = 0;
        for (int oil : oils) answer = Math.max(answer, oil);
        return answer;
    }
    
    void findOil(int sr, int sc, int[][] land, boolean[][] visited) {
        Oil start = new Oil(sr, sc);
        Deque<Oil> queue = new ArrayDeque<>();
        Set<Integer> cols = new HashSet<>();
        visited[sr][sc] = true;
        queue.add(start);
        cols.add(sc);
        
        int total = 1;
        while (!queue.isEmpty()) {
            Oil oil = queue.poll();
            int r = oil.r; int c = oil.c;
            for (int[] dir : drc) {
                int nr = r + dir[0]; int nc = c + dir[1];
                if (0 <= nr && nr < N && 0 <= nc && nc < M) {
                    if (land[nr][nc] == 0 || visited[nr][nc]) continue;
                    visited[nr][nc] = true;
                    queue.add(new Oil(nr, nc));
                    total++;
                    cols.add(nc);
                }
            }
        }
        for (int col : cols) oils[col] += total;
    }
}