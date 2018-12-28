package com.teta_tm.newbook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<String> {
    private Context mContext;
    private ArrayList<String> chapters;
    private ArrayList<String> sections;

    public CustomAdapter(Context mContext,int layout,ArrayList<String> chapters){
        super(mContext,layout,chapters);
        this.mContext = mContext;
        this.chapters = chapters; }
    public CustomAdapter(Context mContext,int layout,ArrayList<String> chapters,ArrayList<String> sections){
        super(mContext,layout,chapters);
        this.mContext = mContext;
        this.chapters = chapters;
        this.sections = sections; }
    @SuppressLint("ViewHolder") @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater in = LayoutInflater.from(mContext);
        View row = in.inflate(sections == null ? R.layout.chapters_row :
                        R.layout.row_result,
                parent,false);
        if(sections == null){
            TextView name = row.findViewById(R.id.chapter_title);
            name.setText(chapters.get(position));
        }else{
            TextView chapter = row.findViewById(R.id.tv_result_chapter);
            TextView section = row.findViewById(R.id.tv_result_section);
            chapter.setText(chapters.get(position));
            section.setText(sections.get(position));
        }

//        name.setTypeface(Typeface.createFromAsset(getAssets(), "font/koodak.ttf"));
        return (row);
    }
}
