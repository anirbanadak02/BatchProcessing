package com.greater.dto;

import java.io.Serializable;

public class ReportCustomerDTO implements Serializable{

	private static final long serialVersionUID = 6794612631790688179L;
	
	private String noofAccounts;
	private String credit;
	private String debit;
	private String fileName;
	private String skippedTransactions;
	
	

	public String getNoofAccounts() {
		return noofAccounts;
	}
	public void setNoofAccounts(String noofAccounts) {
		this.noofAccounts = noofAccounts;
	}
	public String getCredit() {
		return credit;
	}
	public void setCredit(String credit) {
		this.credit = credit;
	}
	public String getDebit() {
		return debit;
	}
	public void setDebit(String debit) {
		this.debit = debit;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getSkippedTransactions() {
		return skippedTransactions;
	}
	public void setSkippedTransactions(String skippedTransactions) {
		this.skippedTransactions = skippedTransactions;
	}
	

}
