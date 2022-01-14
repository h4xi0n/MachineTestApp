package com.ak453.machinetestapp.db;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class SubscriberRepository {
    private static final String TAG = "SubRepo";
    private SubscriberDAO subscriberDAO;
    private LiveData<List<Subscriber>> subscribers;

    public SubscriberRepository(Context context) {
        AppDatabase db = AppDatabase.getDatabase(context,"subscribers");
        subscriberDAO = db.subscriberDAO();
        subscribers = subscriberDAO.getAll();
    }

    LiveData<List<Subscriber>> getSubscribers() {
        return subscribers;
    }

    public Subscriber getSubscriber(String email) {
        return subscriberDAO.findByMail(email);
    }

    public boolean isLoginSuccess(String email, String password) {
        return subscriberDAO.findByLogin(email, password) != null;
    }

    public void insert(final Subscriber subscriber) {
        subscriberDAO.insert(subscriber);
    }
}
