package com.elastos.demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

public class HomeActivity extends Activity {

    public static String words;
    private ListView mInputLv;
    private EditText mShowEdt;
    private String[] tmp = new String[12];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_layout);

        mInputLv = findViewById(R.id.word_list);
        mShowEdt = findViewById(R.id.show_word);

        words = Utils.getWords(this, "words/mnemonic_chinese.txt");

        mInputLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        for(int i=0; i<12; i++) tmp[i] = i+"";
        mInputLv.setAdapter(new WordAdapter(this, tmp));
    }

    public void verify(View view) {
        String result = arryToString(tmp);
        mShowEdt.setText(result);
    }

    public void trans(View view) {

    }

    private String arryToString(String[] arrys){
        StringBuilder sb = new StringBuilder();
        for(String arry : arrys){
            sb.append(arry).append(" ");
        }

        return sb.toString();
    }

}
