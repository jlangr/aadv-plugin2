package utils;

import llms.SourceFile;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static llms.FileType.PROD;
import static org.junit.jupiter.api.Assertions.*;

public class APackageExtractor {
   PackageExtractor packageExtractor = new PackageExtractor();

   @Test
   void extractPackageStatement() {
      var source = "package abc;\n\nclass ABC {}\n";
      var result = packageExtractor.extractPackage(source);
      assertTrue(result.hasPackageStatement());
      assertEquals("package abc;\n\n", result.prefix());
   }

   @Test
   void noPackageStatement() {
      var source = "import abc;";
      var result = packageExtractor.extractPackage(source);
      assertFalse(result.hasPackageStatement());
      assertNull(result.prefix());
   }

   @Test
   void retainLeadingComments() {
      var source = "package abc;\n// hey\nclass ABC {}\n";
      var result = packageExtractor.extractPackage(source);
      assertTrue(result.hasPackageStatement());
      assertEquals("package abc;\n// hey\n", result.prefix());
   }

   @Test
   void retainLeadingCommentsWithInlineComments() {
      var source = "package abc;// hey\nclass ABC {}\n";
      var result = packageExtractor.extractPackage(source);
      assertTrue(result.hasPackageStatement());
      assertEquals("package abc;// hey\n", result.prefix());
   }

   @Test
   void extractPackageWithWhitespace() {
      var source = "\n    \n   package def;\nclass ABC {}\n";
      var result = packageExtractor.extractPackage(source);
      assertTrue(result.hasPackageStatement());
      assertEquals("\n    \n   package def;\n", result.prefix());
   }

   @Test
   void packageWithInlineCommentsAndWhitespace() {
      var source = "package xyz; // whatever\n\nimport x;";
      var result = packageExtractor.extractPackage(source);
      assertTrue(result.hasPackageStatement());
      assertEquals("package xyz; // whatever\n\n", result.prefix());
   }

   @Test
   void includesCommentsPriorToPackageStatement() {
      var source = "// hey\n/* here */\npackage mno;\n\nclass X {}";
      var result = packageExtractor.extractPackage(source);
      assertTrue(result.hasPackageStatement());
      assertEquals("// hey\n/* here */\npackage mno;\n\n", result.prefix());
   }

   @Test
   void omitClassDeclarationOnSameLine() {
      var source = "package xyz; class A {}";
      var result = packageExtractor.extractPackage(source);
      assertTrue(result.hasPackageStatement());
      assertEquals("package xyz;", result.prefix());
   }

   @Test
   void includesCommentsPriorToAndAfterPackageStatement() {
      var source = "// hey\n/* here */\npackage mno;\n\n/* whatever */\n// hey\nclass X {}";
      var result = packageExtractor.extractPackage(source);
      assertTrue(result.hasPackageStatement());
      assertEquals("// hey\n/* here */\npackage mno;\n\n/* whatever */\n// hey\n", result.prefix());
   }

   @Nested
   class UpdateSource {
      @Test
      void returnsSourceAsIsIfNoPackageStatementExists() {
         var sourceFile = new SourceFile(PROD, "class New {}", "");

         var updatedSource = packageExtractor.updateSource(sourceFile, "class Existing {}");

         assertEquals("class New {}", updatedSource);
      }

      @Test
      void retainsPackageTextIfExists() {
         var sourceFile = new SourceFile(PROD, "class New {}", "");

         var updatedSource = packageExtractor.updateSource(sourceFile, "package x;\nclass Existing {}");

         assertEquals("package x;\nclass New {}", updatedSource);
      }
   }
}

