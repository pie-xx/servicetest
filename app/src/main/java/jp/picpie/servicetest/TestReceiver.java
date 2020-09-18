package jp.picpie.servicetest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class TestReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("TestReceiver", "onReceive "+intent.toString());
        TestService.flag=false;
        android.content.Intent _intent;
        _intent=new android.content.Intent(context.getApplicationContext(), TestService.class);
        context.getApplicationContext().stopService(_intent);
    }
}
