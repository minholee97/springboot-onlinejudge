package com.algorithm.service;

import com.algorithm.dto.CodeDto;
import com.algorithm.dto.TestCaseDto;
import com.algorithm.entity.TestCase;
import com.algorithm.repository.TestCaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CodeService {

    private final TestCaseRepository testCaseRepository;

    public List<TestCaseDto> loadTestCaseList(Long problemId) {
        List<TestCase> testCaseList = testCaseRepository.findByProblemIdOrderById(problemId);
        List<TestCaseDto> testCaseDtoList = new ArrayList<>();
        for (TestCase testCase : testCaseList) {
            testCaseDtoList.add(new TestCaseDto(testCase.getId(), testCase.getProblemId(), testCase.getInputData(), testCase.getOutputData()));
        }
        return testCaseDtoList;
    }

    public boolean preprocessing(CodeDto codeDto) throws IOException {
        String lang = codeDto.getLang();
        if (lang.equals("JAVA")) {
            BufferedOutputStream bs = null;
            String fileName = "Main.java";
            String fileContext = codeDto.getUserCode();
            try {
                bs = new BufferedOutputStream(new FileOutputStream("codes/" + fileName));
                bs.write(fileContext.getBytes());
            } catch (Exception e) {
                e.getStackTrace();
            } finally {
                bs.close();
            }
            try {
                Process process = Runtime.getRuntime().exec("cmd /c javac codes/Main.java");
                process.waitFor();
                if (new File("codes/Main.class").exists()) {
                    return true;
                } else {
                    return false;
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    public String execute(CodeDto codeDto) {
        String lang = codeDto.getLang();
        List<TestCaseDto> testCaseDtoList = loadTestCaseList(Long.parseLong(codeDto.getProblemId()));
        boolean correct = true;
        if (lang.equals("JAVA")) {
            try {
                for (TestCaseDto testCaseDto : testCaseDtoList) {
                    Runtime run = Runtime.getRuntime();
                    Process process = run.exec(new String[]{"cmd", "/c", "java codes/Main.java"});
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
                    writer.write(testCaseDto.getInputData());
                    writer.newLine();
                    writer.close();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String line = null;
                    StringBuffer sb = new StringBuffer();
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    String result = sb.toString();
                    boolean check = false;
                    System.out.println(testCaseDto.getInputData() + " " + testCaseDto.getOutputData());
                    if (result.equals(testCaseDto.getOutputData())) {
                        check = true;
                    } else {
                        check = false;
                    }
                    if (!check) {
                        correct = false;
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (correct)
            return "true";
        else
            return "false";
    }
}
