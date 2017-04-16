package com.asia.promo.mechanic.exception;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;

@XmlRootElement
@Getter
@Setter
public class ErrorItem {

	private Integer errorCode;
	private String name;
	private String description;

	ErrorItem() {
	}

	public ErrorItem(Integer errorCode, String name, String description) {
		this.errorCode = errorCode;
		this.name = name;
		this.description = description;
	}

	// getters and setters omitted
}
