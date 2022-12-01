select txt_bank_name as TxtBankName, dat_system_asof as DatSystemAsOf
from   sys_global_vars
where  cod_language ='ENG'
and    cod_rec_status ='A'