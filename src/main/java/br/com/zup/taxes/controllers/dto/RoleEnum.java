package br.com.zup.taxes.controllers.dto;

public enum RoleEnum {
    ADMIN,
    USER,
    NEW_ROLE;

    public static RoleEnum fromString(String string) {
        for (RoleEnum role : RoleEnum.values()) {
            if (role.name().equals(string)) {
                return role;
            }
        }
        return null;
    }
}
