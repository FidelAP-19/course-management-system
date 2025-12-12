package com.TonyPerez.coursemanagement.domain;

import jakarta.persistence.*;

@MappedSuperclass // Base class for entities
public class Employee extends Person{
    @Id //Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="employee_id")
    private int employeeID;

    @Column(name="dept_name", length = 50)
    private String deptName;

    public Employee(){
        deptName = "";
    }
    public Employee(String deptName){
        this.deptName = deptName;
    }
    public Employee(String name, int birthYear, String deptName){
        super(name, birthYear);
        setDeptName(deptName);
    }
    public String getDeptName(){
        return deptName;
    }
    public int getEmployeeID(){
        return employeeID;
    }
    public void setDeptName(String deptName){
        this.deptName = deptName;
    }
    @Override
    public boolean equals(Object obj){
        if(obj == this){
            return true;
        }
        if (obj == null) return false;
        if(!(obj instanceof Employee)){
            return false;
        }
        if (!super.equals(obj)) return false;

        Employee other = (Employee) obj;

        if(this.deptName.equals(other.deptName) && this.employeeID == other.employeeID){
            return true;
        }
        return false;
    }
    @Override
    public String toString(){
        return super.toString() + String.format(" Employee: Department: %20s | Employee Number: %3d", deptName, employeeID);
    }
    @Override
    public int compareTo(Person p){
        if(!(p instanceof Employee)){
            throw new IllegalArgumentException("Can only compare Employee to Employee");
        }
        Employee other = (Employee) p;

        return Integer.compare(this.employeeID, other.employeeID);
    }
}
