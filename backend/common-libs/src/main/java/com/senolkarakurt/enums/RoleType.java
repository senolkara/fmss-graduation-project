package com.senolkarakurt.enums;

import lombok.Getter;

@Getter
public enum RoleType {

    GUEST(0),
    USER(1),
    ADMIN(2);

    private final Integer role;

    RoleType(Integer role) {
        this.role = role;
    }

    public static RoleType fromValue(Integer role){
        for(RoleType roleType:RoleType.values()){
            if (roleType.role.equals(role)){
                return roleType;
            }
        }
        return null;
    }

}
