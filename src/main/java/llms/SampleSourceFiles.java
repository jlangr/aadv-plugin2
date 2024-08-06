package llms;

public class SampleSourceFiles {
   public static final String fizzBuzzProdSource = """
      public class FizzBuzz {
         public static void main(String[] args) {
              FizzBuzz fizzBuzz = new FizzBuzz();

              for (int i = 1; i <= 100; i++) {
                  System.out.println(fizzBuzz.getFizzBuzzValue(i));
              }
         }

         public String getFizzBuzzValue(int number) {
            if (number % 3 == 0 && number % 5 == 0) {
               return "FizzBuzz";
            } else if (number % 3 == 0) {
               return "Fizz";
            } else if (number % 5 == 0) {
               return "Buzz";
            }

            return String.valueOf(number);
        }
      }""";
   public static final String helperProdSource = """
      public class Helper {
      }""";
   public static final String fizzBuzzTestSource = """
      import org.junit.jupiter.api.Test;
      import static org.junit.jupiter.api.Assertions.*;
      
      public class FizzBuzzTest {
      
          @Test
          public void testFizzBuzz() {
              FizzBuzz fizzBuzz = new FizzBuzz();
      
              // Test FizzBuzz cases
              assertEquals("FizzBuzz", fizzBuzz.getFizzBuzzValue(15));
              assertEquals("FizzBuzz", fizzBuzz.getFizzBuzzValue(30));
              assertEquals("FizzBuzz", fizzBuzz.getFizzBuzzValue(45));
      
              // Test Fizz cases
              assertEquals("Fizz", fizzBuzz.getFizzBuzzValue(3));
              assertEquals("Fizz", fizzBuzz.getFizzBuzzValue(6));
              assertEquals("Fizz", fizzBuzz.getFizzBuzzValue(9));
      
              // Test Buzz cases
              assertEquals("Buzz", fizzBuzz.getFizzBuzzValue(5));
              assertEquals("Buzz", fizzBuzz.getFizzBuzzValue(10));
              assertEquals("Buzz", fizzBuzz.getFizzBuzzValue(20));
      
              // Test other cases
              assertEquals("1", fizzBuzz.getFizzBuzzValue(1));
              assertEquals("2", fizzBuzz.getFizzBuzzValue(2));
              assertEquals("4", fizzBuzz.getFizzBuzzValue(4));
              assertEquals("7", fizzBuzz.getFizzBuzzValue(7));
          }
      }""";
}
