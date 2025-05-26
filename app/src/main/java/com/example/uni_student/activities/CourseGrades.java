package com.example.uni_student.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.uni_student.API.RetrofitClient;
import com.example.uni_student.R;
import com.example.uni_student.models.Course;
import com.example.uni_student.models.CoursesResponse;
import com.example.uni_student.models.DefaultResponse;
import com.example.uni_student.models.Student;
import com.example.uni_student.models.StudentResponse;
import com.example.uni_student.models.User;
import com.example.uni_student.storage.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseGrades extends AppCompatActivity {

    private List<Student> studentList;

    private EditText students_AEM, subject_name, grade;
    private Button GradeButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_grade);

        TableLayout tableLayout = findViewById(R.id.tableLayout);

        students_AEM = findViewById(R.id.S_AEM);
        subject_name = findViewById(R.id.subject_name);
        grade = findViewById(R.id.grade);
        GradeButton = findViewById(R.id.grade_button);

        User user = SharedPrefManager.getInstance(CourseGrades.this).getUser();

        GradeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String S_AEM = students_AEM.getText().toString().trim();
                String Subject_name = subject_name.getText().toString().trim();
                String SGrade = grade.getText().toString().trim();

                if (S_AEM.isEmpty()) {
                    students_AEM.setError("Student's AEM is required");
                    students_AEM.requestFocus();
                    return;
                }
                if (Subject_name.isEmpty()) {
                    subject_name.setError("Subject Name is required");
                    subject_name.requestFocus();
                    return;
                }
                if (SGrade.isEmpty()) {
                    grade.setError("Grade is required");
                    grade.requestFocus();
                    return;
                }

                int Grade = Integer.parseInt(SGrade);

                if (Grade < 0 ) {
                    grade.setError("Grade cant be negative");
                    grade.requestFocus();
                    return;
                }
                if (Grade > 10 ) {
                    grade.setError("Grade cant exceed 10");
                    grade.requestFocus();
                    return;
                }

                Call<DefaultResponse> call = RetrofitClient.getInstance().getApi()
                        .insertGradeOn(user.getAEM(), S_AEM, Subject_name, Grade);

                call.enqueue(new Callback<DefaultResponse>() {
                    @Override
                    public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                        Toast.makeText(CourseGrades.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<DefaultResponse> call, Throwable t) {
                        Toast.makeText(CourseGrades.this, "failure", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        Call<StudentResponse> call = RetrofitClient.getInstance().getApi()
                .getEnrolledStudents(user.getAEM());

        call.enqueue(new Callback<StudentResponse>() {
            @Override
            public void onResponse(Call<StudentResponse> call, Response<StudentResponse> response) {
                if (!response.body().isError()) {
                    studentList = response.body().getStudents();

                    for (Student student : studentList) {
                        TableRow row = new TableRow(CourseGrades.this);

                        TextView subjectNameTextView = new TextView(CourseGrades.this);
                        TextView AEMTextView = new TextView(CourseGrades.this);
                        TextView emailTextView = new TextView(CourseGrades.this);

                        subjectNameTextView.setText(student.getSubject_name());
                        AEMTextView.setText(student.getSAEM());
                        emailTextView.setText(student.getSEmail());

                        row.addView(subjectNameTextView);
                        row.addView(AEMTextView);
                        row.addView(emailTextView);

                        tableLayout.addView(row);
                    }
                }
            }

            @Override
            public void onFailure(Call<StudentResponse> call, Throwable t) {
                Toast.makeText(CourseGrades.this, "failure", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
