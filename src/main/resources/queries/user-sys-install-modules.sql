Select m.cod_module as CodModule,
	m.txt_module_name as TxtModuleName,
	m.txt_module_home_page as TxtModuleHomePage,
	m.txt_module_logo_file as TxtModuleLogoFile,
	m.txt_bank_name as TxtBankName,
	m.dat_license_start as DatLicenseStart,
	m.dat_license_expire as DatLicenseExpire,
	m.txt_module_param_1 as TxtModuleParam1,
	m.txt_module_param_2 as TxtModuleParam2,
	m.txt_module_param_3 as TxtModuleParam3,
	m.txt_module_key as TxtModuleKey,
	g.bin_bank_logo_file as BinBankLogoFile
from sys_install_modules m , sys_global_vars g
where g.txt_bank_name = m.txt_bank_name
and g.dat_system_asof between m.dat_license_start and m.dat_license_expire
and g.cod_rec_status = 'A'
and cod_module = :codeModule