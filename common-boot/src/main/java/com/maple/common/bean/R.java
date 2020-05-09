package com.maple.common.bean;

import lombok.*;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 响应信息主体
 *
 * @param <T>
 * @author zhua
 */
@Builder
@ToString
@NoArgsConstructor
@Accessors(chain = true)
public class R<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 成功标记
	 */
	private static final Integer SUCCESS = 200;
	/**
	 * 失败标记
	 */
	private static final Integer FAIL = 500;

	@Getter
	@Setter
	private int code;

	@Getter
	@Setter
	private String msg;

	@Getter
	@Setter
	private T data;

	@Getter
	@Setter
	private Long respTime;


	public static <T> R<T> ok() {
		return restResult(null, SUCCESS, null);
	}

	public static <T> R<T> ok(T data) {
		return restResult(data, SUCCESS, null);
	}

	public static <T> R<T> ok(T data, String msg) {
		return restResult(data, SUCCESS, msg);
	}

	public static <T> R<T> failed() {
		return restResult(null, FAIL, null);
	}

	public static <T> R<T> failed(String msg) {
		return restResult(null, FAIL, msg);
	}

	public static <T> R<T> failed(T data) {
		return restResult(data, FAIL, null);
	}

	public static <T> R<T> failed(T data, String msg) {
		return restResult(data, FAIL, msg);
	}
	public static <T> R<T> isOk(boolean isOk, String msg){
		if(isOk) {
            return restResult(null, SUCCESS, msg + "成功");
        } else {
            return restResult(null, FAIL, msg + "失败, 请重试");
        }
	}

	public static <T> R<T> result(int code, String msg, T data) {
		return restResult(data, code, msg);
	}

	private static <T> R<T> restResult(T data, int code, String msg) {
		R<T> apiResult = new R<>();
		apiResult.setCode(code);
		apiResult.setData(data);
		apiResult.setMsg(msg);
		if(StringUtils.isBlank(msg)) {
			if(SUCCESS != code) {
				apiResult.setMsg("操作失败");
			} else {
				apiResult.setMsg("操作成功");
			}
		}
		return apiResult;
	}
	public R(int code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public R(int code, String msg, T data, Long respTime) {
		this.code = code;
		this.msg = msg;
		this.data = data;
		this.respTime = respTime;
	}
}
