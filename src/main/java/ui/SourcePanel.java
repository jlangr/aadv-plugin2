package ui;

import com.intellij.ui.components.JBScrollPane;
import llms.SourceFile;

import javax.swing.*;
import java.awt.*;

public class SourcePanel extends JPanel {
   public static final String CHIP_TEXT_PROD = "prod";
   public static final String CHIP_TEXT_TEST = "test";
   private static final String MSG_TIP_APPLY_SOURCE = "Apply generated code to project";
   private static final String MSG_CLOSE = "Close source panel";
   private final JTextArea textArea;
   private final SourceFile sourceFile;
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
      var button = createIconButton("right.png", MSG_TIP_APPLY_SOURCE);
      button.addActionListener(e -> sourcePanelListener.applySourceToIDE(sourceFile));
      return button;
   }

   private JButton createCloseButton() {
      var closeButton = createIconButton("close_icon.png", MSG_CLOSE);
      closeButton.addActionListener(e -> sourcePanelListener.delete(sourceFile));
      return closeButton;
   }

   private JButton createIconButton(String imageFilename, String toolTipText) {
      var icon = new ImageIcon(getClass().getResource(imageFilename));
      var button = new JButton(icon);
      button.setToolTipText(toolTipText);
      button.setMargin(new Insets(0, 0, 0, 0));
      button.setContentAreaFilled(false);
      button.setBorderPainted(false);
      button.setFocusPainted(false);
      button.setPreferredSize(new Dimension(16, 16));
      return button;
   }

   private JPanel createSourceTypeChip(SourceFile sourceFile) {
      return switch (sourceFile.fileType()) {
         case PROD -> new Chip(CHIP_TEXT_PROD, Color.BLUE);
         case TEST -> new Chip(CHIP_TEXT_TEST, new Color(0, 150, 0));
      };
   }

   public void updateContent(SourceFile file) {
      textArea.setText(file.source());
   }
}
