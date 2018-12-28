package com.rezaei.newbook;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Search extends AppCompatActivity {

    TextView row_result_section, row_result_chapter;
    RadioGroup rad_group;
    RadioButton rad_btn_chapter, rad_btn_text;
    ImageView img_search;
    EditText ed_search;
    ListView lv_search;
    LinearLayout linear_chapters_sections;
    String SELECT_TITLE = "chapter,section";
    String SELECT_TEXT = "substr(text,1,50)";
    private String select;
    boolean checkListView=false;

    ArrayList<String> section_search, chapter_search, text_search;
    DbHelper dbHelper;
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        inIt();
        rad_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rad_btn_chapters) {
                    select="ELECT_TITLE";
                } else if (checkedId == R.id.rad_btn_text) {
                    select = SELECT_TEXT;

                }
            }
        });

        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkQuary()){
                    search(ed_search.getText().toString());

                }else{
                    Toast.makeText(Search.this, "متن جستجو خالی است", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void search(String quary) {
        dbHelper.opendatabase();
        section_search = new ArrayList<>();
        chapter_search = new ArrayList<>();
        text_search = new ArrayList<>();
        if (select.equalsIgnoreCase(SELECT_TITLE)){
            int count = dbHelper.search_count_title(quary,select);
            if (count>0) {
                for (int i = 0; i < count; i++) {
                    chapter_search.add(dbHelper.search_title(quary, select, i)[0]);
                    section_search.add(dbHelper.search_title(quary, select, i)[1]);
                }
                adapter= new CustomAdapter(this,R.layout.row_result_search,chapter_search,section_search);
            }else {
                String emty="هیچ عبارتی یافت نشد";
                text_search.add(emty);
                adapter= new CustomAdapter(this,R.layout.search_text_row,text_search);
//                Toast.makeText(this, " هیچ عبارتی یافت نشد.", Toast.LENGTH_SHORT).show();

            }

        }else if(select.equalsIgnoreCase(SELECT_TEXT)){
            int count = dbHelper.search_count_text(quary,select);
            if(count>0) {
                for (int i = 0; i < count; i++) {
                    text_search.add(dbHelper.search_text(quary, select, i));
                }
                adapter= new CustomAdapter(this,R.layout.search_text_row,text_search);
            }else {
                String emty="هیچ عبارتی یافت نشد";
                text_search.add(emty);
                adapter= new CustomAdapter(this,R.layout.search_text_row,text_search);
//                Toast.makeText(this, " هیچ عبارتی یافت نشد.", Toast.LENGTH_SHORT).show();

            }

        }

        lv_search.setAdapter(adapter);



        dbHelper.close();
    }

    public void inIt() {
        rad_group = findViewById(R.id.rad_group);
        rad_btn_chapter = findViewById(R.id.rad_btn_chapters);
        rad_btn_text = findViewById(R.id.rad_btn_text);
        img_search = findViewById(R.id.img_search);
        ed_search = findViewById(R.id.ed_search);
        lv_search = findViewById(R.id.lv_search);
        dbHelper = new DbHelper(this);
        select = SELECT_TITLE;
        rad_btn_chapter.setSelected(true);

    }

    public boolean checkQuary() {
        boolean isFild= true;
        if (ed_search.getText().length()==0){
            isFild=false;
            return isFild;
        }else return isFild;

    }


}