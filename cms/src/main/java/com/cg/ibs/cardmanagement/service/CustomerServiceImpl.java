package com.cg.ibs.cardmanagement.service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.cg.ibs.cardmanagement.bean.CaseIdBean;
import com.cg.ibs.cardmanagement.bean.CreditCardBean;
import com.cg.ibs.cardmanagement.bean.CreditCardTransaction;
import com.cg.ibs.cardmanagement.bean.CustomerBean;
import com.cg.ibs.cardmanagement.bean.DebitCardBean;
import com.cg.ibs.cardmanagement.bean.DebitCardTransaction;
import com.cg.ibs.cardmanagement.dao.BankDao;
import com.cg.ibs.cardmanagement.dao.CustomerDao;
import com.cg.ibs.cardmanagement.exceptionhandling.IBSException;
import com.cg.ibs.cardmanagement.dao.CardManagementDaoImpl;

public class CustomerServiceImpl implements CustomerService {

	CustomerDao customerDao;

	public CustomerServiceImpl() {
		customerDao = new CardManagementDaoImpl();

	}

	CustomerBean customObj = new CustomerBean();
	String UCI = "7894561239632587";

	String caseIdGenOne = " ";
	String caseIdTotal = " ";
	static int caseIdGenTwo = 0;
	LocalDateTime timestamp;
	LocalDateTime fromDate;
	LocalDateTime toDate;

	String addToQueryTable(String caseIdGenOne) {
		caseIdTotal = caseIdGenOne + caseIdGenTwo;
		caseIdGenTwo++;
		return caseIdTotal;
	}

	@Override
	public boolean applyNewDebitCard(BigInteger accountNumber) {
		boolean status = false;
		boolean check = customerDao.verifyAccountNumber(accountNumber);

		if (check) {
			CaseIdBean caseIdObj = new CaseIdBean();
			caseIdGenOne = "ANDC";
			timestamp = LocalDateTime.now();

			caseIdObj.setCaseIdTotal(addToQueryTable(caseIdGenOne));
			caseIdObj.setCaseTimeStamp(timestamp);
			caseIdObj.setStatusOfQuery("Pending");
			caseIdObj.setUCI(UCI);
			caseIdObj.setDefineQuery("Account number:"+accountNumber);
			customerDao.newDebitCard(caseIdObj, accountNumber);
			status = true;
		}

		else {
			status = false;
		}
		return status;

	}

	@Override
	public void applyNewCreditCard() {
		CaseIdBean caseIdObj = new CaseIdBean();
		caseIdGenOne = "ANCC";
		timestamp = LocalDateTime.now();

		caseIdObj.setCaseIdTotal(addToQueryTable(caseIdGenOne));
		caseIdObj.setCaseTimeStamp(timestamp);
		caseIdObj.setStatusOfQuery("Pending");
		caseIdObj.setUCI(UCI);
		caseIdObj.setDefineQuery("NA");
		customerDao.newCreditCard(caseIdObj);

	}

	@Override
	public boolean requestDebitCardLost(BigInteger debitCardNumber) throws IBSException {
		boolean status = false;
		boolean check = customerDao.verifyDebitCardNumber(debitCardNumber);

		if (check) {
			CaseIdBean caseIdObj = new CaseIdBean();

			caseIdGenOne = "RDCL";

			timestamp = LocalDateTime.now();

			caseIdObj.setCaseIdTotal(addToQueryTable(caseIdGenOne));
			caseIdObj.setCaseTimeStamp(timestamp);
			caseIdObj.setStatusOfQuery("Pending");
			caseIdObj.setUCI(UCI);
			caseIdObj.setDefineQuery("Debit card Number"+debitCardNumber);
			customerDao.requestDebitCardLost(caseIdObj, debitCardNumber);
			status = true;
			return status;
		} else {
			throw new IBSException("Debit card not found");

		}

	}

