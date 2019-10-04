package com.yash.demo.exception;

import java.util.List;

public class ErrorResponse {

	private int status; 
    private String message;
    private Long timestamp;
    
    
    
	public ErrorResponse(int status, String message, Long details) {
		super();
		this.status = status;
		this.message = message;
		this.timestamp = details;
	}
//	public ErrorResponse() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
	public int getStatus() {
		return status;
	}
//	public void setStatus(int status) {
//		this.status = status;
//	}
	public String getMessage() {
		return message;
	}
//	public void setMessage(String message) {
//		this.message = message;
//	}
	public Long getDetails() {
		return timestamp;
	}
//	public void setDetails(Long details) {
//		this.timestamp = details;
//	}
//	
    
    
    
    
 
   
}
