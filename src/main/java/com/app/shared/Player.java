package com.app.shared;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "player")
public class Player implements Serializable {
    private static final long serialVersionUID = -8706689714326132798L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "secondName")
    private String secondName;

    @Column(name = "dateBirth")
    private Date dateBirth;

    @Column(name = "clubId")
    private int clubId;


    public Player() {
    }

    public Player(String lastName, String firstName, String secondName, Date dateBirth, int clubId) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.secondName = secondName;
        this.dateBirth = dateBirth;
        this.clubId = clubId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        if (lastName != null ? !lastName.equals(player.lastName) : player.lastName != null) return false;
        if (firstName != null ? !firstName.equals(player.firstName) : player.firstName != null) return false;
        return secondName != null ? secondName.equals(player.secondName) : player.secondName == null;
    }

    @Override
    public int hashCode() {
        int result = lastName != null ? lastName.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (secondName != null ? secondName.hashCode() : 0);
        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public Date getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(Date dateBirth) {
        this.dateBirth = dateBirth;
    }

    public int getClubId() {
        return clubId;
    }

    public void setClubId(int clubId) {
        this.clubId = clubId;
    }

    @Override
    public String toString() {
        return "Player{" +
            "id=" + id +
            ", lastName='" + lastName + '\'' +
            ", firstName='" + firstName + '\'' +
            ", secondName='" + secondName + '\'' +
            ", dateBirth=" + dateBirth +
            ", clubId=" + clubId +
            "}\n";
    }
}
