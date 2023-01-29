package sda.project.SRAD.Enums;



public enum EFaculty {
    CIVIL_ENG("CIVIL_ENG"),
    MECH_ENG("MECH_ENG"),
    ELEC_ENG("ELEC_ENG"),
    CHEM_ENG("CHEM_ENG"),
    COMPUTING("COMPUTING"),
    SCIENCE("SCIENCE"),
    FABU("FABU"),
    SOCIAL_SC("SOCIAL_SC"),
    MANAGEMENT("MANAGEMENT"),
    MJIIT("MJIIT"),
    AHIBS("AHIBS"),
    RFTI("RFTI");


    private final String name;

    private EFaculty(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public boolean equals(String otherName) {
        return name.equals(otherName);
    }

    public boolean equals(EFaculty otherName) {
        return name.equals(otherName.name);
    }
}
