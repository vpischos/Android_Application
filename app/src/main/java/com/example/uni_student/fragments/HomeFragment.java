package com.example.uni_student.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.uni_student.R;
import com.example.uni_student.activities.AccountSettings;
import com.example.uni_student.activities.ChooseCourseActivity;
import com.example.uni_student.activities.Grades;
import com.example.uni_student.storage.SharedPrefManager;

public class HomeFragment extends Fragment {
    private Button accountSettingsButton, coursesButton, gradesButton;
    private TextView textViewEmail, textViewName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
            coursesButton = view.findViewById(R.id.button);
            coursesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openCoursesSettings();
                }
            });

            accountSettingsButton = view.findViewById(R.id.button2);
            accountSettingsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                openPersonalInfoSettings();
            }
            });

            gradesButton = view.findViewById(R.id.button3);
            gradesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {openGrades();}
            });

        textViewEmail = view.findViewById(R.id.textViewEmail);
        textViewName = view.findViewById(R.id.textViewName);

        textViewEmail.setText(SharedPrefManager.getInstance(getActivity()).getUser().getEmail());
        textViewName.setText(SharedPrefManager.getInstance(getActivity()).getUser().getName());
    }

    public void openPersonalInfoSettings() {
        Intent intent = new Intent(getContext(), AccountSettings.class);
        startActivity(intent);
    }

    public void openCoursesSettings() {
        Intent intent = new Intent(getContext(), ChooseCourseActivity.class);
        startActivity(intent);
    }
    public void openGrades() {
        Intent intent = new Intent(getContext(), Grades.class);
        startActivity(intent);
    }
}
