from collections import deque

def solution(queue1, queue2):
    queue = queue1 + queue2
    s,e = 0, len(queue1)
    total_num = len(queue)
    answer = 0
    
    if sum(queue) % 2:
        return -1
    
    target = sum(queue) // 2
    num = sum(queue[s:e])
    
    while s < e and e < total_num:
        if num == target:
            return answer
        
        elif num > target:
            if e - s == 1:
                return -1
            num -= queue[s]
            s += 1
            
        elif num < target:
            if e - s == total_num - 1:
                return -1
            num += queue[e]
            e += 1
        answer += 1
    return -1