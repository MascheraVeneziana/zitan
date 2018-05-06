package org.mascheraveneziana.zitan.dto;

import lombok.Data;

@Data
public class UserDto {

    private String id;
    private String name;
    private String email;
    private Boolean required;

    public UserDto() {
    }

    public UserDto(String id, String name, String email, Boolean required) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.required = required;
    }

}
