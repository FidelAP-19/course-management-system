import java.util.ArrayList;
import java.util.List;

public class FacultyRepository {
    private ArrayList<Faculty> facultyList;

    public FacultyRepository() {
        this.facultyList = new ArrayList<>();
    }

    // CREATE
    public void add(Faculty faculty) {
        facultyList.add(faculty);
    }

    // READ
    public Faculty findById(int employeeId) {
        for (Faculty faculty : facultyList) {
            if (faculty.getEmployeeID() == employeeId) {
                return faculty;
            }
        }
        return null;
    }

    public List<Faculty> findAll() {
        return new ArrayList<>(facultyList);  // Return copy to protect original
    }

    public int count() {
        return facultyList.size();
    }

    // DELETE (we might not need this yet, but it's part of CRUD)
    public boolean remove(int employeeId) {
        Faculty faculty = findById(employeeId);
        if (faculty != null) {
            facultyList.remove(faculty);
            return true;
        }
        return false;
    }
}