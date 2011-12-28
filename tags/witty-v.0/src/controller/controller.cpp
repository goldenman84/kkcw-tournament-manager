#include "controller.h"

namespace ktm {
	/*
	 * The env argument contains information about the new session, and
	 * the initial request. It must be passed to the WApplication
	 * constructor so it is typically also an argument for your custom
	 * application constructor.
	*/
	Controller::Controller(const Wt::WEnvironment& env) : Wt::WApplication(env) {
		std::cout << "running app"<< std::endl;
		// set the title of the browser window
		setTitle("KKCW Tournament Manager");

		// add some text to html page
		Wt::WContainerWidget *textdiv = new Wt::WContainerWidget(root());
		textdiv->setStyleClass("text");

		new Wt::WText("<h2>KKCW Tournament Manager</h2>", textdiv);
		new Wt::WText("Created by Cahit Atilgan & Felix Mueller<br />",textdiv);

		Wt::Dbo::backend::MySQL mysql("ktm");
		Wt::Dbo::Session session;
		session.setConnection(mysql);

		session.mapClass<ktm::Tournament>("tournament");
		session.mapClass<ktm::Category>("category");

		/*
		* Try to create the schema (will fail if already exists).
		*/
		try {
			session.createTables();
			std::cerr << "Created database." << std::endl;
		}
		catch (std::exception& e) {
			std::cerr << e.what() << std::endl;
			std::cerr << "Using existing database";
		}

		Wt::Dbo::Transaction transaction(session);

		Tournament *myTournament = new ktm::Tournament();
		myTournament->setName("Winti Cup 2012");
		Wt::Dbo::ptr<Tournament> tournamentPtr = session.add(myTournament);

		Wt::Dbo::ptr<Tournament> tournament = session.find<Tournament>().where("name = ?").bind("Winti Cup 2012");
		std::cout << "\n --> "<< myTournament->getName() << std::endl;

		Category *myCategory = new Category();
		myCategory->name = "Piccolo";
		myCategory->tournament = tournament;
		myCategory->mode = Category::double_elimination;
		Wt::Dbo::ptr<Category> category = session.add(myCategory);
		Wt::Dbo::ptr<Tournament> tr = category->tournament;
		std::cout << "\n --> "<< category->name << ", " << tr->id() << std::endl;

		Category *medium = new Category();
		medium->name = "Medium";
		myCategory->mode = Category::single_elimination;
		medium->tournament = tournament;
		Wt::Dbo::ptr<Category> cat2 = session.add(medium);

		Wt::Dbo::ptr<Tournament> tr2 = cat2->tournament;
		std::cout << "\n --> "<< cat2->name << ", " << tr2->id() << std::endl;

		transaction.commit();
	}
}


Wt::WApplication *createApplication(const Wt::WEnvironment& env) {
	  return new ktm::Controller(env);
	}

	int main(int argc, char **argv) {
		return Wt::WRun(argc, argv, &createApplication);
	}
