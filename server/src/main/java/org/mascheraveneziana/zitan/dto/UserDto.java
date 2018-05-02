package org.mascheraveneziana.zitan.dto;

import lombok.Data;

@Data
public class UserDto {

    private String id;
    private String name;
	private String email;
	private boolean required;

	public UserDto() {
	}

	public UserDto(String id, String name, String email, boolean required) {
	    this.id = id;
	    this.email = email;
	    this.email = email;
	    this.required = required;
	}

}
