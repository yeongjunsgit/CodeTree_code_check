-- 코드를 입력하세요
select i.rest_id, i.rest_name, i.food_type, i.favorites, i.address , round(avg(review_score),2) as score
from (
    select *
    from rest_info
    where address like "서울%"
) as i
join rest_review as r
on i.rest_id = r.rest_id
group by i.rest_id
order by score desc, i.favorites desc