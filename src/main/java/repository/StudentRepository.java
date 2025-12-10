package repository;

import domain.Student;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {
    private ArrayList<Student> studentList;

    public StudentRepository(){
    this.studentList = new ArrayList<>();
    }
    public void add(Student student){
        studentList.add(student);
    }
    public Student findById(int studentId){
        for (Student student : studentList) {
            if (student.getStudentID() == studentId) {
                return student;
            }
        }
        return null;
    }
    public List<Student> findAll(){
        return new ArrayList<>(studentList);
    }
    public int count(){
        return studentList.size();
    }
    public boolean remove(int studentId){
        Student student = findById(studentId);
        if (student != null) {
            studentList.remove(student);
            return true;
        }
        return false;
    }

}
