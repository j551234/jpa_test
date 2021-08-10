package com.island.jpa_test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@SpringBootTest
public class FileCompareTest {
    @Test
    void readTest() {
        String path = "/home/james/下載/CNTP_REC_20210608.D";
        List<String> sourceList = readLineByLineToList(path);
        sourceList.forEach(s -> System.out.println(s));
    }

    @Test
    void splitAndReportTest() {
        String path = "/home/james/下載/CNTP_REC_20210608.D";
        String path2 = "/home/james/下載/CNTP_REC_20210608 .D_2";
        List<String> compareList = readLineByLineToList(path2);
        List<String> sourceList = readLineByLineToList(path);
        List<String> reportList = getReport(compareList, sourceList);

        System.out.println(reportList.toString());

    }


    List<String> getReport(List<String> sourceList, List<String> compareList) {
        List<String> reportList = new ArrayList<>();
        for (int i = 0; i < sourceList.size(); i++) {
            String errorLine = compareResult(sourceList.get(i), compareList.get(i), i);
            if (!errorLine.isEmpty()) {
                reportList.add(errorLine);
            }
        }
        return reportList;
    }


    // add line report
    String compareResult(String checkLine, String compareLine, int lineNumber) {
        List<Boolean> testResult = compareLine(checkLine, compareLine);
        String resultString = reportByLine(testResult);
        StringBuilder sb = new StringBuilder();
        if (!resultString.isEmpty()) {
            sb.append("line ");
            sb.append(lineNumber);
            sb.append(" error column occurs :");
            sb.append(resultString);
        }
        return sb.toString();
    }

    // get each line column difference  occurs
    String reportByLine(List<Boolean> resultList) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < resultList.size(); i++) {
            if (!resultList.get(i)) {
                sb.append(i);
                sb.append(",");
            }
        }
        if (sb.length() != 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();

    }


    // line compare
    List<Boolean> compareLine(String checkLine, String compareLine) {
        List<Boolean> resultList = new ArrayList<>();
        List<String> sourceList = Arrays.asList(checkLine.split("\\|", -1));
        List<String> targetList = Arrays.asList(compareLine.split("\\|", -1));

        for (int i = 0; i < sourceList.size(); i++) {
            if (sourceList.get(i).equals(targetList.get(i))) {
                resultList.add(true);
            } else {
                resultList.add(false);
            }
        }

        return resultList;
    }


    private static List<String> readLineByLineToList(String filePath) {
        List<String> lineList = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> {
                lineList.add(s);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lineList;
    }
}
