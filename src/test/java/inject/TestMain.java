/**
 * 
 */
package inject;

import java.util.List;

import javax.persistence.EntityManager;

import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.internal.sessions.AbstractSession;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.jpa.JpaHelper;
import org.eclipse.persistence.mappings.AttributeAccessor;
import org.eclipse.persistence.mappings.DirectToFieldMapping;
import org.eclipse.persistence.mappings.querykeys.DirectQueryKey;
import org.eclipse.persistence.sessions.DatabaseSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.sap.hackthon.Application;
import com.sap.hackthon.entity.TestEntityA;
import com.sap.hackthon.entity.TestEntityB;
import com.sap.hackthon.orm.UDFAttributeAccessor;

/**
 * @author I310717
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class TestMain {

    @Autowired
    private EntityManager em;

    @Test
    @SuppressWarnings("unchecked")
    public void test() {
    	
    	JpaEntityManager jpaEm = JpaHelper.getEntityManager(em);
    	DatabaseSession dbSession = jpaEm.getDatabaseSession();
    	dbSession.setProperty("multi-tenant.id", "tenant1");
    	ClassDescriptor descriptor = dbSession.getDescriptor(TestEntityB.class);
    	AttributeAccessor accessor = new UDFAttributeAccessor();
    	accessor.setAttributeName("abc");
    	DirectToFieldMapping udfMapping = new DirectToFieldMapping();
    	udfMapping.setFieldName("A_ID");
    	udfMapping.setAttributeAccessor(accessor);
    	descriptor.addMapping(udfMapping);
    	udfMapping.initialize((AbstractSession)dbSession);
    	descriptor.getObjectBuilder().initialize((AbstractSession)dbSession);
    	
        TestEntityB b1 = new TestEntityB();
        b1.setObjectType("testB");
        b1.setUserDefinedField("abc", 500L);
        b1.setName("B1");
        em.persist(b1);
		List<Object[]> res = em.createQuery("select b from TestEntityB b where b.abc = :u").setParameter("u", 500L).getResultList();
        res.size();
        
    }
    
}
