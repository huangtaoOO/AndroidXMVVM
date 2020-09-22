package com.example.file_recovery;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
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
 * @exception: ��
 * @explain: ˵��
 */
public class Client {

    private List<PmgFileBean> fileIndexes ;
    private List<FileBean> notFindFailedFiles ;
    private List<FileBean> repeatFailedFiles ;

    private String xlsxPath = "D:\\�ļ��ָ�\\��ʧ�ļ����ܣ���Ҫ�⴫��.xlsx";

    private List<String> lossPath = new ArrayList<>();

    private String transferPath = "D:\\�ļ��ָ�\\";

    private static int numberOfRepetitions = 0 ;

    public void run(String[] args) {
        System.out.println("---�ļ��ָ� ����ʼִ��---");

        inputParameter();


//        System.out.println("��ȡ�����ļ�����������");
//        System.out.println("�����ļ�Ŀ¼��xlsxPath = " + xlsxPath);
//        buildingIndexes();
//        System.out.println("���������ɹ�����ȡ���ݹ�" + fileIndexes.size() +"��");
//        System.out.println("---��ȡ��ʧ�ļ�Ŀ¼,��ʼ���ݻָ�---");
//        dataRecovery();
//        printErrorFile();
//        System.out.println("---ִ�����---");
//        System.out.println("�ļ��ظ�����" + numberOfRepetitions);
    }

    private void inputParameter() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("�����������ļ���ַ��");
        xlsxPath = scanner.next();
        System.out.println("�������Ŀ¼��ַ��");
        transferPath = scanner.next();
        System.out.println("��������Ҫ�ָ����ļ���ַ(����-1����)��");
        while (true){
            String a = scanner.next();
            if (a.equals("-1")){
                break;
            }else {
                lossPath.add(a);
                System.out.println("��������Ҫ�ָ����ļ���ַ(����-1����)��");
            }
        }

        System.out.println("-- ȷ�������ļ� --");
        System.out.println("�����ļ���ַ��" + xlsxPath);
        System.out.println("��Ŀ¼��ַ��" + transferPath);
        System.out.println("��Ҫ�ָ����ļ���ַ:" + lossPath);
    }

    private void printErrorFile() {
        System.out.println("---δ�������ļ�---");
        for (FileBean bean : notFindFailedFiles){
            System.out.println(bean.path);
        }
        System.out.println("δ�������ļ� - - ִ�г���:" + notFindFailedFiles.size() + "��");

        System.out.println("---�ظ��ļ�����---");
        for (FileBean bean : repeatFailedFiles){
            System.out.println(bean.path);
        }
        System.out.println("�ظ��ļ����� - - ִ�г���:" + repeatFailedFiles.size() + "��");
    }

    private void dataRecovery() {
        List<FileBean> fileList = new ArrayList<>();
        for (String s:lossPath) {
            dataRecovery(s,fileList);
        }
        for (FileBean bean:fileList) {
            System.out.println(bean.path);
        }
        System.out.println("һ������" + fileList.size() + "���ļ�");
        int i = 0 ;
        for (FileBean bean:fileList){
            fileRecoveryLogic(bean);
            i ++;
            System.out.println("������" + i + "/" + fileList.size());
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
            System.out.println("δ��������Ӧ���ݣ�" + bean.path);
            if (notFindFailedFiles==null)
                notFindFailedFiles = new ArrayList<>();
            notFindFailedFiles.add(bean);
        }
    }

    private void restoreFiles(PmgFileBean pmg,FileBean bean) throws IOException {
        String a [] = pmg.getRealLocation().split("\\\\");
        String catalog = transferPath + pmg.getRealLocation().replace(a[a.length-1],"");
        copyFile(new File(bean.path),catalog,a[a.length-1]);
        System.out.println(" ԭ·����" + bean.path + " ����·����" + catalog + a[a.length-1]);
    }

    public void copyFile(File source, String catalog,String dest )throws IOException {
        if(source.isFile()){
            FileInputStream fis = new FileInputStream(source);
            //�����µ��ļ������渴�����ݣ��ļ�������Դ�ļ�����һ��
            File file = new File(catalog);
            if (!file.exists()){
                file.mkdirs();
            }
            File dfile = new File(file,dest);
            if (!dfile.exists()){
                dfile.createNewFile();
            }else {
                numberOfRepetitions++;
                throw new IOException("�ļ��Ѵ��� �� Դ�ļ�="+source + " \nĿ���ļ�" + catalog +dest);
            }

            FileOutputStream fos = new FileOutputStream(dfile);
            // ��д����
            // ��������
            byte[] b = new byte[1024];
            // ���峤��
            int len;
            // ѭ����ȡ
            while ((len = fis.read(b))!=-1) {
                // д������
                fos.write(b, 0 , len);
            }

            //�ر���Դ
            fos.close();
            fis.close();
        }else {
            System.out.println("����һ���ļ�");
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
            System.out.println(path + " -- �ļ�������!");
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
            //��ȡ��һ��sheet
            sheet = wb.getSheetAt(0);
            //��ȡ�������
            int rownum = sheet.getPhysicalNumberOfRows();
            //��ȡ��һ��
            row = sheet.getRow(0);
            //��ȡ�������
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
                                fileBean.setTaskId(0L);
                                break;
                            case 4:
                                cellData = (String) getCellFormatValue(row.getCell(j));
                                fileBean.setTaskName(cellData);
                                break;
                            case 11:
                                cellData = (String) getCellFormatValue(row.getCell(j));
                                fileBean.setFileName(cellData);
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
        //��������������list
        for (PmgFileBean bean : fileIndexes) {
            System.out.println(bean.toString());
        }
    }

    //��ȡxlsx�ļ�
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
            //�ж�cell����
            switch(cell.getCellType()){
                case Cell.CELL_TYPE_NUMERIC:{
                    cellValue = String.valueOf(cell.getNumericCellValue());
                    break;
                }
                case Cell.CELL_TYPE_FORMULA:{
                    //�ж�cell�Ƿ�Ϊ���ڸ�ʽ
                    if(DateUtil.isCellDateFormatted(cell)){
                        //ת��Ϊ���ڸ�ʽYYYY-mm-dd
                        cellValue = cell.getDateCellValue();
                    }else{
                        //����
                        cellValue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                case Cell.CELL_TYPE_STRING:{
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                }
                default:
                    cellValue = "";
            }
        }else{
            cellValue = "";
        }
        return cellValue;
    }
}
