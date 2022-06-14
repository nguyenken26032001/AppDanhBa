package tranvannguyen.com.appdanhba;

import android.graphics.Bitmap;

public class callsInformation {
    private String LogName;
    private String LogPhone;
    private String LogDate;
    private String LogDuration;
    private String LogTime;
    private Bitmap LogImage;


    public callsInformation() {

    }

    public callsInformation(String logName, String logPhone, String logDate, String logDuration, String logTime, Bitmap logImage) {
        LogName = logName;
        LogPhone = logPhone;
        LogDate = logDate;
        LogDuration = logDuration;
        LogTime = logTime;
        LogImage = logImage;
    }

    public String getLogName() {
        return LogName;
    }

    public void setLogName(String logName) {
        LogName = logName;
    }

    public String getLogPhone() {
        return LogPhone;
    }

    public void setLogPhone(String logPhone) {
        LogPhone = logPhone;
    }

    public String getLogDate() {
        return LogDate;
    }

    public void setLogDate(String logDate) {
        LogDate = logDate;
    }

    public String getLogDuration() {
        return LogDuration;
    }

    public void setLogDuration(String logDuration) {
        LogDuration = logDuration;
    }

    public String getLogTime() {
        return LogTime;
    }

    public void setLogTime(String logTime) {
        LogTime = logTime;
    }

    public Bitmap getLogImage() {
        return LogImage;
    }

    public void setLogImage(Bitmap logImage) {
        LogImage = logImage;
    }
}
