package com.algorithm.service;

import com.algorithm.constant.Language;
import com.algorithm.constant.StatusType;
import com.algorithm.dto.CodeDto;
import com.algorithm.dto.TestCaseDto;
import com.algorithm.entity.Member;
import com.algorithm.entity.Problem;
import com.algorithm.entity.Status;
import com.algorithm.entity.TestCase;
import com.algorithm.repository.MemberRepository;
import com.algorithm.repository.ProblemRepository;
import com.algorithm.repository.StatusRepository;
import com.algorithm.repository.TestCaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;

class Timer extends Thread {
    Process process;
    Runtime run;
    String processName;
    String testInput;
    String folder;
    String file;
    String result = "";
    public Timer(Process process, Runtime run, String testInput, String folder, String file) {
        this.process = process;
        this.run = run;
        this.testInput = testInput;
        this.folder = folder;
        this.file = file;
    }
    @Override
    public void run() {
        try {
            process = run.exec("cmd /c java -cp ..\\algorithm\\codes\\" + folder + "\\ " + file);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
            writer.write(testInput);
            writer.newLine();
            writer.close();
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((line = reader.readLine()) != null) {
                result += line + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public Process getProcess() {
        return process;
    }
    public String getResult() {
        return result;
    }
}

@Service
@RequiredArgsConstructor
public class CodeService {

    private final StatusRepository statusRepository;
    private final TestCaseRepository testCaseRepository;
    private final MemberRepository memberRepository;
    private final ProblemRepository problemRepository;

    public List<TestCaseDto> loadTestCaseList(Long problemId) {
        List<TestCase> testCaseList = testCaseRepository.findByProblemIdOrderById(problemId);
        List<TestCaseDto> testCaseDtoList = new ArrayList<>();
        for (TestCase testCase : testCaseList) {
            testCaseDtoList.add(new TestCaseDto(testCase.getId(), testCase.getInputData(), testCase.getOutputData()));
        }
        return testCaseDtoList;
    }

    public Status preprocessing(CodeDto codeDto) {
        Member member = memberRepository.findByEmail(codeDto.getMemberEmail());
        Optional<Problem> optionalProblem = problemRepository.findById(codeDto.getProblemId());
        if (!optionalProblem.isPresent())
            return null;
        Problem problem = optionalProblem.get();

        if (codeDto.getLang().equals("JAVA")) {
            Status status = new Status(null, codeDto.getMemberCode(), StatusType.IN_PROGRESS, Language.JAVA, 0, member, problem);
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
            String fileName = "Main" + String.valueOf(status.getId()) + ".java";
            String fileContext = codeDto.getMemberCode();
            String convertedContext = "";
            StringTokenizer st = new StringTokenizer(fileContext);
            String prevToken = "";
            while(st.hasMoreTokens()) {
                String token = st.nextToken();
                if (token.equals("Main") && prevToken.equals("class"))
                    convertedContext += "Main" + String.valueOf(status.getId()) + " ";
                else
                    convertedContext += token + " ";
                prevToken = token;
            }
            String path = "codes/" + String.valueOf(status.getId());
            File Folder = new File(path);
            if (!Folder.exists()) {
                Folder.mkdir();
            }
            bs = new BufferedOutputStream(new FileOutputStream(path + "/" + fileName));
            bs.write(convertedContext.getBytes());
            bs.close();
            Process compileProcess = Runtime.getRuntime().exec("cmd /c javac " + path + "/" + fileName);
            compileProcess.waitFor();
            if (!new File(path + "/Main" + String.valueOf(status.getId()) +".class").exists()) {
                status.updateStatus(StatusType.COMPILE_ERROR, 0);
                statusRepository.save(status);
                return;
            }

            List<TestCaseDto> testCaseDtoList = loadTestCaseList(codeDto.getProblemId());
            boolean correct = true;
            boolean timeOver = false;
            Runtime run;
            Process process = null;
            String result;
            int timeLimit = status.getProblem().getTimeLimit();
            float progress = 0l;
            int correctCount = 0;
            try {
                for (TestCaseDto testCaseDto : testCaseDtoList) {
                    run = Runtime.getRuntime();
                    Timer timer = new Timer(process, run, testCaseDto.getInputData(), String.valueOf(status.getId()), "Main" + String.valueOf(status.getId()));
                    timer.start();
                    Thread.sleep(timeLimit * 1000);
                    result = timer.getResult().stripTrailing();

                    Process taskProcess = run.exec(new String[]{"cmd", "/c", "jps"});
                    BufferedReader reader = new BufferedReader(new InputStreamReader(taskProcess.getInputStream()));
                    String line = "";
                    ArrayList<String[]> list = new ArrayList<>();
                    while ((line = reader.readLine()) != null) {
                        StringTokenizer tokens = new StringTokenizer(line);
                        String pid = tokens.nextToken();
                        String name = "";
                        if(tokens.hasMoreTokens())
                            name = tokens.nextToken();
                        list.add(new String[] {pid, name});
                    }
                    for (String[] s : list) {
                        if (s[1].equals("Main" + String.valueOf(status.getId()))) {
                            timeOver = true;
                            Process taskKill = run.exec("cmd /c taskkill /F /pid " + s[0]);
                        }
                    }
                    if (timeOver) {
                        break;
                    }
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
            }
            if (timeOver) {
                status.updateStatus(StatusType.TIME_EXCEEDED, progress);
                statusRepository.save(status);
                return;
            } else if (correct) {
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
