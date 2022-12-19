select 	w.id_campaign_wave, w.id_campaign,
		ifnull(s.txt_intro_script, s1.txt_intro_script) as txt_intro_script,
		ifnull(s.txt_ack_script, s1.txt_ack_script) as txt_ack_script,
		ifnull(s.txt_not_qualified_script, s1.txt_not_qualified_script) as txt_not_qualified_script,
		ifnull(s.cod_language, s1.cod_language) as cod_language,
		ifnull(s.cod_channel, s1.cod_channel) as cod_channel
from cmp_campaigns c, cmp_team t, cmp_waves w left outer join cmp_campaign_scripts s
on   w.id_campaign = s.id_campaign
and  w.id_campaign_wave = s.id_campaign_wave
and s.cod_rec_status = 'A', cmp_campaign_scripts s1
where t.id_campaign = c.id_campaign
 and t.txt_login_id = 'waqarali'
 and c.cod_rec_status = 'A'
 and t.cod_rec_status = 'A'
 and c.enu_campaign_status in ('A','R')
 and '2022-12-16' >= c.dat_start
 and '2022-12-16' between t.dat_joined_team and t.dat_left_team
 and c.id_campaign = w.id_campaign
 and w.cod_rec_status = 'A'
 and '2022-12-16' between w.dat_wave_start and w.dat_wave_end
 and w.id_campaign = s1.id_campaign
 and s1.id_campaign_wave = -1
 and s1.cod_rec_status = 'A';