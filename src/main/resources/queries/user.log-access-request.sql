REPLACE INTO sec_user_access_log
		( txt_login_id
		, dat_time_login
		, flg_success
		, txt_login_fail_reason
		, txt_ip_address_source
		, txt_browser_used
		, txt_os_used
		, txt_user_agent_string)
VALUES (:txtLoginId, now(), :flgSuccess, :txtLoginFailReason, :txtIpAddressSource, :txtBrowserUsed, :txtOsUsed, :txtUserAgentString)