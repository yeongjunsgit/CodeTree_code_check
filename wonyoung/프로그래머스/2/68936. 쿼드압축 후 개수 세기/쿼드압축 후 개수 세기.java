import java.util.*;

class Solution {
    static int[] answer = new int[2];
    static int[][] arr;
    public int[] solution(int[][] arrCopy) {
        arr = arrCopy;
        int n = arr.length;
        quadTree(0, 0, n, n, n);
        return answer;
    }
    
    public static void quadTree(int sr, int sc, int er, int ec, int size){
        boolean isZero = false, isOne = false;
        if(size == 1){
            answer[arr[sr][sc]]++;
            return;
        }
        
        for(int i = sr; i < er; i++){
            for(int j = sc; j < ec; j++){
                if(arr[i][j] == 1) isOne = true;
                if(arr[i][j] == 0) isZero = true;
                if(isZero && isOne) break;
            }            
        }
        
        if(isZero && isOne){
            quadTree(sr, sc, (sr + er)/2, (sc + ec)/2, size/2);
            quadTree(sr, (sc + ec)/2, (sr + er)/2, ec, size/2);
            quadTree((sr + er)/2, sc, er, (sc + ec)/2, size/2);
            quadTree((sr + er)/2, (sc + ec)/2, er, ec, size/2);
        } else {
            if(isOne) answer[1]++;
            if(isZero) answer[0]++;
        }
        
    }
    
    
}