package com.jms.domain.ws;


public class Message {
	private String message;
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
}
