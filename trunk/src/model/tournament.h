#ifndef TOURNAMENT_H_
#define TOURNAMENT_H_

#include <Wt/Dbo/Dbo>
#include <string>

using namespace Wt;

class Tournament  {
 public:

 	std::string name;

   template <class Action>
   void persist(Action& a);
 };

#endif // TOURNAMENT_H_