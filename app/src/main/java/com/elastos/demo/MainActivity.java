package com.elastos.demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.elastos.jni.Utility;

import java.io.UnsupportedEncodingException;


public class MainActivity extends Activity {

    private TextView mMnemonicTv;
    private TextView mPrivateKeyTv;
    private TextView mPublicKeyTv;
    private TextView mAddressTv;
    private TextView mRawTransactionTv;

    private String mMmnemonic = "措 像 林 害 京 陕 摊 捞 遍 率 问 万";
    private String mPrivateKey;
    private String mPublicKey;
    private String mAddress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);

        mMnemonicTv = findViewById(R.id.mnemonic);
        mPrivateKeyTv = findViewById(R.id.private_key);
        mPublicKeyTv = findViewById(R.id.public_key);
        mAddressTv = findViewById(R.id.address);
        mRawTransactionTv = findViewById(R.id.raw_transaction);
    }

    public void generateMmemonic(View view) {
        String mn = Utility.getInstance(this).generateMnemonic("zh");
        mMnemonicTv.setText(mn);
    }

    public void generateSinglePrivateKey(View view) {
        if (mPrivateKey == null) {
            mPrivateKey = Utility.getInstance(this).getSinglePrivateKey(mMmnemonic);
            mPrivateKeyTv.setText(mPrivateKey);
        }
    }

    public void getSinglePublicKey(View view) {
        mPublicKey = Utility.getInstance(this).getSinglePublicKey(mMmnemonic);
        mPublicKeyTv.setText(mPublicKey);
    }

    public void getAddress(View view) {
        if (mAddress == null) {
            if (mPublicKey == null) return;
            mAddress = Utility.getInstance(this).getAddress(mPublicKey);
            mAddressTv.setText(mAddress);
        }
    }

    private String signedHex;
    private static final String msg = "message";

    public void sign(View view) {
        byte[] aa = Utility.getInstance(this).sign(mPrivateKey, msg.getBytes());
        signedHex = bytesToHex(aa);
        Log.i("sign", "signedHex:" + signedHex);
    }

    public void verify(View view) {
        Log.i("verify", "pubKey:" + mPublicKey);
        Log.i("verify", "msg:" + msg);
        Log.i("verify", "signedHex:" + signedHex);
        boolean isValid = Utility.getInstance(this).verify(mPublicKey, msg.getBytes(), hexToByteArray(signedHex));
        Log.i("verify", "isValid:" + isValid);
    }

    public void getDid(View view) {
        String did = Utility.getInstance(this).getDid(mPublicKey);
        Log.i("getDid", "did:" + did);
    }

    public void getPublicKeyFromPrivateKey(View view) {
        if (mPrivateKey != null && !mPrivateKey.isEmpty()) {
            String pubKey = Utility.getInstance(this).getPublicKeyFromPrivateKey(mPrivateKey);
            Log.i("PublicKeyFromPrivateKey", "pubKey:" + pubKey);
        }
    }

    public void isAddressValid(View view) {
        if (mAddress != null && !mAddress.isEmpty()) {
            boolean isAddressValid = Utility.getInstance(this).isAddressValid(mAddress);
            Log.i("isAddressValid", "isAddressValid:" + isAddressValid);
        }
    }

    public void generateRawTransaction(View view) {
        String transaction = "{\"Transactions\":[{\"UTXOInputs\":[{\"address\":\"EU3e23CtozdSvrtPzk9A1FeC9iGD896DdV\",\"txid\":\"7736d0be003e86039f25bec1128a8eba12d67bb8a032947159f7617dbd368486\",\"index\":0,\"privateKey\":\"DFE88FE877CD15EDFCFA2125158618BB5CB76C9465B87D0B339B735FF7F59E61\"}],\"Fee\":100,\"Outputs\":[{\"amount\":1000,\"address\":\"EPzxJrHefvE7TCWmEGQ4rcFgxGeGBZFSHw\"},{\"amount\":99998900,\"address\":\"EU3e23CtozdSvrtPzk9A1FeC9iGD896DdV\"}]}]}";
        String rawTransaction = Utility.getInstance(this).generateRawTransaction(transaction);
        mRawTransactionTv.setText(rawTransaction);
        //020001000a3138303432383933383301868436bd7d61f759719432a0b87bd612ba8e8a12c1be259f03863e00bed0367700000000000002b037db964a231458d2d6ffd5ea18944c4f90e63d547c5d3b9874df66a4ead0a3e80300000000000000000000214b177c93439e1e31b1cda7c3b290f977c74cd0bfb037db964a231458d2d6ffd5ea18944c4f90e63d547c5d3b9874df66a4ead0a3b4dcf5050000000000000000217779f85469b90d2f648d6ba771fb641d1782715e00000000014140b87b1b489932bde0d163d566c1144113ef6ac27e5402c537901f810f5e38d300e4be1143d706c9a67cd2c994add92bd806e1a5ff208362f09dfdf09185977bb8232103293cd3a3359b65fea091cb6260675bd03a3c5e29cffb504136a508e9bbbd5a8bac
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() < 2) {
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public static byte[] hexToByteArray(String inHex) {
        int hexlen = inHex.length();
        byte[] result;
        if (hexlen % 2 == 1) {
            //奇数
            hexlen++;
            result = new byte[(hexlen / 2)];
            inHex = "0" + inHex;
        } else {
            //偶数
            result = new byte[(hexlen / 2)];
        }
        int j = 0;
        for (int i = 0; i < hexlen; i += 2) {
            result[j] = hexToByte(inHex.substring(i, i + 2));
            j++;
        }
        return result;
    }


    public static byte hexToByte(String inHex) {
        return (byte) Integer.parseInt(inHex, 16);
    }

}
