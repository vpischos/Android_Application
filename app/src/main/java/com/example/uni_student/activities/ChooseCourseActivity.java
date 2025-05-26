package com.example.uni_student.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.uni_student.API.RetrofitClient;
import com.example.uni_student.models.DefaultResponse;
import com.example.uni_student.models.User;
import com.example.uni_student.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChooseCourseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        User user = SharedPrefManager.getInstance(ChooseCourseActivity.this).getUser();

        Call<DefaultResponse> call = RetrofitClient.getInstance().getApi()
                .checkStudent(user.getAEM());
        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                Intent intent;
                if (!response.body().isError()) {
                    intent = new Intent(ChooseCourseActivity.this, tableUsers.class);
                } else {
                    intent = new Intent(ChooseCourseActivity.this, noCourses.class);
                }
                finish();
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {

            }
        });
    }
}