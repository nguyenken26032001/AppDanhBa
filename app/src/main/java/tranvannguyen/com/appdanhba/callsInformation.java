package tranvannguyen.com.appdanhba;

public class callsInformation {
    private String name;
    private String phone;
    private String date;
    private String duration;

    public callsInformation(String name, String phone, String date, String duration) {
        this.name = name;
        this.phone = phone;
        this.date = date;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
