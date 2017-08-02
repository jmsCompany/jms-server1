package com.jms.domain.ws;


public class Message  implements java.io.Serializable{
	private String code;
	private String message;
	private String msg;
	private MessageTypeEnum messageTypeEnum;
	
	public Message()
	{
	}
	
	public Message(String message,MessageTypeEnum messageTypeEnum)
	{
		this.message = message;
		this.messageTypeEnum=messageTypeEnum;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public MessageTypeEnum getMessageTypeEnum() {
		return messageTypeEnum;
	}
	public void setMessageTypeEnum(MessageTypeEnum messageTypeEnum) {
		this.messageTypeEnum = messageTypeEnum;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return message;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
