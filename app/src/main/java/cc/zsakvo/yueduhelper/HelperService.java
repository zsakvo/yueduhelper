package cc.zsakvo.yueduhelper;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;


import java.io.IOException;
import java.util.Timer;

import androidx.core.app.NotificationCompat;
import cc.zsakvo.yueduhelper.server.HelperServer;

import static android.content.ContentValues.TAG;

@SuppressLint("NewApi")
public class HelperService extends Service {

    public static final String TAG = "MyService";

    private HelperServer server;

    private int port;

    private Timer mTimer;

    private void startTimer(Service service){
        if (mTimer==null){
            mTimer = new Timer();
            CheckTask checkTask = new CheckTask(this,server,service,port);
            mTimer.schedule(checkTask,0L,800L);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("settings", Context.MODE_MULTI_PROCESS);
        port = preferences.getInt("number_picker",2333);
        Boolean bs_zssq = preferences.getBoolean("bs_zssq", true);
        Boolean bs_jjcs = preferences.getBoolean("bs_jjcs", true);
        Log.d(TAG, "onCreate() executed");
    }

    private Notification getNotification(String s){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent thisIntent = new Intent(this,HelperService.class);
            thisIntent.setAction("Exit");
            Intent activityIntent = new Intent(this, MainActivity.class);
            PendingIntent tpi = PendingIntent.getService(getApplicationContext(),0,thisIntent,0);
            PendingIntent api = PendingIntent.getActivity(getApplicationContext(),0,activityIntent,0);
            NotificationCompat.Action action =
                    new NotificationCompat.Action.Builder(android.R.drawable.ic_menu_call, "停止服务", tpi)
                            .build();
            String NOTIFICATION_CHANNEL_ID = "cc.zsakvo.yueduhelper";
            String channelName = "解析服务";
            NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_MIN);
            chan.setLightColor(Color.BLUE);
            chan.setLockscreenVisibility(Notification.VISIBILITY_SECRET);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            assert manager != null;
            manager.createNotificationChannel(chan);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
            return notificationBuilder.setOngoing(true)
                    .setSmallIcon(R.drawable.ic_book)
                    .setTicker("服务已启动")
                    .setContentTitle("服务已启动")
                    .setContentText(s)
                    .setContentIntent(api)
                    .setWhen(System.currentTimeMillis())
                    .setColor(getApplicationContext().getColor(R.color.colorPrimaryDark))
                    .setPriority(NotificationManager.IMPORTANCE_UNSPECIFIED)
                    .setCategory(Notification.CATEGORY_SERVICE)
                    .addAction(action)
                    .build();
        }else {
            Intent thisIntent = new Intent(this,HelperService.class);
            thisIntent.setAction("Exit");
            Intent activityIntent = new Intent(this, MainActivity.class);
            PendingIntent tpi = PendingIntent.getService(getApplicationContext(),0,thisIntent,0);
            PendingIntent api = PendingIntent.getActivity(getApplicationContext(),0,activityIntent,0);
            NotificationCompat.Action action =
                    new NotificationCompat.Action.Builder(android.R.drawable.ic_menu_call, "停止服务", tpi)
                            .build();
            return new NotificationCompat.Builder(getApplicationContext(),  null)
                    .setSmallIcon(R.drawable.ic_book)
                    .setTicker("服务已启动")
                    .setContentTitle("服务已启动")
                    .setContentText(s)
                    .setWhen(System.currentTimeMillis())
                    .setContentIntent(api)
                    .addAction(action).build();
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction()!=null && intent.getAction().equals("Exit")){
            stopSelf();
        }else {
            if (server==null){
                flags = START_FLAG_RETRY;
                server = new HelperServer(port);
                String checkPackageName = "com.gedoor.monkeybook";
                try{

                    String runningPackageName = new RunningTaskUtil(this).getTopRunningTasks().getPackageName();
                    if (runningPackageName.equals(checkPackageName)){
                        String string = "正在阅读，已开放端口："+port;
                        startForeground(1,getNotification(string));
                    }else {
                        String string = "尚未阅读，端口已关闭";
                        startForeground(1,getNotification(string));
                    }
                    startTimer(this);
//                    startForeground(1,getNotification());
                    Intent intent1 = new Intent("cc.zsakvo.yueduhelper");
                    intent1.putExtra("isRunning",true);
                    sendBroadcast(intent1);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }



    @Override
    public void onDestroy() {
        stopForeground(true);
        mTimer.cancel();
        mTimer.purge();
        mTimer = null;
        Intent intent1 = new Intent("cc.zsakvo.yueduhelper");
        intent1.putExtra("isRunning",false);
        sendBroadcast(intent1);
        if (server!=null){
            server.stop();
        }
        Log.d(TAG, "onDestroy() executed");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
