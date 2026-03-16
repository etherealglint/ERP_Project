University ERP System
A Java based desktop ERP system designed to streamline university academic operations through role based access control, modular UI design, and secure database backed workflows.
Built with Java Swing, MySQL, and a layered architecture to support Students, Instructors, and Administrators.

The primary Goal of this project is to provide a simplified, unified platform that improves
efficiency of routine academic operations. By implementing access control, database-backed
validation, and a clean user interface, the system demonstrates how university workflows can
be digitised in a secure and user-friendly manner.

Key Features:
1.Role-based dashboards (Student/Instructor/Admin)
2.Secure authentication with encrypted passwords
3.Course registration with deadline & maintenance enforcement
4.Instructor grade computation with configurable weightages
5.Real-time class statistics and grade distribution charts
6.Admin controlled maintenance mode
7.PDF transcript generation
8.Strict access control with runtime permission checks

Architecture and Design
Layered Architecture:
1.UI Layer (Java Swing Panels)
2.Controller Layer
3.Service Layer (Business Logic & Permissions)
4.Data Layer (JDBC)
5.MySQL Database
Modular UI:
1.Panels loaded dynamically based on user role
2.Prevents unauthorized access by design

Tech Stack:
Language: Java (Oracle OpenJDK 17)
UI: Java Swing, FlatLaf, MigLayout
Database: MySQL, JDBC
Security: jBCrypt (password hashing)
Charts: JFreeChart
PDF: Apache PDFBox
IDE: IntelliJ IDEA

Student Features:
Course catalog & registration
Add/drop courses (deadline enforced set by admin)
Timetable & grades view
Download transcript (PDF)

Instructor Features:
Assigned sections management
Grade updates with customizable weightage
Class statistics with charts
Section wise timetable

Admin Features:
User management (Students/Instructors/Admins)
Course & section CRUD operations
Instructor allotment
System wide maintenance control

Security and Access Control
Role-based UI rendering
Backend permission validation
Maintenance mode enforcement by admin
Access denied handling for restricted pages

Grading Schema:
Configurable grading weightage
Adjustable passing criteria
Automatic final grade computation
Grade distribution visualization (using charts)
By default the weighting rule is Assignment 20%, End_sem 40%, Mid_sem 30%, Quiz 10%.
This can be modified by the instructor and setted to a different value, to compute the final
grades according to their convenience. Instructors can also modify the passing threshold which
was 40% by default.
A+ for >=90 and A for >=80 and B for >=70 and C for >=60 and F for rest.

Database Schema:
AUTH_DB
users_auth

ERP_DB
students, instructors
courses, sections
enrollments, grades
settings

Running the project:
1.Install Java JDK 17
2.Configure MySQL and import schemas
3.Add required libraries
4.Run the main class from IntelliJ IDEA

This project demonstrates:
Real world ERP workflow design.
Secure role-based systems.
Clean Java desktop architecture.
Database driven decision logic.
UI/UX considerations for enterprise software.

Database connection working:
1. UI Layer (FrontEnd)
The UI triggers actions based on user inputs.
Each UI action calls the corresponding controller method in the backend.
2. Controller Layer
The controller receives the request from the UI.
It validates inputs and passes the request to the Service Layer.
3. Service Layer
Makes decisions (is maintenance mode on?, does this user have permission?) then
Calls the data Layer for database operations.
4. Data Layer
Responsible only for database access.
Runs sql queries
Returns data to the Service Layer.
5. Database (MySQL)
Responds with results of SQL operations.
