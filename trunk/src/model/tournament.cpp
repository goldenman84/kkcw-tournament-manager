/*
 * tournament.cpp
 *
 *  Created on: Dec 4, 2011
 *      Author: catilgan
 */

#include "tournament.h"

namespace ktm {

	std::string Tournament::getName() const {
		return this->name;
	}

	void Tournament::setName(std::string name) {
		this->name = name;
	}
}
