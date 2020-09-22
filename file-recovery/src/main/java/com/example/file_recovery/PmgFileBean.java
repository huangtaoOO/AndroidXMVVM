package com.example.file_recovery;

import java.util.Objects;

/**
 * @author: tao
 * @time: 2020/9/21
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public class PmgFileBean {

    private String projectName ;

    private String subName ;

    private String taskName;

    private Long taskId;

    private String fileName;

    private String realLocation;

    private String subCode;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRealLocation() {
        return realLocation;
    }

    public void setRealLocation(String realLocation) {
        this.realLocation = realLocation;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PmgFileBean fileBean = (PmgFileBean) o;
        return fileName.equals(fileBean.fileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileName);
    }

    @Override
    public String toString() {
        return "PmgFileBean{" +
                "projectName='" + projectName + '\'' +
                ", subName='" + subName + '\'' +
                ", taskName='" + taskName + '\'' +
                ", taskId=" + taskId +
                ", fileName='" + fileName + '\'' +
                ", realLocation='" + realLocation + '\'' +
                ", subCode='" + subCode + '\'' +
                '}';
    }
}
