package pl.project.ProjectManagement.service.interfaces;

import pl.project.ProjectManagement.model.Project;
import pl.project.ProjectManagement.model.Student;
import pl.project.ProjectManagement.model.Task;
import pl.project.ProjectManagement.model.dto.ProjectDto;
import pl.project.ProjectManagement.model.dto.StudentDto;
import pl.project.ProjectManagement.model.dto.TaskDto;

public interface ModelWrapper {


    Project getProjectFromDto(ProjectDto dto);

    Student getStudentFromDto(StudentDto dto);

    Task getTaskFromDto(TaskDto dto);
}
