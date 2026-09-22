
# codeTree 해적 선장 코디 문제 풀이

## 문제

해적 선장 코디는 함대를 이끌고 대형 선박을 침몰시키려 합니다.

코디는 대형 함선을 침몰시키기 위해 총 *T*개의 명령을 순차적으로 내립니다. 각 명령은 아래 네 가지 유형 중 하나입니다.

> **공격 준비**

- 코디가 대형 함선을 공격하기 위해 *N*척의 선박에 사격 준비를 지시합니다.
- 각 선박은 고유 선박 번호 *i**d**i*, 공격력 *p**i*, 재장전 시간 *r**i*를 가지며, 초기 상태는 모두 **사격 대기**입니다.

> **지원요청**

- 추가 병력을 요청하여 새로운 선박이 합류합니다.
- 새로 합류한 선박은 **사격 대기** 상태로 추가되며, 선박 번호 *i**d*, 공격력 *p*, 재장전 시간 *r*를 가집니다.

> **함포 교체**

- *i**d*번 선박의 함포를 교체합니다.
- 교체 후 해당 선박의 공격력은 *pw*가 됩니다.

> **공격 명령**

- **사격 대기** 상태인 선박 중 공격력이 가장 높은 선박 최대 5척에 일제 사격을 명령합니다. 공격력이 같다면 선박 번호 *i**d*가 작은 선박을 우선 선택하며, 총 피해가 최대가 되도록 선박을 고릅니다.
- 사격에 참여한 선박들의 공격력 합만큼 대형 함선에 피해를 줍니다.
- 사격한 선박은 즉시 재장전에 들어가며, 사격 시점을 포함해 *r*시간이 경과하면 다시 **사격 대기** 상태로 전환됩니다.
- 재장전 중인 선박은 공격할 수 없습니다.

각 명령은 1시간 단위로 실행됩니다. 즉, *i*번째 명령이 수행된 뒤 1시간이 지나면 *i*+1번째 명령이 수행됩니다.

**공격 명령**이 주어질 때마다, 해당 차례의 총 피해량, 사격에 참여한 선박 수, 그리고 사격 우선순위에 따른 사격 선박들의 *i**d*를 출력하는 프로그램을 작성하세요.

### 입력

### 제한 조건

### 출력

### 입력 예제

### 예제 1

입력

```
7
100 3 10 5 2 3 7 1 8 7 3
400
400
200 2 9 2
400
300 3 6
400
```

출력

```
19 3 3 8 10
7 1 3
28 4 2 3 8 10
20 3 2 3 10
```

### 예제 2

입력

```
6
100 6 1 10 2 2 10 2 3 9 1 4 8 1 5 8 3 6 7 1
400
400
300 6 12
200 7 9 2
400
```

출력

```
45 5 1 2 3 4 5
24 3 3 4 6
50 5 6 1 2 3 7
```



## 풀이 코드

