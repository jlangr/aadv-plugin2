package ui;

public interface ExampleListener {
   void upsert(String panelName, String name, String text);
   void delete(String name);
   void addNewExample();
}
