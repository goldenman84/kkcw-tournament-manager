#ifndef TOURNAMENT_H_
#define TOURNAMENT_H_

#include <Wt/Dbo/Dbo>
#include <Wt/Dbo/Types>
#include <Wt/Dbo/WtSqlTraits>
#include <string>

namespace ktm {

	class Category;

	class Tournament : public Wt::Dbo::Dbo<Tournament> {
		public:
		 Wt::Dbo::Session *Session;

		private:
			std::string name;
			Wt::Dbo::collection< Wt::Dbo::ptr<Category> > categories;

		public:
			template<class Action>
			void persist(Action& a) {
				Wt::Dbo::field(a, this->name, "name");
				Wt::Dbo::hasMany(a, this->categories, Wt::Dbo::ManyToOne, "tournament");
			}

			std::string getName() const;
			void setName(std::string name);
	};
}
#endif // TOURNAMENT_H_
