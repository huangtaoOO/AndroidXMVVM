package com.example.file_recovery;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * @author: tao
 * @time: 2020/9/22
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public class Client {

    private List<PmgFileBean> fileIndexes ;
    private List<FileBean> notFindFailedFiles ;
    private List<FileBean> repeatFailedFiles ;

    private String xlsxPath = "D:\\文件恢复\\丢失文件汇总（不要外传）.xlsx";

    private List<String> lossPath = new ArrayList<>();
    {
        lossPath.add("D:\\文件恢复\\补录文件10-16");
//        lossPath.add("D:\\文件恢复\\丢失文件补录\\北京诚公管理咨询股份有限公司");
//        lossPath.add("D:\\文件恢复\\丢失文件补录\\北京华麒通信科技有限公司");
//        lossPath.add("D:\\文件恢复\\丢失文件补录\\北京华麒通信科技有限公司（第二批）");
//        lossPath.add("D:\\文件恢复\\丢失文件补录\\丢失文件统计-白城资料部分");
//        lossPath.add("D:\\文件恢复\\丢失文件补录\\丢失文件统计-吉林资料部分");
//        lossPath.add("D:\\文件恢复\\丢失文件补录\\丢失文件统计-省公司资料部分");
//        lossPath.add("D:\\文件恢复\\丢失文件补录\\丢失文件统计-延边资料部分");
//        lossPath.add("D:\\文件恢复\\丢失文件补录\\丢失文件统计-长春资料部分");
//        lossPath.add("D:\\文件恢复\\丢失文件补录\\吉林吉大通信设计院股份有限公司");
//        lossPath.add("D:\\文件恢复\\丢失文件补录\\吉林省荣瑞信诚监理有限公司");
//        lossPath.add("D:\\文件恢复\\丢失文件补录\\吉林长邮通信建设有限公司");
//        lossPath.add("D:\\文件恢复\\丢失文件补录\\上海邮电设计咨询研究院有限公司");
//        lossPath.add("D:\\文件恢复\\丢失文件补录\\天元瑞信通信技术股份有限公司");
//        lossPath.add("D:\\文件恢复\\丢失文件补录\\延边剩余文件");
//        lossPath.add("D:\\文件恢复\\丢失文件补录\\长春剩余文件");
//        lossPath.add("D:\\文件恢复\\丢失文件补录\\长春现场照片补录文件");
//        lossPath.add("D:\\文件恢复\\丢失文件补录\\中国移动通信集团设计院有限公司");
//        lossPath.add("D:\\文件恢复\\丢失文件补录\\中移建设有限公司");
    }

    private List<String> heads = new ArrayList<>();
    {
        heads.add("项目名称");
        heads.add("子项名称");
        heads.add("子项编号");
        heads.add("任务ID");
        heads.add("任务名称");
        heads.add("检查项名称");
        heads.add("提交公司");
        heads.add("提交部门");
        heads.add("提交人");
        heads.add("手机号");
        heads.add("提交时间");
        heads.add("文件名称");
        heads.add("文件大小");
        heads.add("文件位置");
    }

    private String transferPath = "D:\\文件恢复\\";

    private static int numberOfRepetitions = 0 ;

    public void run(String[] args) {
        System.out.println("---文件恢复 程序开始执行---");
//        inputParameter();
        System.out.println("读取配置文件，构建索引");
        System.out.println("配置文件目录，xlsxPath = " + xlsxPath);
        buildingIndexes();
        System.out.println("构建索引成功，获取数据共" + fileIndexes.size() +"条");
        System.out.println("---读取丢失文件目录,开始数据恢复---");
        dataRecovery();
        printErrorFile();
        System.out.println("---执行完毕---");
        System.out.println("文件重复数：" + numberOfRepetitions);
        System.out.println("--输出错误文件--");
        printErrorLog();
        System.out.println("--输出错误文件成功--");
    }

    private void printErrorLog() {
        createExcelFile();
    }

    public boolean createExcelFile() {
        boolean isCreateSuccess = false;
        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook();//HSSFWorkbook();//WorkbookFactory.create(inputStream);
        }catch(Exception e) {
            System.out.println("It cause Error on CREATING excel workbook: ");
            e.printStackTrace();
        }
        if(workbook != null) {
            Sheet sheet = workbook.createSheet("错误日志");
            Row row0 = sheet.createRow(0);
            for(int i = 0; i < heads.size(); i++) {
                Cell cell_1 = row0.createCell(i, Cell.CELL_TYPE_STRING);
                CellStyle style = getStyle(workbook);
                cell_1.setCellStyle(style);
                cell_1.setCellValue( heads.get(i));
                sheet.autoSizeColumn(i);
            }
            int rowNum = 1;
            for (PmgFileBean bean : fileIndexes) {
                if (!bean.isRecovered()){
                    Row row = sheet.createRow(rowNum);
                    for(int i = 0; i < heads.size(); i++) {
                        Cell cell = row.createCell(i, Cell.CELL_TYPE_STRING);
                        switch (i){
                        case 0:
                            cell.setCellValue(bean.getProjectName());
                            break;
                        case 1:
                            cell.setCellValue(bean.getSubName());
                            break;
                        case 2:
                            cell.setCellValue(bean.getSubCode());
                            break;
                        case 3:
                            cell.setCellValue(bean.getTaskId());
                            break;
                        case 4:
                            cell.setCellValue(bean.getTaskName());
                            break;
                        case 5:
                            cell.setCellValue(bean.getCheckName());
                            break;
                        case 6:
                            cell.setCellValue(bean.getCompany());
                            break;
                        case 7:
                            cell.setCellValue(bean.getDepartment());
                            break;
                        case 8:
                            cell.setCellValue(bean.getUser());
                            break;
                        case 9:
                            cell.setCellValue(bean.getPhone());
                            break;
                        case 10:
                            cell.setCellValue(bean.getTime());
                            break;

                        case 11:
                            cell.setCellValue(bean.getFileName());
                            break;
                        case 12:
                            cell.setCellValue(bean.getFileSize());
                            break;
                        case 13:
                            cell.setCellValue(bean.getRealLocation());
                            break;
                        default:break;
                        }
                    }
                    rowNum++;
                }
            }
            try {
                String filePath = transferPath + "错误日志.xlsx";
                File file = new File(filePath);
                if (!file.exists())
                    file.createNewFile();
                FileOutputStream outputStream = new FileOutputStream(file);
                workbook.write(outputStream);
                outputStream.flush();
                outputStream.close();
                isCreateSuccess = true;
            } catch (Exception e) {
                System.out.println("It cause Error on WRITTING excel workbook: ");
                e.printStackTrace();
            }
        }
        return isCreateSuccess;
    }

    private CellStyle getStyle(Workbook workbook){
        CellStyle style = workbook.createCellStyle();
        // 设置单元格字体
        Font headerFont = workbook.createFont(); // 字体
        headerFont.setFontHeightInPoints((short)14);
        headerFont.setColor(HSSFColor.RED.index);
        headerFont.setFontName("宋体");
        style.setFont(headerFont);
        style.setWrapText(true);
        return style;
    }

    private void inputParameter() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入配置文件地址：");
        xlsxPath = scanner.next();
        System.out.println("请输入根目录地址：");
        transferPath = scanner.next();
        System.out.println("请输入需要恢复的文件地址(输入-1结束)：");
        while (true){
            String a = scanner.next();
            if (a.equals("-1")){
                break;
            }else {
                lossPath.add(a);
                System.out.println("请输入需要恢复的文件地址(输入-1结束)：");
            }
        }

        System.out.println("-- 确认配置文件 --");
        System.out.println("配置文件地址：" + xlsxPath);
        System.out.println("根目录地址：" + transferPath);
        System.out.println("需要恢复的文件地址:" + lossPath);
    }

    private void printErrorFile() {
        System.out.println("---未搜索到文件---");
        if (notFindFailedFiles!=null){
            for (FileBean bean : notFindFailedFiles){
                System.out.println(bean.path);
            }
        }
        System.out.println("未搜索到文件 - - 执行出错共:" + (notFindFailedFiles==null?0:notFindFailedFiles.size()) + "个");

        System.out.println("---重复文件出错---");
        if (repeatFailedFiles!=null){
            for (FileBean bean : repeatFailedFiles){
                System.out.println(bean.path);
            }
        }
        System.out.println("重复文件出错 - - 执行出错共:" + (repeatFailedFiles==null?0:repeatFailedFiles.size()) + "个");
    }

    private void dataRecovery() {
        List<FileBean> fileList = new ArrayList<>();
        for (String s:lossPath) {
            dataRecovery(s,fileList);
        }
        for (FileBean bean:fileList) {
            System.out.println(bean.path);
        }
        System.out.println("一共发现" + fileList.size() + "个文件");
        int i = 0 ;
        for (FileBean bean:fileList){
            fileRecoveryLogic(bean);
            i ++;
            System.out.println("进行中" + i + "/" + fileList.size());
        }
    }

    private void fileRecoveryLogic(FileBean bean) {
        boolean isInto = false;
        String s = bean.path.replace(bean.prefix + "\\","");
        String[] data = s.split("\\\\");
        for (PmgFileBean pmg : fileIndexes){
            String b = data[data.length-1];
            String c = b.split("\\.")[0];
            boolean isFile = pmg.getFileName().contains(c);
            if (isFile){
                isInto = true;
                try{
                    restoreFiles(pmg,bean);
                }catch (IOException e){
                    System.err.println(e.getMessage());
                    if (repeatFailedFiles == null)
                        repeatFailedFiles = new ArrayList<>();
                    repeatFailedFiles.add(bean);
                }
                break;
            }
        }
        if (!isInto){
            System.out.println("未搜索到对应数据：" + bean.path);
            if (notFindFailedFiles==null)
                notFindFailedFiles = new ArrayList<>();
            notFindFailedFiles.add(bean);
        }
    }

    private void restoreFiles(PmgFileBean pmg,FileBean bean) throws IOException {
        String a [] = pmg.getRealLocation().split("\\\\");
        String catalog = transferPath + pmg.getRealLocation().replace(a[a.length-1],"");
        copyFile(new File(bean.path),catalog,a[a.length-1]);
        pmg.setRecovered(true);
    }

    public void copyFile(File source, String catalog,String dest )throws IOException {
        if(source.isFile()){
            FileInputStream fis = new FileInputStream(source);
            //创建新的文件，保存复制内容，文件名称与源文件名称一致
            File file = new File(catalog);
            if (!file.exists()){
                file.mkdirs();
            }
            File dfile = new File(file,dest);
            if (!dfile.exists()){
                dfile.createNewFile();
            }else {
                numberOfRepetitions++;
                throw new IOException("文件已存在 ， 源文件="+source + " \n目标文件" + catalog +dest);
            }

            FileOutputStream fos = new FileOutputStream(dfile);
            // 读写数据
            // 定义数组
            byte[] b = new byte[1024];
            // 定义长度
            int len;
            // 循环读取
            while ((len = fis.read(b))!=-1) {
                // 写出数据
                fos.write(b, 0 , len);
            }

            //关闭资源
            fos.close();
            fis.close();
        }else {
            System.out.println("不是一个文件");
        }
    }

    private void dataRecovery(String path, List<FileBean> fileList) {
        File file = new File(path);
        if (file.exists()) {
            LinkedList<File> list = new LinkedList<>();
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (file2.isDirectory()) {
                    list.add(file2);
                } else {
                    fileList.add(new FileBean(path,file2.getAbsolutePath()));
                }
            }
            File temp_file;
            while (!list.isEmpty()) {
                temp_file = list.removeFirst();
                files = temp_file.listFiles();
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        list.add(file2);
                    } else {
                        fileList.add(new FileBean(path,file2.getAbsolutePath()));
                    }
                }
            }
        } else {
            System.out.println(path + " -- 文件不存在!");
        }
    }

    private void buildingIndexes(){
        fileIndexes = new ArrayList<>();
        Workbook wb;
        Sheet sheet;
        Row row;
        String cellData;
        String filePath = xlsxPath;
        wb = readExcel(filePath);
        if(wb != null){
            //获取第一个sheet
            sheet = wb.getSheetAt(0);
            //获取最大行数
            int rownum = sheet.getPhysicalNumberOfRows();
            //获取第一行
            row = sheet.getRow(0);
            //获取最大列数
            int colnum = row.getPhysicalNumberOfCells();
            for (int i = 1; i<rownum; i++) {
                PmgFileBean fileBean = new PmgFileBean();
                row = sheet.getRow(i);
                if(row !=null){
                    for (int j=0;j<colnum;j++){
                        switch (j){
                            case 0:
                                cellData = (String) getCellFormatValue(row.getCell(j));
                                fileBean.setProjectName(cellData);
                                break;
                            case 1:
                                cellData = (String) getCellFormatValue(row.getCell(j));
                                fileBean.setSubName(cellData);
                                break;
                            case 2:
                                cellData = (String) getCellFormatValue(row.getCell(j));
                                fileBean.setSubCode(cellData);
                                break;
                            case 3:
                                cellData = (String) getCellFormatValue(row.getCell(j));
                                fileBean.setTaskId(cellData);
                                break;
                            case 4:
                                cellData = (String) getCellFormatValue(row.getCell(j));
                                fileBean.setTaskName(cellData);
                                break;
                            case 5:
                                cellData = (String) getCellFormatValue(row.getCell(j));
                                fileBean.setCheckName(cellData);
                                break;
                            case 6:
                                cellData = (String) getCellFormatValue(row.getCell(j));
                                fileBean.setCompany(cellData);
                                break;
                            case 7:
                                cellData = (String) getCellFormatValue(row.getCell(j));
                                fileBean.setDepartment(cellData);
                                break;
                            case 8:
                                cellData = (String) getCellFormatValue(row.getCell(j));
                                fileBean.setUser(cellData);
                                break;
                            case 9:
                                cellData = (String) getCellFormatValue(row.getCell(j));
                                fileBean.setPhone(cellData);
                                break;
                            case 10:
                                cellData = (String) getCellFormatValue(row.getCell(j));
                                fileBean.setTime(cellData);
                                break;

                            case 11:
                                cellData = (String) getCellFormatValue(row.getCell(j));
                                fileBean.setFileName(cellData);
                                break;
                            case 12:
                                cellData = (String) getCellFormatValue(row.getCell(j));
                                fileBean.setFileSize(cellData);
                                break;
                            case 13:
                                cellData = (String) getCellFormatValue(row.getCell(j));
                                fileBean.setRealLocation(cellData.trim().replace("/","\\"));
                                break;
                            default:break;
                        }
                    }
                    fileIndexes.add(fileBean);
                }else{
                    break;
                }
            }
        }
        //遍历解析出来的list
        for (PmgFileBean bean : fileIndexes) {
            System.out.println(bean.toString());
        }
    }

    //读取xlsx文件
    public Workbook readExcel(String filePath){
        if(filePath==null){
            return null;
        }
        String extString = filePath.substring(filePath.lastIndexOf("."));
        InputStream is;
        try {
            is = new FileInputStream(filePath);
            if(".xls".equals(extString)){
                return new HSSFWorkbook(is);
            }else if(".xlsx".equals(extString)){
                return new XSSFWorkbook(is);
            }else{
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object getCellFormatValue(Cell cell){
        Object cellValue;
        if(cell!=null){
            //判断cell类型
            switch(cell.getCellTypeEnum()){
                case NUMERIC:
                    cellValue = String.valueOf((long)cell.getNumericCellValue());
                    break;
                case STRING:
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                default:
                    cellValue = "";
            }
        }else{
            cellValue = "";
        }
        return cellValue;
    }
}
