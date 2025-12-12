package com.TonyPerez.coursemanagement.controller;

import com.TonyPerez.coursemanagement.domain.Faculty;
import com.TonyPerez.coursemanagement.repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/faculty")
public class FacultyRestController {

    private final FacultyRepository facultyRepository;

    @Autowired
    public FacultyRestController(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @GetMapping
    public List<Faculty> getAllFaculty() {
        return facultyRepository.findAll();
    }

    /**
     * GET /api/faculty/{id}
     * Returns one faculty member by ID
     *
     * @param id - extracted from URL path (e.g., /api/faculty/5)
     * @return Faculty if found, 404 Not Found if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getFacultyById(@PathVariable Integer id) {
        Optional<Faculty> faculty = facultyRepository.findById(id);

        if (faculty.isPresent()) {
            return ResponseEntity.ok(faculty.get());  // 200 OK with faculty data
        } else {
            return ResponseEntity.notFound().build();  // 404 Not Found
        }
    }

    /**
     * POST /api/faculty
     * Creates a new faculty member
     *
     * @RequestBody - Jackson converts JSON to Faculty object automatically
     */
    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyRepository.save(faculty);
    }
}