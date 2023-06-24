package com.airhubmaster.airhubmaster.dto.game;

/**
 * A simple class for mapping confirmed message from the server after successful purchase in shop view
 */
public class ShopConfirmedPurchaseDto {

    /**
     * Variable declaration
     */
    long moneyAfterTransact;
    String message;

    public ShopConfirmedPurchaseDto(long moneyAfterTransact, String message) {
        this.moneyAfterTransact = moneyAfterTransact;
        this.message = message;
    }

    public long getMoneyAfterTransact() {
        return moneyAfterTransact;
    }

    public void setMoneyAfterTransact(long moneyAfterTransact) {
        this.moneyAfterTransact = moneyAfterTransact;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
