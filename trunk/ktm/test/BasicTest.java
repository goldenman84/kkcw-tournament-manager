import org.hibernate.dialect.FirebirdDialect;
import org.junit.*;

import java.util.*;

import play.test.*;
import models.*;

public class BasicTest extends UnitTest {

	@Before
	public void setup() {
		Fixtures.deleteDatabase();
	}

	@Test
	public void createAndRetrieveTournament() {

		// create and save a new tournament in database
		Date tDate = new Date();
		new Tournament("Winti Cup 2012", tDate).save();

		// find the user in database
		Tournament t = Tournament.find("byName", "Winti Cup 2012").first();

		assertNotNull(t);
		assertEquals(t.date, tDate);
	}

	@Test
	public void createCategory() {
		// create and save a new tournament in database
		Date tDate = new Date();
		Tournament tournament = new Tournament("Winti Cup 2012", tDate).save();

		// create a new category
		new Category(tournament, "Piccolo", Category.EliminationMode.Single)
				.save();

		// test the category
		assertEquals(1, Category.count());

		// retrieve the posts
		List<Category> categories = Category.find("byTournament", tournament)
				.fetch();

		assertEquals(1, categories.size());
		Category category = categories.get(0);
		assertEquals(tournament, category.tournament);
		assertEquals("Piccolo", category.name);
		assertEquals(Category.EliminationMode.Single, category.mode);
	}

	@Test
	public void createFighter() {
		Fighter fighter = new Fighter("Mike", "Tyson", 45, 190).save();

		assertEquals(1, Fighter.count());

		Fighter mike = Fighter.find("byFirstnameAndLastname",
				fighter.firstname, fighter.lastname).first();

		assertNotNull(mike);
		assertEquals("Mike", mike.firstname);
		assertEquals("Tyson", mike.lastname);
		assertEquals(45, mike.age);
		assertEquals(190, mike.size);

	}
}
