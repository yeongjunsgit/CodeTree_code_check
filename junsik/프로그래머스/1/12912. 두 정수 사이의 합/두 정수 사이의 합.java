class Solution {
    public long solution(int a, int b) {
        long answer = 0;
        int c = Math.min(a, b); int d = Math.max(a, b);
        for (int s = c; s <= d; s++) answer += s;
        return answer;
    }
}