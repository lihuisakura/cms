package com.lihui.cms.domain.enums;

public enum ContentType {

	
	HTML("文本"),
	IMAGE("图片");
	
	private String content;

	
	private ContentType(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public String getName() {
		return this.name();
	}
	
	private int getOrdinal() {
		return this.ordinal();
	}
	
	
}
