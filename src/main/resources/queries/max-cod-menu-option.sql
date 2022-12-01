select max(m.cod_menu_option) as cod_menu_option
from   sec_menu_options m, sec_user_role_menus um
where  m.cod_rec_status = 'A'
and    um.cod_rec_status = 'A'
and    m.cod_menu_option = um.cod_menu_option
and    um.cod_user_role = 'SU'
and    m.cod_menu_option not in
      (select ifnull(cod_parent_menu_option,'')
	   from   sec_menu_options where cod_rec_status = 'A')
order by num_display_order asc