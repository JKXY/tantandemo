package com.wanpu.tantandemo.util;

import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * sharedPreference 数据存储
 * 
 */
public class SPUtil {

	private SharedPreferences mShared;

	private Editor mEditor;

	/**
	 * 构造方法。
	 * 
	 * @param context
	 * @param kvName
	 *            键值表名称。
	 * @param mode
	 *            打开的模式。值为Context.MODE_APPEND, Context.MODE_PRIVATE,
	 *            Context.WORLD_READABLE, Context.WORLD_WRITEABLE.
	 */
	public SPUtil(Context context, String kvName, int mode) {
		mShared = context.getSharedPreferences(kvName, mode);
		mEditor = mShared.edit();
	}

	/**
	 * 获取保存着的boolean对象。
	 * 
	 * @param key
	 *            键名
	 * @param defValue
	 *            当不存在时返回的默认值。
	 * @return 返回获取到的值，当不存在时返回默认值。
	 */
	public boolean getBoolean(String key, boolean defValue) {
		return mShared.getBoolean(key, defValue);
	}

	/**
	 * 获取保存着的int对象。
	 * 
	 * @param key
	 *            键名
	 * @param defValue
	 *            当不存在时返回的默认值。
	 * @return 返回获取到的值，当不存在时返回默认值。
	 */
	public int getInt(String key, int defValue) {
		return mShared.getInt(key, defValue);
	}

	/**
	 * 获取保存着的long对象。
	 * 
	 * @param key
	 *            键名
	 * @param defValue
	 *            当不存在时返回的默认值。
	 * @return 返回获取到的值，当不存在时返回默认值。
	 */
	public long getLong(String key, long defValue) {
		return mShared.getLong(key, defValue);
	}

	/**
	 * 获取保存着的float对象。
	 * 
	 * @param key
	 *            键名
	 * @param defValue
	 *            当不存在时返回的默认值。
	 * @return 返回获取到的值，当不存在时返回默认值。
	 */
	public float getFloat(String key, float defValue) {
		return mShared.getFloat(key, defValue);
	}

	/**
	 * 获取保存着的String对象。
	 * 
	 * @param key
	 *            键名
	 * @param defValue
	 *            当不存在时返回的默认值。
	 * @return 返回获取到的值，当不存在时返回默认值。
	 */
	public String getString(String key, String defValue) {
		return mShared.getString(key, defValue);
	}

	/**
	 * 获取所有键值对。
	 * 
	 * @return 获取到的所胡键值对。
	 */
	public Map<String, ?> getAll() {
		return mShared.getAll();
	}

	/**
	 * 注意：当保存的value不是boolean, byte(会被转换成int保存),int, long, float,
	 * String等类型时将调用它的toString()方法进行值的保存。
	 * 
	 * @param key
	 *            键名称。
	 * @param value
	 *            值。
	 * @return 引用的KV对象。
	 */
	public SPUtil put(String key, Object value) {
		if (value instanceof Boolean) {
			mEditor.putBoolean(key, (Boolean) value).commit();
		} else if (value instanceof Integer || value instanceof Byte) {
			mEditor.putInt(key, (Integer) value).commit();
		} else if (value instanceof Long) {
			mEditor.putLong(key, (Long) value).commit();
		} else if (value instanceof Float) {
			mEditor.putFloat(key, (Float) value).commit();
		} else if (value instanceof Double) {
			mEditor.putFloat(key, Float.parseFloat(value.toString())).commit();
		} else if (value instanceof String) {
			mEditor.putString(key, (String) value).commit();
		} else {
			if (null == value) {
				value = "";
			}
			mEditor.putString(key, value.toString()).commit();
		}
		return this;
	}

	/**
	 * 移除键值对。
	 * 
	 * @param key
	 *            要移除的键名称。
	 * @return 引用的KV对象。
	 */
	public SPUtil remove(String key) {
		mEditor.remove(key).commit();
		return this;
	}

	/**
	 * 清除所有键值对。
	 * 
	 * @return 引用的KV对象。
	 */
	public SPUtil clear() {
		mEditor.clear().commit();
		return this;
	}

	/**
	 * 是否包含某个键。
	 * 
	 * @param key
	 *            查询的键名称。
	 * @return 当且仅当包含该键时返回true, 否则返回false.
	 */
	public boolean contains(String key) {
		return mShared.contains(key);
	}
}
