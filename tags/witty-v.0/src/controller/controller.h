#ifndef CONTROLLER_H_
#define CONTROLLER_H_

#include <string>
#include <Wt/WApplication>
#include <Wt/WEnvironment>
#include <Wt/WContainerWidget>
#include <Wt/WText>
#include <Wt/Dbo/Dbo>
#include <MySQL.h>

#include "../model/tournament.h"
#include "../model/category.h"

namespace ktm  {

	class Controller : public Wt::WApplication {
		public:
			Controller(const Wt::WEnvironment& env);
		private:
	};
}
#endif // CONTROLLER_H_
