package com.chiem.alameringen.API;

import com.chiem.alameringen.Models.Emergency;
import com.chiem.alameringen.Models.Place;

public interface EmergencyApiListener {

    void onEmergencySucces(Emergency emergency);
    void onAllEmergencysRecieved();
    void onPlaceRecieved(Place place);
}
