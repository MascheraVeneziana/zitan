package org.mascheraveneziana.zitan.dto;

import lombok.Data;

@Data
public class UserDto {

    private String id;
    private String name;
	private String email;

	public UserDto(String email) {
		this.email = email;
	}

}
