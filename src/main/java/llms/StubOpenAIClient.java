package llms;

import java.util.List;

import static llms.FileType.PROD;
import static llms.FileType.TEST;
import static llms.SampleSourceFiles.*;

public class StubOpenAIClient extends OpenAIClient {
   @Override
   public Files retrieveCompletion(String prompt, ExampleList examples) {
      return new Files(
         List.of(
            new SourceFile(PROD, fizzBuzzProdSource, "FizzBuzz.java"),
            new SourceFile(PROD, helperProdSource, "Helper.java")),
         List.of(new SourceFile(TEST, fizzBuzzTestSource, "FizzBuzzTest.java")));
   }
}
