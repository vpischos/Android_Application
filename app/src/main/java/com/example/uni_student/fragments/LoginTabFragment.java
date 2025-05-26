package com.example.uni_student.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uni_student.API.RetrofitClient;
import com.example.uni_student.R;
import com.example.uni_student.activities.ProfileActivity;
import com.example.uni_student.models.loginResponse;
import com.example.uni_student.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginTabFragment extends Fragment implements View.OnClickListener{
    private EditText editTextEmail, editTextAEM;
    private EditText editTextPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_login_tab, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextEmail = view.findViewById(R.id.login_email);
        editTextPassword = view.findViewById(R.id.login_password);
        editTextAEM = view.findViewById(R.id.login_AEM);

        view.findViewById(R.id.login_button).setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();

        if(SharedPrefManager.getInstance(requireContext()).isLoggedIn()) {
            Intent intent = new Intent(getActivity(), ProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String AEM = editTextAEM.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Password required");
            editTextPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            editTextPassword.setError("Password should be at least 6 characters long");
            editTextPassword.requestFocus();
            return;
        }
        if (AEM.isEmpty()) {
            editTextAEM.setError("AEM is required");
            editTextAEM.requestFocus();
            return;
        }
        if (AEM.length() < 4 || AEM.charAt(0) != '0' && AEM.charAt(0) != '1') {
            editTextAEM.setError("AEM must be over 4 numbers long and should start with 0 or 1 if you are a professor.");
            editTextAEM.requestFocus();
            return;
        }

        Call<loginResponse> call = RetrofitClient
                .getInstance().getApi().userlogin(email, password, AEM);

        call.enqueue(new Callback<loginResponse>() {
            @Override
            public void onResponse(Call<loginResponse> call, Response<loginResponse> response) {
                loginResponse loginresponse = response.body();

                if (!loginresponse.isError()) {
                    SharedPrefManager.getInstance(requireContext()).saveUser(loginresponse.getUser());

                    Intent intent = new Intent(getActivity(), ProfileActivity.class);

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                } else {
                    Toast.makeText(getActivity(), loginresponse.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<loginResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        userLogin();
    }
}