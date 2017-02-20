/*
 * Copyright (C) 2012 www.amsoft.cn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.ireader.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Xml;
import android.view.inputmethod.InputMethodManager;

import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;

// TODO: Auto-generated Javadoc

/**
 * © 2012 amsoft.cn
 * 名称：AbAppUtil.java 
 * 描述：应用工具类.
 *
 */
public class AppUtil {

    /**
     * 获取屏幕尺寸与密度.
     *
     * @param context the context
     * @return mDisplayMetrics
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        Resources mResources;
        if (context == null){
            mResources = Resources.getSystem();
            
        }else{
            mResources = context.getResources();
        }
        DisplayMetrics mDisplayMetrics = mResources.getDisplayMetrics();
        return mDisplayMetrics;
    }
    
    /**
     * 打开键盘.
     *
     * @param context the context
     */
    public static void showSoftInput(Context context){
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
    
    /**
     * 关闭键盘事件.
     *
     * @param context the context
     */
    public static void closeSoftInput(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager)context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && ((Activity)context).getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(((Activity)context).getCurrentFocus()
                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    
    /**
     * 获取包信息.
     *
     * @param context the context
     */
    public static PackageInfo getPackageInfo(Context context) {
    	PackageInfo info = null;
	    try {
	        String packageName = context.getPackageName();
	        info = context.getPackageManager().getPackageInfo(packageName,PackageManager.GET_ACTIVITIES);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return info;
    }

	/**
	 * 应用程序运行命令获取 Root权限，设备必须已破解(获得ROOT权限)
	 * @return 应用程序是/否获取Root权限
	 */
	public static boolean getRootPermission(Context context) {
		String path = context.getPackageCodePath();  
	    return getRootPermission(path);
	}
	
	/**
	 * 修改文件权限
	 * @return 文件路径
	 */
	public static boolean getRootPermission(String path) {
		Process process = null;
		DataOutputStream os = null;
		try {
			File  file = new File(path);
			if(!file.exists()){
				return false;
			}
			String cmd = "chmod 777 " + path;
			// 切换到root帐号
			process = Runtime.getRuntime().exec("su");
			os = new DataOutputStream(process.getOutputStream());
			os.writeBytes(cmd + "\n");
			os.writeBytes("exit\n");
			os.flush();
			process.waitFor();
		} catch (Exception e) {
			return false;
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				process.destroy();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

    /**
     * 
     * 描述：获取可用内存.
     * @param context
     * @return
     */
	public static long getAvailMemory(Context context){  
        //获取android当前可用内存大小  
        ActivityManager activityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);  
        MemoryInfo memoryInfo = new MemoryInfo();  
        activityManager.getMemoryInfo(memoryInfo);  
        //当前系统可用内存 ,将获得的内存大小规格化  
        return memoryInfo.availMem;  
    }  
	
	/**
	 * 
	 * 描述：总内存.
	 * @param context
	 * @return
	 */
	public static long getTotalMemory(Context context){  
		//系统内存信息文件  
        String file = "/proc/meminfo";
        String memInfo;  
        String[] strs;  
        long memory = 0;  
          
        try{  
            FileReader fileReader = new FileReader(file);  
            BufferedReader bufferedReader = new BufferedReader(fileReader,8192);  
            //读取meminfo第一行，系统内存大小 
            memInfo = bufferedReader.readLine(); 
            strs = memInfo.split("\\s+");  
            for(String str:strs){  
                LogUtil.d(AppUtil.class,str+"\t");
            }  
            //获得系统总内存，单位KB  
            memory = Integer.valueOf(strs[1]).intValue()*1024;
            bufferedReader.close();  
        }catch(Exception e){  
            e.printStackTrace();
        }  
        //Byte转位KB或MB
        return memory;  
    }  
    
	/**
	 * 
	 * 获取mac地址.
	 * @param context
	 * @return
	 */
	public static String getMac(Context context) {
		WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		if (info.getMacAddress() == null) {
			return null;
		} else {
			return info.getMacAddress();
		}
	}
	
	/**
	 * 
	 * 获取SSID地址.
	 * @param context
	 * @return
	 */
	public static String getSSID(Context context) {

		WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		if (info.getSSID() == null) {
			return null;
		} else {
			return info.getSSID();
		}
	}

	/**
	 * 
	 * 获取IMSI.
	 * @return
	 */
	public static String getIMSI(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		if (telephonyManager.getSubscriberId() == null) {
			return null;
		} else {
			return telephonyManager.getSubscriberId();
		}
	}

	/**
	 * 
	 * 获取IMEI.
	 * @return
	 */
	public static String getIMEI(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		if (telephonyManager.getDeviceId() == null) {
			return null;
		} else {
			return telephonyManager.getDeviceId();
		}
	}
	
	/**
	 * 手机号码
	 * @return
	 */
	public static String getPhoneNumber(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		if (telephonyManager.getLine1Number() == null || telephonyManager.getLine1Number().length() < 11) {
			return null;
		} else {
			return telephonyManager.getLine1Number();
		}
	}
	
	/**
	 * 
	 * 获取QQ号.
	 * @return
	 */
	public static String getQQNumber(Context context) {
		String path = "/data/data/com.tencent.mobileqq/shared_prefs/Last_Login.xml";
		getRootPermission(context);
		File file = new File(path);
		getRootPermission(path);
		boolean flag = file.canRead();
		String qq = null;
		if(flag){
			try {
				FileInputStream is = new FileInputStream(file);
				XmlPullParser parser = Xml.newPullParser();
				parser.setInput(is, "UTF-8");
				int event = parser.getEventType();
				while (event != XmlPullParser.END_DOCUMENT) {

					switch (event) {
					case XmlPullParser.START_DOCUMENT:
						break;
					case XmlPullParser.START_TAG:
						if ("map".equals(parser.getName())) {
						}
						if ("string".equals(parser.getName())) {
							String uin = parser.getAttributeValue(null, "name");
							if (uin.equals("uin")) {
								qq = parser.nextText();
								return qq;
							}
						}
						break;
					case XmlPullParser.END_TAG:
						break;
					}
					event = parser.next();
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
		
	/**
	 * 
	 * 获取WEIXIN号.
	 * @return
	 */
	public static String getWeiXinNumber(Context context) {
		String path = "/data/data/com.tencent.mm/shared_prefs/com.tencent.mm_preferences.xml";
		getRootPermission(context);
		File file = new File(path);
		getRootPermission(path);
		boolean flag = file.canRead();
		String weixin = null;
		if(flag){
			try {
				FileInputStream is = new FileInputStream(file);
				XmlPullParser parser = Xml.newPullParser();
				parser.setInput(is, "UTF-8");
				int event = parser.getEventType();
				while (event != XmlPullParser.END_DOCUMENT) {

					switch (event) {
					case XmlPullParser.START_DOCUMENT:
						break;
					case XmlPullParser.START_TAG:
						if ("map".equals(parser.getName())) {
						}
						if ("string".equals(parser.getName())) {
							String nameString = parser.getAttributeValue(null, "name");
							if (nameString.equals("login_user_name")) {
								weixin = parser.nextText();
								return weixin;
							}
						}
						break;
					case XmlPullParser.END_TAG:
						break;
					}
					event = parser.next();
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
