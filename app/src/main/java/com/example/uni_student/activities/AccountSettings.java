package com.example.uni_student.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.example.uni_student.API.RetrofitClient;
import com.example.uni_student.R;
import com.example.uni_student.models.DefaultResponse;
import com.example.uni_student.models.User;
import com.example.uni_student.models.loginResponse;
import com.example.uni_student.storage.SharedPrefManager;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class AccountSettings extends AppCompatActivity implements View.OnClickListener{

    private Button buttonSave, buttonChangePassword, buttonLogout, buttonDelete;
    private EditText editTextCurrentPassword, editTextNewPassword;
    private EditText editTextEmail, editTextName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_settings);

        buttonSave = findViewById(R.id.buttonSave);
        buttonChangePassword = findViewById(R.id.buttonChangePassword);
        buttonLogout = findViewById(R.id.buttonLogout);
        buttonDelete = findViewById(R.id.buttonDelete);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextName = findViewById(R.id.editTextName);
        editTextCurrentPassword = findViewById(R.id.editTextCurrentPassword);
        editTextNewPassword = findViewById(R.id.editTextNewPassword);

        buttonSave.setOnClickListener(this);
        buttonChangePassword.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);

    }

    private void logout() {
        SharedPrefManager.getInstance(AccountSettings.this).clear();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void deleteUser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AccountSettings.this);
        builder.setTitle("Are you sure?");
        builder.setMessage("This action is irreversible...");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                User user = SharedPrefManager.getInstance(AccountSettings.this).getUser();
                Call<DefaultResponse> call = RetrofitClient.getInstance().getApi().deleteUser(user.getAEM());

                call.enqueue(new Callback<DefaultResponse>() {
                    @Override
                    public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                        if (!response.body().isError()) {
                            SharedPrefManager.getInstance(AccountSettings.this).clear();
                            Intent intent = new Intent(AccountSettings.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }

                        Toast.makeText(AccountSettings.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<DefaultResponse> call, Throwable t) {

                    }
                });
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog ad = builder.create();
        ad.show();
    }

    private void updatePassword() {
        String currentpassword = editTextCurrentPassword.getText().toString().trim();
        String newpassword = editTextNewPassword.getText().toString().trim();

        if (currentpassword.isEmpty()) {
            editTextCurrentPassword.setError("Password required");
            editTextCurrentPassword.requestFocus();
            return;
        }

        if (newpassword.isEmpty()) {
            editTextNewPassword.setError("Enter new password");
            editTextNewPassword.requestFocus();
            return;
        }

        if (newpassword.length() < 6) {
            editTextNewPassword.setError("New password should be at least 6 characters long");
            editTextNewPassword.requestFocus();
            return;
        }

        User user = SharedPrefManager.getInstance(AccountSettings.this).getUser();

        Call<DefaultResponse> call = RetrofitClient.getInstance().getApi()
                .updatePassword(currentpassword, newpassword, user.getEmail());

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                Toast.makeText(AccountSettings.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Toast.makeText(AccountSettings.this, "failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateProfile() {
        String email = editTextEmail.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        User user = SharedPrefManager.getInstance(AccountSettings.this).getUser();

        if (!email.equals(user.getEmail())) {
            Call<DefaultResponse> call = RetrofitClient.getInstance().getApi()
                    .checkEmail(user.getEmail(), user.getAEM());

            call.enqueue(new Callback<DefaultResponse>() {
                @Override
                public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                    if(response.body().isError()) {
                        editTextEmail.setError(response.body().getMessage());
                        editTextEmail.requestFocus();
                    }
                }

                @Override
                public void onFailure(Call<DefaultResponse> call, Throwable t) {
                    Toast.makeText(AccountSettings.this, "Failure", Toast.LENGTH_LONG).show();
                }
            });

            return;
        }
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
        if (name.isEmpty()) {
            editTextName.setError("Your name is required");
            editTextName.requestFocus();
            return;
        }
        else {
            Call<loginResponse> call = RetrofitClient.getInstance()
                .getApi().updateUser(
                        user.getAEM(),
                        email,
                        name
                    );

            call.enqueue(new Callback<loginResponse>() {
                @Override
                public void onResponse(Call<loginResponse> call, Response<loginResponse> response) {
                    Toast.makeText(AccountSettings.this, response.body().getMessage(), Toast.LENGTH_LONG).show();

                    if (!response.body().isError()) {
                        SharedPrefManager.getInstance(AccountSettings.this).saveUser(response.body().getUser());
                    }
                }

                @Override
                public void onFailure(Call<loginResponse> call, Throwable t) {

                }
            });
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonSave) {
            updateProfile();
        }
        else if (v.getId() == R.id.buttonChangePassword) {
            updatePassword();
        }
        else if (v.getId() == R.id.buttonLogout) {
            logout();
        }
        else if (v.getId() == R.id.buttonDelete) {
            deleteUser();
        }
    }
}