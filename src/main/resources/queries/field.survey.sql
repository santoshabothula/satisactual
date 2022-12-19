select count(distinct d.id_question) as num_questions_answered, max(r.enu_response_status) as enu_response_status, r.id_response
from cmp_response r, cmp_response_detail d
where r.id_campaign = d.id_campaign
and r.id_campaign_wave = d.id_campaign_wave
and r.id_response = d.id_response
and r.id_campaign = :id_campaign
and r.id_campaign_wave = :id_campaign_wave
and r.id_response = :id_response
group by r.id_response