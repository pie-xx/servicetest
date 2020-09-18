package jp.picpie.servicetest;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class TestActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        android.content.Intent _intent;
        _intent=new android.content.Intent(getApplicationContext(), TestService.class);
        getApplicationContext().startForegroundService(_intent);
        //getApplicationContext().startService(_intent);

        Log.d("TestReceiver", "startForegroundService "+_intent.toString());

        Toast.makeText( this, "startForegroundService", Toast.LENGTH_LONG).show();
        finish();
    }
}