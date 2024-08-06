package utils;

public class Console {
   private String lastMessage;

   public void log(String message) {
      System.out.println(message);
      lastMessage = message;
   }

   public String lastMessage() {
      return lastMessage;
   }
}
