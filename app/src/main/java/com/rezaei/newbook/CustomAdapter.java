package com.rezaei.newbook;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class CustomAdapter extends ArrayAdapter {

    private Context mContext;
    private ArrayList<String>chapters,sections;
    private ArrayList<Integer>stars;
    DbHelper dbHelper;


    public CustomAdapter(Context mContext,int layout,ArrayList<String> chapters ) {
        super(mContext,layout,chapters);
        this.mContext=mContext;
        this.chapters=chapters;

    }
    public CustomAdapter(int layout ,ArrayList<String> sections,ArrayList<Integer>stars,Context mContext){
        super(mContext,layout,sections);
        this.mContext=mContext;
        this.sections=sections;
        this.stars=stars;

    }

    public CustomAdapter(Context mContext,int layout,ArrayList<String> chapters,ArrayList<String> sections ) {
        super(mContext,layout,chapters);
        this.mContext=mContext;
        this.chapters=chapters;
        this.sections=sections;

    }


    @SuppressLint("ViewHolder") @Override
    public View getView(final int position , View convertView, ViewGroup parent) {
        dbHelper=new DbHelper(mContext);
        LayoutInflater in = LayoutInflater.from(mContext);
        if (sections!=null&&stars!=null&&chapters==null){
            View row = in.inflate(R.layout.section_row,parent,false);
                final ImageView img_star=row.findViewById(R.id.star_book_mark);
                final TextView section_title=row.findViewById(R.id.section_title);
                TextView section_num = row.findViewById(R.id.section_num);
                if(stars.get(position)==0){
                    img_star.setImageResource(android.R.drawable.star_big_off);
                }else{
                    img_star.setImageResource(android.R.drawable.star_big_on);
                }
                section_title.setText(sections.get(position));
                section_num.setText((position+1)+"");
                img_star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(stars.get(position)==1){
                        updateStar(0,sections.get(position),position);
                        img_star.setImageResource(android.R.drawable.star_big_off);
                    }else {
                        updateStar(1,sections.get(position),position);
                        img_star.setImageResource(android.R.drawable.star_big_on);
                    }
                }
            });
                return (row);

        }else if(stars==null&&sections==null&&chapters!=null) {
            View row = in.inflate(R.layout.search_text_row, parent, false);
                TextView text = row.findViewById(R.id.tv_text_sea);
                TextView teedad = row.findViewById(R.id.tv_text_num_sea);
                text.setText(chapters.get(position));
                teedad.setText((position + 1) + "");
                return (row);

        } else if (stars==null&&sections!=null&&chapters!=null) {
            View row= in.inflate(R.layout.row_result_search,parent,false);
                TextView chapter = row.findViewById(R.id.tv_row_result_chapter);
                TextView section = row.findViewById(R.id.tv_row_result_section);
                //here we inflate the chapter row,section row
                // chapter and section is a text view and name
                chapter.setText(chapters.get(position));
                section.setText(sections.get(position));
                return (row);
        }else {
            View row = in.inflate(R.layout.section_row,parent,false);
            ImageView img_delete=row.findViewById(R.id.star_book_mark);
            img_delete.setImageResource(android.R.drawable.ic_menu_delete);
            TextView section_title=row.findViewById(R.id.section_title);
            TextView section_num = row.findViewById(R.id.section_num);
            section_title.setText(sections.get(position));
            section_num.setText((position+1)+"");

            img_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    diloge(position);
                }
            });
            return(row);
        }
//    name.setTypeface(Typeface.createFromAsset(getAssets(), "font/koodak.ttf"));
    }
    public void updateStar(int value,String section,int position){
        DbHelper dbHelper=new DbHelper(mContext);
        dbHelper.opendatabase();
        dbHelper.updateBookMark(value,section);
        if(position!=-1)
            stars.add(position,value);
        dbHelper.close();
    }

    public void diloge(final int position){
        final Dialog di= new Dialog(mContext);
        di.requestWindowFeature(Window.FEATURE_NO_TITLE);
        di.setContentView(R.layout.diloge_delete);
        di.setCanceledOnTouchOutside(true);
        di.setCancelable(true);
        di.show();
        Button btn_cancel=di.findViewById(R.id.btn_cancel);
        Button btn_ok=di.findViewById(R.id.btn_ok);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                di.dismiss();
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStar(0,sections.get(position),-1);
                BookMarkActivity.refresher();
                di.dismiss();
            }
        });
    }


}
