import java.util.ArrayList;

public class Faculty extends Employee{
private ArrayList<Course> coursesTaught;
private int numCoursesTaught;
private boolean isTenured;


public Faculty(){
    super();
    coursesTaught = new ArrayList<>();
    numCoursesTaught = 0;
    isTenured = false;

}
public Faculty(boolean isTenured){
    super();
    coursesTaught = new ArrayList<>();
    numCoursesTaught = 0;
    this.isTenured = isTenured;

}
public Faculty(String deptName, boolean isTenured){
    super(deptName);
    coursesTaught = new ArrayList<>();
    this.isTenured = isTenured;
    numCoursesTaught = 0;
}
public Faculty(String name, int birthYear, String deptName, boolean isTenured){
    super(name, birthYear, deptName);
    this.isTenured = isTenured;
    numCoursesTaught = 0;
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
    if (coursesTaught.size() < 100) {
        coursesTaught.add(course);
        numCoursesTaught = coursesTaught.size();
    }
}
public void addCoursesTaught(Course [] courses){
    for(Course course : courses){
        coursesTaught.add(course);
    }
    numCoursesTaught = coursesTaught.size();
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

        if(this.getNumCoursesTaught() > other.getNumCoursesTaught()) return 1;
        if (this.getNumCoursesTaught() < other.getNumCoursesTaught()) return -1;
        return 0;
    }
}
