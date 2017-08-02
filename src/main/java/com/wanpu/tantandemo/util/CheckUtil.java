package com.wanpu.tantandemo.util;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;


public class CheckUtil {

	/**
	 * 判断手机号码是否正确
	 *
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern.compile("^((1))\\d{10}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

}
