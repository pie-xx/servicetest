package jp.picpie.servicetest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

public class TestReceiver extends BroadcastReceiver  {
    static MediaPlayer mPlayer = new MediaPlayer();

    @Override
    public void onReceive(final Context context, Intent intent) {
        Log.d("TestReceiver", "onReceive "+intent.toString());
        TestService.flag=false;
        //android.content.Intent _intent;
        //_intent=new android.content.Intent(context.getApplicationContext(), TestService.class);
        Bundle extras = intent.getExtras();
        String action = intent.getAction();
        int dval = extras.getInt("talkMemoId");

        try {
            if(mPlayer.isPlaying()) {
                mPlayer.stop();
                mPlayer.release();
                mPlayer = null;
            }
            if( mPlayer==null){
                mPlayer = new MediaPlayer();
            }
            if(action.equals("jp.picpie.REQ_START_PLAY_TALKMEMO")){
                if( dval== 1) {
                    mPlayer.setDataSource("/storage/emulated/0/DCIM/Skrillex.mp3");  // /sdcard/Music/saturdaynight.mp3
                }else{
                    mPlayer.setDataSource("/storage/emulated/0/DCIM/saturdaynight.mp3");  // /sdcard/Music/saturdaynight.mp3
                }
                mPlayer.prepare();
                mPlayer.start();
                mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        Intent intent = new Intent("jp.picpie.NOTIFY_STOP_PLAY_TALKMEMO");
                        intent.setPackage("jp.picpie.bcsender");
                        intent.putExtra("talkMemoId", 2);
                        context.sendBroadcast(intent);
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        context.getApplicationContext().stopService(_intent);
    }

}
