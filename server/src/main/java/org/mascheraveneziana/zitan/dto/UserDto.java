package org.mascheraveneziana.zitan.dto;

import lombok.Data;

@Data
public class UserDto {
	private String email;
	
	
	public UserDto(String email) {
		this.email = email;
	}
}
