package ru.sabah.struktura;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Ttt {
    @Id
    private int id;
    private String data;

    public Ttt() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Ttt{" +
                "id=" + id +
                ", data='" + data + '\'' +
                '}';
    }
}