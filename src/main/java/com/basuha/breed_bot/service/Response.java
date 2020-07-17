package com.basuha.breed_bot.service;


import com.basuha.breed_bot.message.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Response {
	private String status;
	private Object message;
	private Message botMessage;

	public Response(String status, Object message) {
		this.status = status;
		this.message = message;
	}
}
