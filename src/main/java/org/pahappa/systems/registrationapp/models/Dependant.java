package org.pahappa.systems.registrationapp.models;
import javax.persistence.*;
@Entity
@Table(name = "dependant_table")
public class Dependant extends User{
    private String gender;

//    @ManyToOne
//    private User user;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
