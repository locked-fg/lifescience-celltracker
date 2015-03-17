# System Requirements #

  * [Java Runtime Environment - Version 7 or higher](http://www.oracle.com/technetwork/java/javase/downloads/jre-7u3-download-1501631.html)
  * 64-Bit operating system
  * minimum 4GB main memory


---



## NOTES ##
To load large TIF sequences (more than 200 MB) the jar has to be started with following parameters on a windows platform:



---

java -d64 -Xmx5120m -jar lifescience-celltracker.jar

---


**-d64** loads the 64-Bit Java Virtual Machine
**-Xmx5120m** sets the maximal used main memory to 5120 MB

Please adjust these settings according to your system configuration. You should keep the -Xmx value below 2/3 of free system memory.