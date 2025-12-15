package edu.univ.erp.domain;

public class Section {
    private int sectionId;
    private int courseId;
    private Integer instructorId;
    private String dayTime;
    private String room;
    private String semester;
    private int capacity;
    private int year;

    private String courseTitle;
    private int credits;

    private String courseCode;

    public Section(int sectionId, int courseId, int instructorId, String dayTime, String room, String semester, int capacity, int year) {
        this.sectionId = sectionId;
        this.courseId = courseId;
        this.instructorId = instructorId;
        this.dayTime = dayTime;
        this.room = room;
        this.semester = semester;
        this.capacity = capacity;
        this.year = year;
    }

    public Section() {

    }

    public int getSectionId() {
        return sectionId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public Integer getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Integer instructorId) {
        this.instructorId = instructorId;
    }

    public String getDayTime() {
        return dayTime;
    }

    public void setDayTime(String dayTime) {
        this.dayTime = dayTime;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString(){
        return courseId+" - Section "+sectionId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public int getCredits(){
        return credits;
    }

    public void setCourseTitle(String title){
        this.courseTitle=title;
    }

    public void setCredits(int credits){
        this.credits=credits;
    }
//    @Override
//    public String toString(){
//        return "Course "+courseId+" / Section "+sectionId;
//    }
}
