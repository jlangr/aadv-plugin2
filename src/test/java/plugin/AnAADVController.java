package plugin;

import llms.AADVModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ui.AADVPromptPanel;
import utils.IDGenerator;

import static org.junit.jupiter.api.Assertions.assertSame;
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
   class Examples {
      AADVController controller = AADVController.get(null);
      @Mock
      AADVPromptPanel promptView;
      @Mock
      AADVModel model;
      @Mock
      IDGenerator idGenerator;

      @BeforeEach
      void setup() {
         when(idGenerator.generate()).thenReturn("1");

         controller.setIdGenerator(idGenerator);
         controller.setPromptView(promptView);
         controller.setModel(model);
      }

      @AfterEach
      void resetController() {
         AADVController.reset();
         Mockito.reset(idGenerator);
      }

      @Test
      void addedToModelAndPromptViewOnAddNewExample() {
         controller.addNewExample();

         verify(model).addExample("1");
         verify(promptView).addNewExample("1");
      }

      @Test
      void deletedFromModelAndPromptViewOnDelete() {
         controller.delete("1");

         verify(model).deleteExample("1");
         verify(promptView).deleteExample("1");
      }
   }
}