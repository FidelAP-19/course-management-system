package com.TonyPerez.coursemanagement.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@MappedSuperclass
public class Person implements Comparable<Person> {

    @NotBlank(message = "Name cannot be empty")
    @Column(name="name", length = 100)
    private String name;

    @Min(value = 1900, message = "Birth year must be at least 1900")  // ← Add this
    @Max(value = 2015, message = "Birth year must be at most 2015")
    @Column(name ="birth_year")
    private int birthYear;

    public Person(){
        name = "";
        birthYear = 0;
    }
    public Person(String name, int birthYear){
        this.name = name;
        this.birthYear = birthYear;
    }
    public String getName(){
        return name;
    }
    public int getBirthYear(){
        return birthYear;
    }
    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public boolean equals(Object obj){
        if(obj == this){
            return true;
        }
        if (obj == null) return false;
        if(!(obj instanceof Person)){
            return false;
        }

        Person other = (Person) obj;

        if(!(this.name.equals(other.name))){
            return false;
        }
        if(this.birthYear != other.birthYear){
            return false;
        }
        return true;
    }
    @Override
    public String toString(){
        return String.format("Person: Name: %30s | Birth Year: %4d", name, birthYear);
    }
    @Override
    public int compareTo(Person p){
        if(this.birthYear > p.birthYear){
            return 1;
        }
        if(this.birthYear < p.birthYear){
            return -1;
        }
        return 0;
    }
}
