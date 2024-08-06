package plugin;

import llms.*;
import llms.openai.OpenAI;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import plugin.settings.AADVPluginSettings;
import ui.AADVPromptPanel;
import ui.SourcePanel;
import ui.SourcePanelListener;
import utils.IDGenerator;
import utils.idea.IDEAEditor;

import javax.swing.*;

import java.util.List;

import static llms.FileType.PROD;
import static llms.FileType.TEST;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnAADVController {
   @Test
   void isRetrievedViaSingletonAccessor() {
      var controller = AADVController.get(null);
      var controllerToo = AADVController.get(null);

      assertSame(controller, controllerToo);
   }

   @Nested
   class WhenInitializedWithMocks {
      AADVController controller = AADVController.get(null);
      AADVModel model = new AADVModel();

      @Mock
      AADVPromptPanel promptView;
      @Mock
      AADVOutputPanel outputView;
      @Mock
      IDGenerator idGenerator;
      @Mock
      IDEAEditor ideaEditor;
      @Mock
      AADVPluginSettings aadvPluginSettings;
      @Mock
      OpenAI openAI;

      @BeforeEach
      void setup() {
         when(idGenerator.generate()).thenReturn("1");

         controller.setIdGenerator(idGenerator);
         controller.setPromptView(promptView);
         controller.setOutputView(outputView);
         controller.setModel(model);
         controller.setIDEAEditor(ideaEditor);
         controller.setAADVPluginSettings(aadvPluginSettings);
         controller.setOpenAI(openAI);
      }

      @AfterEach
      void resetController() {
         AADVController.reset();
         Mockito.reset(idGenerator);
      }

      @Nested
      class SendPrompt {
         @Test
         void showsErrorWhenApiKeyNull() {
            when(aadvPluginSettings.retrieveAPIKey()).thenReturn(null);

            controller.send("");

            verify(promptView).showMessage(AADVPromptPanel.MSG_KEY_NOT_CONFIGURED);
         }

         @Nested
         class WithApiKey {
            SourceFile prodFile = new SourceFile(PROD, "class X{}", "X.java");
            SourceFile testFile = new SourceFile(TEST, "class AnX{}", "AnX.java");
            Files files = new Files(List.of(prodFile), List.of(testFile));

            @BeforeEach
            void setup() {
               when(aadvPluginSettings.retrieveAPIKey()).thenReturn("key");
               when(promptView.getParent()).thenReturn(new JPanel());
            }

            @Test
            void retrievesCompletionFromOpenAI () throws InterruptedException {
               when(openAI.retrieveCompletion(new Prompt("text", new ExampleList())))
                  .thenReturn(files);

               controller.send("text");
               controller.thread.join();

               assertEquals("X.java", model.getPanel(prodFile).get().getName());
               assertEquals("AnX.java", model.getPanel(testFile).get().getName());
            }

            @Test
            void updatesPanelWithNewSource() throws InterruptedException {
               model.add(new SourcePanel(prodFile, controller));
               var updatedProdFile = new SourceFile(PROD, "class X{int x;}", "X.java");
               var files = new Files(List.of(updatedProdFile), List.of());
               when(openAI.retrieveCompletion(new Prompt("text", new ExampleList())))
                  .thenReturn(files);

               controller.send("text");
               controller.thread.join();

               assertEquals("class X{int x;}", model.getPanel(prodFile).get().getContent());
            }
         }
      }

      @Nested
      class SourcePanels {
         @Test
         void applySourceToIDEReplacesEditorContent() {
            var sourceFile = new SourceFile(TEST, "class X{}", "X.java");

            controller.applySourceToIDE(sourceFile);

            verify(ideaEditor).replaceEditorContent(null, sourceFile);
         }

         @Test
         void delete() {
            var sourceFile = new SourceFile(TEST, "class X{}", "X.java");
            var sourcePanel = new SourcePanel(sourceFile, mock(SourcePanelListener.class));
            model.add(sourcePanel);
            assertFalse(model.getPanel(sourceFile).isEmpty());

            controller.delete(sourceFile);

            verify(controller.getOutputView()).removeSourcePanel(sourcePanel);
            assertTrue(model.getPanel(sourceFile).isEmpty());
         }
      }

      @Nested
      class PromptText {
         @Test
         void updatesModelWithPromptTest() {
            controller.update("text");

            assertEquals("text", model.getPromptText());
         }
      }

      @Nested
      class Dump {
         @Test
         void writesPromptSummaryToConsole() {
            model.setPromptText("prompt text");
            model.addExample("1");
            model.updateExample("1", "example name", "example text");

            controller.dump();

            assertTrue(controller.getConsole().lastMessage().contains("prompt text"));
            assertTrue(controller.getConsole().lastMessage().contains("example name\nexample text"));
         }
      }

      @Nested
      class Examples {
         @Test
         void updateChangesExampleData() {
            model.addExample("1");

            controller.update("1", "name", "text");

            assertEquals(new Example("1", "name", "text"), model.getExample("1"));
         }

         @Test
         void addedToModelAndPromptViewOnAddNewExample() {
            controller.addNewExample();

            var example = model.getExample("1");
            var expectedExample = new Example("1", "", "");
            assertEquals(expectedExample, example);
            verify(promptView).addNewExample(expectedExample);
         }

         @Test
         void deletedFromModelAndPromptViewOnDelete() {
            controller.delete("1");

            assertThrows(ExampleNotFoundException.class, () -> model.getExample("1"));
            verify(promptView).deleteExample("1");
         }

         @Nested
         class ToggleEnabled {
            @Test
            void updatesModelExample() {
               model.addExample("1");

               controller.toggleEnabled("1");

               assertFalse(model.getExample("1").isEnabled());
            }

            @Test
            void refreshesPromptView() {
               model.addExample("1");
               var example = new Example("1", "", "", false);

               controller.toggleEnabled("1");

               verify(promptView).refreshExample(example);
            }
         }
      }
   }
}