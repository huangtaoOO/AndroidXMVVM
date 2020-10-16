package com.example.file_recovery;

/**
 * @author: tao
 * @time: 2020/9/21
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public class PmgFileBean {
    //项目名称
    private String projectName ;
    //子项名称
    private String subName ;
    //子项编号
    private String subCode;
    //任务id
    private String taskId;
    //任务名称
    private String taskName;
    //检查项名称
    private String checkName;
    //提交公司
    private String company;
    //提交部门
    private String department;
    //提交人
    private String user;
    //手机号码
    private String phone;
    //提交时间
    private String time;
    //文件名称
    private String fileName;
    //文件大小
    private String fileSize;
    //服务器上真实地址
    private String realLocation;

    private boolean isRecovered = false;

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

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getCheckName() {
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getRealLocation() {
        return realLocation;
    }

    public void setRealLocation(String realLocation) {
        this.realLocation = realLocation;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public boolean isRecovered() {
        return isRecovered;
    }

    public void setRecovered(boolean recovered) {
        isRecovered = recovered;
    }

    @Override
    public String toString() {
        return "PmgFileBean{" +
                "projectName='" + projectName + '\'' +
                ", subName='" + subName + '\'' +
                ", subCode='" + subCode + '\'' +
                ", taskId='" + taskId + '\'' +
                ", taskName='" + taskName + '\'' +
                ", checkName='" + checkName + '\'' +
                ", company='" + company + '\'' +
                ", department='" + department + '\'' +
                ", user='" + user + '\'' +
                ", phone='" + phone + '\'' +
                ", time='" + time + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileSize='" + fileSize + '\'' +
                ", realLocation='" + realLocation + '\'' +
                '}';
    }
}
