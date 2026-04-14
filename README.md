To run the front and backend, simply from the project directory, type: podman compose up -d
To run the Selenium tests, I opened pom.xl in terminal and ran individually: mvn test -Dtest=StudentUITest and mvn test -Dtest=CourseUITest

Source code found in end2end/CourseUITest.java and end2end/StudentUITest.java. Some test cases omitted due to repititveness (editing course each field, etc.)

PDF with Selenium Test Cases and images showing showing all tests run successfully included. 

