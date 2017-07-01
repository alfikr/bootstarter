package liquidcreative.belajarboot.pojo;

import java.util.Date;

/**
 * Created by danang on 01/07/17.
 */

public class Konfigurasi {
    private Date alarmDate;
    private boolean hasShown;
    private Date createDate;

    public Date getAlarmDate() {
        return alarmDate;
    }

    public void setAlarmDate(Date alarmDate) {
        this.alarmDate = alarmDate;
    }

    public boolean isHasShown() {
        return hasShown;
    }

    public void setHasShown(boolean hasShown) {
        this.hasShown = hasShown;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
