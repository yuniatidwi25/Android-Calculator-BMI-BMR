package com.example.androidhw1;


public class PersonalInfo {
    private String id, name, age;


    public PersonalInfo(String id, String name, String age)
    {

        this.setId(id);
        this.setName(name);
        this.setAge(age);

    }

    public String getAge() {
        return age;
    }

    private void setAge(String age) {
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
