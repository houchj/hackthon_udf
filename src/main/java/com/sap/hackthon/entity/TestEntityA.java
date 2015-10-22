/**
 * 
 */
package com.sap.hackthon.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sap.hackthon.framework.beans.UserDefineEntity;

/**
 * @author I310717
 *
 */
@Entity
@Table(name = "TEST_ENTITY_A")
public class TestEntityA extends UserDefineEntity{

    @Id
    @GeneratedValue(generator = "TestEntityASeq")
    @SequenceGenerator(name = "TestEntityASeq", sequenceName = "TEST_ENTITY_A_SEQ", allocationSize = 1)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "anEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private final List<TestEntityB> bEntities = new ArrayList<TestEntityB>();

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the bEntities
     */
    public List<TestEntityB> getbEntities() {
        return bEntities;
    }

}
