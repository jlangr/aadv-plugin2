package llms.openai;

import llms.Files;
import llms.Prompt;
import llms.SourceFile;
import plugin.settings.AADVPluginSettings;
import utils.Http;

import java.util.List;

import static llms.FileType.PROD;
import static llms.FileType.TEST;
import static llms.SampleSourceFiles.*;

public class StubOpenAIClient extends OpenAIClient {
   public StubOpenAIClient(Http http) {
      super(http, new AADVPluginSettings());
   }

   @Override
   public Files retrieveCompletion(Prompt prompt) {
      return new Files(
         List.of(
            new SourceFile(PROD, fizzBuzzProdSource, "FizzBuzz.java"),
            new SourceFile(PROD, helperProdSource, "Helper.java")),
         List.of(new SourceFile(TEST, fizzBuzzTestSource, "FizzBuzzTest.java")));
   }
}
