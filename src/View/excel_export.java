package View;

import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JTable;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class excel_export {

	public static String export(JTable table, JTable table_sum)
	{
		String result = null;
		LocalDateTime now = LocalDateTime.now();
		String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH_mm_ss"));
		
        String export_pass = "C:\\Users\\"+System.getProperty("user.name")+"\\Desktop\\"+"정산후 회수금 "+formatedNow+".xlsx";
        XSSFWorkbook workbook = new XSSFWorkbook(); // 새 엑셀 생성
        XSSFSheet sheet = workbook.createSheet("시트명"); // 새 시트(Sheet) 생성

        XSSFRow curRow;
        curRow = sheet.createRow(0);    // row 생성
        
        String header[]= {"일자","번호","현금","현금합계","상품권","상품권합계","물품","물품합계"};
		String header_sum[]= {"현금","현금합계","상품권","상품권합계","물품","물품합계"};
        for(int i = 0; i < header.length; i++)
        {
        	curRow.createCell(i).setCellValue(header[i]);
        }
        
        for(int j = 0; j < table.getRowCount(); j++)
        {
        	curRow = sheet.createRow(j+1);
        	for(int k = 0; k < table.getColumnCount(); k++)
        	{
        		curRow.createCell(k).setCellValue(table.getValueAt(j, k)+"");
        	}
        }
        curRow = sheet.createRow(table.getRowCount()+2);
        for(int l = 0; l < table_sum.getColumnCount(); l++)
        {
        	curRow.createCell(l).setCellValue(header_sum[l]);
        }
        curRow = sheet.createRow(table.getRowCount()+3);
        for(int l = 0; l < table_sum.getColumnCount(); l++)
        {
        	curRow.createCell(l).setCellValue(table_sum.getValueAt(1, l)+"");
        }
        
        try {
            FileOutputStream fileoutputstream = new FileOutputStream(export_pass);
            workbook.write(fileoutputstream);
            fileoutputstream.close();
            workbook.close();
            result = "엑셀파일생성성공";
        } catch (Exception e) {
            e.printStackTrace();
            result = "엑셀파일생성실패";
        }
        return result;
	}
}
