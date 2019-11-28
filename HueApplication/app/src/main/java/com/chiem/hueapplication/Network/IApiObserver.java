package com.chiem.hueapplication.Network;

import com.chiem.hueapplication.Models.Connection;
import com.chiem.hueapplication.Models.Light;

public interface IApiObserver {

    void addLight(Light light);
    void addLink(String key);

}
