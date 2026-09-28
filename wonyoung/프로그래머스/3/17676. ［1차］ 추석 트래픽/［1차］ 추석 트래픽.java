import java.util.*;

class Solution {
    public int solution(String[] lines) {
        int n = lines.length;
        int[] starts = new int[n];
        int[] ends = new int[n];
        
        for (int i = 0; i < n; i++) {
            String[] log = lines[i].split(" ");
            String[] time = log[1].split(":");

            int h = Integer.parseInt(time[0]);
            int m = Integer.parseInt(time[1]);
            int sMs = Integer.parseInt(time[2].replace(".", ""));
            int end = (h * 3600 + m * 60) * 1000 + sMs;

            String dur = log[2];
            int t = (int) Math.round(
                Double.parseDouble(dur.substring(0, dur.length() - 1)) * 1000);

            ends[i] = end;
            starts[i] = end - t + 1;
        }
        
        int answer = 0;
        for (int i = 0; i < n; i++) {
            int winStart = ends[i];
            int winEnd = winStart + 999;
            int cnt = 0;
            for (int j = 0; j < n; j++) {
                if (starts[j] <= winEnd && ends[j] >= winStart) cnt++;
            }
            answer = Math.max(answer, cnt);
        }
        return answer;
    }
}