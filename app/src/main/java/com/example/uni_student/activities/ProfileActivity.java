package com.example.uni_student.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uni_student.R;
import com.example.uni_student.fragments.HomeFragment;
import com.example.uni_student.fragments.ProfFragment;
import com.example.uni_student.fragments.ProfessorsListFragment;
import com.example.uni_student.fragments.UsersFragment;
import com.example.uni_student.models.User;
import com.example.uni_student.storage.SharedPrefManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class ProfileActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    private TextView textView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        BottomNavigationView navigationView = findViewById(R.id.bottomNav);
        navigationView.setOnItemSelectedListener(this);

        User user = SharedPrefManager.getInstance(ProfileActivity.this).getUser();
        String AEM = user.getAEM();

        if (AEM.charAt(0) == '0') {
            displayFragment(new HomeFragment());
        }
        else if (AEM.charAt(0) == '1'){
            displayFragment(new ProfFragment());
        }
    }

    private void displayFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.relativeLayout, fragment)
                .commit();
    }

    @Override
    public void onStart() {
        super.onStart();

        if(!SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        if (item.getItemId() == R.id.home_menu) {
            User user = SharedPrefManager.getInstance(ProfileActivity.this).getUser();
            String AEM = user.getAEM();

            if (AEM.charAt(0) == '0') {
                fragment = new HomeFragment();
            }
            else if (AEM.charAt(0) == '1'){
                fragment = new ProfFragment();
            }
        }
        else if (item.getItemId() == R.id.users_menu) {
            fragment = new UsersFragment();
        }
        else if (item.getItemId() == R.id.teachers_menu) {
            fragment = new ProfessorsListFragment();
        }

        if (fragment != null) {
            displayFragment(fragment);
        }

        return false;
    }
}