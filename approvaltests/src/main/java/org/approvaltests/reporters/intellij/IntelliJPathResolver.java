package org.approvaltests.reporters.intellij;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

import com.spun.util.SystemUtils;
import java.util.stream.Stream;

public class IntelliJPathResolver
{
  private final String channelsPath;
  public IntelliJPathResolver(Edition edition)
  {
    this.channelsPath = getInstallRoot() + "/JetBrains/Toolbox/apps/" + edition.getDirectory() + "/ch-0/";
  }

  private String getRuntimeSuffix() {
    if (SystemUtils.isWindowsEnviroment())
    {
      return "/bin/idea64.exe";
    }
    else if (SystemUtils.isMacEnviroment())
    {
      return "/IntelliJ IDEA.app/Contents/MacOS/idea";
    }
    else // Linux
    {
      return "/bin/idea.sh";
    }
  }

  private String getInstallRoot() {
    if (SystemUtils.isWindowsEnviroment())
    {
      return System.getenv("LOCALAPPDATA");
    }
    else if (SystemUtils.isMacEnviroment())
    {
      return System.getenv("HOME") + "/Library/Application Support";
    }
    else // Linux
    {
      return System.getenv("HOME") +  "/.local/share";
    }
  }

  public String findIt()
  {
    String notPresentPath = "C:\\Intelli-not-present.exe";
    try
    {
      return getIntelliJPath().map(Objects::toString).orElse(notPresentPath);
    }
    catch (IOException e)
    {
      return notPresentPath;
    }
  }
  private Optional<Path> getIntelliJPath() throws IOException
  {
    try (Stream<Path> walk = Files.walk(Paths.get(channelsPath), 1, FileVisitOption.FOLLOW_LINKS))
    {
      return walk //
          .map(Path::getFileName) //
          .map(Objects::toString) //
          .filter(Version::isVersionFile) //
          .map(Version::new) //
          .max(Comparator.naturalOrder()) //
          .map(this::getPath);
    }
  }
  private Path getPath(Version version)
  {
    return Paths.get(channelsPath + version.version + getRuntimeSuffix()).toAbsolutePath();
  }
}
