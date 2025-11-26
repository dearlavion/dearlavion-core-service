package com.dearlavion.coreservice.wish;

public class WishRequestDTO {
    private String requestId;
    private String copilotName;
    private String status;
    public WishRequestDTO() {}

    public String getRequestId() {
        return requestId;
    }
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
    public String getCopilotName() {
        return copilotName;
    }
    public void setCopilotName(String copilotName) { this.copilotName = copilotName; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
