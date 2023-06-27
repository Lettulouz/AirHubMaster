package com.airhubmaster.airhubmaster.dto.game;

public class PlaneSendResponseDto {

    /**
     * Variable declaration
     */
    int currentLevel;
    long fromLevel;
    long toLevel;
    long currentExp;
    int nextLevel;
    int maxLevels;
    boolean isUpgraded;
    int addedExp;
    int prize;
    int totalCost;
    long accountDeposit;
    String arrival;

    public PlaneSendResponseDto(int currentLevel, long fromLevel, long toLevel, long currentExp,
                                int nextLevel, int maxLevels, boolean isUpgraded, int addedExp,
                                int prize, int totalCost, long accountDeposit, String arrival) {
        this.currentLevel = currentLevel;
        this.fromLevel = fromLevel;
        this.toLevel = toLevel;
        this.currentExp = currentExp;
        this.nextLevel = nextLevel;
        this.maxLevels = maxLevels;
        this.isUpgraded = isUpgraded;
        this.addedExp = addedExp;
        this.prize = prize;
        this.totalCost = totalCost;
        this.accountDeposit = accountDeposit;
        this.arrival = arrival;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public long getFromLevel() {
        return fromLevel;
    }

    public void setFromLevel(long fromLevel) {
        this.fromLevel = fromLevel;
    }

    public long getToLevel() {
        return toLevel;
    }

    public void setToLevel(long toLevel) {
        this.toLevel = toLevel;
    }

    public long getCurrentExp() {
        return currentExp;
    }

    public void setCurrentExp(long currentExp) {
        this.currentExp = currentExp;
    }

    public int getNextLevel() {
        return nextLevel;
    }

    public void setNextLevel(int nextLevel) {
        this.nextLevel = nextLevel;
    }

    public int getMaxLevels() {
        return maxLevels;
    }

    public void setMaxLevels(int maxLevels) {
        this.maxLevels = maxLevels;
    }

    public boolean isUpgraded() {
        return isUpgraded;
    }

    public void setUpgraded(boolean upgraded) {
        isUpgraded = upgraded;
    }

    public int getAddedExp() {
        return addedExp;
    }

    public void setAddedExp(int addedExp) {
        this.addedExp = addedExp;
    }

    public int getPrize() {
        return prize;
    }

    public void setPrize(int prize) {
        this.prize = prize;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public long getAccountDeposit() {
        return accountDeposit;
    }

    public void setAccountDeposit(long accountDeposit) {
        this.accountDeposit = accountDeposit;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }
}
