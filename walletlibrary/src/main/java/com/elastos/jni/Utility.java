package com.elastos.jni;

import android.content.Context;
import android.content.res.AssetManager;

import org.wallet.library.utils.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

/**
 * BreadWallet
 * <p/>
 * Created by Mihail Gutan <mihail@breadwallet.com> on 8/4/15.
 * Copyright (c) 2016 breadwallet LLC
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
public class Utility {

    private static Utility mInstance;
    private static String mWords = null;
    private static String mLang = null;
    private static Context mContext;

    static {
        System.loadLibrary("utility");
    }

    private Utility(Context context){
        this.mContext = context;
    }

    public static Utility getInstance(Context context){
        if(mInstance == null){
            mInstance = new Utility(context);
        }
        return mInstance;
    }

    public String getSinglePrivateKey(String mnemonic){
        mLang = detectLang(mContext, mnemonic);
        mWords = getWords(mContext, mLang +"-BIP39Words.txt");
        return getSinglePrivateKey(getLanguage(mLang), mnemonic, mWords, "");
    }

    public String getSinglePublicKey(String mnemonic){
        mLang = detectLang(mContext, mnemonic);
        mWords = getWords(mContext, mLang +"-BIP39Words.txt");
        return getSinglePublicKey(getLanguage(mLang), mnemonic, mWords, "");
    }

    public String generateMnemonic(String language){
        String words = getWords(mContext, language +"-BIP39Words.txt");
        return nativeGenerateMnemonic(getLanguage(language), words);
    }

    private String getSinglePrivateKey(String jlanguage, String jmnemonic, String jwords, String jpassword){
        return nativeGetSinglePrivateKey(jlanguage, jmnemonic, jwords, jpassword);
    }

    private String getSinglePublicKey(String jlanguage, String jmnemonic, String jwords, String jpassword){
        return nativeGetSinglePublicKey(jlanguage, jmnemonic, jwords, jpassword);
    }

    public String getAddress(String jpublickey){
        return nativeGetAddress(jpublickey);
    }

    public String generateRawTransaction(String transaction){
        return nativeGenerateRawTransaction(transaction);
    }

    public byte[] sign(String jprivateKey, byte[] data){
        return nativeSign(jprivateKey, data);
    }

    public boolean verify(String publicKey, byte[] data, byte[] signedData){
        return nativeVerify(publicKey, data, signedData);
    }

    public String getDid(String publicKey){
        return nativeGetDid(publicKey);
    }

    public String getPublicKeyFromPrivateKey(String privateKey){
        return nativeGetPublicKeyFromPrivateKey(privateKey);
    }

    public boolean isAddressValid(String address){
        return nativeIsAddressValid(address);
    }

    private static native String nativeGenerateMnemonic(String jlanguage, String jwords);

    private static native String nativeGetSinglePrivateKey(String jlanguage, String jmnemonic, String jwords, String jpassword);

    private static native String nativeGetSinglePublicKey(String jlanguage, String jmnemonic, String jwords, String jpassword);

    private static native String nativeGetAddress(String jpublickey);

    private static native String nativeGenerateRawTransaction(String jtransaction);

    private static native byte[] nativeSign(String jprivateKey, byte[] jdata);

    private static native boolean nativeVerify(String jpublicKey, byte[] jdata, byte[] jsignedData);

    private static native String nativeGetDid(String jpublicKey);

    private static native String nativeGetPublicKeyFromPrivateKey(String jprivateKey);

    private static native boolean nativeIsAddressValid(String jaddress);

    public static String getLanguage(String languageCode){
        if(languageCode.equalsIgnoreCase("en")) return "english";
        if(languageCode.equalsIgnoreCase("es")) return "spanish";
        if(languageCode.equalsIgnoreCase("fr")) return "french";
        if(languageCode.equalsIgnoreCase("ja")) return "japanese";
        if(languageCode.equalsIgnoreCase("zh")) return "chinese";
        return "english";
    }

    public static String getWords(Context context, String name) {
        if (name == null) return null;
        StringBuffer content = new StringBuffer();
        InputStream inputStream = null;
        try {
            inputStream = context.getResources().getAssets().open("words/"+name);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (inputStream != null) {
                InputStreamReader inputReader = new InputStreamReader(inputStream);
                BufferedReader buffreader = new BufferedReader(inputReader);
                String line;
                while ((line = buffreader.readLine()) != null)
                    content.append(line).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return content.toString();
    }

    public static String[] LANGS = {"en", "es", "fr", "ja", "zh"};
    public static String detectLang(Context app, String paperKey) {
        if (StringUtils.isNullOrEmpty(paperKey)) {
            return null;
        }
        String lang = "en";
        String cleanPaperKey = cleanPaperKey(app, paperKey);
        String firstWord = cleanPaperKey.split(" ")[0];

        for (String s : LANGS) {
            List<String> words = getList(app, s);
            if (words.contains(firstWord)) {
                lang = s;
                break;
            }
        }
        return lang;
    }

    public static String cleanPaperKey(Context activity, String phraseToCheck) {
        return Normalizer.normalize(phraseToCheck.replace("　", " ")
                .replace("\n", " ").trim().replaceAll(" +", " "), Normalizer.Form.NFKD);
    }

    public static String cleanWord(String word) {
        String w = Normalizer.normalize(word.trim().replace("　", "")
                .replace(" ", ""), Normalizer.Form.NFKD);
        return w;
    }

    private static List<String> getList(Context app, String lang) {
        String fileName = "words/" + lang + "-BIP39Words.txt";
        List<String> wordList = new ArrayList<>();
        BufferedReader reader = null;
        try {
            AssetManager assetManager = app.getResources().getAssets();
            InputStream inputStream = assetManager.open(fileName);
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = reader.readLine()) != null) {
                wordList.add(cleanWord(line));
            }
        } catch (Exception ex) {
            ex.printStackTrace();

        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>(wordList);
    }
}
