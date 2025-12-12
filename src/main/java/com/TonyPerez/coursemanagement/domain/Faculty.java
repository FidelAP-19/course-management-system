package com.TonyPerez.coursemanagement.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "faculty")
public class Faculty extends Employee{
    @ManyToMany
    @JoinTable(
            name = "faculty_courses",
            joinColumns = @JoinColumn(name = "faculty_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> coursesTaught;

    @Column(name = "is_tenured")
    private boolean isTenured;


public Faculty(){
    super();
    coursesTaught = new ArrayList<>();
    isTenured = false;

}
public Faculty(boolean isTenured){
    super();
    coursesTaught = new ArrayList<>();
    this.isTenured = isTenured;

}
public Faculty(String deptName, boolean isTenured){
    super(deptName);
    coursesTaught = new ArrayList<>();
    this.isTenured = isTenured;
}
public Faculty(String name, int birthYear, String deptName, boolean isTenured){
    super(name, birthYear, deptName);
    this.isTenured = isTenured;
    coursesTaught = new ArrayList<>();
}

public boolean isTenured(){
return isTenured;
}
public int getNumCoursesTaught(){
    return coursesTaught.size();
}
public void setIsTenured(boolean isTenured){
    this.isTenured = isTenured;
}
public void addCourseTaught(Course course) {
        coursesTaught.add(course);
      //  numCoursesTaught = coursesTaught.size();
}
public void addCoursesTaught(Course [] courses){
    for(Course course : courses){
        coursesTaught.add(course);
    }
  //  numCoursesTaught = coursesTaught.size();
}
public Course getCourseTaught(int index){
    if (index >= coursesTaught.size()|| index < 0){
        return null;
    }
    return coursesTaught.get(index);

}
public String getCourseTaughtAsString(int index){
    if (index >= coursesTaught.size() || index < 0){
        return "";
    }
    Course c = coursesTaught.get(index);
    return c.getCourseDept() + "-" + c.getCourseNum();

}
public String getAllCoursesTaughtAsString(){
    String courseList = "";
    for(int i = 0; i < coursesTaught.size(); i++){
        courseList += getCourseTaughtAsString(i);
        if (i < coursesTaught.size() - 1)
            courseList += ",";
    }
    return courseList;
}

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;
        if (!(obj instanceof Faculty)) return false;
        if(!super.equals(obj)) return false;

        Faculty other = (Faculty) obj;

        if(this.isTenured != other.isTenured) return false;
        if (this.coursesTaught.size() != other.coursesTaught.size()) return false;
        if(!this.coursesTaught.equals(other.coursesTaught)) return false;
        return true;

    }

    @Override
    public String toString(){
        String tenured = "Is Tenured";
        if (!isTenured) {
            tenured = "Not Tenured";
        }
        return super.toString() + String.format(" Faculty: %11s | Number of Courses Taught: %3d | Courses Taught: %s", tenured, coursesTaught.size(), getAllCoursesTaughtAsString());
    }

    @Override
    public int compareTo(Person p){
        if(!(p instanceof Faculty)){
            //throw new IllegalArgumentException("Can only compare Faculty to Faculty");
            return super.compareTo(p);
        }
        Faculty other = (Faculty) p;

        return Integer.compare(this.getNumCoursesTaught(), other.getNumCoursesTaught());
    }
}
