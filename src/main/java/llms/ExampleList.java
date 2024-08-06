package llms;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class ExampleList {
   private List<Example> examples = new ArrayList<>();

   public ExampleList() {}

   public ExampleList(Example... examples) {
      this.examples.addAll(asList(examples));
   }

   public void add(String id, String name, String text) {
      examples.add(new Example(id, name, text));
   }

   public List<Example> getAll() {
      return examples;
   }

   public Example get(String id) {
      var result = getOptional(id);
      if (result.isEmpty())
         throw new ExampleNotFoundException();
      return result.get();
   }

   public Optional<Example> getOptional(String id) {
      return examples.stream()
         .filter(e -> e.id().equals(id))
         .findFirst();
   }

   public void delete(String id) {
      var iterator = examples.iterator();
      while (iterator.hasNext()) {
         var example = iterator.next();
         if (example.id().equals(id)) {
            iterator.remove();
            break;
         }
      }
   }

   public void update(String id, String name, String text) {
      examples = examples.stream()
         .map(example -> example.id().equals(id)
            ? new Example(id, name, text, example.isEnabled())
            : example)
         .collect(toList());
   }

   public String toPromptText() {
       return getAll().stream()
          .filter(Example::isEnabled)
          .map(Example::toPromptText)
          .collect(joining("\n---\n"));
   }

   public void toggleEnabled(String id) {
      examples = examples.stream()
         .map(example -> example.id().equals(id)
            ? new Example(id, example.name(), example.text(), !example.isEnabled())
            : example)
         .collect(toList());
   }

   // generated

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      ExampleList that = (ExampleList) o;
      return Objects.equals(examples, that.examples);
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(examples);
   }
}
