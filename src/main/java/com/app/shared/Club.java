package com.app.shared;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "club")
public class Club implements Serializable {
    private static final long serialVersionUID = -8706689714326132798L;

    @Id
    @GeneratedValue
    @Column(name = "club_id")
    private int id;

    @Column(name = "name")
    private String name;

    public Club(String name) {
        this.name = name;
    }

    public Club() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Club club = (Club) o;

        return name != null ? name.equals(club.name) : club.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Club{" +
                "id=" + id +
                ", name='" + name + '\'' +
                "}\n";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
