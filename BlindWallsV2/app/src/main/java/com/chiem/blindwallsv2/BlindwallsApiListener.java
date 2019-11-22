package com.chiem.blindwallsv2;

import com.chiem.blindwallsv2.Model.BlindWall;

public interface BlindwallsApiListener {
    void onWallAvailable(BlindWall wallsInfo);
    void onWallError(Error error);
}