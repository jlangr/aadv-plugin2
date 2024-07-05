package ui;

import com.intellij.ui.components.JBScrollPane;
import llms.SourceFile;
import javax.swing.*;
import java.awt.*;

public class SourcePanel extends JPanel {
   public static final String UPDATE_SOURCEBASE_WITH_CODE = "Apply";
   public static final String CHIP_TEXT_PROD = "prod";
   public static final String CHIP_TEXT_TEST = "test";

   public SourcePanel(SourceFile sourceFile, UpdateFileListener updateFileListener) {
      setLayout(new GridBagLayout());
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.insets = new Insets(5, 5, 5, 5);
      gbc.fill = GridBagConstraints.HORIZONTAL;

      // Icon or chit for file type
      gbc.gridx = 0;
      gbc.gridy = 0;
      gbc.weightx = 0;
      JPanel chip = createSourceTypeChip(sourceFile);
      add(chip, gbc);

      // File name label
      gbc.gridx = 1;
      gbc.gridy = 0;
      gbc.weightx = 1.0;
      gbc.anchor = GridBagConstraints.WEST;
      JLabel filenameLabel = new JLabel(sourceFile.fileName());
      Font boldFont = new Font(filenameLabel.getFont().getName(), Font.BOLD, filenameLabel.getFont().getSize());
      filenameLabel.setFont(boldFont);
      add(filenameLabel, gbc);

      // Update button
      gbc.gridx = 2;
      gbc.gridy = 0;
      gbc.weightx = 0;
      gbc.anchor = GridBagConstraints.EAST;
      JButton updateSourcebaseWithCodeButton = new JButton(UPDATE_SOURCEBASE_WITH_CODE);
      updateSourcebaseWithCodeButton.addActionListener(e -> updateFileListener.update(sourceFile));
      add(updateSourcebaseWithCodeButton, gbc);

      // Scrollable text area
      gbc.gridx = 0;
      gbc.gridy = 1;
      gbc.gridwidth = 3;
      gbc.fill = GridBagConstraints.BOTH;
      gbc.weightx = 1.0;
      gbc.weighty = 1.0;
      JTextArea textArea = new JTextArea(sourceFile.source());
      textArea.setEditable(false);
      JScrollPane scrollPane = new JBScrollPane(textArea);
      add(scrollPane, gbc);

      setPreferredSize(new Dimension(400, 100));
      setMinimumSize(new Dimension(400, 100));
   }

   private JPanel createSourceTypeChip(SourceFile sourceFile) {
      return switch (sourceFile.fileType()) {
         case PROD -> new Chip(CHIP_TEXT_PROD, Color.BLUE);
         case TEST -> new Chip(CHIP_TEXT_TEST, new Color(0, 150, 0));
      };
   }
}

