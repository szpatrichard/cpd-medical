package com.zubaid.cpd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class view extends AppCompatActivity {
    ListView idListView;
    ArrayList<String> idArrayList = new ArrayList<>();
    ArrayAdapter<String> idArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        SQLiteDatabase db = openOrCreateDatabase("cpd", MODE_PRIVATE, null);
        idListView = findViewById(R.id.idListView);
        final Cursor c = db.rawQuery("SELECT * FROM activity", null);
        int dateIndex = c.getColumnIndex("date");
        int typeIndex = c.getColumnIndex("type");
        int cdpIndex = c.getColumnIndex("cdp");

        idArrayList.clear();
        idArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, idArrayList);
        idListView.setAdapter(idArrayAdapter);

        final ArrayList<activity_Record> records = new ArrayList<>();
        while (c.moveToNext()) {
            String date = c.getString(dateIndex);
            String type = c.getString(typeIndex);
            String cdp = c.getString(cdpIndex);
            idArrayList.add(date + "\t\t\t\t" + type + "\t\t\t\t" + cdp);
            records.add(new activity_Record(date, type, cdp));
        }
        idArrayAdapter.notifyDataSetChanged();

        idListView.setOnItemClickListener((parent, view, position, id) -> {
            activity_Record record = records.get(position);
            String date = record.getDate();
            String type = record.getType();
            String cdp = record.getCdp();
            Toast.makeText(this, date + "\t\t\t\\" + type + "\t\t\t\t" + cdp, Toast.LENGTH_SHORT).show();
        });

    }

}