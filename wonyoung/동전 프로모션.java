import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int INF = Integer.MAX_VALUE;

        int[] dp = new int[m+1];
        Arrays.fill(dp, INF);
        dp[0] = 0;

        List<Integer> AType = new ArrayList<>();
        List<Integer> BType = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            char cType = sc.next().charAt(0);
            int cValue = sc.nextInt();
            if(cType == 'A'){
                AType.add(cValue);
            } else {
                BType.add(cValue);
            }
        }

        AType.sort(Comparator.reverseOrder());
        BType.sort(Comparator.reverseOrder());

        // B 타입 동전 한번씩만 활용하기.
        for(int b: BType){
            if(b <= m) dp[b] = 1;
            for(int i = m; i > b; i--){
                if(dp[i] != INF && i + b <= m){
                    dp[i + b] = Math.min(dp[i] + 1, dp[i+b]);
                }
            }
        }

        // A 타입 동전 사용하기
        for(int a: AType){
            for(int i = a; i <= m; i++){
                if(dp[i - a] != INF){
                    dp[i] = Math.min(dp[i], dp[i-a] + 1);
                }
            }
        }
        System.out.println(dp[m] == INF ? -1 : dp[m]);
    }
}