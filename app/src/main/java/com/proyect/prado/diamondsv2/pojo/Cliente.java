package com.proyect.prado.diamondsv2.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class Cliente implements Parcelable{
    private String name;
    private String last_name;
    private int dni;
    private String email;
    private String password;

    public Cliente(Parcel in) {
        name = in.readString();
        last_name = in.readString();
        dni = in.readInt();
        email = in.readString();
        password = in.readString();
    }

    public static final Creator<Cliente> CREATOR = new Creator<Cliente>() {
        @Override
        public Cliente createFromParcel(Parcel in) {
            return new Cliente(in);
        }

        @Override
        public Cliente[] newArray(int size) {
            return new Cliente[size];
        }
    };

    public Cliente() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(last_name);
        dest.writeInt(dni);
        dest.writeString(email);
        dest.writeString(password);
    }

    public String getName() {
        return name;
    }

    public String setName(String name) {
        return name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
