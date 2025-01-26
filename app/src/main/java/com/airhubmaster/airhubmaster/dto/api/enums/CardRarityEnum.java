package com.airhubmaster.airhubmaster.dto.api.enums;

public enum CardRarityEnum {
    COMMON(1),
    UNCOMMON(2),
    RARE(3),
    EPIC(4);

    private final int value;

    CardRarityEnum(int value) {
        this.value = value;
    }
}
