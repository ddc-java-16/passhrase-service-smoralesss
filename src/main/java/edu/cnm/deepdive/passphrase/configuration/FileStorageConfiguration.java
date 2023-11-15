package edu.cnm.deepdive.passphrase.configuration;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "file-storage")
public class FileStorageConfiguration {

  /** Flag controlling whether the application home directory will be used as the file storage home. */
  private boolean applicationHome = true;
  private List<String> contentTypes;
  private String directory = "uploads";
  private Pattern subdirectoryPattern = Pattern.compile("^(.{4})(.{2})(.{2}).*$");
  private FilenameProperties filename;

  public boolean isApplicationHome() {
    return applicationHome;
  }

  public void setApplicationHome(boolean applicationHome) {
    this.applicationHome = applicationHome;
  }

  public List<String> getContentTypes() {
    return contentTypes;
  }

  public void setContentTypes(List<String> contentTypes) {
    this.contentTypes = contentTypes;
  }

  public String getDirectory() {
    return directory;
  }

  public void setDirectory(String directory) {
    this.directory = directory;
  }

  public Pattern getSubdirectoryPattern() {
    return subdirectoryPattern;
  }

  public void setSubdirectoryPattern(Pattern subdirectoryPattern) {
    this.subdirectoryPattern = subdirectoryPattern;
  }

  public FilenameProperties getFilename() {
    return filename;
  }

  public void setFilename(
      FilenameProperties filename) {
    this.filename = filename;
  }

  public static class FilenameProperties {

    private String unknown;
    private String format;
    private int randomizerLimit;
    private TimestampProperties timestamp;


    public String getUnknown() {
      return unknown;
    }

    public void setUnknown(String unknown) {
      this.unknown = unknown;
    }

    public String getFormat() {
      return format;
    }

    public void setFormat(String format) {
      this.format = format;
    }

    public int getRandomizerLimit() {
      return randomizerLimit;
    }

    public void setRandomizerLimit(int randomizerLimit) {
      this.randomizerLimit = randomizerLimit;
    }

    public TimestampProperties getTimestamp() {
      return timestamp;
    }

    public void setTimestamp(
        TimestampProperties timestamp) {
      this.timestamp = timestamp;
    }

    public static class TimestampProperties {

      private String format;
      private String timeZone;

      public String getFormat() {
        return format;
      }

      public void setFormat(String format) {
        this.format = format;
      }

      public String getTimeZone() {
        return timeZone;
      }

      public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
      }
    }
  }
}
