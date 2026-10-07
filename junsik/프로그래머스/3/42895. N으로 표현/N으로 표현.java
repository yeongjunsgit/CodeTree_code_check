import java.util.*;

class Solution {
    public int solution(int N, int number) {
        List<Set<Integer>> dp = new ArrayList<>();
        for (int i = 0; i <= 8; i++) dp.add(new HashSet<>());

        int repeated = 0;
        for (int i = 1; i <= 8; i++) {
            Set<Integer> current = dp.get(i);

            // N, NN, NNN, ... 형태의 수
            repeated = repeated * 10 + N;
            current.add(repeated);

            // 총 i번의 사용을 j번과 i-j번으로 나눕니다.
            for (int j = 1; j < i; j++) {
                Set<Integer> left = dp.get(j);
                Set<Integer> right = dp.get(i - j);

                for (int a : left) {
                    for (int b : right) {
                        current.add(a + b);
                        current.add(a - b);
                        current.add(a * b);
                        if (b != 0) current.add(a / b);
                    }
                }
            }

            if (current.contains(number)) {
                return i;
            }
        }
        return -1;
    }
}