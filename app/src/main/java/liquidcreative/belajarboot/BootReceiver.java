package liquidcreative.belajarboot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class BootReceiver extends BroadcastReceiver {
    private final static String TAG=BootReceiver.class.getSimpleName();
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        if(Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())){
            Log.d(TAG,"Boot complete");
            //Intent n = new Intent(context,BootService.class);
            //context.startService(n);
            Intent n = new Intent(context,MainActivity.class);
            n.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(n);
        }

    }
}
