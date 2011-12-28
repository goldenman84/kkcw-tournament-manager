#ifndef TOURNAMENT_H_
#define TOURNAMENT_H_

#include <Wt/Dbo/Dbo>
#include <Wt/Dbo/Types>
#include <Wt/Dbo/WtSqlTraits>
#include <string>

namespace ktm {
	class Tournament;
	class Category;
}

namespace Wt {
	namespace Dbo {
		template<>
		struct dbo_traits<ktm::Tournament> : public dbo_default_traits {
			typedef std::string IdType;
			static IdType invalidId() { return std::string(); }
			static const char *surrogateIdField() { return 0; }
			static const char *versionField() { return 0; }
		};
	}
}

namespace ktm {

	class Tournament : public Wt::Dbo::Dbo<Tournament> {

		public:
			Wt::Dbo::Session *Session;

		private:
			std::string name;
			Wt::Dbo::collection< Wt::Dbo::ptr<Category> > categories;

		public:
			template<class Action>
			void persist(Action& a) {
				Wt::Dbo::id(a, this->name, "name", 32);
				Wt::Dbo::hasMany(a, this->categories, Wt::Dbo::ManyToOne, "tournament");
			}

			std::string getName() const;
			void setName(std::string name);
	};
}
#endif // TOURNAMENT_H_
