Select distinct m.txt_menu_param_1 as TxtMenuParam1,
				m.txt_menu_param_2 as TxtMenuParam2,
				m.txt_menu_param_3 as TxtMenuParam3
from sec_menu_options m, sec_user_x_roles uxr, sec_user_role_menus urm
where m.cod_menu_option = :codMenuOption
and uxr.txt_login_id = :txtLoginId
and m.cod_menu_option = urm.cod_menu_option
and uxr.cod_user_role = urm.cod_user_role
and m.cod_rec_status = 'A'
and uxr.cod_rec_status = 'A'
and urm.cod_rec_status = 'A'
and ifnull(m.flg_is_root_menu, 'N') = 'N'
and m.cod_menu_option not in (select cod_parent_menu_option from sec_menu_options where cod_rec_status = 'A')