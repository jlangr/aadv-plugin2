package llms;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;

public class ExampleList {
   private List<Example> examples = new ArrayList<>();

   public ExampleList() {}

   public ExampleList(Example... examples) {
      this.examples.addAll(asList(examples));
   }

   public void add(String id, String name, String text) {
      examples.add(new Example(id, text, name));
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

   String concatenate() {
       return getAll().stream()
          .map(Example::toPromptString)
          .collect(joining("\n---\n"));
   }
}
