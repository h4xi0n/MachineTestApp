package com.ak453.machinetestapp.fragment;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ak453.machinetestapp.R;
import com.ak453.machinetestapp.databinding.FragmentRegisterBinding;
import com.ak453.machinetestapp.db.Subscriber;
import com.ak453.machinetestapp.db.SubscriberRepository;
import com.ak453.machinetestapp.util.Utils;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegisterFragment extends Fragment {
    private FragmentRegisterBinding binding;
    ExecutorService executor;
    Handler handler;
    private static final String TAG = "H4X123";

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View.OnClickListener s = Navigation.createNavigateOnClickListener(R.id.action_registerFragment_to_loginFragment);
        binding.registerFragmentBackButton.setOnClickListener(s);
        binding.buttonRegisternow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendDataToDB();
            }
        });
    }

    private void sendDataToDB() {
        executor = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());
        Subscriber subscriber = new Subscriber();
        SubscriberRepository repository = new SubscriberRepository(getActivity());

        subscriber.subscriberName = binding.registerTextName.getText().toString();
        subscriber.subscriberEmail = binding.registerTextEmail.getText().toString();
        if(binding.registerTextPassword.getText().toString().equals(binding.registerTextPasswordagain.getText().toString()))
            subscriber.subscriberPassword = binding.registerTextPassword.getText().toString();
        else {
            binding.registerTextPassword.setError("Password Does Not Match");
            binding.registerTextPasswordagain.setError("Password Does Not Match");
            return;
        }


        if(TextUtils.isEmpty(subscriber.subscriberName) || TextUtils.isEmpty(subscriber.subscriberEmail) || TextUtils.isEmpty(subscriber.subscriberPassword)) {
            Toast.makeText(getActivity(), "Please enter all the details", Toast.LENGTH_SHORT).show();
            return;
        }

        executor.execute(() -> {
            boolean isSubscriberExists = repository.getSubscriber(subscriber.subscriberEmail) != null;
            if(!isSubscriberExists)
                repository.insert(subscriber);
            handler.post(() -> {
                if(isSubscriberExists) {
                    Toast.makeText(getActivity(), "User already exists, Please log in", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

}