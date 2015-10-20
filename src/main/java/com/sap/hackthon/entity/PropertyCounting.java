package com.sap.hackthon.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "T_PROPERTY_COUNTING")
public class PropertyCounting extends BasicEntityAdapter{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="PropertyCountingSeq")
    @SequenceGenerator(name = "PropertyCountingSeq", sequenceName = "T_PROPERTY_COUNTING_SEQ", allocationSize = 100)
    @Column
    private Long id;

    @Column
    private String fieldName;
    
    @Column
    private Long counting;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Long getCounting() {
		return counting;
	}

	public void setCounting(Long counting) {
		this.counting = counting;
	}
}
