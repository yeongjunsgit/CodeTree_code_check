with max_number as (
    select distinct count(*) as cnt
    from rest_review
    group by member_id
    order by count(*) desc
    limit 1
),

best_member as (
    select member_id
    from rest_review
    group by member_id
    having count(*) = (select cnt from max_number)
)

select m.member_name, r.review_text, r.review_date
from rest_review as r
join member_profile as m
on r.member_id = m.member_id
where m.member_id in (select member_id from best_member)
order by r.review_date, r.review_text;
