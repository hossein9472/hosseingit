package com.teta_tm.newbook;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PageActivity extends AppCompatActivity {
    private TextView page_title,page_text,page_number;
    private Button next,prev;
    private String section,chapter;
    private ArrayList<PageClass> pages;
    private DbHelper dbHelper;
    private ActionBar actionbar;
    private int numOfPages,currentPage;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        actionbar = getSupportActionBar();
        pages = new ArrayList<>();
        dbHelper = new DbHelper(this);
        init();
        section = getIntent().getStringExtra("section");
        chapter = getIntent().getStringExtra("chapter");
        actionbar.setTitle(getString(R.string.chapter)+" "+chapter);
        page_title.setText(section);

        refresher();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextPage(currentPage,numOfPages);
            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPrevPage(currentPage,numOfPages);
            }
        });
    }

    public void init(){
        page_number = findViewById(R.id.tv_pagenumber);
        page_text = findViewById(R.id.tv_section_text);
        page_title = findViewById(R.id.tv_section_title);
        next = findViewById(R.id.btn_next);
        prev = findViewById(R.id.btn_prev);
    }

    public void refresher(){
        dbHelper.opendatabase();
        numOfPages = dbHelper.page_counter("page",section);
        currentPage = 1;
        for(int i = 0 ; i < numOfPages ; i++){
            String text = dbHelper.text_getter("text",section,i+1,0);
            PageClass page = new PageClass(text,i+1);
            pages.add(page);
        }
        setPage(pages.get(0).getPage_text(),pages.get(0).getPage_number(),numOfPages);
        checkButtons();
        dbHelper.close();
    }

    public void setPage(String text, int pageNumber,int total){
        page_text.setText(text);
        page_number.setText(getString(R.string.page)+" "+pageNumber+" "+
                            getString(R.string.from)+" "+total);
    }

    public void checkButtons(){
        if(currentPage == 1){
            prev.setEnabled(false);
            next.setEnabled(true);
        }else if(currentPage == numOfPages){
            next.setEnabled(false);
            prev.setEnabled(true);
        }else{
            prev.setEnabled(true);
            next.setEnabled(true);
        }
    }
    public void goToNextPage(int currentPage, int total){
        int nextPage = currentPage+1;
        if(nextPage <= total){
            setPage(pages.get(currentPage).getPage_text(),
                    pages.get(currentPage).getPage_number(),total);
        }
        this.currentPage = nextPage;
        checkButtons();
    }

    public void goToPrevPage(int currentPage, int total){
        int prevPage = currentPage - 1;
        if( prevPage >= 1){
            setPage(pages.get(prevPage-1).getPage_text(),
                    pages.get(prevPage-1).getPage_number(),total);
        }
        this.currentPage = prevPage;
        checkButtons();
    }
}
