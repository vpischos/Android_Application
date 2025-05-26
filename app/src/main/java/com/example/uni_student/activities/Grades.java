package com.example.uni_student.activities;

import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.uni_student.API.RetrofitClient;
import com.example.uni_student.R;
import com.example.uni_student.models.Grade;
import com.example.uni_student.models.GradeResponse;
import com.example.uni_student.models.Student;
import com.example.uni_student.models.StudentResponse;
import com.example.uni_student.models.User;
import com.example.uni_student.storage.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Grades extends AppCompatActivity {

    private List<Grade> gradeList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grades_layout);

        TableLayout tableLayout = findViewById(R.id.tableLayout);

        User user = SharedPrefManager.getInstance(Grades.this).getUser();

        Call<GradeResponse> call = RetrofitClient.getInstance().getApi()
                .getGrades(user.getAEM());

        call.enqueue(new Callback<GradeResponse>() {
            @Override
            public void onResponse(Call<GradeResponse> call, Response<GradeResponse> response) {
                if (!response.body().isError()) {
                    gradeList = response.body().getGrades();

                    for (Grade grade : gradeList) {
                        TableRow row = new TableRow(Grades.this);

                        TextView subjectNameTextView = new TextView(Grades.this);
                        TextView GradeNumTextView = new TextView(Grades.this);

                        subjectNameTextView.setText(grade.getSubject_name());
                        GradeNumTextView.setText(grade.getGradeNumber());

                        row.addView(subjectNameTextView);
                        row.addView(GradeNumTextView);

                        tableLayout.addView(row);
                    }
                }
            }

            @Override
            public void onFailure(Call<GradeResponse> call, Throwable t) {
                Toast.makeText(Grades.this, "failure", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
