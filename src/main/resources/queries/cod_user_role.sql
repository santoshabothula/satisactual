select cod_user_role
from   sec_user_roles
where  flg_self_service_role = 'Y'
and    flg_default_value = 'Y'
and    cod_rec_status ='A'
order by dat_last_checker Desc