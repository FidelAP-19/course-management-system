package com.TonyPerez.coursemanagement.domain;

import jakarta.persistence.*;

public class GeneralStaff extends Employee{
    private String duty;
    public GeneralStaff(){
        duty = "";
    }
    public GeneralStaff(String duty){
        this.duty = duty;
    }
    public GeneralStaff(String deptName, String duty){
        super(deptName);
        this.duty = duty;
    }
    public GeneralStaff(String name, int birthYear, String deptName, String duty){
        super(name, birthYear, deptName);
        this.duty = duty;
    }
    public String getDuty(){
        return duty;
    }
    @Override
    public boolean equals(Object obj){
        if (obj == this) return true;
        if (obj == null) return false;
        if (!(obj instanceof GeneralStaff)) return false;
        if(!super.equals(obj)) return false;

        GeneralStaff other = (GeneralStaff) obj;

        if(!this.duty.equals(other.duty)) return false;

        return true;

    }
    @Override
    public String toString(){
        return super.toString() + String.format(" GeneralStaff: Duty: %10s", duty);
    }

}
