package com.cjh.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DataAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private int layout;
    private ArrayList<DataVO> data;

    public DataAdapter (Context context, int layout, ArrayList<DataVO> data) {
        this.context = context;
        this.layout = layout;
        this.data = data;

        //context에서 layoutInflater 추출
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() { return data.size();}

    @Override
    public Object getItem(int position) { return data.get(position);}


    @Override
    public long getItemId(int position) { return position;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // ======================================= 항목당 한번씩 일어나는 일
        // - Arraylist<ChatVO> 에 저장되어 있는 데이터로 View로 바뀐 템플릿을 꾸민다!
        // - listView에 추가한다~~
        if (convertView == null) { // 이전에 inflate된 View가 없다면~~
            convertView = inflater.inflate(layout, parent, false);
        }

        // 템플릿을 View로 만들어서 저장해둔 객체인 converView안에서 찾겠다
        // 템플릿에서 찾겠다
        ImageView img = convertView.findViewById(R.id.img);
        TextView number = convertView.findViewById(R.id.number);
        TextView black_date = convertView.findViewById(R.id.black_date);

        img.setImageResource(data.get(position).getImg());
        number.setText(data.get(position).getNumber());
        black_date.setText(data.get(position).getBlackdate());


        return convertView;
    }



}
