package domain;

public class Employee extends Person{
    private String deptName;
    static private int numEmployees = 0;
    private int employeeID;

    public Employee(){
        deptName = "";
        numEmployees++;
        employeeID = numEmployees;
    }
    public Employee(String deptName){
        this.deptName = deptName;
        numEmployees++;
        employeeID = numEmployees;
    }
    public Employee(String name, int birthYear, String deptName){
        super(name, birthYear);
        setDeptName(deptName);
        numEmployees++;
        employeeID = numEmployees;
    }
    public String getDeptName(){
        return deptName;
    }
    public static int getNumEmployees(){
        return numEmployees;
    }
    public int getEmployeeID(){
        return employeeID;
    }
    public void setDeptName(String deptName){
        this.deptName = deptName;
    }
    @Override
    public boolean equals(Object obj){
        if(obj == this){
            return true;
        }
        if (obj == null) return false;
        if(!(obj instanceof Employee)){
            return false;
        }
        if (!super.equals(obj)) return false;

        Employee other = (Employee) obj;

        if(this.deptName.equals(other.deptName) && this.employeeID == other.employeeID){
            return true;
        }
        return false;
    }
    @Override
    public String toString(){
        return super.toString() + String.format(" Employee: Department: %20s | Employee Number: %3d", deptName, employeeID);
    }
    @Override
    public int compareTo(Person p){
        if(!(p instanceof Employee)){
            throw new IllegalArgumentException("Can only compare Employee to Employee");
        }
        Employee other = (Employee) p;

        if(this.employeeID > other.employeeID){
            return 1;
        }
        if(this.employeeID < other.employeeID){
            return -1;
        }
        return 0;
    }
}
