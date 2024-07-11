package llms;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.joining;

public class ExampleList {
   private List<Example> examples = new ArrayList<>();

   public void add(String id, String text) {
      examples.add(new Example(id, text));
   }

   public List<Example> getAll() {
      return examples;
   }

   public Example get(String id) {
      var result = examples.stream()
         .filter(e -> e.getId().equals(id))
         .findFirst();
      if (result.isEmpty())
         throw new ExampleNotFoundException();
      return result.get();
   }

   public void deleteExample(String id) {
      var iterator = examples.iterator();
      while (iterator.hasNext()) {
         var example = iterator.next();
         if (example.getId().equals(id)) {
            iterator.remove();
            break;
         }
      }
   }

   public void update(String id, String text) {
      var example = get(id);
      example.setText(text);
   }

   // TODO test
   String concatenateExamples() {
       return getAll().stream()
          .map(Example::getText)
          .collect(joining("\n---\n"));
   }
}
