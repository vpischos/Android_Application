package com.example.uni_student.activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.uni_student.API.RetrofitClient;
import com.example.uni_student.R;
import com.example.uni_student.models.DefaultResponse;
import com.example.uni_student.models.User;
import com.example.uni_student.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class noCourses extends AppCompatActivity {

    private static final int MAX_SELECTED_COURSES = 5;

    private TextView titleTextView;
    private Button submitButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        User user = SharedPrefManager.getInstance(noCourses.this).getUser();

        if (user.getFK1_University_code() == 1) {
            setContentView(R.layout.activity_choose_course);
        }
        else if (user.getFK1_University_code() == 2) {
            setContentView(R.layout.activity_choose_course_mechanical);
        }
        else if (user.getFK1_University_code() == 3) {
            setContentView(R.layout.activity_choose_course_education);
        }

        titleTextView = findViewById(R.id.titleTextView);
        submitButton = findViewById(R.id.submitButton);
        LinearLayout checkboxContainer = findViewById(R.id.checkboxContainer);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSelectedCoursesText();
            }
        });

        for (int i = 1; i <= 20; i++) {
            int checkboxId = getResources().getIdentifier("checkbox_course" + i, "id", getPackageName());
            CheckBox checkBox = findViewById(checkboxId);

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateSelectedCoursesText();
                }
            });
        }
    }
    @SuppressLint("SetTextI18n")
    private void updateSelectedCoursesText() {
        StringBuilder selectedCourses = new StringBuilder();

        int selectedCount = 0;
        for (int i = 1; i <= 20; i++) {
            int checkboxId = getResources().getIdentifier("checkbox_course" + i, "id", getPackageName());
            CheckBox checkBox = findViewById(checkboxId);

            if (checkBox.isChecked()) {
                selectedCourses.append(checkBox.getText()).append("\n");
                selectedCount++;
            }
        }

        if (selectedCount > MAX_SELECTED_COURSES) {
            titleTextView.setText("You can't select more than 5 courses.");
        } else if (selectedCount > 0) {
            titleTextView.setText("Selected Courses:\n" + selectedCourses.toString());
        } else {
            titleTextView.setText("You have no courses picked. Please pick exactly 5 courses.");
        }

        submitButton.setEnabled(selectedCount == MAX_SELECTED_COURSES);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = SharedPrefManager.getInstance(noCourses.this).getUser();
                String[] stringArray = selectedCourses.toString().split("\n");

                AlertDialog.Builder builder = new AlertDialog.Builder(noCourses.this);
                builder.setTitle("Are you sure?");
                builder.setMessage("If you choose these 5 courses you will not be able to change them after you click register...");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ViewGroup scrollview = findViewById(R.id.scrollview);
                        disableEnableControls(false, scrollview);
                        submitButton.setEnabled(false);

                        for (String str : stringArray) {
                            Call<DefaultResponse> call = RetrofitClient.getInstance().getApi()
                                    .insertOfferTo(user.getFK1_University_code(), user.getAEM(), str);

                            call.enqueue(new Callback<DefaultResponse>() {
                                @Override
                                public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                                    Toast.makeText(noCourses.this, str + " " + response.body().getMessage(), Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onFailure(Call<DefaultResponse> call, Throwable t) {
                                    Toast.makeText(noCourses.this, "failed", Toast.LENGTH_LONG).show();
                                }
                            });
                        }

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // Finish the activity after the delay
                                finish();
                            }
                        }, 18000);
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
        });
    }

    private void disableEnableControls(boolean enable, ViewGroup vg) {
        for (int i = 0; i < vg.getChildCount(); i++) {
            View child = vg.getChildAt(i);
            child.setEnabled(enable);
            if (child instanceof ViewGroup) {
                disableEnableControls(enable, (ViewGroup) child);
            }
        }
    }
}
