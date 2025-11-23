package com.dearlavion.coreservice.wish;

public class WishRequestDTO {
    private String requestId;
    private String copilotName;
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

    public void setCopilotName(String copilotName) {
        this.copilotName = copilotName;
    }
}
