/**
 * 
 */
package inject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.hackthon.Application;
import com.sap.hackthon.entity.TestEntityB;
import com.sap.hackthon.framework.beans.UserSettings;
import com.sap.hackthon.framework.utils.GlobalConstants;
import com.sap.hackthon.services.biz.EntityService;

/**
 * @author I310717
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class TestMain {

	@Autowired
	private UserSettings settings;
	
	@Autowired
	private EntityService entityServcie;
	
	@PersistenceContext
	private EntityManager entityManager;
	
//    @Test
    @Transactional
    @Rollback(false)
    public void test() {
    	settings.setVariable(GlobalConstants.TENANT, "tenant1");
    	
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("p1", 1002);
    	params.put("p2", "DGADE");
    	List<TestEntityB> res = entityServcie.find("SELECT b FROM TestEntityB b where b.ABC = :p1 and b.GGG = :p2", params, "TestEntityB");
    	res.size();
    }
    
//    @Test
    public void test2() throws JsonParseException, JsonMappingException, IOException{
    	String json = "{\"c\":[{\"x\":\"xcdc\"}]}";
    	ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		Object res = objectMapper.readValue(json, TestA.class);
		System.out.println(res);
    }
    
//    @Test
    public void test3() throws JsonParseException, JsonMappingException, IOException{
    	TestC c = new TestC();
    	c.setX("gagadddd");
    	List<TestC> cList = new ArrayList<TestC>();
    	cList.add(c);
    	TestA a = new TestA();
    	a.setC(cList);
    	ObjectMapper objectMapper = new ObjectMapper();
		String res2 = objectMapper.writeValueAsString(a);
		System.out.println(res2);
    }
    
//    @Test
    public void test4() throws JsonParseException, JsonMappingException, IOException{
    	String json = "[{\"c\":[{\"x\":\"xcdc\"}]}]";
    	ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		Object res = objectMapper.readValue(json, Collection.class);
		System.out.println(res);
    }
    
    @Test
    public void test5(){
    	TestC[] cs = new TestC[2];
    	TestC c1 = new TestC();
    	c1.setX("c1");
    	TestC c2 = new TestC();
    	c2.setX("c2");
    	cs[0] = c1;
    	cs[1] = c2;
    	Map<String, TestC> m = Arrays.stream(cs).collect(Collectors.toMap(TestC::getX, Function.identity()));
    	System.out.println(m.size());
    }
    
}
