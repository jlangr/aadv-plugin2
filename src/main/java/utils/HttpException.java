package utils;

public class HttpException extends RuntimeException {
   public HttpException(String message) {
      super(message);
   }

   public HttpException(Exception e) {
      super(e);
   }
}
