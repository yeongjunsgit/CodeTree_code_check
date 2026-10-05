
import java.util.*;

class Node {
    Node[] child = new Node[26];
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
        for (int i = 0; i < word.length(); i++) {
            int idx = word.charAt(i) - 'a';
            if (node.child[idx] == null) node.child[idx] = new Node();
            node = node.child[idx];
            node.cnt++;
        }
    }
    
    int search(String word) {
        int cnt = 0;
        Node node = root;
        for (int i = 0; i < word.length(); i++) {
            int idx = word.charAt(i) - 'a';
            node = node.child[idx];
            cnt++;
            if (node.cnt == 1) break;
        }
        return cnt;
    }
}