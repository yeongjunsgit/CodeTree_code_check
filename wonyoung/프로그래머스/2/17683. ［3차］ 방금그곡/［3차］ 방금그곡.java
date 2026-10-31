import java.util.*;

class Solution {
    public String solution(String m, String[] musicinfos) {
        Map<String, int[]> song = new HashMap<>();
    
        List<String> melody = melodyList(m);
        String[] score = new String[1440];
        int time = -1;
        String answer = "(None)";
        
        for(String musicinfo: musicinfos){
            String[] music = musicinfo.split(",");
            String[] start = music[0].split(":");
            String[] end = music[1].split(":");
            String title = music[2];
            List<String> pattern = melodyList(music[3]);
            
            int s = Integer.parseInt(start[0])*60 + Integer.parseInt(start[1]);
            int e = Integer.parseInt(end[0])*60 + Integer.parseInt(end[1]);
            int n = pattern.size();
            
            for(int i = s; i < e; i++){
                int idx = (i - s) % n;
                score[i] = pattern.get(idx);
            }
            
            int ml = melody.size();
            for(int i = s; i <= e - ml; i++){
                if(isMelody(melody, score, i)){
                    if(time < e - s){
                        answer = title;
                        time = e - s;
                    }
                    break;
                }
            }
        }
        return answer;
    }
    
    public static List<String> melodyList(String m){
        List<String> melody = new ArrayList<>();
        for(int i = 0; i < m.length();i++){
            if(i < m.length() - 1 && m.charAt(i+1) == '#'){
                melody.add(m.substring(i, i+2));
                i++;
            } else {
                melody.add(m.substring(i, i+1));
            }
        }
        return melody;
    }
    
    public static boolean isMelody(List<String> melody, String[] score, int s){
        int len = melody.size();
        
        for(int i = s; i < s + len; i++){
            if(!score[i].equals(melody.get(i-s))) return false;
        }
        return true;
    }
}