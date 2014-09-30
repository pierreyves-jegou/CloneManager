Goal
============
Two goals : Allow to find duplicate files on computer / find files which haven't been backed up

Installation
============
Requirements : At least Java 1.8 (can be downloaded here 'http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html')

- Extract the archive content
- Open a console
- execute the following command (replace 'pathToJava' and 'pathToExtractedArchive' with the suitable values)
	unix/linux : /pathToJava/java -jar /pathToExtractedArchive/CloneManager-GUI-1.0-SNAPSHOT.jar
	windows : \pathToJava\java -jar \pathToExtractedArchive\CloneManager-GUI-1.0-SNAPSHOT.jar



Developpement
============
This project has been developped under netbeans 8.0 using Java Scene builder 2.0.

The maven dependency regarding "aspectj-maven-plugin-1.7-SNAPSHOT.jar" has to be set manually using the following steps : 
- This jar can be found under the directory "external-librairies"
- execute the following command (replace pathToAspectJar with the suitable values) 
	mvn install:install-file -Dfile=pathToAspectJar/aspectj-maven-plugin-1.7-SNAPSHOT.jar -DgroupId=org.codehaus.mojo -DartifactId=aspectj-maven-plugin -Dversion=1.7-SNAPSHOT -Dpackaging=jar


