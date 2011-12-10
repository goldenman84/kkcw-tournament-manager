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

	class Tournament;

	class Category :  public Wt::Dbo::Dbo<Tournament> {
		public:
			std::string name;
			enum mode {
				single_elimination = 0,
				double_elimination = 1
			};
			Wt::Dbo::ptr<Tournament> tournament;

			template<class Action>
			void persist(Action& a) {
				Wt::Dbo::field(a, this->name, "name");
				Wt::Dbo::belongsTo(a, this->tournament, "tournament");
			};
	};
}

#endif /* CATEGORY_H_ */