	@Override
	public boolean requestCreditCardLost(BigInteger creditCardNumber) throws IBSException {
		boolean status = false;
		boolean check = customerDao.verifyCreditCardNumber(creditCardNumber);
		if (check) {
			CaseIdBean caseIdObj = new CaseIdBean();

			caseIdGenOne = "RCCL";

			timestamp = LocalDateTime.now();

			caseIdObj.setCaseIdTotal(addToQueryTable(caseIdGenOne));
			caseIdObj.setCaseTimeStamp(timestamp);
			caseIdObj.setStatusOfQuery("Pending");
			caseIdObj.setUCI(UCI);
			caseIdObj.setDefineQuery("Credit card number"+creditCardNumber);
			customerDao.requestCreditCardLost(caseIdObj, creditCardNumber);
			status = true;
			return status;
		} else {

			throw new IBSException("Credit card not found");
		}

	}

	public boolean raiseDebitMismatchTicket(String transactionId) throws IBSException {
		boolean status = false;
		boolean transactionResult = customerDao.verifyDebitTransactionId(transactionId);
		if (transactionResult) {
			CaseIdBean caseIdObj = new CaseIdBean();
			caseIdGenOne = "RDMT";

			timestamp = LocalDateTime.now();

			caseIdObj.setCaseIdTotal(addToQueryTable(caseIdGenOne));
			caseIdObj.setCaseTimeStamp(timestamp);
			caseIdObj.setStatusOfQuery("Pending");
			caseIdObj.setUCI(UCI);
			caseIdObj.setDefineQuery("Transaction ID"+transactionId);

			customerDao.raiseDebitMismatchTicket(caseIdObj, transactionId);
			status = true;
			return status;
		} else {
			
				throw new IBSException("Transaction ID not found");

		}
	

	}

	public List<DebitCardBean> viewAllDebitCards() {

		return customerDao.viewAllDebitCards();
	}

	@Override
	public List<CreditCardBean> viewAllCreditCards() {

		return customerDao.viewAllCreditCards();

	}

	public String verifyDebitcardType(BigInteger debitCardNumber) {
		boolean check = customerDao.verifyDebitCardNumber(debitCardNumber);
		if (check) {
			String type = customerDao.getdebitCardType(debitCardNumber);
			return type;
		} else {

			throw new NullPointerException("Debit card not found");
		}
	}

	@Override
	public String requestDebitCardUpgrade(BigInteger debitCardNumber, int myChoice) {

		CaseIdBean caseIdObj = new CaseIdBean();

		caseIdGenOne = "RDCU";
		timestamp = LocalDateTime.now();

		caseIdObj.setCaseIdTotal(addToQueryTable(caseIdGenOne));
		caseIdObj.setCaseTimeStamp(timestamp);
		caseIdObj.setStatusOfQuery("Pending");
		caseIdObj.setUCI(UCI);
		if (myChoice == 1) {
			caseIdObj.setDefineQuery("Upgrade to Gold for card Number :"+debitCardNumber);

			customerDao.requestDebitCardUpgrade(caseIdObj, debitCardNumber);
			return (" Successful Application for Upgradation to GOLD  ");
		} else if (myChoice == 2) {
			caseIdObj.setDefineQuery("Upgrade to Platinum for card Number :"+debitCardNumber);
			customerDao.requestDebitCardUpgrade(caseIdObj, debitCardNumber);
			return (" Successful Application for Upgradation to PLATINUM  ");
		} else {
			return ("Choose a valid option");
		}

	}

	public boolean verifyDebitCardNumber(BigInteger debitCardNumber) throws IBSException {

		boolean check = customerDao.verifyDebitCardNumber(debitCardNumber);
		if (!check)
			throw new IBSException(" Debit Card Number does not exist");
		return (check);
	}

	public boolean verifyDebitPin(int pin, BigInteger debitCardNumber) {
		if (pin == customerDao.getDebitCardPin(debitCardNumber)) {

			return true;
		} else {
			return false;
		}
	}

	public String resetDebitPin(BigInteger debitCardNumber, int newPin) {

		customerDao.setNewDebitPin(debitCardNumber, newPin);
		return ("PIN UPDATED SUCCESSFULLY!");
	}

	public boolean verifyCreditCardNumber(BigInteger creditCardNumber) throws IBSException {

		boolean check1 = customerDao.verifyCreditCardNumber(creditCardNumber);
		if (!check1)
			throw new IBSException(" Credit Card Number does not exist");
		return (check1);
	}

	public boolean verifyCreditPin(int pin, BigInteger creditCardNumber) {

		if (pin == customerDao.getCreditCardPin(creditCardNumber)) {

			return true;
		} else {
			return false;
		}
	}

