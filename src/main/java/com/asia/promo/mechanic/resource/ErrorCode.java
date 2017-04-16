package com.asia.promo.mechanic.resource;

public enum ErrorCode {
	INVALID_INPUT_PARAMETER(90001),
	USER_NOT_FOUND_EXCEPTION(90010), 
	USER_FOUND_EXCEPTION(90011), 
	INVALID_PASSWORD_EXCEPTION(90012),
	TABLE_NOT_FOUND_EXCEPTION(90100);

	private int code;

	ErrorCode(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
