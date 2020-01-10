package com.chiem.alameringen.Helpers;

import android.content.Context;
import android.view.MenuItem;

import androidx.fragment.app.Fragment;

import com.chiem.alameringen.Fragments.EmergencysFragment;
import com.chiem.alameringen.Fragments.MapFragment;
import com.chiem.alameringen.Fragments.PlaceFragment;
import com.chiem.alameringen.R;

public class NavigationHelper {

    private static NavigationHelper instance;

    private MapFragment mapFragment;
    private PlaceFragment placeFragment;
    private EmergencysFragment emergencysFragment;

    private NavigationHelper() {
        mapFragment = new MapFragment();
        placeFragment = new PlaceFragment();
        emergencysFragment = new EmergencysFragment();
    }

    public Fragment NavigateMenuItemClick(MenuItem item) {

        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.action_map:
                fragment = mapFragment;
                break;
            case R.id.action_place:
                fragment = placeFragment;
                break;
            case R.id.action_emergency:
                fragment = emergencysFragment;
                break;
        }
        return fragment;
    }

    public static NavigationHelper getInstance() {

        if(instance == null) {
            instance = new NavigationHelper();
        }
        return instance;

    }
}
