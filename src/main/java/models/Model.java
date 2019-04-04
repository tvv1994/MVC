package models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Model {
    private int id;
    private String name;
    private int number;
    private List<String> listTag;
    private Date date;

    public Model() {
    }

    public Model(String name, int number, List<String> listTag) {
        this.name = name;
        this.number = number;
        this.listTag = listTag;
        date = new Date();
    }

    public Model(int id, String name, int number, List<String> listTag, Date date) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.listTag = listTag;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public List<String> getListTag() {
        return listTag;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
