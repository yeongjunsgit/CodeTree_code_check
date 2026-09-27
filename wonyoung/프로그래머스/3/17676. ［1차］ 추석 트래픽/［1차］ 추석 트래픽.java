import java.util.*;

class Solution {
    public static final int DAY = 1000*60*60*24;
    public int solution(String[] lines) {
        int[] prefixSum = new int[DAY + 1];
        
        for(String line: lines){
            String[] log = line.split(" ");
            String[] time = log[1].split(":");
            double s = Double.parseDouble(log[2].substring(0, log[2].length() - 1));
            int t = (int) (s * 1000.0);
            
            int[] index = setIndex(time, t);
            int start = index[0], end = index[1];
            prefixSum[start]++;
            prefixSum[end+1]--;
        }
        
        int maxReq = 0;
        for(int i = 0; i <= DAY; i++){
            if(i > 0) prefixSum[i] += prefixSum[i-1];
            maxReq = Math.max(maxReq, prefixSum[i]);
        }
        
        return maxReq;
    }
    
    public static int[] setIndex(String[] time, int t){
        int h = Integer.parseInt(time[0]);
        int m = Integer.parseInt(time[1]);
        int ms = (int)(Double.parseDouble(time[2])*1000);
        int end = h*60*60*1000 + m*60*1000 + ms;
        int start = end - t < 1000 ? 0 : end - t - 998;
        return new int[]{start, end};
    }
}