package bg.sofia.uni.fmi.mjt.cache;

import java.time.LocalDateTime;
import java.time.Month;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MemCacheTest{

	private MemCache<String, Integer> cache = new MemCache<String, Integer>();

	@Before
	public void setUp(){
		cache = new MemCache<String, Integer>();
	}
	
	@Test
	public void GetExisting() throws CapacityExceededException {

		LocalDateTime date = LocalDateTime.of(2018, Month.NOVEMBER, 20, 12, 30);

		this.cache.set("tanya", 20, date);

		Integer integ = 20;

		Assert.assertEquals(cache.get("tanya"), integ);

	}
	
	@Test
	public void GetExpired() throws CapacityExceededException {

		LocalDateTime date = LocalDateTime.of(2017, Month.NOVEMBER, 10, 11, 30);

		this.cache.set("eho", 10, date);

		Assert.assertEquals(cache.get("eho"), null);

	}

	@Test
	public void GetNonExisting() throws CapacityExceededException {

		Assert.assertEquals(cache.get("bazzz"), null);

	}
	
	@Test
	public void GetExpirationExisting() throws CapacityExceededException {

		LocalDateTime date = LocalDateTime.of(2018, Month.NOVEMBER, 20, 12, 30);

		this.cache.set("tanya", 20, date);

		Integer integ = 20;

		Assert.assertEquals(cache.getExpiration("tanya"), date);

	}
	
	@Test
	public void GetExpirationExpired() throws CapacityExceededException {

		LocalDateTime date = LocalDateTime.of(2017, Month.NOVEMBER, 10, 11, 30);

		this.cache.set("eho", 10, date);

		Assert.assertEquals(cache.getExpiration("eho"), null);

	}
	
	@Test
	public void GetExpirationNonExisting(){

		Assert.assertEquals(cache.getExpiration("bazzz"), null);

	}
	
	@Test
	public void RemoveExisting() throws CapacityExceededException {

		LocalDateTime date = LocalDateTime.of(2018, Month.NOVEMBER, 20, 12, 30);

		this.cache.set("tanya", 20, date);

		Integer integ = 20;

		Assert.assertTrue(cache.remove("tanya"));

	}
	
	@Test
	public void RemoveExpired() throws CapacityExceededException {

		LocalDateTime date = LocalDateTime.of(2017, Month.NOVEMBER, 10, 11, 30);

		this.cache.set("eho", 10, date);

		Assert.assertFalse(cache.remove("eho"));

	}
	
	@Test
	public void RemoveNonExisting(){

		Assert.assertFalse(cache.remove("bazzz"));

	}
	
	@Test
	public void SizeOfExisting() throws CapacityExceededException {

		LocalDateTime date1 = LocalDateTime.of(2018, Month.NOVEMBER, 20, 12, 30);
		LocalDateTime date2 = LocalDateTime.of(2019, Month.NOVEMBER, 20, 12, 30);
		LocalDateTime date3 = LocalDateTime.of(2020, Month.NOVEMBER, 20, 12, 30);

		this.cache.set("tanya", 20, date1);
		this.cache.set("radost", 30, date2);
		this.cache.set("yavor", 40, date3);

		Assert.assertEquals(cache.size(), 3);

	}
	
	@Test
	public void SizeOfExistingAndExpired() throws CapacityExceededException {

		LocalDateTime date1 = LocalDateTime.of(2018, Month.NOVEMBER, 20, 12, 30);
		LocalDateTime date2 = LocalDateTime.of(2019, Month.NOVEMBER, 20, 12, 30);
		LocalDateTime date3 = LocalDateTime.of(2015, Month.NOVEMBER, 20, 12, 30);

		this.cache.set("tanya", 20, date1);
		this.cache.set("radost", 30, date2);
		this.cache.set("yavor", 40, date3);

		Assert.assertEquals(cache.size(), 3);

	}
	
	@Test
	public void SizeOfExpiredOnly() throws CapacityExceededException {

		LocalDateTime date1 = LocalDateTime.of(2013, Month.NOVEMBER, 20, 12, 30);
		LocalDateTime date2 = LocalDateTime.of(2014, Month.NOVEMBER, 20, 12, 30);
		LocalDateTime date3 = LocalDateTime.of(2015, Month.NOVEMBER, 20, 12, 30);

		this.cache.set("tanya", 20, date1);
		this.cache.set("radost", 30, date2);
		this.cache.set("yavor", 40, date3);

		Assert.assertEquals(cache.size(), 3);

	}
	
	@Test
	public void SizeOfEmpty() throws CapacityExceededException {

		Assert.assertEquals(cache.size(), 0);

	}
	
	@Test
	public void ClearTest() throws CapacityExceededException {

		LocalDateTime date1 = LocalDateTime.of(2018, Month.NOVEMBER, 20, 12, 30);
		LocalDateTime date2 = LocalDateTime.of(2019, Month.NOVEMBER, 20, 12, 30);
		LocalDateTime date3 = LocalDateTime.of(2020, Month.NOVEMBER, 20, 12, 30);

		this.cache.set("tanya", 20, date1);
		this.cache.set("radost", 30, date2);
		this.cache.set("yavor", 40, date3);
		
		this.cache.clear();

		Assert.assertEquals(cache.size(), 0);

	}
	
	@Test
	public void GetHitRateOfExisting() throws CapacityExceededException {

		LocalDateTime date1 = LocalDateTime.of(2018, Month.NOVEMBER, 20, 12, 30);
		LocalDateTime date2 = LocalDateTime.of(2019, Month.NOVEMBER, 20, 12, 30);
		LocalDateTime date3 = LocalDateTime.of(2020, Month.NOVEMBER, 20, 12, 30);

		this.cache.set("tanya", 20, date1);
		this.cache.set("radost", 30, date2);
		this.cache.set("yavor", 40, date3);

		Assert.assertEquals(cache.getHitRate(), 1, 0.000001);

	}
	
	@Test
	public void GetHitRateOfExistingAndExpired() throws CapacityExceededException {

		LocalDateTime date1 = LocalDateTime.of(2018, Month.NOVEMBER, 20, 12, 30);
		LocalDateTime date2 = LocalDateTime.of(2019, Month.NOVEMBER, 20, 12, 30);
		LocalDateTime date3 = LocalDateTime.of(2015, Month.NOVEMBER, 20, 12, 30);
		LocalDateTime date4 = LocalDateTime.of(2014, Month.NOVEMBER, 20, 12, 30);

		this.cache.set("tanya", 20, date1);
		this.cache.set("radost", 30, date2);
		this.cache.set("yavor", 40, date3);
		this.cache.set("maggie", 50, date4);

		Assert.assertEquals(cache.getHitRate(), 0.50, 0.000001);
		
	}
	
	@Test
	public void GetHitRateOfExpiredOnly() throws CapacityExceededException {

		LocalDateTime date1 = LocalDateTime.of(2013, Month.NOVEMBER, 20, 12, 30);
		LocalDateTime date2 = LocalDateTime.of(2014, Month.NOVEMBER, 20, 12, 30);
		LocalDateTime date3 = LocalDateTime.of(2015, Month.NOVEMBER, 20, 12, 30);

		this.cache.set("tanya", 20, date1);
		this.cache.set("radost", 30, date2);
		this.cache.set("yavor", 40, date3);

		Assert.assertEquals(cache.getHitRate(), 0, 0.000001);

	}

}
