package com.island.jpa_test;

public class PersonInfo {
    String name;
    String password;
    String info;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public PersonInfo() {

    }

    @Override
    public String toString() {
        return "PersonInfo{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
