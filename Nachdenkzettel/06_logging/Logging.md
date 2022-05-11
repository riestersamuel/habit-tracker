# Nachdenkzettel: Logging

[logging (2).pdf](Nachdenkzettel%20Logging%204645b9ae5dc64f93b306a3d572b2a7b0/logging_(2).pdf)

# Aufgabe 1

PDF

# Aufgabe 2

- Error → If an error occurs, e.g. when there are too few connections to the database
- Info → If we want to report basic information like the start of an app
- Debug → Information that is diagnostically helpful to people more than just developers (IT, sysadmins, etc.)

# Aufgabe 3

Either the rolling appender to a certain amount or to a certain
limit the time interval, i.e. let him start from the beginning.

# Aufgabe 4

yes, it decreases application performance as it uses some CPU cycles and other resources (memory, etc).

# Aufgabe 5

You have to analyze multiple log files to find the error. Furthermore, the problem could overlap and it´s hard to find where the error is.

# Aufgabe 6

- Webserver: Logging HTTP requests and status code
- App Server: Exceptions, Time, Data requests and access

# Aufgabe 7

On the one hand you want to know which connections are currently free and which ones are available for use. On the other hand, it would also make sense to log WHO, WHEN, and WHICH connection.

# Aufgabe 8

They have to be understandable for non-nerds → Easy language and understandable and only relevant statements for the User. 

Authority should be mentioned in the Log - File.