package models;

import java.sql.Date;

public class Receipt {
	
	private Integer receiptId;
	private Integer orderId;
	private Double receiptOrderAmount;
	private Date receiptPaymentDate;
	private String receiptType;
	
	// parameter yang belum digunakan -> Double receiptOrderAmount, Date receiptPaymentDate,
	// String receiptType
	
	public Receipt(Integer orderId, Double receiptOrderAmount, String receiptType) {
		super();
		this.receiptId = receiptId;
		this.orderId = orderId;
		this.receiptOrderAmount = receiptOrderAmount;
		this.receiptPaymentDate = receiptPaymentDate;
		this.receiptType = receiptType;
	}

	public Integer getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(Integer receiptId) {
		this.receiptId = receiptId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Double getReceiptOrderAmount() {
		return receiptOrderAmount;
	}

	public void setReceiptOrderAmount(Double receiptOrderAmount) {
		this.receiptOrderAmount = receiptOrderAmount;
	}

	public Date getReceiptPaymentDate() {
		return receiptPaymentDate;
	}

	public void setReceiptPaymentDate(Date receiptPaymentDate) {
		this.receiptPaymentDate = receiptPaymentDate;
	}

	public String getReceiptType() {
		return receiptType;
	}

	public void setReceiptType(String receiptType) {
		this.receiptType = receiptType;
	}
	
}