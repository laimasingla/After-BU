package com.cg.ibs.cardmanagement.bean;

import java.math.BigInteger;
import java.time.LocalDateTime;


public class CaseIdBean {

	public CaseIdBean() {
		
	}

	private String caseIdTotal;

	@Override
	public String toString() {
		return "CaseIdBean [caseIdTotal=" + caseIdTotal + ", caseTimeStamp=" + caseTimeStamp + ", statusOfQuery= "
				+ statusOfQuery + " UCI=" + UCI + ", defineQuery=" + defineQuery+ "]";
	}

	private LocalDateTime caseTimeStamp;
	private String statusOfQuery;
	private BigInteger accountNumber;
	private String UCI ;
	private String defineQuery;
	private BigInteger cardNumber;
	

	public BigInteger getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(BigInteger cardNumber) {
		this.cardNumber = cardNumber;
	}

	

	public String getDefineQuery() {
		return defineQuery;
	}

	public void setDefineQuery(String defineQuery) {
		this.defineQuery = defineQuery;
	}

	public BigInteger getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(BigInteger bigInteger) {
		this.accountNumber = bigInteger;
	}

	public String getUCI() {
		return UCI;
	}

	public void setUCI(String uCI) {
		UCI = uCI;
	}

	public String getCaseIdTotal() {
		return caseIdTotal;
	}

	public void setCaseIdTotal(String caseIdTotal) {
		this.caseIdTotal = caseIdTotal;
	}

	public LocalDateTime getCaseTimeStamp() {
		return caseTimeStamp;
	}

	public void setCaseTimeStamp(LocalDateTime timestamp) {
		this.caseTimeStamp = timestamp;
	}

	public String getStatusOfQuery() {
		return statusOfQuery;
	}

	public void setStatusOfQuery(String statusOfQuery) {
		this.statusOfQuery = statusOfQuery;
	}

	public void setAccountNumber(String string) {
		// TODO Auto-generated method stub
		
	}

	public void getCaseTimeStamp(LocalDateTime timestamp) {
		// TODO Auto-generated method stub
		
	}

	public void getCardNumber(BigInteger returnDebitCardNumber) {
		// TODO Auto-generated method stub
		
	}

	public void getDefineQuery(String string) {
		// TODO Auto-generated method stub
		
	}

}
