# java-library-program
Library program made with Java and MySQL through JDBC

The program has 4 separate data classes: books, borrows, users and librarians. <br>
All the aformentioned data is saved in a MySQL database through the JDBC connection with the database. <br>
The program enables the user to log in as a librarian, in first time usage scenario all the fields are to be filled with 0 in order to log in succesfully. <br>
When logged in, the librarian has the ability to add users who can borrow books(up to 3 per user), add books and input borrows. <br>
Navigation is ordered through 4 tabs, scrolling and add/edit/remove buttons as well as tables that show the current status of the database. <br>


![image](https://user-images.githubusercontent.com/91484772/135620757-69062fd2-fa18-4985-ade4-5d10ec901646.png)


### BORROWS
To borrow a book to a user the librarian must know the ID of the user and the ID of the book, as well as the time interval for which the user wants to borrow the book. <br>
Once input, the borrow will show up in the table where the expected return date as well as the data about the book and the user will be displayed. <br>
If the user is returning a book the librarian can return the book in the RETURN part of the tab using the same data needed for borrowing except the time interval. <br>


![image](https://user-images.githubusercontent.com/91484772/135620838-39e1e3ce-468a-495a-8dc5-e679f5b69ee7.png)


### BOOK 
Books are added depending on ID field; there cannot be 2 books with the same ID. <br>
Each book can have determined fields such as ID, title, author, edition and genre. <br>
After the book is added the user can click on the book in the table to edit the information in the EDIT part of the tab. <br>


![image](https://user-images.githubusercontent.com/91484772/135621010-aa7f8fc8-5b0f-42a8-882d-8143f6f1a029.png)

![image](https://user-images.githubusercontent.com/91484772/135621137-f526522c-43e9-41c0-b2b3-a5455b9cbc63.png)


### USER
The principle is the same as with books. <br>
Each user has fields ID, name, surname and number of books borrowed. <br>
The editing principle works the same. <br>


![image](https://user-images.githubusercontent.com/91484772/135620911-07124496-eec6-4a0a-91fb-46895653913a.png)


### LIBRARIAN
Each librarian must have a separate ID. <br>
After the first input librarian, the default log in credentials(all zeros) are deleted. <br>


![image](https://user-images.githubusercontent.com/91484772/135621189-c914ee64-6fcf-48ae-80a5-72d22e4218fa.png)


## Getting it up and running
### Requirements:
JRE version 8 or higher (JDK install recommended) (https://java.com/en/download/)  
MySQL Server (https://dev.mysql.com/downloads/installer/)  
<!-- MySQL Workbench (https://dev.mysql.com/downloads/installer/) -->

### Setup:
When creating a new server in MySQL Server, all the fields should be left as they are.   
The root password should be set to "1234" (without the quotes).    
The program creates a database itself so there is no need for database creation in MySQL Workbench.
