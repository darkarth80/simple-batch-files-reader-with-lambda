package com.darkarth.demo;

import com.darkarth.demo.processor.FileProcessor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FileProcessorTest {

    @Autowired
    private FileProcessor fp;

    @Test
    public void listFiles(){
    }

}