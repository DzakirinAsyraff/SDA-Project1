package sda.project.SRAD.Enums;



public enum EStudentApplicationStatus {
    DRAFT("DRAFT"),
    PENDING_PAYMENT("PENDING_PAYMENT"),
    APPLICATION_SUBMITTED("APPLICATION_SUBMITTED"),
    DOCUMENT_REVIEWED_BY_SRAD("DOCUMENT_REVIEWED_BY_SRAD"),
    REQUIRE_DOCUMENT_RESUBMIT("REQUIRE_DOCUMENT_RESUBMIT"),
    APPLICATION_REVIEWED_BY_SRAD("APPLICATION_REVIEWED_BY_SRAD"),
    REJECTED("REJECTED"),
    APPROVED("APPROVED"),
    PENDING_PROPOSAL_REVIEW("PENDING_PROPOSAL_REVIEW"),
    PENDING_ASSIGN_SUPERVISOR("PENDING_ASSIGN_SUPERVISOR"),
    SUPERVISOR_ASSIGNED("SUPERVISOR_ASSIGNED");

    private final String name;

    private EStudentApplicationStatus(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public boolean equals(String otherName) {
        return name.equals(otherName);
    }

    public boolean equals(EStudentApplicationStatus otherName) {
        return name.equals(otherName.name);
    }
}
