package sda.project.SRAD.Enums;


public enum EUserType {
    STUDENT("STUDENT"),
    SRADSTAFF("SRADSTAFF"),
    FACULTYSTAFF("FACULTYSTAFF"),
    AGENT("AGENT");


    private final String name;

    private EUserType(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public boolean equals(String otherName) {
        return name.equals(otherName);
    }

    public boolean equals(EUserType otherName) {
        return name.equals(otherName.name);
    }
}