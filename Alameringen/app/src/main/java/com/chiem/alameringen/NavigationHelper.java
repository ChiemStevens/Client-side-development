package com.chiem.alameringen;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

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
