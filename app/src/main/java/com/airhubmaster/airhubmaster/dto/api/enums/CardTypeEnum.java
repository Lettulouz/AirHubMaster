package com.airhubmaster.airhubmaster.dto.api.enums;

public enum CardTypeEnum {
    PERSONNEL(1),
    EXPANSION(2),
    ADDON(3),
    SECURITY(4);

    private final int value;

    CardTypeEnum(int value) {
        this.value = value;
    }
}
