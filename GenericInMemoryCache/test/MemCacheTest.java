package bg.sofia.uni.fmi.mjt.cache;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MemCacheTest {

	private MemCache<String, Integer> cache = new MemCache<String, Integer>();

	@Before
	public void setUp() {
		cache = new MemCache<String, Integer>();
	}

	@Test
	public void GetExisting() throws CapacityExceededException {

		LocalDateTime date = LocalDateTime.of(2018, Month.NOVEMBER, 20, 12, 30);

		this.cache.set("tanya", 20, date);

		Integer integ = 20;

		Assert.assertEquals(integ, cache.get("tanya"));

	}

	@Test
	public void GetExpired() throws CapacityExceededException {

		cache = new MemCache<String, Integer>(1000000000);

		LocalDateTime date = LocalDateTime.of(2017, Month.NOVEMBER, 10, 11, 30);

		this.cache.set("eho", 10, date);

		Assert.assertEquals(null, cache.get("eho"));

	}

	@Test
	public void GetNonExisting() throws CapacityExceededException {

		Assert.assertEquals(null, cache.get("bazzz"));

	}

	@Test
	public void GetExpirationExisting() throws CapacityExceededException {

		LocalDateTime date = LocalDateTime.of(2018, Month.NOVEMBER, 20, 12, 30);

		this.cache.set("tanya", 20, date);

		Integer integ = 20;

		Assert.assertEquals(date, cache.getExpiration("tanya"));

	}

	@Test
	public void GetExpirationNonExisting() {

		Assert.assertEquals(null, cache.getExpiration("bazzz"));

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

		Assert.assertTrue(cache.remove("eho"));

	}

	@Test
	public void RemoveNonExisting() {

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

		Assert.assertEquals(3, cache.size());

	}

	@Test
	public void SizeOfExistingAndExpired() throws CapacityExceededException {

		LocalDateTime date1 = LocalDateTime.of(2018, Month.NOVEMBER, 20, 12, 30);
		LocalDateTime date2 = LocalDateTime.of(2019, Month.NOVEMBER, 20, 12, 30);
		LocalDateTime date3 = LocalDateTime.of(2015, Month.NOVEMBER, 20, 12, 30);

		this.cache.set("tanya", 20, date1);
		this.cache.set("radost", 30, date2);
		this.cache.set("yavor", 40, date3);

		Assert.assertEquals(3, cache.size());

	}

	@Test(expected = NullPointerException.class)
	public void SetNullPointers() throws CapacityExceededException {

		this.cache.set(null, null, null);
		Assert.assertEquals(0, cache.size());

	}

	@Test
	public void SizeOfExpiredOnly() throws CapacityExceededException {

		LocalDateTime date1 = LocalDateTime.of(2013, Month.NOVEMBER, 20, 12, 30);
		LocalDateTime date2 = LocalDateTime.of(2014, Month.NOVEMBER, 20, 12, 30);
		LocalDateTime date3 = LocalDateTime.of(2015, Month.NOVEMBER, 20, 12, 30);

		this.cache.set("tanya", 20, date1);
		this.cache.set("radost", 30, date2);
		this.cache.set("yavor", 40, date3);

		Assert.assertEquals(3, cache.size());

	}

	@Test(expected = CapacityExceededException.class)
	public void FullCache() throws CapacityExceededException {

		cache = new MemCache<String, Integer>(3);

		LocalDateTime date1 = LocalDateTime.of(2020, Month.NOVEMBER, 20, 12, 30);
		LocalDateTime date2 = LocalDateTime.of(2021, Month.NOVEMBER, 20, 12, 30);
		LocalDateTime date3 = LocalDateTime.of(2022, Month.NOVEMBER, 20, 12, 30);

		this.cache.set("tanya", 20, date1);
		this.cache.set("radost", 30, date2);
		this.cache.set("yavor", 40, date3);

		this.cache.set("maggie", 50, date1);
	}

	@Test
	public void SizeOfEmpty() throws CapacityExceededException {

		Assert.assertEquals(0, cache.size());

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

		Assert.assertEquals(0, cache.size());

	}

	@Test
	public void GetHitRateOfExisting() throws CapacityExceededException {

		LocalDateTime date1 = LocalDateTime.of(2018, Month.NOVEMBER, 20, 12, 30);
		LocalDateTime date2 = LocalDateTime.of(2019, Month.NOVEMBER, 20, 12, 30);
		LocalDateTime date3 = LocalDateTime.of(2020, Month.NOVEMBER, 20, 12, 30);

		this.cache.set("tanya", 20, date1);
		this.cache.set("radost", 30, date2);
		this.cache.set("yavor", 40, date3);
		
		this.cache.get("tanya");
		this.cache.get("radost");
		this.cache.get("yavor");

		Assert.assertEquals(1, cache.getHitRate(), 0.000001);

	}

	@Test
	public void GetHitRateOfExpiredAndNonExisting() throws CapacityExceededException {

		LocalDateTime date1 = LocalDateTime.of(2013, Month.NOVEMBER, 20, 12, 30);
		LocalDateTime date2 = LocalDateTime.of(2014, Month.NOVEMBER, 20, 12, 30);

		this.cache.set("tanya", 20, date1);
		this.cache.set("radost", 30, date2);

		this.cache.get("tanya");
		this.cache.get("radost");
		this.cache.get("foo");
		this.cache.get("bar");

		Assert.assertEquals(0, cache.getHitRate(), 0.000001);

	}

	@Test
	public void GetHitRateOfExistingAndNonExisting() throws CapacityExceededException {

		LocalDateTime date1 = LocalDateTime.of(2020, Month.NOVEMBER, 20, 12, 30);
		LocalDateTime date2 = LocalDateTime.of(2021, Month.NOVEMBER, 20, 12, 30);

		this.cache.set("tanya", 20, date1);
		this.cache.set("radost", 30, date2);

		this.cache.get("tanya");
		this.cache.get("radost");
		this.cache.get("foo");
		this.cache.get("bar");

		Assert.assertEquals(0.5, cache.getHitRate(), 0.000001);

	}

	@Test
	public void GetHitRateOfClearCache() throws CapacityExceededException {

		LocalDateTime date1 = LocalDateTime.of(2020, Month.NOVEMBER, 20, 12, 30);
		LocalDateTime date2 = LocalDateTime.of(2021, Month.NOVEMBER, 20, 12, 30);

		this.cache.set("tanya", 20, date1);
		this.cache.set("radost", 30, date2);
		
		this.cache.get("tanya");
		this.cache.get("radost");

		this.cache.clear();

		Assert.assertEquals(0, cache.getHitRate(), 0.000001);

	}

}
