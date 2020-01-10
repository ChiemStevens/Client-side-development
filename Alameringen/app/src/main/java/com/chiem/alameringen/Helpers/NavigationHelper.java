package com.chiem.alameringen.Helpers;

import android.content.Context;
import android.view.MenuItem;

import androidx.fragment.app.Fragment;

import com.chiem.alameringen.Fragments.EmergencysFragment;
import com.chiem.alameringen.Fragments.MapFragment;
import com.chiem.alameringen.Fragments.PlaceFragment;
import com.chiem.alameringen.R;

public class NavigationHelper {

    public static Fragment NavigateMenuItemClick(Context context, MenuItem item) {

        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.action_map:
                fragment = new MapFragment();
                break;
            case R.id.action_place:
                fragment = new PlaceFragment();
                break;
            case R.id.action_emergency:
                fragment = new EmergencysFragment();
                break;
        }
        return fragment;
    }
}
