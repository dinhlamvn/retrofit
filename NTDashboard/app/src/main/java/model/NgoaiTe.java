package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NgoaiTe {

    @SerializedName("items")
    @Expose
    private List<Item> items = null;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public class Item {

        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("imageurl")
        @Expose
        private String imageurl;
        @SerializedName("muatienmat")
        @Expose
        private String muatienmat;
        @SerializedName("muack")
        @Expose
        private String muack;
        @SerializedName("bantienmat")
        @Expose
        private String bantienmat;
        @SerializedName("banck")
        @Expose
        private String banck;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getImageurl() {
            return imageurl;
        }

        public void setImageurl(String imageurl) {
            this.imageurl = imageurl;
        }

        public String getMuatienmat() {
            return muatienmat;
        }

        public void setMuatienmat(String muatienmat) {
            this.muatienmat = muatienmat;
        }

        public String getMuack() {
            return muack;
        }

        public void setMuack(String muack) {
            this.muack = muack;
        }

        public String getBantienmat() {
            return bantienmat;
        }

        public void setBantienmat(String bantienmat) {
            this.bantienmat = bantienmat;
        }

        public String getBanck() {
            return banck;
        }

        public void setBanck(String banck) {
            this.banck = banck;
        }

    }
}
