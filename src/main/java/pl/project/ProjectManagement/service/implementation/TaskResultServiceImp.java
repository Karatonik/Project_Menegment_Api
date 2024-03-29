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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
                String fileName = LocalDateTime.now().format(formatter) + "-" + UUID.randomUUID();

                byte[] bytes = file.getBytes();
                Path path = Paths.get("src/main/resources/static/uploaded/" + fileName);
                Files.write(path, bytes);
                taskResult.setFileName(fileName);
                return this.taskResultRepository.save(taskResult);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new TaskResult();
    }

    @Override
    public TaskResult getTaskResult(long resultId, String projectOwnerEmail) {
        Optional<TaskResult> optionalTaskResult = this.taskResultRepository.findById(resultId);
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
    public File getTaskResultFile(long resultId, String projectOwnerEmail) {
        Optional<TaskResult> optionalTaskResult = this.taskResultRepository.findById(resultId);
        if (optionalTaskResult.isPresent()) {
            TaskResult taskResult = optionalTaskResult.get();
            if (taskResult.getTask().getProject()
                    .getProjectOwner().getEmail().contains(projectOwnerEmail)) {

                Path path = Paths.get("src/main/resources/static/uploaded/" + taskResult.getFileName());
                File file = new File(path.toUri());
                if (file.exists()) {
                    return file;
                }
            }
        }

        return null;
    }

    @Override
    public Page<TaskResult> getTaskResultsByTask(long taskId, String projectOwnerEmail, Pageable pageable) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            if (task.getProject().getProjectOwner().getEmail().equals(projectOwnerEmail)) {
                return taskResultRepository.findAllByTask(task, pageable);
            }
        }
        return Page.empty();
    }
}
