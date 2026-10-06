/*
시계방향 90도 회전
이후 행 = 이전 열
이후 열 = N - 1 - 이전 행

반시계방향 90도 회전
이후 행 = N - 1 - 이전 열
이후 열 = 이전 행
*/
import java.util.*;

class Solution {
    int N, M, total;
    
    public boolean solution(int[][] key, int[][] lock) {
        M = key.length;
        N = lock.length;
        total = N * N;

        for (int[] row : lock) total -= Arrays.stream(row).sum();
        
        for (int i = 0; i < 4; i++) {
            for (int r = 1 - M; r < N; r++) {
                for (int c = 1 - M; c < N; c++) {
                    if (unlock(key, lock, r, c)) return true;
                }
            }
            if (i == 3) break;
            key = rotate(key);
        }
        return false;
    }
    
    // 시계방향 90도 회전
    int[][] rotate(int[][] arr) {
        int[][] narr = new int[M][M];
        for (int r = 0; r < M; r++) {
            for (int c = 0; c < M; c++) {
                int nr = c; int nc = M - 1 - r;
                narr[nr][nc] = arr[r][c];
            }
        }
        return narr;
    }
    
    boolean unlock(int[][] key, int[][] lock, int R, int C) {
        int cnt = 0;
        for (int r = 0; r < M; r++) {
            for (int c = 0; c < M; c++) {
                int nr = r + R; int nc = c + C;
                if (0 <= nr && nr < N && 0 <= nc && nc < N) {
                    int check = key[r][c] + lock[nr][nc];
                    if (check == 2 || check == 0) return false; // 돌기가 만나거나 홈을 메꾸지 못한다면
                    
                    if (key[r][c] == 1) cnt++;
                }
            }
        }
        return cnt == total ? true : false;
    }
}