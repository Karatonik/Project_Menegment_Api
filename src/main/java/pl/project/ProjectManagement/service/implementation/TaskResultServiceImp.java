package pl.project.ProjectManagement.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.project.ProjectManagement.model.Task;
import pl.project.ProjectManagement.model.TaskResult;
import pl.project.ProjectManagement.repository.TaskRepository;
import pl.project.ProjectManagement.repository.TaskResultRepository;
import pl.project.ProjectManagement.service.interfaces.TaskResultService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public TaskResult setTaskResult(TaskResult taskResult) {
        if (taskResult.getTask().getProject().getStudents().contains(taskResult.getStudent())) {
            return this.taskResultRepository.save(taskResult);
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
    public List<TaskResult> getTaskResultsByTask(long taskId, String projectOwnerEmail) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            if (task.getProject().getProjectOwner().getEmail().equals(projectOwnerEmail)) {
                return task.getTaskResults();
            }
        }
        return new ArrayList<>();
    }
}
