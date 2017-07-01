package liquidcreative.belajarboot;

import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;

public class BootService extends Service {
    private App app;
    public BootService() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        throw new UnsupportedOperationException("Not yet implemented");

        /*AlertDialog.Builder builder=new AlertDialog.Builder(getApplicationContext());
        builder.setMessage("hello from boot");
        builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
        return null;*/
    }
}
