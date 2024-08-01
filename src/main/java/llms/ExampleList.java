package llms;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;

public class ExampleList {
   // TODO tests for all this!
   private List<Example> examples = new ArrayList<>();

   public ExampleList() {}

   public ExampleList(Example... examples) {
      this.examples.addAll(asList(examples));
   }

   // TODO test
   public void add(String id, String name, String text) {
      examples.add(new Example(id, name, text));
   }

   public List<Example> getAll() {
      return examples;
   }

   public Example get(String id) {
      var result = getExampleOptional(id);
      if (result.isEmpty())
         throw new ExampleNotFoundException();
      System.out.println("FOUND EXAMPLE WITH ID " + id);
      return result.get();
   }

   // TODO test
   public Optional<Example> getExampleOptional(String id) {
      return examples.stream()
         .filter(e -> e.getId().equals(id))
         .findFirst();
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

   public void update(String id, String name, String text) {
      var example = getExampleOptional(id);
      if (example.isEmpty())
         throw new ExampleNotFoundException();

      example.get().setText(text);
      example.get().setName(name);
   }

   public String concatenate() {
       return getAll().stream()
          .filter(Example::isEnabled)
          .map(Example::toPromptString)
          .collect(joining("\n---\n"));
   }

   public void toggleEnabled(String id) {
      var example = getExampleOptional(id);
      if (example.isPresent())
         example.get().toggleEnabled();
   }
}
