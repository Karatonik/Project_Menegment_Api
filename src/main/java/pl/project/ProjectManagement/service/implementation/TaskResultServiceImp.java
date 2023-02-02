package pl.project.ProjectManagement.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.project.ProjectManagement.model.Task;
import pl.project.ProjectManagement.model.TaskResult;
import pl.project.ProjectManagement.repository.TaskRepository;
import pl.project.ProjectManagement.repository.TaskResultRepository;
import pl.project.ProjectManagement.service.interfaces.TaskResultService;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskResultServiceImp implements TaskResultService {

    private final TaskResultRepository taskResultRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public TaskResultServiceImp(TaskResultRepository taskResultRepository,
                                TaskRepository taskRepository) {
        this.taskResultRepository = taskResultRepository;
        this.taskRepository = taskRepository;

    }

    @Override
    public TaskResult setTaskResult(TaskResult taskResult, MultipartFile file) {
        if (taskResult.getTask().getProject().getStudents().contains(taskResult.getStudent()) && (!file.isEmpty())) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss-SSS");
                System.out.println(file.getOriginalFilename());
                String uploadedFileName = file.getOriginalFilename();
//                Optional<String> extension = Optional.ofNullable(uploadedFileName)
//                        .filter(f -> f.contains("."))
//                        .map(f -> f.substring((uploadedFileName.lastIndexOf("." + 1))));
                String extension = com.google.common.io.Files.getFileExtension(uploadedFileName);
                System.out.println("extension: " + extension);
                String fileName = LocalDateTime.now().format(formatter) + "-" + UUID.randomUUID() + "." + extension;

                byte[] bytes = file.getBytes();
                Path path = Paths.get("src/main/resources/static/uploaded/" + fileName);
                Files.write(path, bytes);
                taskResult.setFileName(fileName);
                taskResult= this.taskResultRepository.save(taskResult);
                System.out.println(taskResult);
                return taskResult;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new TaskResult();
    }

    @Override
    public TaskResult getTaskResult(long resultId, String projectOwnerEmail) {
        Optional<TaskResult> optionalTaskResult = this.taskResultRepository.findById(resultId);
        System.out.println("opt: "+optionalTaskResult);
        if (optionalTaskResult.isPresent()) {
            TaskResult taskResult = optionalTaskResult.get();
            if (taskResult.getTask().getProject()
                    .getProjectOwner().getEmail().contains(projectOwnerEmail)) {
                return taskResult;
            }
        }

        return new TaskResult();
    }

    @Override
    public File getTaskResultFile(long resultId, String projectOwnerEmail) throws IOException {
        System.out.println("resultId: " + resultId);
        System.out.println("projectOwnerEmail: " + projectOwnerEmail);
        Optional<TaskResult> optionalTaskResult = this.taskResultRepository.findById(resultId);
        System.out.println("task result: " + optionalTaskResult);
        System.out.println("Czy jest present: " + optionalTaskResult.isPresent());
        System.out.println(resultId);
        if (optionalTaskResult.isPresent()) {
            TaskResult taskResult = optionalTaskResult.get();
            if (taskResult.getTask().getProject()
                    .getProjectOwner().getEmail().contains(projectOwnerEmail)) {

                Path path = Paths.get("src/main/resources/static/uploaded/" + taskResult.getFileName());
                System.out.println(path);
                File file = new File(path.toUri());
                System.out.println(file);
                if (file.exists()) {
                    return file;
                }
                return file;
            }
        }

        return null;
    }

    @Override
    public Page<TaskResult> getTaskResultsByTask(long taskId, String projectOwnerEmail, Pageable pageable) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            System.out.println(task);
            System.out.println(projectOwnerEmail);
            if (task.getProject().getProjectOwner().getEmail().equals(projectOwnerEmail)) {
                System.out.println("tesst"+taskResultRepository.findAllByTask(task));
                return taskResultRepository.findAllByTask(task, pageable);
            }
        }
        return Page.empty();
    }
}
