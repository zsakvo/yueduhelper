package cc.zsakvo.yueduhelper;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import java.io.IOException;
import java.util.TimerTask;

import androidx.core.app.NotificationCompat;
import cc.zsakvo.yueduhelper.server.HelperServer;

public class CheckTask extends TimerTask {

    public static final String TAG = "CheckTask";
    private HelperServer mHelperServer;
    private Context mContext;
    private Service mService;
    private int mPort;

    public CheckTask(Context context, HelperServer helperServer, Service service,int port){
        mHelperServer = helperServer;
        mContext = context;
        mService = service;
        mPort = port;
    }


    @Override
    public void run() {
        String checkPackageName = "com.gedoor.monkeybook";
        Boolean checkStatus = mHelperServer.isAlive();
        String runningPackageName = new RunningTaskUtil(mContext).getTopRunningTasks().getPackageName();
        if (runningPackageName.equals(checkPackageName)){
            if (!checkStatus){
                try {
                    mHelperServer.start();
                    String string = "正在阅读，已开放端口："+mPort;
                    mService.startForeground(1,getNotification(string));
                    Log.e(TAG, "run: OK" );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else {
            if (checkStatus){
                mHelperServer.stop();
                String string = "尚未阅读，端口已关闭";
                mService.startForeground(1,getNotification(string));
            }
        }
    }

    private Notification getNotification(String s){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent thisIntent = new Intent(mContext,HelperService.class);
            thisIntent.setAction("Exit");
            Intent activityIntent = new Intent(mContext, MainActivity.class);
            PendingIntent tpi = PendingIntent.getService(mContext,0,thisIntent,0);
            PendingIntent api = PendingIntent.getActivity(mContext,0,activityIntent,0);
            NotificationCompat.Action action =
                    new NotificationCompat.Action.Builder(android.R.drawable.ic_menu_call, "停止服务", tpi)
                            .build();
            String NOTIFICATION_CHANNEL_ID = "cc.zsakvo.yueduhelper";
            String channelName = "解析服务";
            NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_MIN);
            chan.setLightColor(Color.BLUE);
            chan.setLockscreenVisibility(Notification.VISIBILITY_SECRET);
            NotificationManager manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            assert manager != null;
            manager.createNotificationChannel(chan);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID);
            return notificationBuilder.setOngoing(true)
                    .setSmallIcon(R.drawable.ic_book)
                    .setTicker("服务已启动")
                    .setContentTitle("服务已启动")
                    .setContentText(s)
                    .setContentIntent(api)
                    .setWhen(System.currentTimeMillis())
                    .setColor(mContext.getColor(R.color.colorPrimaryDark))
                    .setPriority(NotificationManager.IMPORTANCE_UNSPECIFIED)
                    .setCategory(Notification.CATEGORY_SERVICE)
                    .addAction(action)
                    .build();
        }else {
            Intent thisIntent = new Intent(mContext,HelperService.class);
            thisIntent.setAction("Exit");
            Intent activityIntent = new Intent(mContext, MainActivity.class);
            PendingIntent tpi = PendingIntent.getService(mContext,0,thisIntent,0);
            PendingIntent api = PendingIntent.getActivity(mContext,0,activityIntent,0);
            NotificationCompat.Action action =
                    new NotificationCompat.Action.Builder(android.R.drawable.ic_menu_call, "停止服务", tpi)
                            .build();
            return new NotificationCompat.Builder(mContext,  null)
                    .setSmallIcon(R.drawable.ic_book)
                    .setTicker("服务已启动")
                    .setContentTitle("服务已启动")
                    .setContentText(s)
                    .setWhen(System.currentTimeMillis())
                    .setContentIntent(api)
                    .addAction(action).build();
        }
    }
}
