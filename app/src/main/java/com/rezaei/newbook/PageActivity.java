package com.rezaei.newbook;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.ArrayList;

public class PageActivity extends AppCompatActivity {
    private TextView page_title,page_text,page_number;
    private Button next,prev;
    private String section,chapter;
    private android.support.v7.widget.Toolbar toolbar_page;
    ActionBar actionBar;
    private ArrayList<PageClass> pages;
    private DbHelper dbHelper;
    int curentPage=1;
    int total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        pages = new ArrayList<>();
        dbHelper = new DbHelper(this);
        init();
        section = getIntent().getStringExtra("section");
        chapter= getIntent().getStringExtra("chapter");
        page_title.setText(section);

        refresher();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextPage(curentPage,total);
            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPrevPage();
            }
        });



    }
    public void init(){
        page_number = findViewById(R.id.tv_pagenumber);
        page_text = findViewById(R.id.tv_section_text);
        page_title = findViewById(R.id.tv_section_title);
        next = findViewById(R.id.btn_next);
        prev = findViewById(R.id.btn_prev);
        toolbar_page= findViewById(R.id.toolbar_chapter);
    }

    public void refresher(){
        dbHelper.opendatabase();
        int numOfPages = dbHelper.page_counter("page",section);
        total=numOfPages;
        curentPage=1;
        for(int i = 0 ; i < numOfPages ; i++){
            String text= dbHelper.text_getter("text",section,i+1,0);
            PageClass page= new PageClass(text,i+1);
            pages.add(page);
        }
        setPage(pages.get(0).getPage_text(),pages.get(0).getPage_number(),numOfPages);
        checkButtons();
        dbHelper.close();

    }

    public  void setPage(String text, int pageNumber, int total){
        page_text.setText(text);
        page_number.setText(getString(R.string.pages)+" "+pageNumber+" "+getString(R.string.from)+" "+total);
    }

    public void checkButtons(){
        if (curentPage==1){
            prev.setEnabled(false);
        }else if(curentPage == total){
            next.setEnabled(false);
        }else {
            prev.setEnabled(true);
            next.setEnabled(true);
        }
    }

    public void goToNextPage(int curentPage, int total){
        int nextPage= curentPage+1;
        if(nextPage<=total){
            setPage(pages.get(curentPage).getPage_text(),pages.get(curentPage).getPage_number(),total);
        }
        this.curentPage= nextPage;
        checkButtons();
    }

    public void goToPrevPage(){
        int prevPage= curentPage-1;
        if(prevPage>=1){
            setPage(pages.get(prevPage-1).getPage_text(),pages.get(prevPage-1).getPage_number(),total);
        }
        curentPage= prevPage;
        checkButtons();


    }


}

