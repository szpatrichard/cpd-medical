package com.zubaid.cpd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class reflection extends AppCompatActivity {

    EditText idDateEditText, idTypeEditText, idCDPEditText;
    Button idBTNSave, idBTNVeiw, idBTNStartR, idBTNStopR,idBTNStartPlay, idBTNStopPlay;
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private String AudioSavePath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reflection);


        idDateEditText = findViewById(R.id.idDateEditText);
        idTypeEditText = findViewById(R.id.idTypeEditText);
        idCDPEditText = findViewById(R.id.idCDPEditText);
        idBTNSave = findViewById(R.id.idBTNSave);
        idBTNVeiw = findViewById(R.id.idBTNVeiw);
        idBTNStartR = findViewById(R.id.idBTNStartR);
        idBTNStopR = findViewById(R.id.idBTNStopR);
        idBTNStartPlay = findViewById(R.id.idBTNStartPlay);
        idBTNStopPlay = findViewById(R.id.idBTNStopPlay);

        // Start Recording
        idBTNStartR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkPermissionFromDevice() == true){
                    AudioSavePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +"recordingAudio.mp3";
                    mediaRecorder = new MediaRecorder();
                    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                    mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
                    mediaRecorder.setOutputFile(AudioSavePath);
                    Toast.makeText(reflection.this, "Recording Started", Toast.LENGTH_SHORT).show();
                    try {
                        mediaRecorder.prepare();
                        mediaRecorder.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    ActivityCompat.requestPermissions(reflection.this, new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.RECORD_AUDIO
                    }, 1);
                }
            }
        });

        // Stop Recording
        idBTNStopR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaRecorder.stop();
                mediaRecorder.release();
                Toast.makeText(getApplicationContext(), "Recording Completed", Toast.LENGTH_SHORT).show();
            }
        });

        // Start Playing
        idBTNStartPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(AudioSavePath);
                    mediaPlayer.prepare();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mediaPlayer.start();
                Toast.makeText(reflection.this, "Playing Audio", Toast.LENGTH_SHORT).show();
            }
        });

        // Stop Playing
        idBTNStopPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                Toast.makeText(reflection.this, "Stop Playing", Toast.LENGTH_SHORT).show();
            }
        });


        idBTNVeiw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), view.class);
                startActivity(intent);
//                Toast.makeText(Activity_activtiy.this, "View", Toast.LENGTH_SHORT).show();
            }
        });

        idBTNSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
            }
        });

    }

    private boolean checkPermissionFromDevice(){
        int first = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int second = ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO);
        return first == PackageManager.PERMISSION_GRANTED && second == PackageManager.PERMISSION_GRANTED;
    }

    public void insert(){
        try {
            String date = idDateEditText.getText().toString();
            String type = idTypeEditText.getText().toString();
            String cdp = idCDPEditText.getText().toString();

            SQLiteDatabase db = openOrCreateDatabase("cpd", MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS activity(date VARCHAR, type VARCHAR, cdp VARCHAR)");

            String sql = "INSERT INTO activity VALUES('"+date+"', '"+type+"', '"+cdp+"')";
            db.execSQL(sql);
            Toast.makeText(this, "Insert Successfully", Toast.LENGTH_SHORT).show();

            idDateEditText.setText("");
            idTypeEditText.setText("");
            idCDPEditText.setText("");
            idDateEditText.requestFocus();


        }
        catch (Exception e){
            Toast.makeText(this, "Insert Failed", Toast.LENGTH_SHORT).show();
        }
    }
}