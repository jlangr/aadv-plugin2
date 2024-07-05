package utils;

import llms.SourceFile;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static llms.FileType.PROD;
import static org.junit.jupiter.api.Assertions.*;

public class APackageExtractor {
   PackageExtractor packageExtractor = new PackageExtractor();

   @Test
   void testExtractPackageStatement() {
      var source = "package abc;\n\nclass ABC {}\n";
      var result = packageExtractor.extractPackage(source);
      assertTrue(result.hasPackageStatement());
      assertEquals("package abc;\n\n", result.prefix());
   }

   @Test
   void testNoPackageStatement() {
      var source = "import abc;";
      var result = packageExtractor.extractPackage(source);
      assertFalse(result.hasPackageStatement());
      assertNull(result.prefix());
   }

   @Test
   void testRetainLeadingComments() {
      var source = "package abc;\n// hey\nclass ABC {}\n";
      var result = packageExtractor.extractPackage(source);
      assertTrue(result.hasPackageStatement());
      assertEquals("package abc;\n// hey\n", result.prefix());
   }

   @Test
   void testRetainLeadingCommentsWithInlineComments() {
      var source = "package abc;// hey\nclass ABC {}\n";
      var result = packageExtractor.extractPackage(source);
      assertTrue(result.hasPackageStatement());
      assertEquals("package abc;// hey\n", result.prefix());
   }

   @Test
   void testExtractPackageWithWhitespace() {
      var source = "\n    \n   package def;\nclass ABC {}\n";
      var result = packageExtractor.extractPackage(source);
      assertTrue(result.hasPackageStatement());
      assertEquals("\n    \n   package def;\n", result.prefix());
   }

   @Test
   void testPackageWithInlineCommentsAndWhitespace() {
      var source = "package xyz; // whatever\n\nimport x;";
      var result = packageExtractor.extractPackage(source);
      assertTrue(result.hasPackageStatement());
      assertEquals("package xyz; // whatever\n\n", result.prefix());
   }

   @Test
   void testIncludesCommentsPriorToPackageStatement() {
      var source = "// hey\n/* here */\npackage mno;\n\nclass X {}";
      var result = packageExtractor.extractPackage(source);
      assertTrue(result.hasPackageStatement());
      assertEquals("// hey\n/* here */\npackage mno;\n\n", result.prefix());
   }

   @Test
   void testOmitClassDeclarationOnSameLine() {
      var source = "package xyz; class A {}";
      var result = packageExtractor.extractPackage(source);
      assertTrue(result.hasPackageStatement());
      assertEquals("package xyz;", result.prefix());
   }

   @Test
   void testIncludesCommentsPriorToAndAfterPackageStatement() {
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

