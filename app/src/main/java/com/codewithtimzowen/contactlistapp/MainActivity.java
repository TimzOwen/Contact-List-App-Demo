package com.codewithtimzowen.contactlistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tvFamily, tvProff, tvMessage, tvNicknames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvFamily = findViewById(R.id.tv_family);
        tvProff = findViewById(R.id.tv_proff);
        tvNicknames = findViewById(R.id.tv_nickNames);
        tvMessage = findViewById(R.id.tv_message);

        tvMessage.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), MessageActivity.class)));
        tvNicknames.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), NickNamesActivity.class)));
        tvFamily.setOnClickListener(v -> startActivity(new Intent(this, FamilyActivity.class)));
    }
}