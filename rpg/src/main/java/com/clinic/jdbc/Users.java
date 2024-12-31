package com.clinic.jdbc;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    private int id;
    private String name;
    private String contactNumber;
    private String doctor = "Dr. Ramirez";
    private String date;

    public Users(String name, String contactNumber, String date) {
        this.name = name;
        this.contactNumber = contactNumber;
        this.date = date;
    }
}