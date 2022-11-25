UPDATE sec_user_master
SET flg_disabled = if(num_failed_pwd > :numFailedPwd, 'Y','N'), num_failed_pwd = if(num_failed_pwd > :numFailedPwd + 1, num_failed_pwd, num_failed_pwd + 1)
WHERE txt_login_id=:txtLoginId