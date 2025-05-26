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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uni_student.API.RetrofitClient;
import com.example.uni_student.R;
import com.example.uni_student.activities.ProfileActivity;
import com.example.uni_student.models.DefaultResponse;
import com.example.uni_student.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupTabFragment extends Fragment implements  View.OnClickListener {
    private EditText editTextEmail, editTextPassword, editTextName, editTextAEM;
    private Spinner spinner;
    private TextView errorText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_signup_tab, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        editTextEmail = view.findViewById(R.id.signup_email);
        editTextPassword = view.findViewById(R.id.signup_password);
        editTextName = view.findViewById(R.id.signup_name);
        editTextAEM = view.findViewById(R.id.AEM_signup);
        errorText = view.findViewById(R.id.errorText);
        spinner = view.findViewById(R.id.University_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.university_departments, R.layout.spinner_dropdown_item_layout);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        view.findViewById(R.id.signup_button).setOnClickListener(this);
    }

    private void userSignUp() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
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
        if (name.isEmpty()) {
            editTextName.setError("Your name is required");
            editTextName.requestFocus();
            return;
        }
        if (AEM.isEmpty()) {
            editTextAEM.setError("Your AEM is required");
            editTextAEM.requestFocus();
            return;
        }

        if (AEM.length() < 4 || AEM.charAt(0) != '0' && AEM.charAt(0) != '1') {
            editTextAEM.setError("AEM must be over 4 numbers long and should start with 0 or 1 if you are a professor.");
            editTextAEM.requestFocus();
            return;
        }

        if (spinner.getSelectedItemPosition() == 0) {
            errorText.setVisibility(View.VISIBLE);
            errorText.setText("Please select a university");
            errorText.requestFocus();
            return;
        }
        else {
            errorText.setVisibility(View.GONE);
        }

        String university = spinner.getSelectedItem().toString();

        Call<DefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .createUser(email, password, name, AEM, university);

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {

                if (response.code() == 201) {
                    DefaultResponse dr = response.body();
                    Toast.makeText(getContext(), dr.getMessage(), Toast.LENGTH_LONG).show();
                }
                else if (response.code() == 422){
                    Toast.makeText(getContext(), "Some error occurred", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        userSignUp();
    }
}