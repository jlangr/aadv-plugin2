package ui;

public interface PromptListener {
   void send(String text);
   void dump();
   void update(String text);
}
