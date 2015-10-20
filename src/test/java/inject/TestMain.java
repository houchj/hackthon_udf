/**
 * 
 */
package inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.sap.hackthon.Application;
import com.sap.hackthon.entity.GlobalSettings;
import com.sap.hackthon.entity.PropertyMeta;
import com.sap.hackthon.enumeration.UDFTypeEnum;
import com.sap.hackthon.services.PropertyMetaService;
import com.sap.hackthon.utils.GlobalConstants;

/**
 * @author I310717
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class TestMain {

	@Autowired
	private GlobalSettings settings;
	
	@Autowired
	private PropertyMetaService metaService;
	
    @Test
    public void test() {
    	settings.setVariable(GlobalConstants.TENANT, "tenant1");
    	PropertyMeta meta = new PropertyMeta();
    	meta.setDisplayName("ABC");
    	meta.setInternalName("ABC_ID");
    	meta.setObjectType("TestEntityB");
    	meta.setParamIndex(1);
    	meta.setType(UDFTypeEnum.DECIMAL);
    	metaService.create(meta);
    }
    
}
