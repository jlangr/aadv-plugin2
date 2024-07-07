package ui;

import llms.SourceFile;

public interface SourcePanelListener {
   void update(SourceFile sourceFile);
   void delete(SourceFile sourceFile);
}
