package liquidcreative.belajarboot;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import liquidcreative.belajarboot.pojo.Konfigurasi;

public class NotificationAlertReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        App app=App.getInstance();
        Konfigurasi konfigurasi=app.getPrefMan().getKonfigurasi();
        konfigurasi.setHasShown(true);
        konfigurasi.setCreateDate(null);
        konfigurasi.setAlarmDate(null);
        app.getPrefMan().setKonfig(konfigurasi);
        createNotification(context,"jajal thok","mbuh njajal","jajal bos");
    }
    public void createNotification(Context context, String msg, String msgText, String msgAlert){

        PendingIntent notificIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(msg)
                .setTicker(msgAlert)
                .setContentText(msgText);

        mBuilder.setContentIntent(notificIntent);

        mBuilder.setDefaults(NotificationCompat.DEFAULT_SOUND);

        mBuilder.setAutoCancel(true);

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(1, mBuilder.build());


    }
}
