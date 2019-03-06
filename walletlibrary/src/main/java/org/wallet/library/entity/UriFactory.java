package org.wallet.library.entity;

import android.net.Uri;

import org.wallet.library.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class UriFactory {

//    elaphant://identity?AppID="123"&SerialNumber=123456ef&
//    Icon=https://xxxx.png&AppName=WeChat&
//    DID=xxxxxxxxxxxx&Description=developerSite&RandomNumber=1345678&

    public static final String SCHEME_KEY = "scheme_key";
    public static final String TYPE_KEY = "type_key";

    public String getRequestType() {
        return result.get(TYPE_KEY);
    }

    public String getAppID() {
        return getValue("AppID");
    }

    public String getSerialNumber() {
        return getValue("SerialNumber");
    }

    public String getAppName() {
        return getValue("AppName");
    }

    public String getDID() {
        return getValue("DID");
    }

    public String getPublicKey() {
        return getValue("PublicKey");
    }

    public String getSignature() {
        return getValue("Signature");
    }

    public String getDescription() {
        return getValue("Description");
    }

    public String getRandomNumber() {
        return getValue("RandomNumber");
    }

    public String getCallbackUrl() {
        return getValue("CallbackUrl");
    }

    public String getPaymentAddress() {
        return getValue("PaymentAddress");
    }

    public String getAmount() {
        return getValue("Amount");
    }

    public String getCoinName() {
        return getValue("CoinName");
    }

    public String getReturnUrl(){
        return getValue("ReturnUrl");
    }

    private String getValue(String key){
        String tmp = Uri.decode(result.get(key));
        return tmp;
    }

    private String RequestType;
    public UriFactory setRequestType(String type){
        this.RequestType = type;
        return this;
    }

    private String AppID;
    public UriFactory setAppID(String appID){
        this.AppID = appID;
        return this;
    }

    private String SerialNumber;
    public UriFactory setSerialNumber(String serialNumber){
        this.SerialNumber = serialNumber;
        return this;
    }

    private String AppName;
    public UriFactory setAppName(String appName){
        this.AppName = appName;
        return this;
    }

    private String DID;
    public UriFactory setDID(String DID){
        this.DID = DID;
        return this;
    }

    private String PublicKey;
    public UriFactory setPublicKey(String publicKey){
        this.PublicKey = publicKey;
        return this;
    }

    private String Signature;
    public UriFactory setSignature(String signature){
        this.Signature = signature;
        return this;
    }

    private String Description;
    public UriFactory setDescription(String description){
        this.Description = description;
        return this;
    }

    private String RandomNumber;
    public UriFactory setRandomNumber(String RandomNumber){
        this.RandomNumber = RandomNumber;
        return this;
    }

    private String CallbackUrl;
    public UriFactory setCallbackUrl(String callbackUrl){
        this.CallbackUrl = callbackUrl;
        return this;
    }

    public String buildLoginUri(){
        result.clear();
//        result.put(RequestType.getClass().getSimpleName(), RequestType);
        result.put("AppID", AppID);
        result.put("SerialNumber", SerialNumber);
        result.put("AppName", AppName);
        result.put("DID", DID);
        result.put("PublicKey", PublicKey);
        result.put("Signature", Signature);
        result.put("Description", Description);
        result.put("RandomNumber", RandomNumber);
        result.put("CallbackUrl", CallbackUrl);

        return create(RequestType, result);
    }

    private String PaymentAddress;
    public UriFactory setPaymentAddress(String PaymentAddress){
        this.PaymentAddress = PaymentAddress;
        return this;
    }

    private String Amount;
    public UriFactory setAmount(String amount){
        this.Amount = amount;
        return this;
    }

    private String CoinName;
    public UriFactory setCoinName(String CoinName){
        this.CoinName = CoinName;
        return this;
    }

    public String buildPayUri(){
        result.clear();
        result.put("AppID", AppID);
        result.put("SerialNumber", SerialNumber);
        result.put("AppName", AppName);
        result.put("DID", DID);
        result.put("PublicKey", PublicKey);
        result.put("Signature", Signature);
        result.put("Description", Description);
        result.put("CallbackUrl", CallbackUrl);
        result.put("PaymentAddress", PaymentAddress);
        result.put("Amount", Amount);
        result.put("CoinName", CoinName);

        return create(RequestType, result);
    }

    private Map<String, String> result = new HashMap();
    public void parse(String uri){
        String[] schemeArr = uri.split("elaphant://");
        if(schemeArr!=null && schemeArr.length>1) {
            result.put(SCHEME_KEY, "elaphant");
            String[] typeArr = schemeArr[1].split("\\?");
            if(typeArr!=null && typeArr.length>1){
                result.put(TYPE_KEY, typeArr[0]);

                String[] andArr = typeArr[1].split("&");
                if(andArr==null || andArr.length<=0) return;
                for(String and : andArr){
                    String[] params = and.split("=");
                    if(params!=null && params.length>1) {
                        result.put(params[0], params[1]);
                    }
                }

            }
        }
    }

    public String create(String type, Map<String, String> params){
        if(StringUtils.isNullOrEmpty(type) || params.isEmpty()) return null;

        StringBuilder sb = new StringBuilder();
        sb.append("elaphant://").append(type).append("?");

        for(Map.Entry<String, String> entry : params.entrySet()){
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }

        return sb.deleteCharAt(sb.length()-1).toString();
    }

}
