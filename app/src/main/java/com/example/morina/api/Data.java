package com.example.morina.api;

import com.example.morina.model.Car;

import java.util.List;

public class Data {
    private String login;
    private String token;
    private List<Car> mCars;

    private static Data sData;

    private Data() {}

    public static Data get() {
        if (sData == null) {
            sData = new Data();
        }
        return sData;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<Car> getCars() {
        return mCars;
    }

    public void setCars(List<Car> cars) {
        mCars = cars;
    }
}
