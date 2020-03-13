package com.lihui.cms.domain;

import java.io.Serializable;

public class Content implements Serializable{

	/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = 1L;
	private String pictrue;
	private String message;
	public String getPictrue() {
		return pictrue;
	}
	public void setPictrue(String pictrue) {
		this.pictrue = pictrue;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Content(String pictrue, String message) {
		super();
		this.pictrue = pictrue;
		this.message = message;
	}
	public Content() {
		super();
	}
	@Override
	public String toString() {
		return "Content [pictrue=" + pictrue + ", message=" + message + "]";
	}
	
}
