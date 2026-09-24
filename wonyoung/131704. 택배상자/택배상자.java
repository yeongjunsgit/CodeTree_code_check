import java.util.*;

class Solution {
    public int solution(int[] order) {
        int N = order.length;
        int answer = 0;
        PriorityQueue<int[]> conveyor = new PriorityQueue<>((a,b) -> a[0] - b[0]);
        Deque<Integer> sub = new ArrayDeque<>();
        
        for(int i = 0; i < N; i++) conveyor.add(new int[]{order[i], i});

        int idx = 0;
        
        while(idx < N){
            // 우선 보조 컨베이어 벨트를 확인해.
            while(!sub.isEmpty()){
                int check = sub.peekLast();
                if(check == idx){
                    sub.pollLast();
                    idx++;
                } else {
                    if(conveyor.isEmpty()){
                        return idx;
                    }
                    break;
                }
                
            }
            
            if(!conveyor.isEmpty()){
                int[] post = conveyor.poll();
                int num = post[1];

                // 실어야 하는 순서이면 실어나르기.
                if(num == idx){
                    idx++;
                    continue;
                }
                // 실어야 하는 순서가 아니면 다른 곳에 보관
                sub.offerLast(num);
            }
            
//             System.out.println("conveyor: " + conveyor);
//             System.out.println("sub: " + sub);
//             System.out.println("idx: " + idx);
//             System.out.println();
        } 
        
        return idx;
    }
}