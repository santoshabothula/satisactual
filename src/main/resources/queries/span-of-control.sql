select  u.cod_org_unit as CodOrgUnit,
		o.txt_org_unit_name as TxtOrgUnitName
from    sec_user_span_derived u, sys_org_chart o
where   o.cod_org_unit = u.cod_org_unit
and     o.cod_rec_status = 'A'
and     txt_login_id = :txtLoginId;