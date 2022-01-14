package com.ak453.machinetestapp.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
@Dao
public interface SubscriberDAO {
    @Query("SELECT * FROM subscribers")
    LiveData<List<Subscriber>> getAll();

    @Query("SELECT * FROM subscribers WHERE subscriber_email = :email")
    Subscriber findByMail(String email);

    @Query("SELECT * FROM subscribers WHERE subscriber_email = :email AND subscriber_password = :password")
    Subscriber findByLogin(String email, String password);

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(Subscriber subscriber);

    @Delete
    void delete(Subscriber subscriber);
}
