import java.util.*;

class Node {
    Map<Character, Node> child = new HashMap<>();
    boolean endOfword;
    int cnt;
}

class Trie {
    Node root = new Node();
    
    void insert(String s){
        Node cur = root;
        for(char c: s.toCharArray()){
            cur = cur.child.computeIfAbsent(c, k -> new Node());
            cur.cnt++;
        }
        cur.endOfword = true;
    }
    
    int findStartWith(String s){
        Node cur = root;
        int idx = 0;
        for(char c: s.toCharArray()){
            cur = cur.child.get(c);
            idx++;
            
            if(cur.cnt == 1) return idx;
        }
        return idx;
    }
}

class Solution {
    public int solution(String[] words) {
        int answer = 0;
        Trie trie = new Trie();
        
        for(String word: words) trie.insert(word);
        for(String word: words) answer += trie.findStartWith(word);
        return answer;
    }
}