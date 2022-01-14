package com.ak453.machinetestapp.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "subscribers")
public class Subscriber {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "subscriber_email")
    public String subscriberEmail;

    @ColumnInfo(name = "subscriber_name")
    public String subscriberName;

    @ColumnInfo(name = "subscriber_password")
    public String subscriberPassword;


}
