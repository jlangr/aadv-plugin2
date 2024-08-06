package llms;

import org.junit.jupiter.api.Test;

import java.util.List;

import static llms.FileType.PROD;
import static llms.FileType.TEST;
import static llms.SampleSourceFiles.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ACodeResponseSplitter {
   @Test
   void splitsMultipleFiles() {
      var response = """
         /* prod class FizzBuzz.java */
         %s
         /* end prod class */

         /* prod class Helper.java */
         %s
         /* end prod class */

         /* test class FizzBuzzTest.java */
         %s
         /* end test class */
         """.formatted(fizzBuzzProdSource, helperProdSource, fizzBuzzTestSource);

      var files = new CodeResponseSplitter().split(response);

      assertEquals(
         new Files(
            List.of(
               new SourceFile(PROD, fizzBuzzProdSource, "FizzBuzz.java"),
               new SourceFile(PROD, helperProdSource, "Helper.java")),
            List.of(new SourceFile(TEST, fizzBuzzTestSource, "FizzBuzzTest.java"))),
         files);
   }
}
