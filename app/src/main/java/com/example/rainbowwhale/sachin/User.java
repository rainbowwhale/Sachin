package com.example.rainbowwhale.sachin;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rainbowwhale on 2015-10-03.
 */
public class User implements Parcelable {
    String name;
    String nick;
    String desc;
    int gender;
    int photoNumber;
    User next = null;

    public void setName(String name) {
        this.name = name;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setPhotoNumber(int photoNumber) {
        this.photoNumber = photoNumber;
    }

    public void setNext(User next) {
        this.next = next;
    }

    public String getName() {
        return name;
    }

    public  String getNick(){
        return nick;
    }

    public String getDesc() {
        return desc;
    }

    public int getGender() {
        return gender;
    }

    public int getPhotoNumber() {
        return photoNumber;
    }

    public User getNext() {
        return next;
    }


    User(){
        /* default */
    }

    User(String N, String C, String D, int G, int P){
        name = N;
        nick = C;
        desc = D;
        gender = G;
        photoNumber = P;
    }

    User(User U){
        name = U.getName();
        nick = U.getNick();
        desc = U.getDesc();
        gender = U.getGender();
        photoNumber = U.getPhotoNumber();
    }

    public User getUser(int n){
        return (n!=0) ? next.getUser(n - 1):this;
    }

    public int lengthUser(int n){
        return (next==null)? n+1:next.lengthUser(n + 1);
    }

    public User last(){
        return (next==null)?this:next.last();
    }

    public void append(String N, String C, String D, int G, int P){
        this.last().next = new User(N,C,D, G, P);
        /*
        if(next!=null)
            next.append(N, D);
        else
            next = new User(N, D);
            */
    }

    public void append(User U){
        this.last().next = new User(U);
    }

    public void DropOut(int n){
        if(n==0)
        {
            User TMP = next;
            next = null;
            name = TMP.name;
            name = TMP.nick;
            desc = TMP.desc;
            gender = TMP.gender;
            photoNumber = TMP.photoNumber;
            next = TMP.next;
        }
        else
        {
            User TMP = this.getUser(n).next;
            this.getUser(n-1).next = null;
            this.getUser(n-1).next = TMP;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(nick);
        dest.writeString(desc);
        dest.writeInt(gender);
        dest.writeInt(photoNumber);
        dest.writeParcelable((Parcelable) next, flags);
    }

    public static final Parcelable.Creator<User> CREATOR=new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            User tmp = new User();
            tmp.setName(source.readString());
            tmp.setNick(source.readString());
            tmp.setDesc(source.readString());
            tmp.setGender(source.readInt());
            tmp.setPhotoNumber(source.readInt());
            tmp.next = source.readParcelable(User.class.getClassLoader());
            return tmp;
        }

        @Override
        public User[] newArray(int size) {
            return new User[0];
        }
    };
}
