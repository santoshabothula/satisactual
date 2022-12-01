SELECT txt_login_id as TxtLoginId,
       dat_time_login as DatTimeLogin,
       flg_success as FlgSuccess,
       ifnull(txt_ip_address_source, '') as TxtIpAddressSource,
       txt_browser_used as TxtBrowserUsed,
       txt_os_used as TxtOsUsed,
       txt_login_fail_reason as TxtLoginFailReason,
       TIMESTAMPDIFF(MINUTE, dat_time_login, NOW()) AS Difference
from sec_user_access_log
where txt_login_id = :txtLoginId
order by dat_time_login desc limit 1