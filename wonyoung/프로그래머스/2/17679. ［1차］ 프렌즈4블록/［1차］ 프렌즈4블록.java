import java.util.*;

class Solution {
    public static int[][] dir = {{0,0},{1,0},{0,1},{1,1}};
    public static char[][] boardblock;
    public static boolean[][] checked;
    public static int M, N, answer;
    public int solution(int m, int n, String[] board) {
        M = m;
        N = n;    
        boardblock = new char[m][n];
        for(int i = 0; i < m; i++) for(int j = 0; j < n; j++) boardblock[i][j] = board[i].charAt(j);
        
        answer = 0;
        while(check()){
            delete();
            move();
        }
        return answer;
    }
    
    public static boolean check(){
        checked = new boolean[M][N];
        boolean flag = false;
        for(int i = 0; i < M-1; i++){
            for(int j = 0; j < N-1; j++){
                if(boardblock[i][j] != '.' && is4Block(i,j)){
                    checked[i][j] = true;
                    flag = true;
                }
            }
        }
        return flag;
    }
    
    public static void delete(){
        for(int i = 0; i < M-1; i++){
            for(int j = 0; j < N-1; j++){
                if(checked[i][j]){
                    for(int[] d: dir){
                        int ni = i + d[0], nj = j + d[1];
                        if(boardblock[ni][nj] != '.'){
                            boardblock[ni][nj] = '.';
                            answer++;
                        }
                    }
                }
            }
        }        
    }
    
    public static void move(){
        for(int i = M-2; i >= 0; i--){
            for(int j = 0; j < N; j++){
                if(boardblock[i][j] != '.') changeDirection(i, j);
            }
        }
    }
    
    
    public static void changeDirection(int i, int j){
        while(i < M-1 && boardblock[i+1][j] == '.'){
            boardblock[i+1][j] = boardblock[i][j];
            boardblock[i++][j] = '.';
        }
    }
    
    public static boolean is4Block(int i, int j){
        char character = boardblock[i][j];
        for(int[] d : dir){
            int ni = i + d[0], nj = j + d[1];
            if(boardblock[ni][nj] != character) return false;
        }
        return true;
    }
}