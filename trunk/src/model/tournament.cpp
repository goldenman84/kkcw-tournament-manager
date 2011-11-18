#include "tournament.h"

namespace dbo = Wt::Dbo;

class Tournament {

public:  
  template<class Action>
  void persist(Action& a)
  {
    dbo::field(a, name,     "name");
  }
}