package ca.mcgill.ecse321.petadoptionapp.model;

import javax.persistence.Entity;

@Entity
public class Address{
   private String street;

public void setStreet(String value) {
    this.street = value;
}
public String getStreet() {
    return this.street;
}
private String city;

public void setCity(String value) {
    this.city = value;
}
public String getCity() {
    return this.city;
}
private String postalCode;

public void setPostalCode(String value) {
    this.postalCode = value;
}
public String getPostalCode() {
    return this.postalCode;
}
private String state;

public void setState(String value) {
    this.state = value;
}
public String getState() {
    return this.state;
}
private String country;

public void setCountry(String value) {
    this.country = value;
}
public String getCountry() {
    return this.country;
}
}
