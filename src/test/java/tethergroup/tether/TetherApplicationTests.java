package tethergroup.tether;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tethergroup.tether.repositories.FriendshipRepository;

@SpringBootTest
class TetherApplicationTests {

	@Autowired
	private FriendshipRepository friendshipDao;

	@Test
	void contextLoads() {
	}

	@Test
	public void friendDelete() {
		friendshipDao.deleteById(33L);
	}

}
