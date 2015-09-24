package com.sap.hackthon.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sap.hackthon.utils.ObjectTypeEnum;

@Entity
@Table(name="T_PROPERTYMETA2")
public class PropertyMeta {

	    @Id
//	    @GeneratedValue(strategy = GenerationType.AUTO)
	    @GeneratedValue(generator = "PropertyMetaSeq")
	    @SequenceGenerator(name = "PropertyMetaSeq", sequenceName = "PROPERTYMETA_SEQ", allocationSize = 100)
	    @Column
	    private Long id;

	    @Column
	    private String tenantId;

	    @Column
	    private String objectName;

	    @Column
	    @Enumerated(EnumType.STRING)
	    private ObjectTypeEnum type;

	    @Column
	    private String internalName;

	    @Column
	    private String displayName;

	    @Column
	    private Integer paramIndex;

	    @Column
	    private boolean systemField;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getTenantId() {
			return tenantId;
		}

		public void setTenantId(String tenantId) {
			this.tenantId = tenantId;
		}

		public String getObjectName() {
			return objectName;
		}

		public void setObjectName(String objectName) {
			this.objectName = objectName;
		}

		public ObjectTypeEnum getType() {
			return type;
		}

		public void setType(ObjectTypeEnum type) {
			this.type = type;
		}

		public String getInternalName() {
			return internalName;
		}

		public void setInternalName(String internalName) {
			this.internalName = internalName;
		}

		public String getDisplayName() {
			return displayName;
		}

		public void setDisplayName(String displayName) {
			this.displayName = displayName;
		}

		public Integer getParamIndex() {
			return paramIndex;
		}

		public void setParamIndex(Integer paramIndex) {
			this.paramIndex = paramIndex;
		}

		public boolean isSystemField() {
			return systemField;
		}

		public void setSystemField(boolean systemField) {
			this.systemField = systemField;
		}

	}
