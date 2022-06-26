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
    public void processing(CodeDto codeDto, Status status) throws IOException, InterruptedException {
        String lang = codeDto.getLang();
        if (lang.equals("JAVA")) {
            BufferedOutputStream bs = null;
            String fileName = "Main.java";
            String fileContext = codeDto.getMemberCode();
            String path = "codes/" + String.valueOf(status.getId());
            File Folder = new File(path);
            if (!Folder.exists()) {
                Folder.mkdir();
            }
            bs = new BufferedOutputStream(new FileOutputStream(path + "/" + fileName));
            bs.write(fileContext.getBytes());
            bs.close();
            Process compileProcess = Runtime.getRuntime().exec("cmd /c javac " + path + "/" + fileName);
            compileProcess.waitFor();
            if (!new File(path + "/Main.class").exists()) {
                status.updateStatus(StatusType.COMPILE_ERROR, 0);
                statusRepository.save(status);
                return;
            }

            List<TestCaseDto> testCaseDtoList = loadTestCaseList(codeDto.getProblemId());
            boolean correct = true;
            Runtime run;
            Process process = null;
            BufferedWriter writer;
            BufferedReader reader = null;
            String result;
            float progress = 0l;
            int correctCount = 0;
            try {
                for (TestCaseDto testCaseDto : testCaseDtoList) {
                    run = Runtime.getRuntime();
                    process = run.exec(new String[]{"cmd", "/c", "java " + path + "/" + fileName});
                    writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
                    writer.write(testCaseDto.getInputData());
                    writer.newLine();
                    writer.close();
                    String line;
                    StringBuffer sb = new StringBuffer();
                    reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    result = sb.toString().stripTrailing();
                    if (!result.equals(testCaseDto.getOutputData())) {
                        correct = false;
                        break;
                    }
                    correctCount += 1;
                    progress = (float)correctCount / (float)testCaseDtoList.size() * 100;
                    status.updateStatus(StatusType.IN_PROGRESS, progress);
                    statusRepository.save(status);
                    statusRepository.flush();
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
                status.updateStatus(StatusType.SUCCESS, progress);
                statusRepository.save(status);
                return;
            } else {
                status.updateStatus(StatusType.FAIL, progress);
                statusRepository.save(status);
                return;
            }
        }
    }
}
