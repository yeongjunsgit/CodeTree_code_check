import java.util.*;
import java.io.*;

public class Main {
    public static Map<Integer, int[]> directions = new HashMap<>();
    public static int N, r, c, d;
    public static int[] dirOrder;
    public static int[][] ocean;
    public static boolean[][] visited;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        r = Integer.parseInt(st.nextToken()) - 1;
        c = Integer.parseInt(st.nextToken()) - 1;
        d = Integer.parseInt(st.nextToken());
    
        ocean = new int[N][N];
        visited = new boolean[N][N];
        
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < N; j++) ocean[i][j] = Integer.parseInt(st.nextToken());
        }

        directions.put(1, new int[]{-1, 0});    // 상
        directions.put(2, new int[]{1, 0});     // 하
        directions.put(3, new int[]{0, -1});    // 좌
        directions.put(4, new int[]{0, 1});     // 우
        
        dirOrder = new int[]{3, 2, 4, 1};  // 좌, 하, 우, 상

        while(true){
            visited[r][c] = true;
            System.out.printf("%d %d\n", r+1, c+1);

            //  1. 인접 탐험
            boolean isMovable = false;
            int[] explodeOrder = setDirection(d, dirOrder);

            for(int idx: explodeOrder){ 
                int nr = r + directions.get(idx)[0];
                int nc = c + directions.get(idx)[1];

                if(inOcean(nr, nc) && ocean[nr][nc] == 0 && !visited[nr][nc]){
                    visited[nr][nc] = true;
                    r = nr;
                    c = nc;
                    d = idx;
                    isMovable = true;
                    break;
                }
            }

            // 2. 가까운 바다로 이동.
            boolean shiftNearSea = false;

            if(!isMovable){
                int[] result = seekNewPlace(d, r, c);
                d = result[0];
                r = result[1];
                c = result[2];
                if(result[1] != -1 || result[2] != -1) shiftNearSea = true;
            }

            if(!isMovable && !shiftNearSea) break;
        }

    }

    public static void printOcean(boolean[][] visited){
        for(int i = 0; i < N; i++) System.out.println(Arrays.toString(visited[i]));
    }

    public static int[] setDirection(int d, int[] dirOrder){
        int cur = -1, left, right, turn180;
        for(int i = 0; i < 4; i++){
            if(dirOrder[i] == d){
                cur = i;
                break;
            }
        }

        left = (cur + 1) % 4;
        right = (cur - 1 + 4) % 4;
        turn180 = (cur + 2) % 4;

        return new int[]{dirOrder[cur], dirOrder[left], dirOrder[right], dirOrder[turn180]};
    }

    public static boolean inOcean(int nr, int nc){
        return 0 <= nr && nr < N && 0 <= nc && nc < N; 
    }

    // 적합한 목적지를 찾는다. 
    public static int[] findTarget(int r, int c){
        int[][] map = new int[N][N];
        for(int i = 0; i < N; i++) Arrays.fill(map[i], -1);

        PriorityQueue<int[]> points = new PriorityQueue<>((a,b) -> {
            if(a[0] != b[0]) return a[0] - b[0];
            if(a[1] != b[1]) return a[1] - b[1];
            return a[2] - b[2];
        });

        map[r][c] = 0;
        points.add(new int[]{0, r, c});

        while(!points.isEmpty()){
            int[] point = points.poll();

            // poll 시점에 (거리,행,열) 정렬이 되어 있는 상태에서 방문한 곳이 아니라면 행, 열 번호가 가장 작은 칸이 됨. 
            if(!(point[1] == r && point[2] == c) && !visited[point[1]][point[2]]){
                return new int[]{point[1], point[2]};
            }

            for(int i = 1; i <= 4; i++){
                int nr = point[1] + directions.get(i)[0];
                int nc = point[2] + directions.get(i)[1];

                if(inOcean(nr, nc) && ocean[nr][nc] == 0 && map[nr][nc] == -1){
                    map[nr][nc] = point[0] + 1;
                    points.add(new int[]{map[nr][nc], nr, nc});
                }
            }
        }
        //  갈곳 없음
        return null;
    }

    // 목적지에서의 거리를 구한다.
    public static int[][] distFromTarget(int tr, int tc){
        int[][] dist = new int[N][N];
        for(int[] row : dist) Arrays.fill(row, -1);

        Deque<int[]> q = new ArrayDeque<>();
        dist[tr][tc] = 0;
        q.offerLast(new int[]{tr, tc});

        while(!q.isEmpty()){
            int[] cur = q.pollFirst();
            for(int i = 1; i <= 4; i++){
                int nr = cur[0] + directions.get(i)[0];
                int nc = cur[1] + directions.get(i)[1];
                if(inOcean(nr, nc) && ocean[nr][nc] == 0 && dist[nr][nc] == -1){
                    dist[nr][nc] = dist[cur[0]][cur[1]] + 1;
                    q.offerLast(new int[]{nr, nc});
                }
            }
        }
        return dist;
    }


    public static int[] seekNewPlace(int d, int r, int c){
        int[] target = findTarget(r, c);
        if(target == null) return new int[]{-1, -1, -1};

        int[][] dist = distFromTarget(target[0], target[1]);

        int curR = r, curC = c, lastDir = d;

        // target에 도착할 때까지
        while(curR != target[0] || curC != target[1]){
            for(int idx : dirOrder){ // 좌,하,우,상
                int nr = curR + directions.get(idx)[0];
                int nc = curC + directions.get(idx)[1];

                // 거리가 줄어드는 경로여야 이동.
                if(inOcean(nr, nc) && ocean[nr][nc] == 0 && dist[nr][nc] == dist[curR][curC] - 1){
                    curR = nr;
                    curC = nc;
                    lastDir = idx;
                    break;
                }
            }
        }
        return new int[]{lastDir, curR, curC};
    }
}