package org.wallet.library.entity;

public class LoginResponse extends Response{

    public void setRandomNumber(String randomNumber) {
        RandomNumber = randomNumber;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }


    public void setELAAddress(String ELAAddress) {
        this.ELAAddress = ELAAddress;
    }

    public void setETHAddress(String ETHAddress) {
        this.ETHAddress = ETHAddress;
    }

    public void setBCHAddress(String BCHAddress) {
        this.BCHAddress = BCHAddress;
    }

    public void setBTCAddress(String BTCAddress) {
        this.BTCAddress = BTCAddress;
    }

    public void setExpirationDate(String expirationDate) {
        ExpirationDate = expirationDate;
    }

    public String getRandomNumber() {
        return RandomNumber;
    }

    public String getNickName() {
        return NickName;
    }

    public String getELAAddress() {
        return ELAAddress;
    }

    public String getETHAddress() {
        return ETHAddress;
    }

    public String getBCHAddress() {
        return BCHAddress;
    }

    public String getBTCAddress() {
        return BTCAddress;
    }

    public String getExpirationDate() {
        return ExpirationDate;
    }

    private String RandomNumber;
    private String NickName;
    private String ELAAddress;
    private String ETHAddress;
    private String BCHAddress;
    private String BTCAddress;
    private String ExpirationDate;
}
