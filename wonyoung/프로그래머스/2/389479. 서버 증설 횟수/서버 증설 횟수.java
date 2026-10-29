import java.util.*;

class Solution {
    public int solution(int[] players, int m, int k) {
        int answer = 0;
        
        Deque<int[]> server = new ArrayDeque<>();
        int l = 0;
        
        for(int i = 0; i < 24; i++){
            if(!server.isEmpty()){
                while(!server.isEmpty() && i >= server.peek()[1]){
                    int[] srv = server.pollFirst();
                    l -= srv[0];
                }
            }
            
            int s = players[i] / m;
            
            if(s - l <= 0) continue;
            
            answer += (s - l);
            server.offerLast(new int[]{s-l, i + k});
            l += (s - l);
            // System.out.printf("time: %d, answer: %d, server.length: %d\n", i, answer, l);
        }
        return answer;
    }
}