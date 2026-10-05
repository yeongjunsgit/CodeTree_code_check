
import java.util.*;

class Node {
    Map<Character, Node> child = new HashMap<>();
    int cnt = 0;
}

class Solution {
    final Node root = new Node();
    
    public int solution(String[] words) {
        
        for (String word : words) {
            makeDict(word);
        }
        
        int answer = 0;
        for (String word : words) {
            answer += search(word);
        }
        return answer;
    }
    
    void makeDict(String word) {
        Node node = root;
        for (char ch : word.toCharArray()) {
            node = node.child.computeIfAbsent(ch, c -> new Node());
            node.cnt++;
        }
    }
    
    int search(String word) {
        int cnt = 0;
        Node node = root;
        for (char ch : word.toCharArray()) {
            node = node.child.get(ch);
            cnt++;
            if (node.cnt == 1) break;
        }
        return cnt;
    }
}