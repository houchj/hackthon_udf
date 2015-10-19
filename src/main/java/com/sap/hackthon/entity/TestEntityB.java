/**
 * 
 */
package com.sap.hackthon.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author I310717
 *
 */
@Entity
@Table(name = "TEST_ENTITY_B")
public class TestEntityB extends UserDefineEntity{

    @Id
    @GeneratedValue(generator = "TestEntityBSeq")
    @SequenceGenerator(name = "TestEntityBSeq", sequenceName = "TEST_ENTITY_B_SEQ", allocationSize = 1)
    private Long id;

    private String name;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "a_id")
    private TestEntityA anEntity;

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
     * @return the anEntity
     */
    public TestEntityA getAnEntity() {
        return anEntity;
    }

    /**
     * @param anEntity
     *            the anEntity to set
     */
    public void setAnEntity(TestEntityA anEntity) {
        this.anEntity = anEntity;
    }

}
