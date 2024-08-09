package ui;

import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBScrollPane;
import llms.SourceFile;
import utils.UI;
import javax.swing.*;
import java.awt.*;

public class SourcePanel extends JPanel {
   public static final String CHIP_TEXT_PROD = "prod";
   public static final String CHIP_TEXT_TEST = "test";
   private static final String MSG_TIP_APPLY_SOURCE = "Apply generated code to project";
   private static final String MSG_CLOSE = "Close source panel";
   private final JTextArea textArea;
   private SourceFile sourceFile;
   private final SourcePanelListener sourcePanelListener;

   public SourcePanel(SourceFile sourceFile, SourcePanelListener sourcePanelListener) {
      this.sourceFile = sourceFile;
      this.sourcePanelListener = sourcePanelListener;

      setName(sourceFile.fileName());

      setLayout(new BorderLayout());

      add(createTitlePanel(), BorderLayout.NORTH);

      textArea = new JTextArea(sourceFile.source());
      textArea.setEditable(false);
      add(new JBScrollPane(textArea), BorderLayout.CENTER);

      setPreferredSize(new Dimension(400, 100));
      setMinimumSize(new Dimension(400, 100));
   }

   private JPanel createTitlePanel() {
      var panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
      panel.add(createCloseButton());
      panel.add(createUpdateButton());
      panel.add(createSourceTypeChip(sourceFile));
      panel.add(createFilenameLabel());
      return panel;
   }

   private JLabel createFilenameLabel() {
      var filenameLabel = new JLabel(sourceFile.fileName());
      var boldFont = new Font(filenameLabel.getFont().getName(), Font.BOLD, filenameLabel.getFont().getSize());
      filenameLabel.setFont(boldFont);
      return filenameLabel;
   }

   private JButton createUpdateButton() {
      return UI.createIconButton("right.png", MSG_TIP_APPLY_SOURCE,
         e -> sourcePanelListener.applySourceToIDE(sourceFile));
   }

   private JButton createCloseButton() {
      return UI.createIconButton("close_icon.png", MSG_CLOSE,
         e -> sourcePanelListener.delete(sourceFile));
   }

   private JPanel createSourceTypeChip(SourceFile sourceFile) {
      return switch (sourceFile.fileType()) {
         case PROD -> new Chip(CHIP_TEXT_PROD, JBColor.BLUE);
         case TEST -> new Chip(CHIP_TEXT_TEST, JBColor.GREEN);
      };
   }

   public void updateContent(SourceFile file) {
      this.sourceFile = file;
      textArea.setText(sourceFile.source());
   }

   public String getContent() {
      return textArea.getText();
   }
}
