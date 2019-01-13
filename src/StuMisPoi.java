import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StuMisPoi {
    public List<Student> readexcel(File file) throws IOException {
        InputStream fis = new FileInputStream(file);
        XSSFWorkbook hssfWorkbook = new XSSFWorkbook(fis);
        Student student = null;
        List<Student> list = new ArrayList();
        // 循环工作表Sheet
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            XSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            // 循环行Row
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow != null) {
                    student = new Student();
                    XSSFCell no = hssfRow.getCell(0);
                    XSSFCell name = hssfRow.getCell(1);
                    XSSFCell age = hssfRow.getCell(2);
                    XSSFCell gender = hssfRow.getCell(3);
                    no.setCellType(CellType.STRING);

                    student.setSno(no.getStringCellValue());
                    student.setSname(getValue(name));
                    student.setSage((int) Double.parseDouble(getValue(age)));
                    student.setSgender(getValue(gender));
                    list.add(student);
                }
            }
        }
        fis.close();
        return list;
    }

    private String getValue(XSSFCell xssfcell) {

        if (xssfcell.getCellType() == CellType.BOOLEAN) {
            // 返回布尔类型的值
            return String.valueOf(xssfcell.getBooleanCellValue());
        } else if (xssfcell.getCellType() == CellType.NUMERIC) {
            // 返回数值类型的值
            return String.valueOf(xssfcell.getNumericCellValue());
        } else {
            // 返回字符串类型的值
            return String.valueOf(xssfcell.getStringCellValue());
        }
    }

    public static void xlsDto2Excel(List<Student> students,File file) throws Exception {
        // 获取总列数
        int CountColumnNum = students.size();
        // 创建Excel文档
        XSSFWorkbook hwb = new XSSFWorkbook();
        Student student = null;
        // sheet 对应一个工作页
        XSSFSheet sheet = hwb.createSheet("pldrxkxxmb");
        XSSFRow firstrow = sheet.createRow(0); // 下标为0的行开始
        XSSFCell[] firstcell = new XSSFCell[CountColumnNum];
        String[] names = new String[CountColumnNum];
        names[0] = "学号";
        names[1] = "姓名";
        names[2] = "年龄";
        names[3] = "性别";
        for (int j = 0; j < CountColumnNum; j++) {
            firstcell[j] = firstrow.createCell(j);
            firstcell[j].setCellValue(new XSSFRichTextString(names[j]));
        }
        for (int i = 0; i < students.size(); i++) {
            // 创建一行
            XSSFRow row = sheet.createRow(i + 1);
            // 得到要插入的每一条记录
            student = students.get(i);
            for (int colu = 0; colu <= 3; colu++) {
                // 在一行内循环
                XSSFCell xh = row.createCell(0);
                xh.setCellValue(student.getSno());
                XSSFCell xm = row.createCell(1);
                xm.setCellValue(student.getSname());
                XSSFCell yxsmc = row.createCell(2);
                yxsmc.setCellValue(student.getSage());
                XSSFCell kcm = row.createCell(3);
                kcm.setCellValue(student.getSgender());
            }
        }
        // 创建文件输出流，准备输出电子表格
        OutputStream out = new FileOutputStream(file);
        hwb.write(out);
        out.close();
        System.out.println("数据库导出成功");
    }
}
