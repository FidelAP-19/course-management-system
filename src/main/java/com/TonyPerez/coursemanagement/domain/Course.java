package com.TonyPerez.coursemanagement.domain;

import jakarta.persistence.*;
import java.util.Objects;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"courseDept", "courseNum"})
})
public class Course implements Comparable<Course>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //Database generated ID

    @Column(name = "is_graduate_course")
    private boolean isGraduateCourse;

    @Size(min = 2, max = 10, message = "Department code must be 2-10 characters")
    @Column(name = "course_dept", nullable = false, length = 10)
    private String courseDept;

    @Min(value = 100, message = "Course number must be between 100 and 999")  // ← Add this
    @Max(value = 999, message = "Course number must be between 100 and 999")
    @Column(name="course_num", nullable = false)
    private int courseNum;

    @Min(value = 1, message = "Credits must be positive")
    @Column(name="num_credits", nullable = false)
    private int numCredits;

    protected Course(){

    }
    public Course (boolean isGraduateCourse, int courseNum, String courseDept, int numCredits){
        this.isGraduateCourse = isGraduateCourse;
        this.courseNum = courseNum;
        this.courseDept = courseDept;
        this.numCredits = numCredits;
    }
    public boolean isGraduateCourse(){
        return isGraduateCourse;
    }
    public Long getId() {
        return id;
    }
    public int getCourseNum(){
        return courseNum;
    }
    public String getCourseDept(){
        return courseDept;
    }
    public int getNumCredits(){
        return numCredits;
    }
    public String getCourseName(){
        String degreeType = isGraduateCourse ? "G" : "U";
        return degreeType + getCourseDept() + getCourseNum();
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Course other = (Course) obj;

        return this.courseDept.equals(other.courseDept)
                && this.courseNum == other.courseNum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseDept, courseNum);
    }
    @Override
    public String toString(){
        String grad = isGraduateCourse ? "Graduate" : "Undergraduate";
        return String.format("Course: %3s-%3d | Number of Credits: %02d | %s",
                courseDept, courseNum, numCredits, grad);
    }

    public int compareTo(Course c){
        return Integer.compare(this.courseNum, c.courseNum);

    }

}
