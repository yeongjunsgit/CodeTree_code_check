import java.util.*;

class Solution {
    public String[] solution(String[] orders, int[] course) {
        List<Map<String, Integer>> list = new ArrayList<>();
        for(int i = 0; i <= 10; i++) list.add(new HashMap<>());
        
        List<String> answer = new ArrayList<>();
        for(int c: course){
            for(String order: orders){
                if(order.length() < c) continue;
                
                char[] o = order.toCharArray();
                Arrays.sort(o);
                
                dfs(list.get(c), "", o, 0, c, new boolean[o.length]);
            }
            
            Map<String, Integer> map = list.get(c);
            if(map.size() == 0) continue;
            int max = Collections.max(map.values());
            if(max < 2) continue;
            
            for(String key: map.keySet()){
                if(max == map.get(key)) answer.add(key);
            }
        }
        Collections.sort(answer);   

        return answer.stream().toArray(String[]::new);
    }
    
    public void dfs(Map<String, Integer> map, String str, char[] o, int pos, int n, boolean[] visited){
        if(str.length() == n){
            map.put(str, map.getOrDefault(str, 0) + 1);
            return;
        }
        
        for(int i = pos; i < o.length; i++){
            if(visited[i]) continue;
            visited[i] = true;
            dfs(map, str + String.valueOf(o[i]), o, i+1, n, visited);
            visited[i] = false;
        }
    }
}