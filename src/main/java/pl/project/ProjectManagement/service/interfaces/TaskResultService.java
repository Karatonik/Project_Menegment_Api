package pl.project.ProjectManagement.service.interfaces;

import pl.project.ProjectManagement.model.TaskResult;

import java.util.List;

public interface TaskResultService {


    TaskResult setTaskResult(TaskResult taskResult);

    TaskResult getTaskResult(long resultId, String projectOwnerEmail);

    List<TaskResult> getTaskResultsByTask(long taskId, String projectOwnerEmail);

}
