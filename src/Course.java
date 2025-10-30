public class Course implements Comparable<Course>{
    private boolean isGraduateCourse;
    private String courseDept;
    private int courseNum, numCredits;

    public Course (boolean isGraduateCourse, int courseNum, String courseDept, int numCredits){
        this.isGraduateCourse = isGraduateCourse;
        this.courseNum = courseNum;
        this.courseDept = courseDept;
        this.numCredits = numCredits;
    }
    public boolean isGraduateCourse(){
        return isGraduateCourse;
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
        String degreeType = "";
        if(isGraduateCourse != true){
            degreeType = "U";
        }else {
            degreeType = "G";
        }
        return degreeType + getCourseDept() + getCourseNum();
    }
    @Override
    public boolean equals(Object obj){
        if (this == obj) {
            return true;
        }
        if (obj == null) return false;
        if(!(obj instanceof Course)){
            return false;
        }
        Course other = (Course) obj;

        if(this.isGraduateCourse != other.isGraduateCourse){
            return false;
        }
        if(this.courseNum != other.courseNum){
            return false;
        }
        if(!(this.courseDept.equals(other.courseDept))){
            return false;
        }
        if(this.numCredits != other.numCredits){
            return false;
        }

        return true;

    }
    @Override
    public String toString(){
        String grad = "Undergraduate";
        if(isGraduateCourse){
            grad = "Graduate";
        }
        return String.format("Course: %3s-%3d | Number of Credits: %02d | %s", courseDept, courseNum, numCredits, grad);
    }

    public int compareTo(Course c){
        int courseSum = 0;
        courseSum = this.courseNum - c.courseNum;
        if(courseSum < 0){
            return -1;
        }if(courseSum > 0){
            return 1;
        }
        return 0;

    }

}
