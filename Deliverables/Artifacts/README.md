
Group: LM11  
Date of the exam: 10/07/2018, 18.30 - 19.00  
  
Error found  
  
Description: valueOf() method in the Dice class does not always return the correct number if the object has been serialized and deserialized using the Gson toJson() and fromJson() methods.
The application is interrupted at the first load of the game table if the client-server communication is made using socket technology.
The uncorrect behaviour seems to appear only executing from a JAR file in Windows OS. We are not able to reproduce the error launching the app on IntelliJ IDEA and the Jar on Ubuntu Linux.
  
Fix made: we were able to execute the Jar on Windows using the socket connection by changing the method valueOf() in the Dice class.
  
Old version: 

    public int valueOf(){  
      return face.compareTo("\u2680")+1;  
    }

With face being a String object.
The implementation relies on the String.compareTo() method to return the dice's value.
The method was one of the first implemented and always returned the expected value when tested with IntelliJ and mvn test.

New Version:  

We added a private int parameter to store the dice value.  

    public class Dice {
    private int value;

      public int valueOf(){  
        return value;  
      }  

    }
  
  
The method returns directly an int, instead of making a comparison between 2 unicode characters.  
  
Our hypothesis is that the convertion to Json corrupted the unicode characters (only when launched from Jar on Windows) and caused a null pointer on the client when javaFx tried to use valueOf() to select the correct dice between a list of 6 "dice##.fxml" file.
We discovered the solution this morning after a day of trying when we printed the valueOf() result on the client and discovered that was -9972.
