
C195 Final Assessment v1.0
4/11/2021

Blake Lambert
Student ID: 001337333
blamb52@my.wgu.edu

IntelliJ Community Version 2020.3.3
Java SE 11.0.10
JavaFX SDK 11.0.2
mysql-connector-java-8.0.23


The purpose of this assessment is to create an appointment management application that mimics a real world application development process. The student is given a description of the applications functions and needs and must implement them in the final product. The assessment also demonstrates the students understanding of more advanced development principles and skills by having the application interact with a database that stores the appointments as well as understanding how to integrate time and time zones which are important to consider when building applications for enterprise level clients operating in different locations.

To run the application, the user must first launch it. Once successfully launched, the user is presented with the login menu that displays in their system default language. When the user successfully logs in, the main application interface is created starting with the appointments side. This part of the application has controls that allow the user to add, edit, and remove appointments as well as sort them according to preset time periods. There is also a control to go to the reports menu and run the application reports. If the "Customers" button is clicked, the menu is shifted to the customer controls where the user can create, update, and delete customers. All of these functions interact and update the database in real time to save user changes.

The third report in the reports menu queries the database for customers and their associated country. These results are stored into a hashmap with the Country as the key and number of associate customers as the value. The values of the hashmap are appended to the report space so the user can determine growth of customers in the countries their company operates in.

