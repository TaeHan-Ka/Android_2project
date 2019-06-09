package com.example.myapplication;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class FriendsAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<Phone> data; //Item 목록을 담을 배열
    private int layout;

    public FriendsAdapter(Context context, int layout, ArrayList<Phone> data) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        this.layout = layout;
    }

    @Override
    public int getCount() { //리스트 안 Item의 개수를 센다.
        return data.size();
    }

    @Override
    public String getItem(int position) {
        return data.get(position).getPhone();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
        }
        Phone p = data.get(position);

        //전화번호 연동
        TextView phone = (TextView) convertView.findViewById(R.id.choicephone);
        phone.setText(p.getPhone());


        return convertView;
    }
}