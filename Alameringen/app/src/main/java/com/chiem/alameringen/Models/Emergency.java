package com.chiem.alameringen.Models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Emergency implements Serializable, Comparable<Emergency> {

    private String id;
    private String date;
    private String msg;
    private String text;
    private String lat;
    private String lon;
    private String region;
    private int priority;
    private int scale;
    private String place;
    private String shift;
    private String icon;
    private ArrayList<CapCodes> capCodes;
    private String fireInfo;
    private int hassubs;

    private Date dateTime;

    public Emergency(JSONObject jsonObject) {

        capCodes = new ArrayList<>();
        dateTime = null;

        try {

            this.hassubs = jsonObject.getInt("hassubs");

            if(this.hassubs >= 1) {
                jsonObject = jsonObject.getJSONArray("subitems").getJSONObject(0);
            }

            this.id = jsonObject.getString("id");
            this.date = jsonObject.getString("date");
            this.msg = jsonObject.getString("msg");
            this.text = jsonObject.getString("txt");
            this.lat = jsonObject.getString("lat");
            this.lon = jsonObject.getString("lon");
            this.region = jsonObject.getString("regio");
            this.priority = jsonObject.getInt("prio1");
            this.scale = jsonObject.getInt("omvang");
            this.place = jsonObject.getString("plaats");
            this.shift = jsonObject.getString("dienst");
            this.icon = jsonObject.getString("icon");

            JSONArray capCodes = jsonObject.getJSONArray("capcodes");
            for(int i = 0; i < capCodes.length(); i++) {
                this.capCodes.add(new CapCodes(capCodes.getJSONObject(i)));
            }

            this.fireInfo = jsonObject.getString("brandinfo");


            if(this.date.toLowerCase().contains("vandaag")) {

                int hours = 0;
                int min = 0;

                try {
                    hours = Integer.parseInt(this.date.toLowerCase().substring(8, 10));
                    min = Integer.parseInt(this.date.toLowerCase().substring(11, 13));


                }
                catch (Exception ex) {
                    Log.e("Alameringen", ex.getMessage());
                }

                final Calendar cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY, hours);// for 6 hour
                cal.set(Calendar.MINUTE, min);// for 0 min

                dateTime = cal.getTime();
            }
            else if(this.date.toLowerCase().contains("gisteren")) {

                int hours = 0;
                int min = 0;

                try {
                    hours = Integer.parseInt(this.date.toLowerCase().substring(9, 11));
                    min = Integer.parseInt(this.date.toLowerCase().substring(12, 14));


                }
                catch (Exception ex) {
                    Log.e("Alameringen", ex.getMessage());
                }

                final Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, -1);
                cal.set(Calendar.HOUR_OF_DAY, hours);// for 6 hour
                cal.set(Calendar.MINUTE, min);// for 0 min

                dateTime = cal.getTime();
            }
            else {

                int hours = 0;
                int min = 0;

                try {
                    hours = Integer.parseInt(this.date.toLowerCase().substring(11, 13));
                    min = Integer.parseInt(this.date.toLowerCase().substring(14, 16));
                }
                catch (Exception ex) {
                    Log.e("Alameringen", ex.getMessage());
                }

                String splitDateTime = date.substring(0, 10);
                Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(splitDateTime);

                final Calendar cal = Calendar.getInstance();
                cal.setTime(date1);
                cal.set(Calendar.HOUR_OF_DAY, hours);// for 6 hour
                cal.set(Calendar.MINUTE, min);// for 0 min

                dateTime = cal.getTime();
            }

        }
        catch (JSONException ex) {
            Log.e("Alarmeringen", ex.getMessage());
        }
        catch (ParseException ex) {
            Log.e("Alameringen", ex.toString());
        }

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public ArrayList<CapCodes> getCapCodes() {
        return capCodes;
    }

    public void setCapCodes(ArrayList<CapCodes> capCodes) {
        this.capCodes = capCodes;
    }

    public String getFireInfo() {
        return fireInfo;
    }

    public void setFireInfo(String fireInfo) {
        this.fireInfo = fireInfo;
    }

    public int getHassubs() {
        return hassubs;
    }

    public void setHassubs(int hassubs) {
        this.hassubs = hassubs;
    }



    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date datetime) {
        this.dateTime = datetime;
    }

    @Override
    public int compareTo(Emergency o) {
        return getDateTime().compareTo(o.getDateTime());
    }
}
