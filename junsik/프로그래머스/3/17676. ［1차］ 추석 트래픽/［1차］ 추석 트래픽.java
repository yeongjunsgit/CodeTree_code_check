/*
응답 완료 여부에 관계없이 임의 시간부터 1초간 처리하는 요청의 최대 개수
시작시간 + 처리시간 - 0.001 = 끝시간 (처리시간은 시작, 끝을 포함)
응답완료시간 S를 기준으로 오름차순 정렬
종료시간 i 기준으로 구간 안에 겹치려면
1. 구간 시작 전에 종료되면 안된다.
2. 구간 끝 전에 시작되어야 한다.
*/

class Solution {
    public int solution(String[] lines) {
        int N = lines.length;
        int[] start = new int[N]; // 오름차순
        int[] end = new int[N]; // 오름차순
        for (int i = 0; i < N; i++) {
            int[] log = convert(lines[i]);
            start[i] = log[0];
            end[i] = log[1];
        }
        
        int answer = 0;
        // 종료시간 e 기준으로 1초 뒤의 구간을 바라본다. (e ~ e + 999)
        for (int i = 0; i < N; i++) {
            int e = end[i];
            int count = 0;
            for (int j = i; j < N; j++) {
                if (start[j] > e + 999) continue;
                count++;
            }
            answer = Math.max(answer, count);
        }
        
        return answer;
    }
    
    int[] convert(String line) {
        String[] dateTime = line.split(" ");
        String time = dateTime[1];
        int h = Integer.parseInt(time.substring(0, 2));
        int m = Integer.parseInt(time.substring(3, 5));
        int s = Integer.parseInt(time.substring(6, 8));
        int ms = Integer.parseInt(time.substring(9));
        String under = dateTime[2].substring(0, dateTime[2].length() - 1);
        
        int end = (h * 3600 + m * 60 + s) * 1000 + ms;
        int duration = Integer.parseInt(under.substring(0, 1)) * 1000;
        if (under.length() > 1) duration += Integer.parseInt(under.substring(2, under.length()));
        int start = end - duration + 1;
        return new int[]{start, end};
    }
}