	public String resetCreditPin(BigInteger creditCardNumber, int newPin) {

		customerDao.setNewCreditPin(creditCardNumber, newPin);
		return ("PIN UPDATED SUCCESSFULLY!");
	}

	@Override
	public String requestCreditCardUpgrade(BigInteger creditCardNumber, int myChoice) {

		caseIdGenOne = "RCCU";
		timestamp = LocalDateTime.now();
		CaseIdBean caseIdObj = new CaseIdBean();
		caseIdObj.setCaseIdTotal(addToQueryTable(caseIdGenOne));
		caseIdObj.setCaseTimeStamp(timestamp);
		caseIdObj.setStatusOfQuery("Pending");
		caseIdObj.setUCI(UCI);
		if (myChoice == 1) {
			caseIdObj.setDefineQuery("Upgrade to Gold for credit card number:"+creditCardNumber);

			customerDao.requestCreditCardUpgrade(caseIdObj, creditCardNumber);
			return (" Successful Application for Upgradation to GOLD  ");
		} else if (myChoice == 2) {
			caseIdObj.setDefineQuery("Upgrade to Platinum for credit card number:"+creditCardNumber);
			customerDao.requestDebitCardUpgrade(caseIdObj, creditCardNumber);
			return (" Successful Application for Upgradation to PLATINUM  ");
		} else {
			return ("Choose a valid option");
		}

	}

	@Override
	public String verifyCreditcardType(BigInteger creditCardNumber) {
		boolean check = customerDao.verifyCreditCardNumber(creditCardNumber);
		if (check) {
			String type = customerDao.getcreditCardType(creditCardNumber);
			return type;
		} else {

			throw new NullPointerException("Credit Card not found");
		}

	}

	@Override
	public boolean raiseCreditMismatchTicket(String transactionId) throws IBSException {
		boolean status = false;
		boolean transactionResult = customerDao.verifyCreditTransactionId(transactionId);
		if (transactionResult) {
			CaseIdBean caseIdObj = new CaseIdBean();
			caseIdGenOne = "RCMT";

			timestamp = LocalDateTime.now();

			caseIdObj.setCaseIdTotal(addToQueryTable(caseIdGenOne));
			caseIdObj.setCaseTimeStamp(timestamp);
			caseIdObj.setStatusOfQuery("Pending");
			caseIdObj.setUCI(UCI);
			caseIdObj.setDefineQuery("Transaction ID:"+transactionId);

			customerDao.raiseCreditMismatchTicket(caseIdObj, transactionId);
			status = true;
			return status;
		} else {
		throw new IBSException("TRANSACTION ID NOT FOUND");

		}
		

	}

	public List<DebitCardTransaction> getDebitTrns(int dys, BigInteger debitCardNumber) throws IBSException {
		boolean check = customerDao.verifyDebitCardNumber(debitCardNumber);
		if (check) {
			if (dys >= 1) {
				List<DebitCardTransaction> debitCardBeanTrns = customerDao.getDebitTrans(dys, debitCardNumber);
				if (debitCardBeanTrns.size() == 0)
					throw new IBSException("NO TRANSACTIONS");
				return customerDao.getDebitTrans(dys, debitCardNumber);
			} else {
				return null;
			}

		} else {
			return null;
		}
	}

	@Override
	public List<CreditCardTransaction> getCreditTrans(int days, BigInteger creditCardNumber) throws IBSException {

		boolean check = customerDao.verifyCreditCardNumber(creditCardNumber);
		if (check) {
			if (days >= 1) {

				List<CreditCardTransaction> creditCardBeanTrns = customerDao.getCreditTrans(days, creditCardNumber);
				if (creditCardBeanTrns.size() == 0)
					throw new IBSException("NO TRANSACTIONS");
				return customerDao.getCreditTrans(days, creditCardNumber);
			} else {
				return null;
			}

		} else {
			return null;
		}
	}

	@Override
	public int getLength(int pin) throws IBSException {
		int count = 0;
		while (pin != 0) {
			pin = pin / 10;
			++count;
		}
		if (count != 4)
			throw new IBSException("Incorrect Length of pin ");
		return count;
	}

}