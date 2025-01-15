package com.example.eventsapp;

public class Event {
    private int id;
    private String name;
    private String icon;

    public Event(int id, String name, String icon) {
        this.id = id;
        this.name = name;
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public int getIconResourceId() {
        switch(id) {
            case 1:
                return R.drawable.enactus_icon;
            case 2:
                return R.drawable.android_club_icon;
            case 3:
                return R.drawable.hive_club_icon;
            case 4:
                return R.drawable.joker_icon;
            default:
                return R.drawable.ic_event_placeholder;
        }
    }
}
