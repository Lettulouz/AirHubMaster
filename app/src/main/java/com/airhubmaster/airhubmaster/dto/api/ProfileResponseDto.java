package com.airhubmaster.airhubmaster.dto.api;

import java.util.Date;

/**
 * The helper class responsible for mapping user profile data from the server
 */
public class ProfileResponseDto {

    /**
     * Variable declaration
     */
    String firstName;
    String lastName;
    String login;
    String emailAddress;
    String role;
    int level;
    int exp;
    int money;
    int fromLevel;
    int toLevel;
    Date accountCreated;

    public ProfileResponseDto(String firstName, String lastName, String login,
                              String emailAddress, String role, int level, int exp,
                              int money, int fromLevel, int toLevel, Date accountCreated) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.emailAddress = emailAddress;
        this.role = role;
        this.level = level;
        this.exp = exp;
        this.money = money;
        this.fromLevel = fromLevel;
        this.toLevel = toLevel;
        this.accountCreated = accountCreated;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getFromLevel() {
        return fromLevel;
    }

    public void setFromLevel(int fromLevel) {
        this.fromLevel = fromLevel;
    }

    public int getToLevel() {
        return toLevel;
    }

    public void setToLevel(int toLevel) {
        this.toLevel = toLevel;
    }

    public Date getAccountCreated() {
        return accountCreated;
    }

    public void setAccountCreated(Date accountCreated) {
        this.accountCreated = accountCreated;
    }
}
