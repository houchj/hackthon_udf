package com.sap.hackthon.services.meta;

public interface PropertyCountingService {
	public void addReferenceCounting(String objectType, String fieldName, Long counting);
	public void minusReferenceCounting(String objectType, String fieldName, Long counting);
}
