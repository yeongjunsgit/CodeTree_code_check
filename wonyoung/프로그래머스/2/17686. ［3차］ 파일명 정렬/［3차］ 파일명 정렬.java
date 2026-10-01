import java.util.*;

class File {
    String head;
    String number;
    String tail;
    Integer index;
    
    File(String head, String number, String tail, int index){
        this.head = head;
        this.number = number;
        this.tail = tail;
        this.index = index;
    }
    
    @Override
    public String toString(){
        return head + number + tail;
    }
}

class Solution {
    public String[] solution(String[] files) {
        int N = files.length;
        PriorityQueue<File> fileName = new PriorityQueue<>((a,b)->{
            int cmp = a.head.compareToIgnoreCase(b.head);
            if(cmp != 0) return cmp;            
            int numCmp = Integer.parseInt(a.number) - Integer.parseInt(b.number);
            if(numCmp != 0) return numCmp;            
            return a.index - b.index;
        });
        
        for(int i = 0; i < N; i++){
            int m = files[i].length();
            StringBuilder sb = new StringBuilder();
            String[] values = new String[3];
            int idx = 0;
            
            for(int j = 0; j < m; j++){
                char c = files[i].charAt(j);
                if(idx == 0 && c >= '0' && c <= '9'){
                    values[idx] = sb.toString();
                    idx++;
                    sb.setLength(0);
                    sb.append(c);
                    continue;
                } 
                
                if(idx == 1 && isNumberEnd(c, sb)){
                    values[idx] = sb.toString();
                    idx++;
                    sb.setLength(0);
                    sb.append(c);
                    continue;
                }
                sb.append(c);
            }
            
            values[idx] = sb.toString();
            File newfile = new File(values[0], values[1], values[2], i);
            fileName.add(newfile);
        }
        
        int cnt = 0;
        String[] answer = new String[N];
        while(!fileName.isEmpty()){
            File fn = fileName.poll();
            String name = fn.tail == null ? fn.head + fn.number : fn.head + fn.number + fn.tail;
            answer[cnt++] = name;
        }
        
        return answer;
    }
    
    public static boolean isNumberEnd(char c, StringBuilder sb){
        return (c < '0' || c > '9') || sb.length() >= 5;
    }
                               
                   
                   
                  
}

