package com.sap.hackthon.framework.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "T_VERSION_OBSERVER")
public class VersionObserver extends BasicEntityAdapter{

	@Id
    @GeneratedValue(generator = "VersionObserverSeq")
    @SequenceGenerator(name = "VersionObserverSeq", sequenceName = "VERSION_OBSERVER_SEQ", allocationSize = 1)
	private Long id;
	
	@Column(name = "OBSERVER_TYPE", unique = true)
	private String observerType;
	
	@Column(name = "VERSION")
	private String version;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getObserverType() {
		return observerType;
	}

	public void setObserverType(String observerType) {
		this.observerType = observerType;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
}
