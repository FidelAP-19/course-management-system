package com.TonyPerez.coursemanagement.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "student")
public class Student extends Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int studentID;

    //private int numCoursesTaken;
    @ManyToMany
    @JoinTable(
            name = "student_courses",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List <Course> coursesTaken;
    private boolean isGraduate;
    private String major;

    public Student(){
        super();
        //numCoursesTaken = 0;
        isGraduate = false;
        coursesTaken = new ArrayList<>();
        major = "undeclared";
    }
    public Student(boolean isGraduate){
        super();
        this.isGraduate = isGraduate;
        //numCoursesTaken = 0;
        this.coursesTaken = new ArrayList<>();
        major = "undeclared";
    }
    public Student(String major, boolean isGraduate){
        super();
        this.isGraduate = isGraduate;
       // numCoursesTaken = 0;
        this.coursesTaken = new ArrayList<>();
        this.major = major;
    }
    public Student(String name, int birthYear, String major, boolean isGraduate){
       super(name,birthYear);
       this.coursesTaken = new ArrayList<>();
       this.major = major;
       this.isGraduate = isGraduate;

    }
    public boolean isGraduate(){
        return isGraduate;
    }
    public int getNumCoursesTaken(){
        return coursesTaken.size();
    }
    public int getStudentID(){
        return studentID;
    }
    public String getMajor(){
        return major;
    }
    public void setIsGraduate(boolean isGraduate){
        this.isGraduate = isGraduate;
    }
    public void setMajor(String major){
        this.major = major;
    }
    public void addCourseTaken(Course course){
            coursesTaken.add(course);
    }
    public void addCoursesTaken(Course [] courses){
        for (Course cou : courses){
            coursesTaken.add(cou);
        }
    }
    public Course getCourseTaken(int index){
        if (index < 0 || index >= coursesTaken.size()) return null;
        return coursesTaken.get(index);
    }
    public String getCourseTakenAsString(int index){
        if (index < 0 || index >= coursesTaken.size()) return "";
        Course c = coursesTaken.get(index);
        return c.getCourseDept() + "-" + c.getCourseNum();
    }
    public String getAllCoursesTakenAsString(){
        String studentCourses = "";
        for (int i = 0; i < coursesTaken.size(); i++){
            studentCourses += getCourseTakenAsString(i);
            if(i < coursesTaken.size()-1){
                studentCourses += ",";
            }
        }
        return studentCourses;
    }
    @Override
    public boolean equals(Object obj){
        if(obj == this) return true;
        if (obj == null) return false;
        if(!(obj instanceof Student)) return false;
        if(!super.equals(obj)) return false;

        Student other = (Student) obj;

        if(this.studentID != other.studentID) return false;
        if(this.coursesTaken.size() != other.coursesTaken.size()) return false;
        if(!this.coursesTaken.equals(other.coursesTaken)) return false;
        if(this.isGraduate != other.isGraduate)return false;

        return true;

    }
    public String toString(){
        String graduate = "Undergraduate";
        if(isGraduate){
            graduate = "Graduate";
        }
        return super.toString() + String.format(" Student: studentID: %04d | Major %20s | %14s | Number of Courses Taken: %3d | Courses Taken: %s", studentID, major, graduate, coursesTaken.size(), getAllCoursesTakenAsString());
    }
    public int compareTo(Person p){
        if (!(p instanceof Student)){
            // throw new IllegalArgumentException("Can only compare Student to Student");
            return super.compareTo(p);
        }
        Student other = (Student) p;
        int otherCreditTotal = 0;
        int thisCreditTotal = 0;

        for(int i = 0; i < other.coursesTaken.size(); i++){
            otherCreditTotal += other.coursesTaken.get(i).getNumCredits();
        }
        for(int i = 0; i < this.coursesTaken.size(); i++){
            thisCreditTotal += coursesTaken.get(i).getNumCredits();
        }
        if(thisCreditTotal > otherCreditTotal){
            return 1;
        }
        if(thisCreditTotal < otherCreditTotal){
            return -1;
        }
        return 0;
    }

}
