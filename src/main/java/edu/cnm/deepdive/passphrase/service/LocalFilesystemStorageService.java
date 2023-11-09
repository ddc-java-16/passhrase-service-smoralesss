package edu.cnm.deepdive.passphrase.service;

import edu.cnm.deepdive.passphrase.configuration.FileStorageConfiguration;
import edu.cnm.deepdive.passphrase.configuration.FileStorageConfiguration.FilenameProperties;
import edu.cnm.deepdive.passphrase.configuration.FileStorageConfiguration.FilenameProperties.TimestampProperties;
import java.io.IOException;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.random.RandomGenerator;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.multipart.MultipartFile;

@Service
@Profile("service")
public class LocalFilesystemStorageService implements StorageService {

  private static final String KEY_PATH_DELIMITER = "/";
  private static final String KEY_PATH_FORMAT = "%s" + KEY_PATH_DELIMITER + "%s";
  private static final String INVALID_MEDIA_FORMAT = "%s is not allowed in this storage service.";

  private final RandomGenerator rng;
  private final Path uploadDirectory;
  private final Pattern subdirectoryPattern;
  private final Set<String> whitelist;
  private final DateFormat formatter;
  private final String filenameFormat;
  private final int randomizerLimit;
  private final List<MediaType> contentTypes;

  @Autowired
  public LocalFilesystemStorageService(
      FileStorageConfiguration configuration, ApplicationHome home, RandomGenerator rng) {
    this.rng = rng;
    FilenameProperties filenameProperties = configuration.getFilename();
    TimestampProperties timestampProperties = filenameProperties.getTimestamp();
    String uploadPath = configuration.getDirectory();
    uploadDirectory = configuration.isApplicationHome()
        ? home.getDir().toPath().resolve(uploadPath)
        : Path.of(uploadPath);
    //noinspection ResultOfMethodCallIgnored
    uploadDirectory.toFile().mkdirs();
    subdirectoryPattern = configuration.getSubdirectoryPattern();
    whitelist = configuration.getWhitelist();
    contentTypes = whitelist
        .stream()
        .map(MediaType::valueOf)
        .collect(Collectors.toList());
    filenameFormat = filenameProperties.getFormat();
    randomizerLimit = filenameProperties.getRandomizerLimit();
    formatter = new SimpleDateFormat(timestampProperties.getFormat());
    formatter.setTimeZone(TimeZone.getTimeZone(timestampProperties.getTimeZone()));
  }

  @Override
  public String store(MultipartFile file) throws IOException, HttpMediaTypeException {
    return null;
  }

  @Override
  public Resource retrieve(String key) throws IOException {
    return null;
  }

  @Override
  public boolean delete(String key)
      throws IOException, UnsupportedOperationException, SecurityException {
    return false;
  }
}
