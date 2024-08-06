package llms;

import java.util.ArrayList;
import java.util.List;
import static java.lang.String.format;
import static llms.PromptMessageType.system;
import static llms.PromptMessageType.user;

public class Prompt {

   static final String PROMPT_OVERVIEW = "Generate JUnit test class(es) and production Java code for the solution. " +
      "In output, begin each code listing with a header in either the form:\n" +
      "/* test class TestFileName.java */\n" +
      "or:\n" +
      "/* prod class ProdFileName.java */\n" +
      "End each code listing with a footer, either:\n" +
      "/* end test class */\n" +
      "or:\n" +
      "/* end prod class */. Substitute the real file name for TestFileName and ProdFileName. ";
   public static final String PROMPT_ASSISTANT_GUIDELINES = "You're a Java programming assistant. When asked to generate solution code, include only code. Don't include any explanation. Don't include comments in any code.";
   // TODO: show & allow updates in plugin configuration
   static final String PROMPT_CODE_STYLE = """
      - When possible, prefer functional solutions, with functional methods and immutable classes. Avoid side effects.
      - Extract implementation specifics to separate cohesive methods.
      - Extract conditionals to separate predicate methods.
      - Minimize use of temporary variables. Make calls to methods instead.
      """;
   static final String PROMPT_LANGUAGE_SPECIFIC_CODE_STYLE = """
      - Within chained calls using the streams interface, extract lambda bodies with implementation details to separate methods.
      - Create instance-side methods by default. Do not use static methods unless appropriate or otherwise asked.
      - In tests, do not start the name of the test method with the word "test".
      """;
   static final String PROMPT_TEXT = "Generate code for this:";
   static final String PROMPT_EXAMPLES = "Examples:";
   private final String promptText;
   private final ExampleList examples;

   public Prompt(String promptText, ExampleList examples) {
      this.promptText = promptText;
      this.examples = examples;
   }

   public List<PromptMessage> messages() {
      var messages = new ArrayList<PromptMessage>();
      messages.add(new PromptMessage(system, PROMPT_ASSISTANT_GUIDELINES));
      messages.add(new PromptMessage(system, PROMPT_CODE_STYLE));
      messages.add(new PromptMessage(system, PROMPT_LANGUAGE_SPECIFIC_CODE_STYLE));
      messages.add(new PromptMessage(user, examplesToText()));
      return messages;
   }

   private String examplesToText() {
      return format("%n%s%n", PROMPT_OVERVIEW) +
         format("%n%s%n", PROMPT_TEXT) +
         promptText +
         format("%n%s%n", PROMPT_EXAMPLES) +
         format("%n%s%n", examples.toPromptText());
   }
}
