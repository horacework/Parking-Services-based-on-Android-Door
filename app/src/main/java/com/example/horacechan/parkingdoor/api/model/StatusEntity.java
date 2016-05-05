package com.example.horacechan.parkingdoor.api.model;

public class StatusEntity {
    private String newLogId;
    private int oldStatus;
    private String markerId;

    public String getMarkerId() {
        return markerId;
    }

    public void setMarkerId(String markerId) {
        this.markerId = markerId;
    }

    public String getNewLogId() {
        return newLogId;
    }

    public void setNewLogId(String newLogId) {
        this.newLogId = newLogId;
    }

    public int getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(int oldStatus) {
        this.oldStatus = oldStatus;
    }
}
