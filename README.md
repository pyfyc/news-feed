# News feed management web application (backend part)

## Basic functions:

- view news feed;
- search by news category, name and description;
- add/update/delete news.

## Endpoints list:

### POST /categories

Add category.

Request parameters:

@RequestBody @Valid CreateCategoryDto createCategoryDto - new category object to be added.

Responses:

HTTP 201 — Category has been added (Created);  
HTTP 400 — Category was not added due to failed validation (Bad Request);  

### DELETE /categories/{id}

Delete category by id.

Request parameters:

@PathVariable("id") Long id - id of the category to be deleted.

Responses:

HTTP 200 — Category has been deleted (OK);  
HTTP 500 — Category was not deleted due to it was not found (Internal Server Error);  

### POST /feeds

Add single news.

Request parameters:

@RequestBody @Valid CreateFeedDto createFeedDto - new news object to be added.

Responses:

HTTP 201 — News has been added (Created);  
HTTP 400 — News was not added due to failed validation (Bad Request);  

### DELETE /feeds/{id}

Delete single news by id.

Request parameters:

@PathVariable("id") Long id - id of the news object to be deleted.

Responses:

HTTP 200 — News has been deleted (OK);  
HTTP 500 — News was not deleted due to it was not found (Internal Server Error);  

### PATCH /feeds/{id}

Update single news parameters.

Request parameters:

@PathVariable("id") Long id - id of the new object to be updated.
@RequestBody @Valid UpdateFeedDto updateFeedDto - news updates.

Responses:

HTTP 200 — News has been updated (OK);  
HTTP 404 — News was not updated due to it was not found (Not Found);  

### GET /feeds

Get all news (with pagination).

Request parameters:

@RequestParam(required = false) Integer page - page number (starts from 0).
@RequestParam(required = false) Integer size - number of elements on the page.

Responses:

HTTP 200 — All news returned (OK);  

### GET /feeds/by-name

Get news by name (with pagination).

Request parameters:

@RequestParam(required = true) String name - name of the news to be searched by (containing ignore case).
@RequestParam(required = false) Integer page - page number (starts from 0).
@RequestParam(required = false) Integer size - number of elements on the page.

Responses:

HTTP 200 — News by name returned (OK);  

### GET /feeds/by-desc

Get news by description (with pagination).

Request parameters:

@RequestParam(required = true) String desc - description of the news to be searched by (containing ignore case).
@RequestParam(required = false) Integer page - page number (starts from 0).
@RequestParam(required = false) Integer size - number of elements on the page.

Responses:

HTTP 200 — News by description returned (OK);  

### GET /feeds/by-cat

Get news by category (with pagination).

Request parameters:

@RequestBody CategoryDto category - category object to be searched by.
@RequestParam(required = false) Integer page - page number (starts from 0).
@RequestParam(required = false) Integer size - number of elements on the page.

Responses:

HTTP 200 — News by category returned (OK);  

## Stack of technologies:
Java 17, Spring Boot 3.1.2, Hibernate, PostgreSQL, Lombok, Mapstruct, Spring Doc Open Api   
