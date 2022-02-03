package org.leolo.web.dm;

public class Constant {
	//Session name
	public static final String SESSION_USER_ID = "uid";
	public static final String SESSION_USER_NAME = "uname";
	
	//Basic user information map
	public static final String BUI_KEY_USER_ID = "uid";
	public static final String BUI_KEY_USER_NAME = "uname";
	public static final String BUI_KEY_PASSWORD = "passwd";
	public static final String BUI_KEY_FAIL_CNT = "fail_cnt";
	public static final String BUI_KEY_LAST_LOGIN = "llit";
	
	//System parameter map
	public static final String SP_DATA_DIR = "data_dir";
	public static final String SP_CACHE_DIR = "cache_dir";
	public static final String SP_MAX_CACHE_SIZE = "cache_size";
	public static final String SP_SYSNAME = "sys_name";
	public static final String SP_MAX_LOGIN_FAIL_CNT = "max_fail_login";
	public static final String SP_BCRYPT_COST = "bcrypt_cost";
	public static final String SP_QUEUED_JOB_THREAD = "qj_thread";
	
	//System Information
	public static final String SI_API_VERSION = "0.1";
	
	//Common value
	public static final int COM_DEFAULT_USER_ID = 1;
	public static final int COM_ANY_USER_ID = 2;
}
