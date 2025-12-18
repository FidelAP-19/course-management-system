package com.TonyPerez.coursemanagement.controller;

import com.TonyPerez.coursemanagement.domain.Student;
import com.TonyPerez.coursemanagement.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Optional;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.http.MediaType;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentRestController.class)
public class StudentRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @Test
    public void testGetStudents_NoFilters_ReturnsAllStudents() throws Exception {
        Student student1 = new Student("Alice Johnson", 2000, "Computer Science", false);
        Student student2 = new Student("Bob Smith", 2001, "Mathematics", false);

        when(studentRepository.findAll()).thenReturn(List.of(student1, student2));

        mockMvc.perform(get("/api/students"))


                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Alice Johnson"))
                .andExpect(jsonPath("$[1].name").value("Bob Smith"));
    }

    @Test
    public void testGetStudents_FilterByMajor_ReturnsFilteredStudents() throws Exception {
        Student cs1 = new Student("Alice", 2000, "Computer Science", false);
        Student cs2 = new Student("Bob", 2001, "Computer Science", false);
        Student math = new Student("Charlie", 2002, "Mathematics", false);

        when(studentRepository.findByMajor("Computer Science"))
                .thenReturn(List.of(cs1, cs2));

        mockMvc.perform(get("/api/students")
                .param("major", "Computer Science"))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].major").value("Computer Science"))
                .andExpect(jsonPath("$[1].major").value("Computer Science"));
    }

    @Test
    public void testGetStudents_FilterByMajorAndBirthYear_ReturnsFilteredStudents() throws Exception {
        Student cs1 = new Student("Alice", 2002, "Computer Science", false);
        Student cs2 = new Student("Bob", 2002, "Computer Science", false);
        Student math = new Student("Charlie", 2000, "Mathematics", false);
        Student cs3 = new Student("John", 2002, "Computer Science", false);

        when(studentRepository.findByMajorAndBirthYear("Computer Science", 2002))
                .thenReturn(List.of(cs1, cs2, cs3));

        mockMvc.perform(get("/api/students")
                .param("major", "Computer Science")
                        .param("birthYear", "2002"))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].major").value("Computer Science"))
                .andExpect(jsonPath("$[0].birthYear").value("2002"))
                .andExpect(jsonPath("$[1].major").value("Computer Science"))
                .andExpect(jsonPath("$[1].birthYear").value("2002"))
                .andExpect(jsonPath("$[2].major").value("Computer Science"))
                .andExpect(jsonPath("$[2].birthYear").value("2002"));
    }

    @Test
    public void testGetStudents_InvalidMajor_ReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/students")
                .param("major", "X"))

                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Validation Failed"))
                .andExpect(jsonPath("$.message").value("Invalid query parameters"))
                .andExpect(jsonPath("$.errors['getStudents.major']")
                        .value("Major must be 2-50 characters"));
    }
    @Test
    public void testGetStudents_InvalidBirthYear_ReturnBadRequest() throws Exception{
        mockMvc.perform(get("/api/students")
                .param("birthYear", "1800"))

                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Validation Failed"))
                .andExpect(jsonPath("$.message").value("Invalid query parameters"))
                .andExpect(jsonPath("$.errors['getStudents.birthYear']")
                        .value("Birth Year cannot be less than 1900"));
    }
    @Test
    public void testGetStudents_InvalidMajorAndBirthYear_ReturnBadRequest() throws Exception{
        mockMvc.perform(get("/api/students")
                        .param("major", "X")
                        .param("birthYear", "1800"))

                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Validation Failed"))
                .andExpect(jsonPath("$.message").value("Invalid query parameters"))
                .andExpect(jsonPath("$.errors['getStudents.major']")
                        .value("Major must be 2-50 characters"))
                .andExpect(jsonPath("$.errors['getStudents.birthYear']")
                        .value("Birth Year cannot be less than 1900"));
    }
    @Test
    public void testGetStudents_NoMatchingResults_ReturnsEmptyArray() throws Exception {
        when(studentRepository.findByMajor("Physics"))
                .thenReturn(List.of());


        mockMvc.perform(get("/api/students")
                        .param("major", "Physics"))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }
    @Test
    public void testGetStudents_TypeMismatch_ReturnsBadRequest()throws Exception{

        mockMvc.perform(get("/api/students")
                .param("birthYear", "abc"))

                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Type Mismatch"))
                .andExpect(jsonPath("$.message").value("Invalid value 'abc' for parameter 'birthYear'. Expected type: Integer"));
    }
    @Test
    public void testGetStudentById_StudentExists_ReturnsStudent() throws Exception {
        Student student = new Student("Alice", 2000, "CS", false);

        when(studentRepository.findById(1))
                .thenReturn(Optional.of(student));

        mockMvc.perform(get("/api/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alice"))
                .andExpect(jsonPath("$.major").value("CS"));
    }
    @Test
    public void testGetStudentById_NotFound_ReturnsNoStudent() throws Exception{
        Student student = new Student("Alice", 2000, "CS", false);

        when(studentRepository.findById(999))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/students/999"))
                .andExpect(status().isNotFound());
    }

    //Post

    @Test
    public void testCreateStudent_ValidData_ReturnCreatedStudent() throws Exception{
        Student inputStudent = new Student("Alice", 2000, "CS", false);
        Student savedStudent = new Student("Alice", 2000, "CS", false);

        when(studentRepository.save(any(Student.class)))
                .thenReturn(savedStudent);

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)  // ← Sending JSON
                        .content("""
                       {
                           "name": "Alice",
                           "birthYear": 2000,
                           "major": "CS",
                           "isGraduate": false
                       }
                       """))

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Alice"))
                .andExpect(jsonPath("$.major").value("CS"));
    }

    @Test
    public void testCreateStudent_InvalidData_ReturnsBadRequest() throws Exception{
        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                       {
                           "name": "",
                           "birthYear": 1800,
                           "major": ""
                       }
                       """))
                .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.errors['name']").value("Name cannot be empty"))
                .andExpect(jsonPath("$.errors['birthYear']").value("Birth year must be between 1900 and 2015"))
                .andExpect(jsonPath("$.errors['major']").value("Major cannot be empty"));
    }

}