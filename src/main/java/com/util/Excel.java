package com.util;
import java.io.File;
import java.util.List;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
/**
* Excel报表生成工具类 使用JXL包生成Excel格式的文件
* @param savePath 存储路径
* Author QQ:455570970
*/

public class Excel {

	public String getExcel(List<String[]> strList, String[] strTitle, String banner, String paths) {
		String fp = "upfiles\\" + File.separator + VeDate.getStringDatex() + ".xls";
		String filepath = paths + fp;
		try {
			WritableWorkbook book = Workbook.createWorkbook(new File(filepath));
			WritableSheet sheet = book.createSheet(banner, 0);
			for (int i = 0; i < strTitle.length; i++) {
				sheet.addCell(new Label(i, 0, strTitle[i]));
			}
			for (int i = 0; i < strList.size(); i++) {
				String[] str = strList.get(i);
				for (int j = 0; j < str.length; j++) {
					try {
						Label label = new Label(j, i + 1, str[j]);
						sheet.addCell(label);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			book.write();
			book.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fp;
	}

}








