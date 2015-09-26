package com.sap.hackthon.services;

public interface PropertyCountingService {
	public void addReferenceCounting(String objectName, String fieldName, Long counting);
	public void minusReferenceCounting(String objectName, String fieldName, Long counting);
}
