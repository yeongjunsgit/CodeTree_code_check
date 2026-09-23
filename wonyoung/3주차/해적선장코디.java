import java.util.*;
import java.io.*;

public class Main {
    public static PriorityQueue<int[]> warships;
    public static Map<Integer, int[]> shipMap = new HashMap<>();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        warships = new PriorityQueue<>((a,b) -> {
            if(a[0] != b[0]) return b[0] - a[0];
            if(a[1] != b[1]) return a[1] - b[1];
            return a[2] - b[2];
        });

        int T = Integer.parseInt(st.nextToken());
        for(int i = 0; i < T; i++){
            st = new StringTokenizer(br.readLine());
            int code = Integer.parseInt(st.nextToken());

            switch(code){
               case 100: readyAttack(st); break;
               case 200: support(st, i);  break;
               case 300: change(st); break;
               case 400: fire(i, sb); break;
            }
        }
        System.out.println(sb.toString());
    }

    public static void readyAttack(StringTokenizer st){
        int N = Integer.parseInt(st.nextToken());
        while(N-- > 0){
            int id = Integer.parseInt(st.nextToken());
            int p = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());

            shipMap.put(id, new int[]{p, r, 0, 0}); // id: 공격력, 재사용시간, 다음사용시간, version
            warships.add(new int[]{p, id, 0});  // 공격력, id, version 
        }
    }

    public static void support(StringTokenizer st, int i){
        int id = Integer.parseInt(st.nextToken());
        int p = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());
        shipMap.put(id, new int[]{p, r, i, 0});
        warships.add(new int[]{p, id, 0});
    }

    public static void change(StringTokenizer st){
        int id = Integer.parseInt(st.nextToken());
        int p = Integer.parseInt(st.nextToken());

        int[] info = shipMap.get(id);
        int newVersion = info[3] + 1;
        shipMap.put(id, new int[]{p, info[1], info[2], newVersion});
        warships.add(new int[]{p, id, newVersion});
    }

    public static void fire(int i, StringBuilder sb){
        int totalDamage = 0;
        int totalShips = 0;
        List<Integer> ships = new ArrayList<>();   // 발사한 함선
        List<Integer> checked = new ArrayList<>(); // 발사를 위해 체크한 함선
        
        while(!warships.isEmpty() && totalShips < 5){
            int[] ship = warships.poll();
            int p = ship[0], id = ship[1], version = ship[2];

            int[] info = shipMap.get(id);
            int r = info[1], next = info[2], currentVersion = info[3]; // 재사용시간, 다음사용시간, version

            // 함선 정보가 최신 버전이 아니라면 
            if(version < currentVersion) continue;

            checked.add(id);
            // 발사 가능한 상태이고 5척이 되지 않았다면
            if(next <= i){
                totalDamage += p;
                totalShips++;
                ships.add(id);
                shipMap.put(id, new int[]{p, r, i + r, version});
            }
        }

        for(int num: checked){
            int[] info = shipMap.get(num);
            warships.add(new int[]{info[0], num, info[3]});
        }
        
        sb.append(totalDamage).append(" ").append(totalShips);
        if(!ships.isEmpty()) for(int num: ships) sb.append(" ").append(num);
        sb.append("\n");
    }
}