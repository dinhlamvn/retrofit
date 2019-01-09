package com.example.pc6.retrofittutorial.models;

public class User {

    private String result_code;

    private UserData info;

    public String getResultCode() {
        return result_code;
    }

    public void setResultCode(String resultCode) {
        this.result_code = resultCode;
    }

    public UserData getInfo() {
        return info;
    }

    public void setInfo(UserData info) {
        this.info = info;
    }

    public static class UserData {

        private String id;
        private String name;
        private String bdate;
        private String gender;
        private String email;
        private String addr;
        private String phone;
        private String pws;

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

        public String getBdate() {
            return bdate;
        }

        public void setBdate(String bdate) {
            this.bdate = bdate;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPws() {
            return pws;
        }

        public void setPws(String pws) {
            this.pws = pws;
        }

        @Override
        public String toString() {
            return "Name: " + name +
                    "\nBirthDay: " + bdate +
                    "\nGender: " + (gender.equals("0") ? "Male":"Female") +
                    "\nEmail: " + email +
                    "\nAddress: " + addr +
                    "\nPhone: " + phone;
        }
    }
}
