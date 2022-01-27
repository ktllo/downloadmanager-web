package org.leolo.web.dm.util;

public class JSPUtils {
	public static String nl2br(String str) {
		return str.replace("\n", "<br>").replace("\r", "");
	}
}
