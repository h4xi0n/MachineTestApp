package com.ak453.machinetestapp.db;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ak453.machinetestapp.util.Utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@androidx.room.Database(entities = {Subscriber.class}, version =  1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SubscriberDAO subscriberDAO();

    private static volatile  AppDatabase INSTANCE;
    private static  final  int NUMBER_OF_THREADS = 4;
    static  final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    static AppDatabase getDatabase(Context context,String databaseName) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,AppDatabase.class,databaseName).build();
                }
            }
        }
        return INSTANCE;
    }
}
