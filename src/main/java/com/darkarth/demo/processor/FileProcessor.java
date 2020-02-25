package com.darkarth.demo.processor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.darkarth.demo.model.dto.SimpleDTO;
import com.darkarth.demo.util.LineUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileProcessor.class);

    @Value("${app.conf.file.base-dir}")
    private String baseDir;

    @Value("${app.conf.file.file-pattern}")
    private String filePattern;

    @Autowired
    private LineUtil lu;

    public void processFiles() {

        for (Path path : getPaths()) {
            processFile(path);
        }

    }

    private void processFile(Path path) {
        try (Stream<String> line = Files.lines(path)) {
            List<SimpleDTO> list = line
            .map(
                p -> lu.transform(p)
            )
            .filter(
                o -> o != null
            ).collect(Collectors.toList());
        } catch (IOException e) {
            LOGGER.error("There has been an error while loading files.", e);
        }
    }

    private List<Path> getPaths() {
        try (Stream<Path> walk = Files.walk(Paths.get(baseDir))) {

            return walk.filter(
                p -> filterFilesByName(p.getFileName().toString())
            ).collect(Collectors.toList());
    
        } catch (IOException e) {
            LOGGER.error("There has been an error while loading files.", e);
            return null;
        }
    }

    private boolean filterFilesByName(String name) {
        boolean valid = false;
        valid = name.matches(filePattern);
        if (valid) {
            LOGGER.debug("File {} matched the regex.", name);
        } else {
            LOGGER.debug("File {} not matched the regex.", name);
        }
        return valid;
    }

}