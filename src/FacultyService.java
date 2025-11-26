import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FacultyService {
    private FacultyRepository facultyRepository;
    private CourseRepository courseRepository;


    public FacultyService(FacultyRepository facultyRepo, CourseRepository courseRepo) {
        this.facultyRepository = facultyRepo;
        this.courseRepository = courseRepo;
    }


    public List<Course> addCoursesToFaculty(int facultyId, List<Course> courses) {
        ArrayList <Course> addedCourses = new ArrayList<>();
        // Find Faculty Member
        Faculty faculty = facultyRepository.findById(facultyId);

        if (faculty == null){
            throw new IllegalArgumentException("Faculty not found");
        }
        // Find and Validate Course
        for(Course course:courses){
            Course existingCourse = courseRepository.findByCourseDeptNum(course.getCourseNum(), course.getCourseDept());
            if (existingCourse == null){
                throw new IllegalArgumentException("Course " + course.getCourseName() + " not found");
            }
            //Check Course for duplicate
            boolean alreadyTeaching = false;
            for (int i = 0; i < faculty.getNumCoursesTaught(); i++) {
                if (faculty.getCourseTaught(i).equals(existingCourse)) {
                    alreadyTeaching = true;
                    break;
                }
            }

            //Add Course
                if(!alreadyTeaching){
                    faculty.addCourseTaught(existingCourse);
                    addedCourses.add(existingCourse);
                }
            }
            return addedCourses;
        }
    }