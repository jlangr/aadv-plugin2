package ui;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.function.Consumer;

public class JTextAreaDocumentListener implements DocumentListener {
   private final Consumer<DocumentEvent> callback;

   public JTextAreaDocumentListener(Consumer<DocumentEvent> callback) {
      this.callback = callback;
   }

   @Override
   public void insertUpdate(DocumentEvent e) {
      callback.accept(e);
   }

   @Override
   public void removeUpdate(DocumentEvent e) {
      callback.accept(e);
   }

   @Override
   public void changedUpdate(DocumentEvent e) {
      callback.accept(e);
   }
}
