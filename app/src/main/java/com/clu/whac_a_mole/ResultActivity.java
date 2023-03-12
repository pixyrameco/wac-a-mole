package com.clu.whac_a_mole;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class ResultActivity extends AppCompatActivity {

    TextView textView;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent = getIntent();
        TinyDB tinydb = new TinyDB(this);
        int score = intent.getIntExtra("score", 0);
        textView = (TextView) findViewById(R.id.myScore);
        textView.setText("Current Score is: " + score);
        listView = (ListView) findViewById(R.id.scoreList);

        ArrayList<Integer> arrayList = MainActivity.ratingList;
        arrayList.add(score);
        Collections.sort(arrayList, Collections.reverseOrder());
        tinydb.putListInt("rating", arrayList);

        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);

        findViewById(R.id.playAgain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ResultActivity.this, GameActivity.class));
                finish();
            }
        });
    }
}