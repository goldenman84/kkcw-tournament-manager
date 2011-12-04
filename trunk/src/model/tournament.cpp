#include <string>
#include "tournament.h"

namespace dbo = Wt::Dbo;

template<class Action>
void Tournament::persist(Action& a)
{
	std::string name = "";
    dbo::field(a,name, "name");
}