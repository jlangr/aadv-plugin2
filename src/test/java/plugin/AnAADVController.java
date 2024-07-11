package plugin;

import llms.AADVModel;
import llms.Example;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ui.AADVPromptPanel;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AnAADVController {
   @Test
   void isRetrievedViaSingletonAccessor() {
      var controller = AADVController.get(null);
      var controllerToo = AADVController.get(null);

      assertSame(controller, controllerToo);
   }

   @Nested
   class Examples {
      AADVController controller = AADVController.get(null);
      AADVPromptPanel promptView = mock(AADVPromptPanel.class);
      AADVModel model = mock(AADVModel.class);
      static final Example ABC_EXAMPLE = new Example("abc", "ABC panel");

      @BeforeEach
      void setup() {
         controller.setPromptView(promptView);
         controller.setModel(model);
      }

      @AfterEach
      void resetController() {
         AADVController.reset();
      }

      @Test
      void addedToModelOnAdd() {
         controller.add(ABC_EXAMPLE.getId(), ABC_EXAMPLE.getText());

         verify(model).addExample(ABC_EXAMPLE.getId(), ABC_EXAMPLE.getText());
      }

      @Test
      void deletedFromModelOnDelete() {
         controller.delete(ABC_EXAMPLE.getId());

         verify(model).deleteExample(ABC_EXAMPLE.getId());
      }

      @Test
      void refreshesPromptViewOnAdd() {
         when(model.getExamples()).thenReturn(List.of(ABC_EXAMPLE));

         controller.add(ABC_EXAMPLE.getId(), ABC_EXAMPLE.getText());

         verify(promptView).refreshExamples(List.of(ABC_EXAMPLE));
      }

      @Test
      void refreshesPromptViewOnDelete() {
         when(model.getExamples()).thenReturn(List.of(ABC_EXAMPLE));

         controller.delete(ABC_EXAMPLE.getId());

         verify(promptView).refreshExamples(List.of(ABC_EXAMPLE));
      }
   }
}