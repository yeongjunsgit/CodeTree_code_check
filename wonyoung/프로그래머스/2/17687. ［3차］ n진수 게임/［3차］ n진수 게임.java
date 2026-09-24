import java.util.*;

class Solution {
    public String solution(int n, int t, int m, int p) {
        
        StringBuilder sb = new StringBuilder();
        int num = 0;
        int idx = 0;
        
        while(true){
            String baseN = Integer.toString(num, n).toUpperCase();
            
            for(int i = 0; i < baseN.length(); i++){
                if(idx + 1 == p) sb.append(baseN.charAt(i));
                
                if(sb.length() == t) return sb.toString();
                idx = (idx + 1) % m;
            }
            num++;
        }
    }
}