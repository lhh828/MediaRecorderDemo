package com.lh.mediarecorddemo.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.lh.mediarecorddemo.R;




public class MainActivity extends AppCompatActivity {


    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean ispermission = (ContextCompat.checkSelfPermission(this, PERMISSIONS[0]) != 0 ||
                ContextCompat.checkSelfPermission(this, PERMISSIONS[1]) != 0 ||
                ContextCompat.checkSelfPermission(this, PERMISSIONS[2]) != 0 );

        if(ispermission){
            ActivityCompat.requestPermissions(this,PERMISSIONS,1);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 1 && grantResults != null && grantResults[0] == PackageManager.PERMISSION_GRANTED){

        }else{
            Toast.makeText(this,"拒绝了权限：" + permissions[0],Toast.LENGTH_SHORT).show();
        }
    }

    public void record(View view) {
        startActivity(new Intent(this,RecordActivity.class));
    }
}
