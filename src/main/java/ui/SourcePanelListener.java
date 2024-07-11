package ui;

import llms.SourceFile;

public interface SourcePanelListener {
   void applySourceToIDE(SourceFile sourceFile);
   void delete(SourceFile sourceFile);
}
