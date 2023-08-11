package com.entity;

import java.util.Arrays;

public class HeaderData {
    private String title;
    private String address;

    @Override
    public String toString() {
        return "HeaderData{" +
                "title='" + title + '\'' +
                ", address='" + address + '\'' +
                ", contactNo='" + contactNo + '\'' +
                ", mailID='" + mailID + '\'' +
                ", logo=" + Arrays.toString(logo) +
                '}';
    }

    private String contactNo;
    private String mailID;
    private byte[] logo;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getMailID() {
        return mailID;
    }

    public void setMailID(String mailID) {
        this.mailID = mailID;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }
}
