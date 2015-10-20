/**
 * 
 */
package inject;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.internal.sessions.AbstractSession;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.jpa.JpaHelper;
import org.eclipse.persistence.mappings.AttributeAccessor;
import org.eclipse.persistence.mappings.DirectToFieldMapping;
import org.eclipse.persistence.sessions.DatabaseSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sap.hackthon.Application;
import com.sap.hackthon.entity.GlobalSettings;
import com.sap.hackthon.entity.TestEntityB;
import com.sap.hackthon.services.EntityService;
import com.sap.hackthon.services.UDFAttributeAccessor;
import com.sap.hackthon.utils.GlobalConstants;

/**
 * @author I310717
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class TestMain {

	@Autowired
	private GlobalSettings settings;
	
	@Autowired
	private EntityService entityServcie;
	
	@PersistenceContext
	private EntityManager entityManager;
	
    @Test
    @Transactional
    @Rollback(false)
    public void test() {
    	settings.setVariable(GlobalConstants.TENANT, "tenant1");
    	
    	JpaEntityManager jpaEntityManager = JpaHelper.getEntityManager(entityManager);
		DatabaseSession dbSession = jpaEntityManager.getDatabaseSession();
		ClassDescriptor descriptor = dbSession.getDescriptor(TestEntityB.class);
		AttributeAccessor accessor = new UDFAttributeAccessor();
		accessor.setAttributeName("ABC");
		DirectToFieldMapping udfMapping = new DirectToFieldMapping();
		udfMapping.setFieldName("ABC_ID");
		udfMapping.setAttributeAccessor(accessor);
		descriptor.addMapping(udfMapping);
		udfMapping.initialize((AbstractSession)dbSession);
		descriptor.getFields().addAll(udfMapping.getFields());
		descriptor.getAllFields().addAll(udfMapping.getFields());
		
		AttributeAccessor accessor2 = new UDFAttributeAccessor();
		accessor2.setAttributeName("GGG");
		DirectToFieldMapping udfMapping2 = new DirectToFieldMapping();
		udfMapping2.setFieldName("GGG_STR");
		udfMapping2.setAttributeAccessor(accessor2);
		descriptor.addMapping(udfMapping2);
		udfMapping2.initialize((AbstractSession)dbSession);
		descriptor.getFields().addAll(udfMapping2.getFields());
		descriptor.getAllFields().addAll(udfMapping2.getFields());
		descriptor.getObjectBuilder().initialize((AbstractSession)dbSession);
		descriptor.initialize(descriptor.getQueryManager(), (AbstractSession)dbSession);
//    	((AbstractSession)dbSession).updateTablePerTenantDescriptors("multi-tenant.id", "tenant1");
		entityManager.setProperty("multi-tenant.id", "tenant1");
//    	TestEntityB b = new TestEntityB();
//    	b.setName("TestB");
//    	b.setObjectType("TestEntityB");
//    	b.setUserDefinedField("ABC", 1002);
//    	b.setUserDefinedField("GGG", "DGADE");
//    	entityManager.persist(b);
//    	entityManager.flush();
    	List<TestEntityB> res = entityManager.createQuery("SELECT b FROM TestEntityB b where b.ABC = :p1 and b.GGG = :p2").setParameter("p1", 1003).setParameter("p2", "DGADE").getResultList();
    	res.size();
    }
    
}
