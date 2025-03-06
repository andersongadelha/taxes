package br.com.zup.taxes.controllers.dto;

public enum RoleEnum {
    ROLE_ADMIN,
    ROLE_USER,
    ROLE_NEW;

    public static RoleEnum fromString(String string) {
        for (RoleEnum role : RoleEnum.values()) {
            if (role.name().equals(string)) {
                return role;
            }
        }
        return null;
    }
}