```java
/*
2개의 우선순위 큐를 이용하여 순환하는 방식으로 제어한다.
1. 현재 공격이 가능한 함대를 다루는 우선순위 큐 (우선순위 기준 : 공격력이 가장 높은, 같다면 선박 번호가 낮은)
2. 사격 대기중인 함대를 다루는 우선순위 큐 (우선순위 기준 : 재장전 완료 시간이 가장 낮은)
현재 시간을 계속해서 추적한다. 그리고 이를 기반으로 공격이 가능한 함대를 출력해주는 방식으로 한다.

선박의 함포를 교체하면 선박의 공격력이 바뀐다. 하지만, 우선순위 큐 안에 들어있는 함선의 공격력을 수정하는 것은 어렵다.
따라서, 1차원 배열에 현재 공격력을 기록해두고, 함포가 교체되면 교체된 공격력으로 1번 우선순위 큐에 넣는다. 그리고 우선순위 큐에서 값을 뺄 때 현재 공격력과 똑같은 경우에만 출력하고 카운트하게 한다.
이렇게 하면 편하게 우선순위 큐를 관리하면서 함선의 공격력을 제어할 수 있다.

*/

import java.util.*;
import java.io.*;

class Ship {
    int id;
    int p;
    int r;
    int rT;

    Ship (int id, int p, int r, int rT) {
        this.id = id;
        this.p = p;
        this.r = r;
        this.rT = rT;
    }

}


public class Main {
    public static void main(String[] args) throws Exception {
        int T, command;
        // 준비 완료된 함선들을 담을 우선순위 큐 readied
        PriorityQueue<Ship> readied = new PriorityQueue<>(
            (a, b) -> {
                if (a.p == b.p) {
                    return Integer.compare(a.id, b.id);
                }
                return Integer.compare(b.p, a.p);
            }
        );

        // 준비 중인 함선들을 담을 우선순위 큐 readying
        PriorityQueue<Ship> readying = new PriorityQueue<>(
            (a, b) -> {
                if (a.rT == b.rT) {
                    return Integer.compare(a.id, b.id);
                }
                return Integer.compare(a.rT, b.rT);
            }
        );
        
        // 함선들의 공격력을 담을 HashMap nowPower
        HashMap<Integer, Integer> nowPower = new HashMap<>();
        // 함선들의 현재 대기시간을 담을 HashMap nowRT
        HashMap<Integer, Integer> nowRT = new HashMap<>();
        // 함선들의 재장전시간을 담을 HashMap shipReload
        HashMap<Integer, Integer> shipReload = new HashMap<>();

        // 값을 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        for (int t=0; t<T; ++t) {
            // 매턴마다, readying에서 readied로 복귀할 함선들을 찾아서 복귀시킨다.
            while (!readying.isEmpty() && readying.peek().rT <= t) {
                Ship readiedShip = readying.poll();
                if (readiedShip.p == nowPower.get(readiedShip.id)) {
                    // System.out.println("현재 " + t + "턴 이며 " + readiedShip.id + "가 준비 대기시간이었던" + readiedShip.rT +  "가 되어 복귀합니다.");
                    readied.offer(readiedShip);
                }
            }

            StringTokenizer st = new StringTokenizer(br.readLine());
            command = Integer.parseInt(st.nextToken());
            int N, id_N, p_N, r_N;
            // 공격 준비일때
            if (command == 100) {
                // N척의 선박을 readied에 넣는다.
                N = Integer.parseInt(st.nextToken());
                for (int i=0; i<N; ++i) {
                    id_N = Integer.parseInt(st.nextToken());
                    p_N = Integer.parseInt(st.nextToken());
                    r_N = Integer.parseInt(st.nextToken());

                    readied.offer(new Ship(id_N, p_N, r_N, 0));
                    nowPower.put(id_N, p_N);
                    nowRT.put(id_N, 0);
                    shipReload.put(id_N, r_N);
                } 
            }
            // 지원 요청일때
            else if (command == 200) {
                // readied에 선박을 1대 넣는다.
                id_N = Integer.parseInt(st.nextToken());
                p_N = Integer.parseInt(st.nextToken());
                r_N = Integer.parseInt(st.nextToken());
                readied.offer(new Ship(id_N, p_N, r_N, 0));
                nowPower.put(id_N, p_N);
                nowRT.put(id_N, 0);
                shipReload.put(id_N, r_N);
            }
            // 함포 교체일때
            else if (command == 300) {
                // 먼저 함포를 교체할 대상이 readied에 있는지 readying에 있는지 확인을 위해 기록된 해당 함선의 준비 시간을 본다.
                id_N = Integer.parseInt(st.nextToken());
                p_N = Integer.parseInt(st.nextToken());
                // 만약 준비시간이 현재 턴 이하라면 readied에 존재한다.
                if (nowRT.get(id_N) <= t) {
                    // readied에 변경된 공격력으로 Id가 동일한 Ship을 넣는다. 그리고, nowPower를 갱신한다.
                    readied.offer(new Ship(id_N, p_N, shipReload.get(id_N), nowRT.get(id_N)));
                }
                // 만약 준비시간이 현재 턴 초과라면 readying에 있다.
                else {
                    // readying에 변경된 공격력으로 Id가 동일한 Ship을 넣는다. 그리고, nowPower를 갱신한다.
                    readying.offer(new Ship(id_N, p_N, shipReload.get(id_N), nowRT.get(id_N)));
                }
                nowPower.put(id_N, p_N);
            }
            // 공격 명령일떄
            else {
                int cnt = 0, totalDamage = 0;
                String shotList = "";
                // readied에서 유효한 함선 5개를 꺼내거나 readied가 빌때까지 꺼낸다.
                while (!readied.isEmpty() && cnt < 5) {
                    Ship now = readied.poll();
                    // 만약, 꺼낸 함선의 공격력이 nowPower에 기록된 값과 다르면 해당 함선은 유효하지 않은 함선으로 넘긴다.
                    if (now.p == nowPower.get(now.id)) {
                        // 유효한 함선은 cnt를 증가시키고, 해당 공격력을 totalDamage에 더한다.
                        cnt++;
                        totalDamage += now.p;
                        // 이후, t + r을 계산하여 대기 완료 시간을 연산하고, 이 값을 기준으로 readying에 추가한다.
                        int nextRT = t + now.r;
                        nowRT.put(now.id, nextRT);
                        readying.offer(new Ship(now.id, now.p, now.r, nextRT));
                        shotList += String.valueOf(now.id) + " ";
                    }
                }
                // 연산한 결과를 sb에 저장
                sb.append(String.valueOf(totalDamage)).append(" ").append(String.valueOf(cnt)).append(" ").append(shotList).append("\n");

            }
        }


        System.out.print(sb);


    }
}
```

