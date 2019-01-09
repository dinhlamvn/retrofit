package com.example.pc6.retrofittutorial;

public class User {

    public String result_code;

    public Data data;

    class Data {
        public String id;
        public String name;
        public String age;
    }

    @Override
    public String toString() {
        return "[ID: " + data.id + " - NAME: " + data.name + " - AGE: " + data.age + "]";
    }
}
