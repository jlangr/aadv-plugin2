package utils;

import llms.SourceFile;
import java.util.Optional;
import java.util.regex.Pattern;

public class PackageExtractor {
   public SourcePackage extractPackage(String source) {
      return findPackageStatement(source)
         .map(statement -> new SourcePackage(true, statement))
         .orElseGet(() -> new SourcePackage(false, null));
   }

   private Optional<String> findPackageStatement(String source) {
      var pattern = Pattern.compile("^(\\s*(//.*|/\\*.*\\*/)*\\s*package\\s+[^;]+;)(\\s*//.*|\\s*/\\*.*\\*/)?(\\s*\\n|\\s*\\r\\n|\\s*;)?", Pattern.MULTILINE | Pattern.DOTALL);
      var matcher = pattern.matcher(source);
      return matcher.find() ? Optional.of(reformatPrefix(source, matcher.end(1))) : Optional.empty();
   }

   private String reformatPrefix(String source, int endIndex) {
      var packageStatement = source.substring(0, endIndex);
      var rest = source.substring(endIndex);

      var lines = rest.split("\\r?\\n");
      var formattedPrefix = new StringBuilder(packageStatement);
      for (var line : lines) {
         if (line.trim().isEmpty() || line.trim().startsWith("//") || line.trim().startsWith("/*")) {
            formattedPrefix.append(line).append('\n');
         } else {
            break;
         }
      }
      return formattedPrefix.toString();
   }

   public String updateSource(SourceFile sourceFile, String currentSource) {
      var sourcePackage = extractPackage(currentSource);
      if (!sourcePackage.hasPackageStatement()) return sourceFile.source();

      return sourcePackage.prefix() + sourceFile.source();
   }
}
