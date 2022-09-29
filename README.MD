Prerequisites
—————————

1. The project requires maven installed to run.
2. You need chrome browser installed.
3. You will need to download chromedriver from https://chromedriver.chromium.org/downloads. Once downloaded the file needs to be clicked on to start the chrome driver as a process in the background. You also need to update the location of the chrome driver in the file named Automation.java at line number 54.
4. I have tested for ChromeDriver 105.0.5195.52 with this project. 

To run the project
—————————————

1. Please clone the project to your local machine.
2. cd to /target folder under the project and run “mvn clean package” to run the project.
3. The above command will run the 2 tests in Automation.java 
   a. 1st test case is to solve the API automation question.
   b. 2nd test case is to launch the selenium web driver project.
4. The project also has a file named NameUtility.java which has been fixed to work and print the first name, middle name and the last name.
5. The project also has a file named NameUtilityImproved.java which has a better version using the split method in Java.