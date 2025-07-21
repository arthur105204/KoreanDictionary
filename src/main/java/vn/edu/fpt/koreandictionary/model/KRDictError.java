package vn.edu.fpt.koreandictionary.model;

import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "error")
@XmlAccessorType(XmlAccessType.FIELD)
public class KRDictError {
    @XmlElement(name = "error_code")
    private String errorCode;

    @XmlElement(name = "message")
    private String message;

    // Getters and setters
    public String getErrorCode() { return errorCode; }
    public void setErrorCode(String errorCode) { this.errorCode = errorCode; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
} 