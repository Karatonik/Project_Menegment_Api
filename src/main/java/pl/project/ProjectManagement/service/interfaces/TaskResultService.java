package pl.project.ProjectManagement.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import pl.project.ProjectManagement.model.TaskResult;

import java.io.File;
import java.util.List;

public interface TaskResultService {


    TaskResult setTaskResult(TaskResult taskResult, MultipartFile file);

    TaskResult getTaskResult(long resultId, String projectOwnerEmail);

    File getTaskResultFile(long resultId, String projectOwnerEmail);

    Page<TaskResult> getTaskResultsByTask(long taskId, String projectOwnerEmail, Pageable pageable);

}
