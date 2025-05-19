package model;

public class Application {
    private int id;
    private String studentName;
    private String universityCode;
    private String majorCode;
    private String status;

    public Application(int id, String studentName, String universityCode, String majorCode, String status) {
        this.id = id;
        this.studentName = studentName;
        this.universityCode = universityCode;
        this.majorCode = majorCode;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getUniversityCode() {
        return universityCode;
    }

    public String getMajorCode() {
        return majorCode;
    }

    public String getStatus() {
        return status;
    }
}
