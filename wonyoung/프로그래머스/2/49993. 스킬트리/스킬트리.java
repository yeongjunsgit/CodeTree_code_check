import java.util.*;

class Solution {
    public int solution(String skill, String[] skill_trees) {
        int answer = 0;
        int n = skill.length();
        Map<Character, Character> skill_order = new HashMap<>();
        
        for(int i = 0; i < n; i++){
            char current_skill = skill.charAt(i);
            char before_skill = i == 0 ?  '?' : skill.charAt(i-1);
            skill_order.put(current_skill, before_skill);
        }
        
        for(String skill_tree: skill_trees){
            char step = '?';
            boolean flag = true;
            
            for(char skill_: skill_tree.toCharArray()){
                if(!skill_order.containsKey(skill_)) continue;
                
                if(skill_order.get(skill_) != step){
                    flag = false;
                    break;
                } 
                
                step = skill_;
            }
            
            if(flag) answer++;
        }
        return answer;
    }
}