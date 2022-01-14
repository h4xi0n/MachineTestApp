package com.ak453.machinetestapp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ak453.machinetestapp.R;
import com.ak453.machinetestapp.activity.MainActivity;
import com.ak453.machinetestapp.databinding.FragmentLoginBinding;
import com.ak453.machinetestapp.db.Subscriber;
import com.ak453.machinetestapp.db.SubscriberRepository;
import com.ak453.machinetestapp.util.SPUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class LoginFragment extends Fragment {
    private boolean doubleBackToExitPressedOnce = false;
    private FragmentLoginBinding binding;
    ExecutorService executor;
    Handler handler;
    SPUtil spUtil;
    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spUtil = new SPUtil(getActivity());
        if(!spUtil.getSubEmail().equals(""))
            goToMain();
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                if (doubleBackToExitPressedOnce) {
                    return;
                }

                doubleBackToExitPressedOnce = true;
                Toast.makeText(getContext(), "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce=false;
                    }
                }, 2000);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View.OnClickListener s = Navigation.createNavigateOnClickListener(R.id.action_login_to_register);
        binding.goToRegisterFragmentButton.setOnClickListener(s);
        binding.fragmentLoginLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    sendDataToDB();
            }
        });
    }

    private void sendDataToDB() {
        executor = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());
        SubscriberRepository repository = new SubscriberRepository(getActivity());
        executor.execute(() -> {
            boolean isLoginSuccess = repository.isLoginSuccess(binding.loginEmailEt.getText().toString(),binding.loginPasswordEt.getText().toString());
            if(isLoginSuccess) {
                Subscriber subscriber = repository.getSubscriber(binding.loginEmailEt.getText().toString());
                spUtil.setSubName(subscriber.subscriberName);
                spUtil.setSubEmail(subscriber.subscriberEmail);
            }
            handler.post(() -> {
                if(isLoginSuccess) {
                    goToMain();
                } else {
                    binding.loginEmailEt.setError("Incorrect Email");
                    binding.loginPasswordEt.setError("Incorrect Password");
                }
            });
        });
    }

    private void goToMain() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}