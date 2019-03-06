package com.elastos.demo;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Locale;


public class Utils {
    private static final String TAG = Utils.class.getSimpleName();

    public static void initConfig(Context context) {
        String rootPath = context.getFilesDir().getParent() + "/elastos";
        String[] names = new String[]{"words/CoinConfig.json", "words/mnemonic_chinese.txt", "words/mnemonic_french.txt", "words/mnemonic_italian.txt", "words/mnemonic_japanese.txt", "words/mnemonic_spanish.txt"};

        for (int i = 0; i < names.length; ++i) {
            InputStream is = context.getClass().getClassLoader().getResourceAsStream("assets/config/" + names[i]);

            try {
                OutputStream fosto = new FileOutputStream(rootPath + "/" + names[i]);
                byte[] bt = new byte[1024];
                boolean var7 = false;

                int c;
                while ((c = is.read(bt)) > 0) {
                    fosto.write(bt, 0, c);
                }

                is.close();
                fosto.close();
            } catch (Exception var8) {
                var8.printStackTrace();
            }
        }

    }

    public static String getWords(Context context, String name) {
        if (name == null) return null;
        StringBuffer content = new StringBuffer();
        InputStream inputStream = context.getClass().getClassLoader().getResourceAsStream("assets/" + name);

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

    public static boolean isNullOrEmpty(String value){
        if(value==null || value.isEmpty()) return true;
        return false;
    }
}
