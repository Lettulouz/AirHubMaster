package com.airhubmaster.airhubmaster.dto.api;

import java.util.HashMap;
import java.util.Map;

public class InventoryResponseDto {
    private long id;
    private String cardName;

    public int getCardType() {
        return cardType;
    }

    public void setCardType(int cardType) {
        this.cardType = cardType;
    }

    public int getRarity() {
        return rarity;
    }

    public void setRarity(int rarity) {
        this.rarity = rarity;
    }

    private int cardType;
    private long price;
    private int rarity;
    private boolean isOnSell;
    private Map<String, Integer> attributes = new HashMap<>();

    public String getDisplayNameType() {

        switch (getCardType()) {
            case 1:
                return "Personel";
            case 2:
                return "Rozszerzenie";
            case 3:
                return "Dodatki";
            case 4:
                return "Bezpieczeństwo";
            default:
                return "Unknown";
        }
    }

    public String getDisplayNameRarity() {
        switch (getRarity()) {
            case 1:
                return "Powszechny";
            case 2:
                return "Nietypowy";
            case 3:
                return "Rzadki";
            case 4:
                return "Epicki";
            default:
                return "Unknown";
        }
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }


    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }



    public boolean isOnSell() {
        return isOnSell;
    }

    public void setOnSell(boolean onSell) {
        isOnSell = onSell;
    }

    public Map<String, Integer> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Integer> attributes) {
        this.attributes = attributes;
    }
}