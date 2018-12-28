package com.rezaei.newbook;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.rezaei.newbook.BookMarkActivity.refresher;

public class BookMarkActivity extends AppCompatActivity {


    private static ListView lv_bookmark;
    private static Context mContext;
    private static DbHelper dbHelper;
    private static CustomAdapter adapter;
    private static ArrayList<String> sections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_mark);
        lv_bookmark = findViewById(R.id.lv_bookmarks);
        dbHelper = new DbHelper(this);
        mContext= this;
        refresher();

    }
    public static void refresher() {
        dbHelper.opendatabase();
        sections=new ArrayList<>();
        int numOfBookmarks = dbHelper.count_bookmark();
        for (int i = 0; i < numOfBookmarks; i++) {
            sections.add(dbHelper.bookmark_getter(i));
        }
        adapter = new CustomAdapter(R.layout.section_row, sections,null,mContext);
        lv_bookmark.setAdapter(adapter);
        dbHelper.close();


    }
}
