package com.example.proximate_test.Models;

public class ModelResponse {
    public Boolean status = false;
    public String message = null;
    public String data = null;

    public ModelResponse() {
    }

    public ModelResponse(Boolean status, String message, String data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
