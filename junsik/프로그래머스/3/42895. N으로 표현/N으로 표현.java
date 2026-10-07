import java.util.*;

class Solution {
    public int solution(int N, int number) {
        int MAX = Integer.MAX_VALUE;
        int M = number * N;
        
        Map<Integer, Integer> dp = new HashMap<>(); // 숫자, 횟수
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[0] - o2[0]); // 횟수, 숫자
        List<int[]> Ns = new ArrayList<>(); // 횟수, 숫자
        
        int a = 1; int s = 1; int idx = 1;
        while (s * N <= M) {
            int num = s * N;
            int[] ns = new int[]{idx, num};
            dp.put(num, idx);
            pq.add(ns);
            Ns.add(ns);
            a *= 10;
            s += a;
            idx++;
        }
        
        while (!pq.isEmpty()) {
            int[] now = pq.poll();
            int n = now[1];
            if (n == 0) continue; // 0이라면 횟수만 추가됨.
            
            for (int[] nums : Ns) {
                int nCount = now[0] + nums[0]; int ns = nums[1];
                int[] cases = {n + ns, n - ns, ns - n, n * ns, ns / n, n / ns};

                for (int num : cases) {
                    if (num < -M || num > M) continue;

                    int times = dp.getOrDefault(num, MAX);
                    if (times > nCount) {
                        dp.put(num, nCount);
                        pq.add(new int[]{nCount, num});
                    }
                }
            }
        }
        int answer = dp.getOrDefault(number, 9);
        return answer > 8 ? -1 : answer;
    }
}