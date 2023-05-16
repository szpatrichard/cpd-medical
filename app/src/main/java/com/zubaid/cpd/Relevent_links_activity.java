package com.zubaid.cpd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Relevent_links_activity extends AppCompatActivity {
    Button coru, acslm, email, Logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relevent_links);

        coru = findViewById(R.id.coru);
        acslm = findViewById(R.id.acslm);
        email = findViewById(R.id.email);
        Logout = findViewById(R.id.Logout);

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout(v);
            }
        });

        coru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coru(v);
            }
        });

        acslm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acslm(v);
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email(v);
            }
        });

    }

    public void email(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:info@coru.ie"));
        startActivity(intent);
    }

    public void coru(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.coru.ie/"));
        startActivity(intent);
    }

    public void acslm(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.acslm.ie/"));
        startActivity(intent);
    }

//    create a logout button
    public void Logout(View view) {
        Intent intent = new Intent(Relevent_links_activity.this, Login.class);
        startActivity(intent);
    }
}