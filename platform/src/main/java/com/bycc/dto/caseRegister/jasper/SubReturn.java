package com.bycc.dto.caseRegister.jasper;
public class SubReturn {
    private String leaveDate;
    private String leaveReason;
    private Boolean allReturnOrNot;
    private String note;

    public String getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(String leaveDate) {
        this.leaveDate = leaveDate;
    }

    public String getLeaveReason() {
        return leaveReason;
    }

    public void setLeaveReason(String leaveReason) {
        this.leaveReason = leaveReason;
    }

    public Boolean getAllReturnOrNot() {
        return allReturnOrNot;
    }

    public void setAllReturnOrNot(Boolean allReturnOrNot) {
        this.allReturnOrNot = allReturnOrNot;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}