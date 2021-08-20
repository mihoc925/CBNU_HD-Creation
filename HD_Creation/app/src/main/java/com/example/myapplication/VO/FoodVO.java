package com.example.myapplication.VO;

public class FoodVO {
    public int _id=0;
    public String foodKind="";
    public String foodType="";
    public int img=1;
    public String name="";
    public String note1="";
    public String note2="";
    public String note3="";
    public String note4="";

    public FoodVO(){ }

    @Override
    public String toString() {
        return "FoodVO{" +
                "_id=" + _id +
                ", foodKind='" + foodKind + '\'' +
                ", foodType='" + foodType + '\'' +
                ", img=" + img +
                ", name='" + name + '\'' +
                ", note1='" + note1 + '\'' +
                ", note2='" + note2 + '\'' +
                ", note3='" + note3 + '\'' +
                ", note4='" + note4 + '\'' +
                '}';
    }
}
