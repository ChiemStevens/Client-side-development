package com.chiem.alameringen.Helpers;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import com.chiem.alameringen.Activitys.EmergencyActivity;
import com.chiem.alameringen.Activitys.MainActivity;
import com.chiem.alameringen.Activitys.PlaceActivity;
import com.chiem.alameringen.R;

public class NavigationHelper {

    public static Intent NavigateMenuItemClick(Context context, MenuItem item) {

        Intent intent = null;

        switch (item.getItemId()) {
            case R.id.action_map:
                intent = new Intent(context, MainActivity.class);
                break;
            case R.id.action_place:
                intent = new Intent(context, PlaceActivity.class);
                break;
            case R.id.action_emergency:
                intent = new Intent(context, EmergencyActivity.class);
                break;
        }
        return intent;
    }
}
