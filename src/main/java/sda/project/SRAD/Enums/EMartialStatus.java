package sda.project.SRAD.Enums;




public enum EMartialStatus {
    SINGLE("SINGLE"),
    MARRIED("MARRIED"),
    DIVORCED("DIVORCED"),
    WIDOWED("WIDOWED"),
    SEPARATED("SEPARATED");


    private final String name;

    private EMartialStatus(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public boolean equals(String otherName) {
        return name.equals(otherName);
    }

    public boolean equals(EMartialStatus otherName) {
        return name.equals(otherName.name);
    }
}
