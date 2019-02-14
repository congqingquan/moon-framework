package org.moon.framework.core.utils.web;

/**
 * Created by 明月   on 2018-11-16 / 22:45
 *
 * @Description: 接口响应数据
 */
public class Response<T extends Object> {

	public static final int SUCCESS = 200;

	private int code;
	private T data;
	private String message;

	public Response(int code, T data) {
		super();
		this.code = code;
		this.data = data;
	}

	public Response(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public Response(int code, T data, String message) {
		super();
		this.code = code;
		this.data = data;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 200
	 */
	public static <T> Response<T> success(String message, T data) {
		return new Response<T>(SUCCESS, data, message);
	}
}