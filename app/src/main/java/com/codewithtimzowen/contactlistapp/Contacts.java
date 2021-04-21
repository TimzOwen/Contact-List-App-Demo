package com.codewithtimzowen.contactlistapp;

public class Contacts {
    // private
    final private String mName, mContact;
    private int mImageResourceID = NO_IMAGE;
    private static final int NO_IMAGE = -1;
    private final int mSoundResourceID;

    //constructor
    public Contacts(String name, String contact, int soundResourceID) {
        this.mName = name;
        this.mContact = contact;
        this.mSoundResourceID = soundResourceID;
    }

    //constructor
    public Contacts(String name, String contact, int imageResourceID, int soundResourceID) {
        this.mName = name;
        this.mContact = contact;
        this.mImageResourceID = imageResourceID;
        this.mSoundResourceID = soundResourceID;
    }

    //getter methods
    public String getName() {
        return mName;
    }

    public String getContact() {
        return mContact;
    }

    public int getmImageResoureID() {
        return mImageResourceID;
    }

    public int getSoundResourceID() {
        return mSoundResourceID;
    }

    public boolean hasImage() {
        return mImageResourceID != NO_IMAGE;
    }
}
