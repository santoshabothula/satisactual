select 	l.txt_login_id as TxtLoginId,
		l.txt_user_fname as TxtUserFname,
		l.txt_user_lname as TxtUserLname
from 	sec_user_span_derived o, sec_user_master l
where 	o.cod_org_unit = l.cod_home_branch
and 	l.cod_rec_status = 'A'
and 	o.txt_login_id = :txtLoginId