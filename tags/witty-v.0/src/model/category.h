/*
 * category.h
 *
 *  Created on: Dec 4, 2011
 *      Author: catilgan
 */

#ifndef CATEGORY_H_
#define CATEGORY_H_

#include <Wt/Dbo/Dbo>
#include <Wt/Dbo/Types>
#include <Wt/Dbo/WtSqlTraits>
#include <string>

namespace ktm {
	class Category;
	class Tournament;
}

namespace Wt {
	namespace Dbo {
		template<>
		struct dbo_traits<ktm::Category> : public dbo_default_traits {
			typedef std::string IdType;
			static IdType invalidId() { return std::string(); }
			static const char *surrogateIdField() { return 0; }
			static const char *versionField() { return 0; }
		};
	}
}

namespace ktm {

	class Category :  public Wt::Dbo::Dbo<Tournament> {
		public:
			std::string name;
			enum Mode {
				single_elimination = 0,
				double_elimination = 1
			};
			Wt::Dbo::ptr<Tournament> tournament;

			Mode mode;

			template<class Action>
			void persist(Action& a) {
				Wt::Dbo::id(a, this->name, "name", 32);
				Wt::Dbo::field(a, this->mode, "mode");
				Wt::Dbo::belongsTo(a, this->tournament, "tournament");
			};
	};
}

#endif /* CATEGORY_H_ */
