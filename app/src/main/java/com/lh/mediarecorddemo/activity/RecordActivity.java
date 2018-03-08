package com.lh.mediarecorddemo.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lh.mediarecorddemo.FileUtil;
import com.lh.mediarecorddemo.RecordUtils;
import com.lh.mediarecorddemo.R;

import java.util.UUID;

public class RecordActivity extends AppCompatActivity implements View.OnClickListener{

    TextView recordTv;
    SurfaceView surfaceView;
    RecordUtils recordUtils;
    int recordMaxTime = 30;
    int recordTime ;

    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        recordTv = (TextView) findViewById(R.id.record_tv);
        surfaceView = (SurfaceView) findViewById(R.id.surface_view);


        recordUtils = new RecordUtils(this);
        recordUtils.setRecorderType(RecordUtils.MEDIA_VIDEO);
        recordUtils.setTargetDir(FileUtil.getExternalStorageDirectory(this));
        recordUtils.setTargetName(UUID.randomUUID() + ".mp4");
        recordUtils.setSurfaceView(surfaceView);

        recordTv.setOnClickListener(this);


    }



    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.record_tv){
            if(!recordUtils.isRecording()) {
                handler.postDelayed(run,1000);
                recordUtils.record();
                recordTv.setText(recordMaxTime +"s");
            }else{
                handler.removeCallbacks(run);
                if(recordTime < 3){
                    recordTime = 0;
                    recordUtils.stopRecordUnSave();
                    recordTv.setText("录制");
                    Toast.makeText(this,"时间太短",Toast.LENGTH_SHORT).show();
                }
                else{
                    recordTime = 0;
                    recordUtils.stopRecordSave();
                    recordTv.setText("录制");
                    Toast.makeText(this,"保存地址："+ recordUtils.getTargetFilePath(),Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1) {
                if(recordTime == 30){
                    recordTime = 0;
                    recordUtils.stopRecordSave();
                    recordTv.setText("录制");
                    Toast.makeText(RecordActivity.this,"保存地址："+ recordUtils.getTargetFilePath(),Toast.LENGTH_SHORT).show();
                    return;
                }
                recordTime++;
                recordTv.setText(recordMaxTime - recordTime + "s");
                handler.postDelayed(run,1000);
            }
        }
    };


    Runnable run = new Runnable() {
        @Override
        public void run() {
            handler.sendEmptyMessage(1);
        }
    };




}
