package ui;

public interface ExampleListener {
   void add(String panelName, String name, String text);
   void upsert(String panelName, String name, String text);
   void delete(String name);
}
