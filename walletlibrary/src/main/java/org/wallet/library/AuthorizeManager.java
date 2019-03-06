package org.wallet.library;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.elastos.jni.Utility;

import org.wallet.library.utils.HexUtils;
import org.wallet.library.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class AuthorizeManager {


    public static String sign(Context context, String pk, String source){
        if(StringUtils.isNullOrEmpty(source)) return null;

        byte[] signed = Utility.getInstance(context).sign(pk, source.getBytes());
        if(signed == null) return null;
        return HexUtils.bytesToHex(signed);
    }

    public static boolean verify(Context context, String did, String PK, String source, String signed){

        if(StringUtils.isNullOrEmpty(did) || StringUtils.isNullOrEmpty(PK)
                || StringUtils.isNullOrEmpty(signed) || StringUtils.isNullOrEmpty(source)) return false;

        String tDid = Utility.getInstance(context).getDid(PK);
        boolean isValid = Utility.getInstance(context).verify(PK, source.getBytes(), HexUtils.hexToByteArray(signed));
        if(tDid.equals(did) && isValid) return true;

        return false;
    }

    public static void startWalletActivity(Context context, String extra, String toActivity){
        startWalletActivity(context, extra, null, toActivity);
    }

    public static void startWalletActivity(Context context, String extra, String fromActivity, String toActivity){
        if(StringUtils.isNullOrEmpty(extra) || StringUtils.isNullOrEmpty(toActivity)) return;
        Intent intent;
        if (isAppExist(context, "com.elastos.wallet")) {
            intent = new Intent();
            ComponentName componentName = new ComponentName("com.elastos.wallet",
                    toActivity);
            intent.putExtra(Constants.INTENT_EXTRA_KEY.META_EXTRA, extra);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            if(!StringUtils.isNullOrEmpty(fromActivity)){
                intent.putExtra(Constants.INTENT_EXTRA_KEY.PACKAGE_NAME, context.getPackageName());
                intent.putExtra(Constants.INTENT_EXTRA_KEY.ACTIVITY_CLASS, fromActivity);
            }
            intent.setComponent(componentName);
            context.startActivity(intent);
        } else {
            //download from market
            //Uri uri = Uri.parse("market://details?id=com.elastos.wallet");//id为包名

            //download from server
            Uri apkUri = Uri.parse("https://download.elastos.org/app/elephantwallet/elephant_wallet.apk");
            intent = new Intent(Intent.ACTION_VIEW, apkUri);
            context.startActivity(intent);
        }
    }

    public static void startClientActivity(Context context, String extra, String packageName, String toActivity){
        if (isAppExist(context, packageName)) {
            Intent intent = new Intent();
            ComponentName componentName = new ComponentName(packageName,
                    toActivity);
            intent.putExtra(Constants.INTENT_EXTRA_KEY.META_EXTRA, extra);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setComponent(componentName);
            context.startActivity(intent);
        }
    }

    private static boolean isAppExist(Context context, String packageName){
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        List<String> pName = new ArrayList();
        if(pinfo != null){
            for(int i = 0; i < pinfo.size(); i++){
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);
    }


}
