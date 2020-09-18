package jp.picpie.servicetest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.RequiresApi;

public class TestService extends Service {

    static boolean flag;//スレッドを停止させるフラグ

    public TestService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void onCreate()
    {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("TestService", "called TestService.onStartCommand()");
        String channelId = "service";
        String title = "TestService";

        // 通知設定
        NotificationManager notificationManager =
                (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel =
                new NotificationChannel(channelId, title, NotificationManager.IMPORTANCE_DEFAULT);

        if(notificationManager != null) {
            notificationManager.createNotificationChannel(channel);
            Notification notification = new Notification.Builder(getApplicationContext(), channelId)
                    .setContentTitle(title)
                    .setSmallIcon(R.drawable.menu)
                    .setContentText("service start")
                    .build();

            // フォアグラウンドで実行
            startForeground(1, notification);

            //Thread th;
            //th=new Thread(new test_thread());
            //th.start();

        }


        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("TestService", "onDestroy()");
    }

    private class test_thread implements Runnable {
        @Override
        public void run() {
            flag=true;
            android.media.ToneGenerator tg;
            tg = new android.media.ToneGenerator(android.media.AudioManager.STREAM_SYSTEM, android.media.ToneGenerator.MAX_VOLUME);
            while(flag) {
                try {
                    tg.startTone(android.media.ToneGenerator.TONE_SUP_ERROR,50);
                    Thread.sleep(2000);
                } catch (Exception e) {}
            }
        }
    }
}
