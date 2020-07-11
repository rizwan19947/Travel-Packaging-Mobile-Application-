package com.example.databaseproject;

public class CitiesSample {
    String City_ID;
    String City;

    public String getCity_ID() {
        return City_ID;
    }

    public void setCity_ID(String city_ID) {
        City_ID = city_ID;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    @Override
    public String toString() {
        return "CitiesSample(" + "City ID" + City_ID + "City name" + City;
    }
}
