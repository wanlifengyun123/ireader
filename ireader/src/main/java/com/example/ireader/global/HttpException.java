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
package com.example.ireader.global;


import android.text.TextUtils;

import com.google.gson.JsonSyntaxException;

import org.apache.http.conn.ConnectTimeoutException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

// TODO: Auto-generated Javadoc

/**
 * Created by Administrator on 2016/9/4.
 * 描述：公共异常类.
 */
public class HttpException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1;

	/** The Constant CONNECTEXCEPTION. */
	public String CONNECT_EXCEPTION = "无法连接到网络";

	/** The Constant UNKNOWNHOSTEXCEPTION. */
	public String UNKNOWN_HOST_EXCEPTION = "连接远程地址失败";

	/** The Constant SOCKETEXCEPTION. */
	public String SOCKET_EXCEPTION = "网络连接出错，请重试";

	/** The Constant SOCKETTIMEOUTEXCEPTION. */
	public String SOCKET_TIMEOUT_EXCEPTION = "连接超时，请重试";

	/** The Constant NULLPOINTEREXCEPTION. */
	public String NULL_POINTER_EXCEPTION = "抱歉，远程服务出错了";

	/** The Constant NULLMESSAGEEXCEPTION. */
	public String NULL_MESSAGE_EXCEPTION = "抱歉，程序出错了";

	/** The Constant CLIENTPROTOCOLEXCEPTION. */
	public String CLIENT_PROTOCOL_EXCEPTION = "Http请求参数错误";

	/** The Constant JSON_PALL_EXCEPTION.  */
	public final String JSON_PALL_EXCEPTION = "json解析错误";

	/** 参数个数不够. */
	public String MISSING_PARAMETERS = "参数没有包含足够的值";

	/** The Constant REMOTESERVICEEXCEPTION. */
	public String REMOTE_SERVICE_EXCEPTION = "抱歉，远程服务出错了";

	/** 资源未找到. */
	public String NOT_FOUND_EXCEPTION = "请求的资源无效404";

	/** 没有权限访问资源. */
	public String FORBIDDEN_EXCEPTION = "没有权限访问资源";

	/** 其他异常. */
	public String UNTREATED_EXCEPTION = "未处理的异常";

	
	/** 异常消息. */
	private String msg = null;

	/**
	 * 构造异常类.
	 *
	 * @param e 异常
	 */
	public HttpException(Exception e) {
		super();
		try {
			if (e instanceof ConnectException) {
				msg = CONNECT_EXCEPTION;
			}else if (e instanceof ConnectTimeoutException) {
				msg = CONNECT_EXCEPTION;
			}else if (e instanceof UnknownHostException) {
				msg = UNKNOWN_HOST_EXCEPTION;
			}else if (e instanceof SocketException) {
				msg = SOCKET_EXCEPTION;
			}else if (e instanceof SocketTimeoutException) {
				msg = SOCKET_TIMEOUT_EXCEPTION;
			}else if( e instanceof NullPointerException) {  
				msg = NULL_POINTER_EXCEPTION;
			}else if( e instanceof JsonSyntaxException){
				msg = JSON_PALL_EXCEPTION;
			}
			else {
				if (e == null || TextUtils.isEmpty(e.getMessage())) {
					msg = NULL_MESSAGE_EXCEPTION;
				}else{
				    msg = e.getMessage();
				}
			}
		} catch (Exception e1) {
		}
		
	}

	/**
	 * 用一个消息构造异常类.
	 *
	 * @param message 异常的消息
	 */
	public HttpException(String message) {
		super(message);
		msg = message;
	}

	/**
	 * 描述：获取异常信息.
	 *
	 * @return the message
	 */
	@Override
	public String getMessage() {
		return msg;
	}

}
