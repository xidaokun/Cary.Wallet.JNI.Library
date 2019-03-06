package com.elastos.demo;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WordAdapter extends BaseAdapter {
    private Context mContext;
    private String[] mData;

    public WordAdapter(Context context, String[] data){
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public int getCount() {
        return mData!=null ? mData.length: 0;
    }

    @Override
    public Object getItem(int position) {
        return mData[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private LayoutInflater mInflater;
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.word_item_layout, parent, false);
            holder = new ViewHolder();

            holder.btn = convertView.findViewById(R.id.btn);
            holder.edit = convertView.findViewById(R.id.edt);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String word = s.toString();
                mData[position] = word.trim();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        int index = position + 1;
        holder.edit.setHint(index+"");

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = mData[position];
                if(!Utils.isNullOrEmpty(word) && HomeActivity.words.contains(word)){
                    Toast.makeText(mContext, "存在", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "不存在", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return convertView;
    }

    final class ViewHolder {
        Button btn;
        EditText edit;


    }

}
