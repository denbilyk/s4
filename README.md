# S4 Test assignment

##### Frameworks: Java 8, Spring-Boot, Spring-REST, Spring-Data, Guava, HSQLDB
###### I've used Spring because its simplest way to create some small executable project very fast. Powerful mechanism to map REST services
 
##### API Docs
 
 1. Students
 
  * *Action* - Create
  * *Method*  - POST
  * *URI* - `/student`
  * Parameters: 
    + *student_id* - numeric type - **mandatory**
    + *first_name* - string type -  **mandatory**
    + *last_name* - string type -  **mandatory**
  * Example: 
    `{
       "student_id": 1,
       "first_name": "Bob",
       "last_name" : "Marley"
     }`
  * Response: 
     - 400 - Some of parameters missed or invalid
     - 202 - Student with same id already exists
     - 201 - Student created
    
  * *Action* - Update
    * *Method*  - PUT
    * *URI* - `/student/{id}`
    * Parameters: 
      + *student_id* - numeric type - **optional**
      + *first_name* - string type -  **optional**
      + *last_name* - string type -  **optional**
    * Example: 
      `{
           "last_name" : "Marley"
       }`
    * Response: 
       - 202 - Student not found
       - 201 - Student updated
       
  * *Action* - Delete
    * *Method*  - DELETE
    * *URI* - `/student/{id}`
    * Response: 
      - 202 - Student not found
      - 202 - Student is assign to class
      - 200 - Student deleted
     
  * *Action* - Find all students
    * *Method*  - GET
    * *URI* - `/student`
    * Response: 
       - 200 - OK

  * *Action* - Find student by id
    * *Method*  - GET
    * *URI* - `/student/{id}`
    * Response: 
      - 200 - OK
           
  * *Action* - Find all students assigned to a classes
    * *Method*  - GET
    * *URI* - `/student/classes`
    * Response: 
      - 200 - OK
               
  * *Action* - Perform search in students
    * *Method*  - GET
    * *URI* - `/student?[student_id],[&first_name],[&last_name]`
     * Query parameters: 
          + *student_id* - numeric type - **optional**
          + *first_name* - string type -  **optional**
          + *last_name* - string type -  **optional**
    * Response: 
       - 200 - OK
       
  * *Action* - Assign student to class
    * *Method* - PUT
    * *URI* - `/student/{id}/class/{code}`
    * Path Parameters: 
       + *id* - numeric type - 'student_id'
       + *code* - numeric type - class code
    * Response: 
      - 202 - Student not found
      - 202 - Class not found
      - 201 - Student assigned

  * *Action* - Unassign student from class
    * *Method* - PUT
    * *URI* - `/student/{id}/class/{code}/unassign`
    * Path Parameters: 
       + *id* - numeric type - 'student_id'
       + *code* - numeric type - class code
    * Response: 
      - 202 - Student not found
      - 202 - Class not found
      - 202 - Student is not assign
      - 201 - Student unassigned
      
  * Response examples: 
   `POST:/student`
   `{
          "id": 4,
          "text": "Student with same id has already exist!",
          "status": "STUDENT_EXISTS"
    }`
    
    `POST:/student`
    `{
          "id": 1,
          "text": "Student created",
          "status": "STUDENT_CREATED"
     }`

    `PUT:/student/1/class/2/unassign`
        `{
           "id": null,
           "text": "Student wasn't assigned",
           "status": "STUDENT_NOT_ASSIGNED"
        }`
 
 2. Class
  
   * *Action* - Create
    * *Method*  - POST
    * *URI* - `/class`
    * Parameters: 
      + *code* - numeric type - **mandatory**
      + *title* - string type -  **mandatory**
      + *description* - string type -  **mandatory**
    * Example: 
      `{
           "code": 1,
           "title": "Physics",
           "description" : "Learn Physics!"
       }` 
    * Response: 
       - 400 - Some of parameters missed or invalid
       - 202 - Class with same id already exists
       - 201 - Class created
              
   * *Action* - Update
      * *Method*  - PUT
      * *URI* - `/class/{code}`
      * Parameters: 
        + *code* - numeric type - **optional**
        + *title* - string type -  **optional**
        + *description* - string tpe - **optional**
      * Example: 
        `{
             "title" : "Math"
         }`
      * Response: 
         - 202 - Class not found
         - 201 - Class updated
         
   * *Action* - Delete
      * *Method*  - DELETE
      * *URI* - `/class/{code}`
      * Response: 
        - 202 - Class not found
        - 202 - Students are assign to class
        - 200 - Class deleted
       
   * *Action* - Find all classes
      * *Method*  - GET
      * *URI* - `/class`
      * Response: 
         - 200 - OK
  
   * *Action* - Find class by code
      * *Method*  - GET
      * *URI* - `/class/{code}`
      * Response: 
        - 200 - OK
             
   * *Action* - Find all classes assigned to students
      * *Method*  - GET
      * *URI* - `/class/students`
      * Response: 
        - 200 - OK
                 
   * *Action* - Perform search in classes
      * *Method*  - GET
      * *URI* - `/class?[code],[&title],[&description]`
       * Query parameters: 
            + *code* - numeric type - **optional**
            + *title* - string type -  **optional**
            + *description* - string type -  **optional**
      * Response: 
         - 200 - OK
         
   * Response examples:
      `POST:/class`
      `{
         "id": 3,
         "text": "Class with same id has already exist!",
         "status": "CLASS_EXISTS"
       }`
       
       `DELETE:/class/3`
       `{
          "id": 3,
          "text": "Class is linked with student!",
          "status": "CLASS_WITH_STUDENT"
        }`
        
       `GET:/class/search?title=physics`
       `[
          {
            "title": "Physics",
            "description": "Learn Physics!!",
            "code": 1
          }
        ]`
