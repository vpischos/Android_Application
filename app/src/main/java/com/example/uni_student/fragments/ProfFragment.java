package com.example.uni_student.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.uni_student.R;
import com.example.uni_student.activities.AccountSettings;
import com.example.uni_student.activities.CourseGrades;
import com.example.uni_student.storage.SharedPrefManager;

public class ProfFragment extends Fragment {

    private Button accountSettingsButton, coursesGradesButton;
    private TextView textViewEmail, textViewName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_prof, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        accountSettingsButton = view.findViewById(R.id.button2);
        coursesGradesButton = view.findViewById(R.id.button);

        coursesGradesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCoursesGrades();
            }
        });

        accountSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPersonalInfoSettings();
            }
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

    public void openCoursesGrades() {
        Intent intent = new Intent(getContext(), CourseGrades.class);
        startActivity(intent);
    }
}