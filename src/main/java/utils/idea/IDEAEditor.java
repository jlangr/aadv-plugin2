package utils.idea;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.TextEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.IdeFocusManager;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import llms.SourceFile;
import utils.PackageExtractor;

import java.util.Optional;

import static com.intellij.openapi.command.WriteCommandAction.runWriteCommandAction;
import static com.intellij.openapi.ui.Messages.showMessageDialog;
import static com.intellij.util.ui.UIUtil.getWarningIcon;
import static java.lang.String.format;

// TODO: AI-generated. needs update to use non-deprecated APIs
public class IDEAEditor {
   static final String MSG_CANNOT_APPLY_SOURCE = "AADV: Unable to apply source";
   static final String MSG_SOURCE_NOT_FOUND = "Source file not found: %s. Create a new Java class with this name.";
   public static final String MSG_CANNOT_OPEN_EDITOR = "Unable to open editor: %s";

   public void replaceEditorContent(Project project, SourceFile sourceFile) {
      var virtualFileOptional = firstVirtualFileEndingWith(project, sourceFile.fileName());
      if (virtualFileOptional.isEmpty()) {
         // TODO temporary dump to see if problem happens again
         var virtualFilesByName = FilenameIndex.getVirtualFilesByName(project, sourceFile.fileName(), GlobalSearchScope.projectScope(project));
         System.out.println("files: " + virtualFilesByName);
         // end todo

         showMessageDialog(project,
            format(MSG_SOURCE_NOT_FOUND, sourceFile.fileName()), MSG_CANNOT_APPLY_SOURCE, getWarningIcon());
         return;
      }

      var virtualFile = virtualFileOptional.get();
      var fileEditorManager = FileEditorManager.getInstance(project);
      fileEditorManager.openFile(virtualFile, true);

      var editorOptional = first(virtualFile, fileEditorManager);
      if (editorOptional.isEmpty()) {
         showMessageDialog(project,
            format(MSG_CANNOT_OPEN_EDITOR, sourceFile.fileName()), MSG_CANNOT_APPLY_SOURCE, getWarningIcon());
         return;
      }

      replaceText(project, sourceFile, editorOptional.get());
      focus(project, editorOptional.get());
   }

   void replaceText(Project project, SourceFile sourceFile, Editor editor) {
      var newSource = new PackageExtractor().updateSource(sourceFile, editor.getDocument().getText());
      runWriteCommandAction(project, () -> editor.getDocument().setText(newSource));
   }

   private Optional<Editor> first(VirtualFile virtualFile, FileEditorManager fileEditorManager) {
      var editors = fileEditorManager.getEditors(virtualFile);
      return editors.length > 0
         ? Optional.of(((TextEditor) editors[0]).getEditor())
         : Optional.empty();
   }

   private void focus(Project project, Editor editor) {
      IdeFocusManager.getInstance(project).requestFocus(editor.getContentComponent(), true);
   }

   public Optional<VirtualFile> firstVirtualFileEndingWith(Project project, String filename) {
      return FilenameIndex
         .getVirtualFilesByName(project, filename, GlobalSearchScope.projectScope(project))
         .stream().findFirst();
   }
}
