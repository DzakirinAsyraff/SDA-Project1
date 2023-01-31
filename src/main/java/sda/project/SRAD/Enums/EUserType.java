package sda.project.SRAD.Enums;


public enum EUserType {
    STUDENT( EUserType.STUDENT_STR ),
    SRADSTAFF( EUserType.SRADSTAFF_STR ),
    FACULTYSTAFF( EUserType.FACULTYSTAFF_STR ),
    AGENT( EUserType.AGENT_STR );

    public static final String STUDENT_STR = "STUDENT";
    public static final String SRADSTAFF_STR = "SRADSTAFF";
    public static final String FACULTYSTAFF_STR = "FACULTYSTAFF";
    public static final String AGENT_STR = "AGENT";
    


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