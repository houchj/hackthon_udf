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

import com.sap.hackthon.enumeration.UDFTypeEnum;

@Entity
@Table(name = "T_PROPERTY_META")
public class PropertyMeta extends BasicEntityAdapter{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="PropertyMetaSeq")
    @SequenceGenerator(name = "PropertyMetaSeq", sequenceName = "T_PROPERTY_META_SEQ", allocationSize = 1)
    @Column
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private UDFTypeEnum type;

    @Column(name = "INTERNAL_NAME")
    private String internalName;

    @Column(name = "DISPLAY_NAME")
    private String displayName;

    @Column(name = "PARAM_INDEX")
    private Integer paramIndex;

    @Column(name = "SYSTEM_FIELD")
    private boolean systemField;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UDFTypeEnum getType() {
        return type;
    }

    public void setType(UDFTypeEnum type) {
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
