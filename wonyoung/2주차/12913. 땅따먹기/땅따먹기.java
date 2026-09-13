import java.util.*;

class Solution {
    int solution(int[][] land) {
        int N = land.length;
        int[][] dp = new int[N][4];
        
        for(int i = 0; i < N; i++){
            for(int j = 0; j < 4; j++){
                if(i == 0){
                    dp[i][j] = land[i][j]; 
                    continue;
                } 
                
                for(int k = 0; k < 4; k++){
                    if(k == j) continue;
                    dp[i][j] = Math.max(dp[i-1][k] + land[i][j], dp[i][j]);
                }
            }
        }


        return Arrays.stream(dp[N-1]).max().getAsInt();
    }
}