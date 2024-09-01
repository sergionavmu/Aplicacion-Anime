package abp.animeg7.anime;

import com.intuit.karate.junit5.Karate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AnimeApplicationTests {

	@Test
	void contextLoads() {
	}

	@Karate.Test
	public Karate testUserApi() {
		return Karate.run("src/test/karate/featureFiles/Video.feature");
	}

}
