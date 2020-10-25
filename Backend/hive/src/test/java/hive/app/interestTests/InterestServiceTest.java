package hive.app.interestTests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import hive.app.interest.Interest;
import hive.app.interest.InterestRepository;
import hive.app.interest.InterestService;

@RunWith(SpringRunner.class)
public class InterestServiceTest {
	
	@TestConfiguration
	static class test {
		@Bean
		public InterestService iService() {
			return new InterestService();
		}
		
		@Bean
		public InterestRepository getInterestRepository() { 
			return mock(InterestRepository.class);
		}
	}
	
	@Autowired 
	private InterestService is;
	
	@Autowired
	private InterestRepository ir;
	
	private Interest testInterest;
	
	@Before
	public void beforeTests() {
		when(ir.save((Interest)any(Interest.class))).thenAnswer(new Answer<Interest>() {
		    public Interest answer(InvocationOnMock invocation) throws Throwable {
		    	Object[] args = invocation.getArguments();
		    	Interest interest = (Interest) args[0];
		    	return interest;
		    }
		});	
	}
	
	@Test
	public void testCreate() {
		Map<String, String> body = new HashMap<String, String>();
		body.put("interestId", "0");
		body.put("interestText", "SampleInterest");
		
		is.create(body);
		verify(ir, times(1)).save((Interest) any(Interest.class));
		
		resetMock();
	}
	
	@Test
	public void testUpdate() {
		testInterest = new Interest("SampleInterest");
		when(ir.findOne((Integer) any(Integer.class))).thenReturn(this.testInterest);
		
		Map<String, String> body = new HashMap<String, String>();
		
		body.put("interestId", "0");
		body.put("interestText", "UpdatedInterest");
		
		Interest updatedInterest = is.update(body);
		assertEquals(true, updatedInterest.getInterestText().equals(body.get("interestText")));
		
		resetMock();
	}
	
	public void resetMock() {
		reset(ir);
	}
}
