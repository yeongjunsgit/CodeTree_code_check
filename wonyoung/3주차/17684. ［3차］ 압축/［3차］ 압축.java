import java.util.*;

class Solution {
    public int[] solution(String msg) {
        TreeMap<Integer, String> dict = new TreeMap<>();
        Map<String, Integer> map = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        List<Integer> answer = new ArrayList<>();
        
        for(int i = 1; i <= 26; i++){
            dict.put(i, String.valueOf((char)('A' + i - 1)));
            map.put(String.valueOf((char)('A' + i - 1)), i);
        }
        
        int N = msg.length();
        int idx = 0;
        
        while(idx < N){
            sb.append(msg.charAt(idx));
            idx++;

            while(idx < N && map.containsKey(sb.toString() + msg.charAt(idx))){
                sb.append(msg.charAt(idx));
                idx++;
            }

            answer.add(map.get(sb.toString()));

            if(idx < N){
                int newKey = dict.lastKey() + 1;
                String newStr = sb.toString() + msg.charAt(idx);
                dict.put(newKey, newStr);
                map.put(newStr, newKey);
            }

            sb.setLength(0);
        }

        
        return answer.stream().mapToInt(Integer::intValue).toArray();
    }
}