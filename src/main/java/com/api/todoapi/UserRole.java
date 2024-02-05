package com.api.todoapi;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserRole {

    ADMIN(1, "ADMIN"),
    USER(2, "USER");

    private int code;
    private String description;

    public static UserRole toEnum(Integer code) {
        if (code == null)
            return null;

        for (var x : UserRole.values()) {
            if (code.equals(x.getCode()))
                return x;
        }

        throw new IllegalArgumentException("Invalid code: " + code);
    }
}
