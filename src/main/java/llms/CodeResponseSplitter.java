package llms;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeResponseSplitter {

   public Files split(String response) {
      List<SourceFile> prodFiles = new ArrayList<>();
      List<SourceFile> testFiles = new ArrayList<>();

      // Patterns to match the production and test classes with file names
      Pattern prodPattern = Pattern.compile("/\\* prod class ([^*]+) \\*/\\s*(.*?)\\s*/\\* end prod class \\*/", Pattern.DOTALL);
      Pattern testPattern = Pattern.compile("/\\* test class ([^*]+) \\*/\\s*(.*?)\\s*/\\* end test class \\*/", Pattern.DOTALL);

      // Matcher for production classes
      Matcher prodMatcher = prodPattern.matcher(response);
      while (prodMatcher.find()) {
         String fileName = prodMatcher.group(1).trim();
         String prodClass = prodMatcher.group(2).trim();
         prodFiles.add(new SourceFile(FileType.PROD, prodClass, fileName));
      }

      // Matcher for test classes
      Matcher testMatcher = testPattern.matcher(response);
      while (testMatcher.find()) {
         String fileName = testMatcher.group(1).trim();
         String testClass = testMatcher.group(2).trim();
         testFiles.add(new SourceFile(FileType.TEST, testClass, fileName));
      }

      return new Files(prodFiles, testFiles);
   }
}
