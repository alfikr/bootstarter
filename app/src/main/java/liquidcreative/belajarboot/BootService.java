package liquidcreative.belajarboot;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.v7.app.AlertDialog;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import liquidcreative.belajarboot.pojo.Konfigurasi;

public class BootService extends Service {
    private App app;
    private boolean isAtem;
    private final static String TAG=BootService.class.getSimpleName();
    public BootService() {
        app=App.getInstance();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startTimer();
        //jajalTest();
        return START_NOT_STICKY;
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        Intent broadcastIntent = new Intent("liquidcreative.belajarboot.RestartBootService");
        sendBroadcast(broadcastIntent);
        stopTimerTask();
    }

    private Timer timer;
    private TimerTask timerTask;
    long oldTime=0;
    private void startTimer(){
        timer=new Timer();
        initializeTimer();
        timer.schedule(timerTask,1000,1000);
    }
    public void stopTimerTask(){
        if(timer!=null){
            timer.cancel();
            timer=null;
        }
    }
    private void jajalTest(){
        Konfigurasi konfigurasi=app.getPrefMan().getKonfigurasi();
        AlarmManager alarmManager= (AlarmManager) BootService.this.getSystemService(Context.ALARM_SERVICE);
        if(!isAtem){
            if(konfigurasi.getAlarmDate()!=null && !konfigurasi.isHasShown()){
            Intent i=new Intent(BootService.this,NotificationAlertReceiver.class);
            PendingIntent pi = PendingIntent.getBroadcast(BootService.this,1,i,PendingIntent.FLAG_UPDATE_CURRENT);
            if(new Date().after(konfigurasi.getAlarmDate())){
                Log.d(TAG,"immediatelly");
                Calendar calendar=Calendar.getInstance();
                alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pi);
            }else{
                Log.d(TAG,"alert on the way");
                Calendar calendar=Calendar.getInstance();
                calendar.setTime(konfigurasi.getAlarmDate());
                alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pi);
            }
            isAtem=true;
        }
        }
    }
    private void initializeTimer(){
        timerTask=new TimerTask() {
            @Override
            public void run() {
                if(!isAtem){
            if(konfigurasi.getAlarmDate()!=null && !konfigurasi.isHasShown()){
            Intent i=new Intent(BootService.this,NotificationAlertReceiver.class);
            PendingIntent pi = PendingIntent.getBroadcast(BootService.this,1,i,PendingIntent.FLAG_UPDATE_CURRENT);
            if(new Date().after(konfigurasi.getAlarmDate())){
                Log.d(TAG,"immediatelly");
                Calendar calendar=Calendar.getInstance();
                alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pi);
            }else{
                Log.d(TAG,"alert on the way");
                Calendar calendar=Calendar.getInstance();
                calendar.setTime(konfigurasi.getAlarmDate());
                alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pi);
            }
            isAtem=true;
        }
        }
            }
        };
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"onbind started");


        Log.d(TAG,"onbind finished");
        return new MyBinder();
    }
    public class MyBinder extends Binder{
        public BootService getService(){
            return BootService.this;
        }
    }

}
