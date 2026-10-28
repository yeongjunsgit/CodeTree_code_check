import java.util.*;

class Solution {
    static Map<Integer, List<int[]>> route = new HashMap<>();
    static final int INF = Integer.MAX_VALUE;
    public int solution(int N, int[][] road, int K) {
        int answer = 0;
        
        for(int[] track: road){
            int from = track[0], to = track[1], d = track[2];
            route.computeIfAbsent(from, k -> new ArrayList<>()).add(new int[]{to, d});
            route.computeIfAbsent(to, k -> new ArrayList<>()).add(new int[]{from, d});
        }
                
        int[] distance = new int[N+1];
        Arrays.fill(distance, INF);
        Deque<int[]> q = new ArrayDeque<>();
        q.offerLast(new int[]{1, 0});
        distance[1] = 0;
        
        while(!q.isEmpty()){
            int[] cur = q.pollFirst();
            
            for(int[] next: route.get(cur[0])){
                int point = next[0], cost = next[1];
                if(distance[point] > cur[1] + cost){
                    distance[point] = cur[1] + cost;
                    q.offerLast(new int[]{point, cur[1] + cost});
                }
            }
        }
        
        for(int i = 1; i <= N;i++) if(distance[i] <= K) answer++;
        return answer;
    }
}