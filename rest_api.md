# Einleitung #



## RESTful web services ##

(Copied from http://en.wikipedia.org/wiki/Representational_state_transfer)


A RESTful web service (also called a RESTful web API) is a simple web service implemented using HTTP and the principles of REST. It is a collection of resources, with four defined aspects:

  * the base URI for the web service, such as http://example.com/resources/
  * the Internet media type of the data supported by the web service. This is often JSON, XML or YAML but can be any other valid Internet media type.
  * the set of operations supported by the web service using HTTP methods (e.g., POST, GET, PUT or DELETE).
  * The API must be hypertext driven.

RESTful Web Service HTTP methods[12](12.md)

| **Resource** | **GET** | **PUT** | **POST** | **DELETE** |
|:-------------|:--------|:--------|:---------|:-----------|
|Collection URI, such as http://example.com/resources/ | List the URIs and perhaps other details of the collection's members.|Replace the entire collection with another collection.|Create a new entry in the collection. The new entry's URL is assigned automatically and is usually returned by the operation. |Delete the entire collection.|
|Element URI, such as http://example.com/resources/item17|Retrieve a representation of the addressed member of the collection, expressed in an appropriate Internet media type.|Replace the addressed member of the collection, or if it doesn't exist, create it.|Treat the addressed member as a collection in its own right and create a new entry in it.|Delete the addressed member of the collection.|

## REST API ##

| **Resource** | **GET** | **PUT** | **POST** | **DELETE** |
|:-------------|:--------|:--------|:---------|:-----------|
| **/api/tournaments** | List all tournaments | -       | -        | Delete all tournaments |
| /api/tournaments/{tournamentid} | Retrieve the tournament object with {tournamentid}  | Edit tournament object with {id}, or create it if it doesn't exist |          |            |
| **/api/categories** | List all categories | -       | -        | Delete all categories |
| /api/categories/{categoriesid} | Retrieve category object with {id} | Edit category object with {id}, or create it if it doesn't exist  | -        | Delete category with {id}|
| /api/categories/{categoriesid}/fighters | List all fighters related to category with {id} | -       | -        | Delete all fighters related to category with {id} |
| /api/categories/{categoriesid}/fight\_areas | List the fighting areas assigned to category {id} | -       | -        | Delete all fighting areas assign to category {id} |
| /api/categories/{categoriesid}/rounds | List all rounds related to category {id} | Assign a list of rounds to category {id} | -        | -          |
| **/api/rounds** | List all rounds | -       | -        | -          |
| /api/rounds/{roundsid} | Retrieve round object with {id} | Edit round object with {id}, or create it if it doesn't exist | -        | Delete round with {id} |
| /api/rounds/{roundsid}/brackets | List all brackets in round {id} | Assign a list of brackets to round {id} | -        | Delete all brackets in round {id} |
| **/api/brackets** | List all brackets | Assign a list of brackets to round {id} | -        | Delete all brackets in round {id} |
| /api/brackets/{bracketsid} | List bracket with {id} | Edit bracket object with {id}, or create it if it doesn't exist  | -        | Delete bracket with {id} |
| /api/brackets/{bracketsid}/fights | List all fights in bracket {id} | Assign a list of fight to bracket {id}  | -        | Delete all fights in bracket {id} |
| **/api/fighters** | List all fighters | -       | -        | Delete all fighters |
| /api/fighters/{fighterid} | List fighter with {id} | Edit fighter object with {id}, or create it if it doesn't exist  |          | Delete figher with {id} |
| /api/fighters/{fighterid}/fights | List all fights related to fighter {id} | Assign a list of fights to fighter {id} | -        | Delete all fights related to fighter {id} |
| **/api/fights** | List all fights |         |          |            |
| /api/fights/{fightid} | Retrieve fight object with {id} |         |          |            |
| /api/fights/{fightid}/fight\_areas |  List all fight areas related to fight {id} |         |          |            |
| **/api/fight\_areas** |         |         |          |            |
| /api/fight\_areas/{fight\_areaid} |         |         |          |            |
| /api/fight\_areas/{fight\_areaid}/fights |         |         |          |            |
| /api/fight\_areas/{fight\_areaid}/categories |         |         |          |            |