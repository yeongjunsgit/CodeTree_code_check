import java.util.*;

class Solution {
    public long[] solution(long[] numbers) {
        int n = numbers.length;
        long[] answer = new long[n];
        
        for(int i = 0; i < n; i++){
            if(numbers[i] % 2 == 0){
                answer[i] = numbers[i] + 1;
                continue;
            }
            
            int[] ret = toBinary(numbers[i]);
            int zeroIdx = -1;
            for(int j = 50; j >= 0; j--){
                if(ret[j] == 1) continue;
                
                ret[j] = 1;
                zeroIdx = j;
                break;
            }
            
            for(int k = zeroIdx + 1; k >= 0; k--){
                if(ret[k] == 1){
                    ret[k] = 0;
                    break;
                }
            }            
            answer[i] = toTen(ret);
        }
        
        return answer;
    }
    
    static int[] toBinary(long num){
        int[] ret = new int[51];
        String bin = Long.toBinaryString(num);

        int idx = 50;
        while(idx > -1 && num >= 1){
            ret[idx--] = (int)(num % 2);
            num /= 2;
        }
        return ret;
    }
    
    static long toTen(int[] ret){
        long num = 0;
        for(int i = 50; i >= 0; i--){
            if(ret[i] == 1) num += Math.pow(2, 50 - i);
        }
        return num;
    }
}