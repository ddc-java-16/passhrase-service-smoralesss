package edu.cnm.deepdive.passphrase.service;

import edu.cnm.deepdive.passphrase.configuration.FileStorageConfiguration;
import edu.cnm.deepdive.passphrase.configuration.FileStorageConfiguration.FilenameProperties;
import edu.cnm.deepdive.passphrase.configuration.FileStorageConfiguration.FilenameProperties.TimestampProperties;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.TimeZone;
import java.util.random.RandomGenerator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Profile("service")
public class LocalFilesystemStorageService implements StorageService {

  private static final String KEY_PATH_DELIMITER = FileSystems.getDefault().getSeparator();
  private static final String INVALID_MEDIA_FORMAT = "%s is not allowed in this storage service.";

  private final RandomGenerator rng;
  private final Path uploadDirectory;
  private final Pattern subdirectoryPattern;
  private final Set<String> whitelist;
  private final DateFormat formatter;
  private final String filenameFormat;
  private final int randomizerLimit;

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
    filenameFormat = filenameProperties.getFormat();
    randomizerLimit = filenameProperties.getRandomizerLimit();
    formatter = new SimpleDateFormat(timestampProperties.getFormat());
    formatter.setTimeZone(TimeZone.getTimeZone(timestampProperties.getTimeZone()));
  }

  @Override
  public String store(MultipartFile file) throws StorageException, MediaTypeException {
    if (!whitelist.contains(file.getContentType())) {
      throw new MediaTypeException(String.format(INVALID_MEDIA_FORMAT, file.getContentType()));
    }
    String originalFilename = file.getOriginalFilename();
    String newFilename = String.format(
        filenameFormat,
        formatter.format(new Date()),
        rng.nextInt(randomizerLimit),
        getExtension((originalFilename != null) ? originalFilename : "")
    );
    String subdirectory = getSubdirectory(newFilename);
    Path resolvedPath = uploadDirectory.resolve(subdirectory);
    //noinspection ResultOfMethodCallIgnored
    resolvedPath.toFile().mkdirs();
    try {
      Files.copy(file.getInputStream(), resolvedPath.resolve(newFilename));
    } catch (IOException e) {
      throw new StorageException(e);
    }
    return newFilename;
  }

  @Override
  public Resource retrieve(String key) throws StorageException {
    try {
      return new UrlResource(resolve(key).toUri());
    } catch (MalformedURLException e) {
      throw new StorageException(e);
    }
  }

  @Override
  public boolean delete(String key)
      throws StorageException, UnsupportedOperationException, SecurityException {
    return false;
  }

  @NonNull
  private String getExtension(@NonNull String filename) {
    int position = filename.lastIndexOf('.');
    return (position >= 0)
        ? filename.substring(position + 1)
        : "";
  }

  @NonNull
  private String getSubdirectory(@NonNull String filename) {
    String path;
    Matcher matcher = subdirectoryPattern.matcher(filename);
    if (matcher.matches()) {
      path = IntStream.rangeClosed(1, matcher.groupCount())
          .mapToObj(matcher::group)
          .collect(Collectors.joining(KEY_PATH_DELIMITER));
    } else {
      path = "";
    }
    return path;
  }

  @NonNull
  private Path resolve(@NonNull String key) {
    return uploadDirectory.resolve(getSubdirectory(key)).resolve(key);
  }
}
