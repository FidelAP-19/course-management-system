package repository;

import domain.Course;
import java.util.ArrayList;
import java.util.List;

public class CourseRepository {
    private ArrayList<Course> courseList;
    public CourseRepository(){
        this.courseList = new ArrayList<>();
    }
    public void add(Course course){
        courseList.add(course);
    }
    public Course findByCourseDeptNum(int courseNum, String courseDept){
        for (Course course : courseList){
            if(course.getCourseDept().equals(courseDept) && course.getCourseNum() == courseNum){
                return course;
            }
        }
        return null;
    }
    public List<Course> findAll(){
        return new ArrayList<>(courseList);
    }
    public int count(){
        return courseList.size(); // return total number of Courses
    }
    public boolean remove(int courseNum, String courseDept){
        Course course = findByCourseDeptNum(courseNum, courseDept);
        if(course != null){
            courseList.remove(course);
            return true;
        }
        return false;
    }
}
