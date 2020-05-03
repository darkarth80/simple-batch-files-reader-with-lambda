package com.darkarth.demo.processor;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.darkarth.demo.exception.UncheckedException;
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
        try (Stream<String> line = Files.lines(path, StandardCharsets.UTF_8)) {
            AtomicInteger idx = new AtomicInteger(1);
            List<SimpleDTO> list = line
            .map(
                l -> validate(l, path.toString(), idx.getAndIncrement())
            )
            .filter(
                Objects::nonNull
            ).collect(Collectors.toList());
            LOGGER.debug("list-values-length: {}", list.size());

        } catch (IOException e) {
            LOGGER.error("There has been an error while processing files.", e);
        }
    }

    private List<Path> getPaths() {
        LOGGER.debug("Setting: baseDir: {}", baseDir);

        try (Stream<Path> walk = Files.walk(Paths.get(baseDir))) {

            return walk.filter(
                p -> filterFilesByName(p.toFile())
            ).collect(Collectors.toList());
    
        } catch (IOException e) {
            LOGGER.error("There has been an error while loading files.", e);
            return null;
        }
    }

    private boolean filterFilesByName(File file) {
        boolean valid = false;
        if (!file.isDirectory()) {
            if (file.getName().matches(filePattern)) {
                LOGGER.debug("File {} matched the regex.", file.getName());
                valid = true;
            } else {
                LOGGER.debug("File {} not matched the regex.", file.getName());
                valid = false;
            }
        }
        return valid;
    }

    public SimpleDTO validate(String line, String path, int idx) {
        try {
            return lu.transform(line);
        } catch (UncheckedException e) {
            LOGGER.debug(String.format("There was an error in file %s on line %d will be excluded from processing.", path, idx), e);
            return null;
        }
    }

}