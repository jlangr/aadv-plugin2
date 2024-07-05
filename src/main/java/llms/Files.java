package llms;

import java.util.List;

public record Files(List<SourceFile> prodFiles, List<SourceFile> testFiles) {}
