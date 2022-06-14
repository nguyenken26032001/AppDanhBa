package tranvannguyen.com.appdanhba;

import java.io.Serializable;

public class user implements Serializable {
    private int id;
    private String Name;
    private String phoneNumber;
    private String Email;
    private String Street;
    private String City;
    private byte[] hinhanh;

    public user(int id, String name, String phoneNumber, String email, String street, String city, byte[] hinhanh) {
        this.id = id;
        Name = name;
        this.phoneNumber = phoneNumber;
        Email = email;
        Street = street;
        City = city;
        this.hinhanh = hinhanh;
    }

    public user(int id, String name) {
        this.id = id;
        Name = name;
    }

    public user(int id) {
    }

    public user(int id, String name, String phoneNumber, String email, String street, String city) {
        this.id = id;
        Name = name;
        this.phoneNumber = phoneNumber;
        Email = email;
        Street = street;
        City = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public byte[] getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(byte[] hinhanh) {
        this.hinhanh = hinhanh;
    }
}
