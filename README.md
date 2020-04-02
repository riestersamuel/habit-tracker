# Demo javaFX project
This is a simple javaFX demo project which can be used as a starting point.
Just follow the setup to download and finally run your application.

# Setup
 1. Download IntelliJ IDEA
	- (https://www.jetbrains.com/de-de/idea/download/)
 2. Download JDK 14
	- (https://www.oracle.com/java/technologies/javase-jdk14-downloads.html)
 3. Download this repository (dont clone it)
 4. Open the project in intelliJ IDEA
	- Make sure to enable "auto-import" if prompted
	- Specify JDK Version to 14
 5. Run by hitting the green run button in the top right corner (Make sure "Run javaFX" configuration is selected)

# Adding a remote repository in terminal
1. Navigate to the project folder 
	- cd /c/Users/Test/Desktop/SampleProject
2. Initialize git repository
	- git init
3. Add remote origin
	- git remote add origin https://gitlab.mi.hdm-stuttgart.de/<Dein HdM-Kürzel>/\<Projectname>
4. Add and commit files
	- git add -A
	- git commit -m "Initial commit"
5. Push and create remote master branch
	- git push --set-upstream origin master