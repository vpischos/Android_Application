package com.example.uni_student.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.uni_student.API.RetrofitClient;
import com.example.uni_student.R;
import com.example.uni_student.models.Course;
import com.example.uni_student.models.CoursesResponse;
import com.example.uni_student.models.DefaultResponse;
import com.example.uni_student.models.User;
import com.example.uni_student.models.loginResponse;
import com.example.uni_student.storage.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class tableUsers extends AppCompatActivity {

    private Button button;
    private List<Course> courseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.table_courses_layout);
        button = findViewById(R.id.thanksButton);

        User user = SharedPrefManager.getInstance(tableUsers.this).getUser();

        Call<CoursesResponse> call = RetrofitClient.getInstance().getApi()
                .studentCourses(user.getAEM());

        call.enqueue(new Callback<CoursesResponse>() {
            @Override
            public void onResponse(Call<CoursesResponse> call, Response<CoursesResponse> response) {

                if (!response.body().isError()) {
                    courseList = response.body().getCourses();

                    for (int i = 0; i < courseList.size(); i++) {
                        Course course = courseList.get(i);

                        TextView subjectNameTextView = findViewById(getResources().getIdentifier("subject_name_" + (i + 1), "id", getPackageName()));
                        TextView nameTextView = findViewById(getResources().getIdentifier("name_" + (i + 1), "id", getPackageName()));

                        subjectNameTextView.setText(course.getSubject_name());
                        nameTextView.setText(course.getPName());
                    }
                }else {

                }
            }

            @Override
            public void onFailure(Call<CoursesResponse> call, Throwable t) {
                Toast.makeText(tableUsers.this, "Some exception occurred", Toast.LENGTH_LONG).show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(tableUsers.this, "You're welcome :)", Toast.LENGTH_LONG).show();
            }
        });
    }
}
