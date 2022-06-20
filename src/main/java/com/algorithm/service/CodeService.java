package com.algorithm.service;

import com.algorithm.constant.Language;
import com.algorithm.constant.StatusType;
import com.algorithm.dto.CodeDto;
import com.algorithm.dto.TestCaseDto;
import com.algorithm.entity.Status;
import com.algorithm.entity.TestCase;
import com.algorithm.repository.StatusRepository;
import com.algorithm.repository.TestCaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CodeService {

    private final StatusRepository statusRepository;
    private final TestCaseRepository testCaseRepository;

    public List<TestCaseDto> loadTestCaseList(Long problemId) {
        List<TestCase> testCaseList = testCaseRepository.findByProblemIdOrderById(problemId);
        List<TestCaseDto> testCaseDtoList = new ArrayList<>();
        for (TestCase testCase : testCaseList) {
            testCaseDtoList.add(new TestCaseDto(testCase.getId(), testCase.getInputData(), testCase.getOutputData()));
        }
        return testCaseDtoList;
    }
    @Transactional
    public Status preprocessing(CodeDto codeDto) {
        if (codeDto.getLang().equals("JAVA")) {
            Status status = new Status(codeDto.getMemberEmail(), codeDto.getProblemId(), codeDto.getMemberCode(), StatusType.IN_PROGRESS, Language.JAVA);
            statusRepository.save(status);
            return status;
        }
        return null;
    }

    @Async
    @Transactional
    public void processing(CodeDto codeDto, Status status) throws IOException, InterruptedException {
        String lang = codeDto.getLang();
        if (lang.equals("JAVA")) {
            //status = new Status(codeDto.getMemberEmail(), codeDto.getProblemId(), codeDto.getMemberCode(), StatusType.IN_PROGRESS, Language.JAVA);
            //statusRepository.save(status);
            BufferedOutputStream bs = null;
            String fileName = "Main.java";
            String fileContext = codeDto.getMemberCode();
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

                } else {
                    status.updateStatusType(StatusType.COMPILE_ERROR);
                    statusRepository.save(status);
                    return;
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            List<TestCaseDto> testCaseDtoList = loadTestCaseList(codeDto.getProblemId());
            boolean correct = true;
            Runtime run = null;
            Process process = null;
            BufferedWriter writer = null;
            BufferedReader reader = null;
            String result = null;
            try {
                for (TestCaseDto testCaseDto : testCaseDtoList) {
                    run = Runtime.getRuntime();
                    process = run.exec(new String[]{"cmd", "/c", "java codes/Main.java"});
                    writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
                    writer.write(testCaseDto.getInputData());
                    writer.newLine();
                    writer.close();
                    String line = null;
                    StringBuffer sb = new StringBuffer();
                    reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    result = sb.toString();
                    boolean check = false;
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
            } finally {
                if (process != null) {
                    process.destroy();
                }
                if (reader != null) {
                    reader.close();
                }
            }
            if (correct) {
                status.updateStatusType(StatusType.SUCCESS);
                statusRepository.save(status);
                return;
            } else {
                status.updateStatusType(StatusType.FAIL);
                statusRepository.save(status);
                return;
            }
        }
    }
}
