package com.chiem.alameringen.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.chiem.alameringen.Fragments.EmergencyDetailFragment;
import com.chiem.alameringen.Fragments.EmergencysFragment;
import com.chiem.alameringen.Fragments.MapFragment;
import com.chiem.alameringen.Helpers.LandscapeHelper;
import com.chiem.alameringen.Helpers.NavigationHelper;
import com.chiem.alameringen.Models.Emergency;
import com.chiem.alameringen.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.os.Bundle;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationBinder();

        if(savedInstanceState != null) {
            String fragmentType = LandscapeHelper.getInstance().getTypeOfFragment();
            if(fragmentType.equals("EmergencyDetail")) {
                Emergency emergency = LandscapeHelper.getInstance().getCurrentEmergency();

                EmergencyDetailFragment emergencysFragment = new EmergencyDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("EMERGENCY", emergency);
                emergencysFragment.setArguments(bundle);

                openFragment(emergencysFragment);
            }

        }
        else {
            openFragment(new MapFragment());
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("Fragment", LandscapeHelper.getInstance().getTypeOfFragment());
        outState.putSerializable("EMERGENCY", LandscapeHelper.getInstance().getCurrentEmergency());
    }

    private void NavigationBinder() {

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment = NavigationHelper.getInstance().NavigateMenuItemClick(item);

                openFragment(fragment);

                return true;
            }
        });
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameContent, fragment);
        ft.commit();
    }
}