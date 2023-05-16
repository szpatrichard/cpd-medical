package com.zubaid.cpd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.firestore.core.View;

public class Attachment_activity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attachment);

        imageView = findViewById(R.id.imageView);


    }

    int requestCode = 1;
    public void onActionResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        Context context = getApplicationContext();
        if(requestCode == requestCode && resultCode == Activity.RESULT_OK){
            if(data == null){
                Toast.makeText(context, "No file selected", Toast.LENGTH_LONG).show();
                return;
            }
            Uri uri = data.getData();
            imageView.setImageURI(uri);
        }
    }

//    @Deprecated
//    public void openFileChooser(View view){
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("image/*");
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
//        startActivityForResult(intent, requestCode);
//
//    }

    public void openFileChooser(android.view.View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(intent, requestCode);
    }
}