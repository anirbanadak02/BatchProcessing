package com.greater.dto;

import java.io.Serializable;

public class CustomerDTO implements Serializable{

	private static final long serialVersionUID = -8017421158511970606L;
	private String customerAccount;
	private String transactionalAmount;
	private ReportCustomerDTO reportCustomerDTO;
	private String dateTime;
	
	public String getCustomerAccount() {
		return customerAccount;
	}
	public void setCustomerAccount(String customerAccount) {
		this.customerAccount = customerAccount;
	}
	public String getTransactionalAmount() {
		return transactionalAmount;
	}
	public void setTransactionalAmount(String transactionalAmount) {
		this.transactionalAmount = transactionalAmount;
	}
	public ReportCustomerDTO getReportCustomerDTO() {
		return reportCustomerDTO;
	}
	public void setReportCustomerDTO(ReportCustomerDTO reportCustomerDTO) {
		this.reportCustomerDTO = reportCustomerDTO;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	
}
