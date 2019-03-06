package com.elastos.demo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.wallet.library.entity.UriFactory;

public class RedPacketActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.red_packet_layout);
    }


    public void uriParse(View view){
        String tmp = "elaphant://elapay?CallbackUrl=https%3A%2F%2Fredpacket.elastos.org%2Fpacket%2Fgrab%2F2785028325609049-0%3F_locale%3Den_US%26offset%3D-480&Description=redpacket&AppID=cc053c61afc22dda9a309e96943c1734&SerialNumber=8328FAC296973F3D206A2DFAFB5CCA896E880B1BC09FE6D547B8EF85C39BCCD4&PublicKey=028971D6DA990971ABF7E8338FA1A81E1342D0E0FD8C4D2A4DF68F776CA66EA0B1&Signature=90E8A60DC055C90F4765E91B6E4F07031F55CF7DD2DA4EF1EF55EA41D160CB48879F62D70EC8ED090E4CBBE013D21E7580C36CFA2173A997ADADB7255B23098F&Amount=0.000200&PaymentAddress=EfLrR3EnqxV1MYzFDgvEsx9s65ywiP3Bp6&DID=ihKwfxiFpYme8mb11roShjjpZcHt1Ru5VB&CoinName=ELA&AppName=Red Packet";
        UriFactory uriFactory = new UriFactory();
        uriFactory.parse(tmp);
        Log.i("xidaokun", "xidaokun");
    }
}
