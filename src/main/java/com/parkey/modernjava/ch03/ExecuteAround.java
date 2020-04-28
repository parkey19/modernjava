package com.parkey.modernjava.ch03;

import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ExecuteAround {

  static File file;

  public static void main(String... args) throws IOException {
    // 더 유연하게 리팩토링할 메서드
    file = new ClassPathResource("data.txt").getFile();

    String result = processFileLimited();
    System.out.println(result);

    System.out.println("---");

    String oneLine = processFile((BufferedReader b) -> b.readLine());
    System.out.println(oneLine);

    String twoLines = processFile((BufferedReader b) -> b.readLine() + b.readLine());
    System.out.println(twoLines);

  }

  public static String processFileLimited() throws IOException {
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      return br.readLine();
    }
  }

  public static String processFile(BufferedReaderProcessor p) throws IOException {
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      return p.process(br);
    }
  }

  @FunctionalInterface
  public interface BufferedReaderProcessor {
    String process(BufferedReader b) throws IOException;
  }

}
