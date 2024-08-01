package ui;

public interface ExampleListener {
   void update(String panelName, String name, String text);
   void delete(String name);
   void addNewExample();
   void toggleEnabled(String name);
}
