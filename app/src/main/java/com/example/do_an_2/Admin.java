package com.example.do_an_2;

public class Admin {
    String emmail;
    String password;

    public Admin() {
    }

    public Admin(String emmail, String password) {
        this.emmail = emmail;
        this.password = password;
    }

    public String getEmmail() {
        return emmail;
    }

    public void setEmmail(String emmail) {
        this.emmail = emmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "emmail='" + emmail + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
