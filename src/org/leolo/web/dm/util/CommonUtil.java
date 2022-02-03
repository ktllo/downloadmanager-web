package org.leolo.web.dm.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtil {
	
	public static String formatDate(Date date) {
		return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(date);
	}
	
}
