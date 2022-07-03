# General information
Our project is a combination of a habit tracker and a journal app. The goal of our app is to help people to live better lives. The idea is that by doing simple positive habits every day, life becomes much better in the long run.

When starting the program you’ll see all the main functions. We wanted to create a simple utility app which is easy to use and where the user can see all useful information at a glimpse. On the left you have the “Habit” section, on the right your daily journal. You can add a habit by clicking on the “Add a new Habit” Button. A new Window will pop up, where you can name your new habit and set the days where you want to accomplish the habit. As soon as you click on “OK”, the habit will be added to your main screen. The days where you haven’t accomplished the habit (yet) will be colored red. Accomplished ones will be marked green.
When you don’t want to remove a habit, just click on the “Remove Habit” button and delete the habit from the table below. To exit the “delete mode”, click on the “Remove Habit” button again. To see your progress over the last weeks, click on the arrows above the habit - table and skip through the weeks.
On the right side of the screen is the “Daily Journal ''. Here you can write a short paragraph (no more than 200 Words) to keep track of your thoughts or things you’ve done over the day.

# Installing and starting the program
In order to run the program you need Maven and Java installed on your computer. You can find Java on [this page](https://www.oracle.com/java/technologies/javase-jre-downloads.html).
Then you should clone the project from [this GitLab page](https://gitlab.mi.hdm-stuttgart.de/ss546/habit-tracker) into a folder on your computer.

## Compiling the program
Creating the ```.jar``` file:
1) Move to the root folder of the project and open the terminal there
2) Create the jar package by entering: ```mvn package```
3) The jar file can be found in the subfolder ```target/```

## Starting the program
After successful compiling you can run ```java -jar target/habitTracker-1.0.jar```

# Instructions
## How to track your habits
### Add a new habit
In order to add a new habit you have to click on the button "Add a new habit" and fill in the form. In the form you can decide on which days you plan to do the habit. Do that by ticking the corresponding checkboxes. After that, the new habit will be shown in the table and you can start to track it.
### Remove a habit
In order to remove a habit you have to click on the button "Remove a habit". After that a new icon will be shown in the table. Click on that icon to remove the habit.
### Jump back to previous weeks
In order to jump back and forth to previous weeks, you can use the two buttons with the arrows above the table. By doing this you can see your progress in the past. Seeing the progress you made is motivating and it might help you to sustain your habits, even when you don't feel like doing so - just because you don't want to "lose your progress".
## How to use the journal
The journal is a simple way to become conscious about what you actually do with your days and stores daily insights. It can store insights, thoughts and basically everything you want it to store. However, it's designed to be a little bit like a twitter tweet - short and expressive. That's why we have limited the length of the text to 200 characters. The entries are also limited in size, which means you can't spam it with infinite empty rows.
### Add a new entry
In order to add a new entry, you need to enter some text into the text area and afterwards click on the "Add entry" button. All entries are ordered by date, so the newest entry will be on top.
## How to change the displayed name
In order to change the displayed name, you can navigate to the settings menu and enter your name there. After that you can hit the "Save changes" button and return to the main page.

# Developers
- Bestvater, Tom (tb173)
- Beutel, Julius (jb266)
- Riester, Samuel (sr185)
- Singer, Steffen (ss546)