package net.cmkj.common.utils.uuid;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExportSheetUtil {
    public static void exportExcel(XSSFWorkbook workbook, int sheetNum, String sheetTitle, String[] headers, List<List<String>> result) throws Exception {
        // 生成一个表格
        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(sheetNum, sheetTitle);

        // 设置表格默认列宽度为20个字节
        sheet.setDefaultColumnWidth((short) 20);
        // 产生表格标题行
        XSSFRow row = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            XSSFCell cell = row.createCell((short) i);
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
            cell.setCellValue(text.toString());
        }
        // 遍历集合数据，产生数据行
        if (result != null) {
            int index = 1;
            for (List<String> m : result) {
                row = sheet.createRow(index);
                int cellIndex = 0;
                for (Object str : m) {
                    XSSFCell cell = row.createCell((short) cellIndex);
                    cell.setCellValue(str.toString());
                    cellIndex++;
                }
                index++;
            }
        }

    }

    public static void exportSingleHeaderExcel2( Workbook workbook,List<Map<String, Object>> lmap) {
        if(lmap.size()>0) {
            for (int i = 0; i < lmap.size(); i++) {
                String sheetName = lmap.get(i).get("sheetName"+i).toString();
                List<Map<String, Object>> datas= (List<Map<String, Object>>) lmap.get(i).get("datas"+i);
                List<Map<String, Object>> titles= (List<Map<String, Object>>) lmap.get(i).get("titles"+i);
                Sheet sheet = workbook.createSheet(sheetName);
                CellStyle headerCellStyle = workbook.createCellStyle();
                CellStyle cellStyle = workbook.createCellStyle();
                setTableStyle(workbook, headerCellStyle, cellStyle);

                // 创建表头
//                Row headerRow = sheet.createRow(0);
//                for (int k = 0; k < titles.size(); k++) {
//                    Cell cell = headerRow.createCell(k);
//                    cell.setCellValue(titles.get(k).get("name") != null ? titles.get(k).get("name").toString() : "");
//                    cell.setCellStyle(headerCellStyle); // 设置表头样式
//                }

                Row headerRow1 = sheet.createRow(0);
                Row headerRow2 = sheet.createRow(1);
                // 合并第一列的第一行和第二行
                Cell cell1 = headerRow1.createCell(0);
                cell1.setCellValue("部门名称");
                cell1.setCellStyle(headerCellStyle);
                sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0)); // 合并第0行到第1行，第0列

                Cell cell111 = headerRow2.createCell(0);
                cell111.setCellStyle(headerCellStyle);

                Cell cell21 = headerRow1.createCell(1);
                cell21.setCellValue("建设类型");
                cell21.setCellStyle(headerCellStyle);
                sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, 1)); // 合并第0行到第1行，第0列

                Cell cell211 = headerRow2.createCell(1);
                cell211.setCellStyle(headerCellStyle);

                Cell cell3 = headerRow1.createCell(2);
                cell3.setCellValue("资本性支出项目");
                cell3.setCellStyle(headerCellStyle);
                sheet.addMergedRegion(new CellRangeAddress(0, 1, 2, 2)); // 合并第0行到第1行，第0列

                Cell cell31 = headerRow2.createCell(2);
                cell31.setCellStyle(headerCellStyle);

                Map<String, Object> stringObjectMap = titles.get(3);
                String name = stringObjectMap.get("name").toString();
                Cell cell4 = headerRow1.createCell(3);
                cell4.setCellValue(name.substring(0,name.length()-2));
                cell4.setCellStyle(headerCellStyle);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 3, 4)); // 合并第0行到第1行，第0列

                Cell cell5 = headerRow2.createCell(3);
                cell5.setCellValue("数量");
                cell5.setCellStyle(headerCellStyle);

                Cell cell6 = headerRow2.createCell(4);
                cell6.setCellValue("金额");
                cell6.setCellStyle(headerCellStyle);

                Map<String, Object> stringObjectMap2 = titles.get(5);
                String name2 = stringObjectMap2.get("name").toString();
                Cell cell7 = headerRow1.createCell(5);
                cell7.setCellValue(name2.substring(0,name2.length()-2));
                cell7.setCellStyle(headerCellStyle);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 5, 6)); // 合并第0行到第1行，第0列

                Cell cell8 = headerRow2.createCell(5);
                cell8.setCellValue("数量");
                cell8.setCellStyle(headerCellStyle);


                Cell cell9 = headerRow2.createCell(6);
                cell9.setCellValue("金额");
                cell9.setCellStyle(headerCellStyle);

                Cell cell10 = headerRow1.createCell(7);
                cell10.setCellValue("增速");
                cell10.setCellStyle(headerCellStyle);
                sheet.addMergedRegion(new CellRangeAddress(0, 1, 7, 7)); // 合并第0行到第1行，第0列

                Cell cell101 = headerRow2.createCell(7);
                cell101.setCellStyle(headerCellStyle);

                Map<String, Object> stringObjectMap8 = titles.get(8);
                String name8 = stringObjectMap8.get("name").toString();
                Cell cell11 = headerRow1.createCell(8);
                cell11.setCellValue(name8);
                cell11.setCellStyle(headerCellStyle);
                sheet.addMergedRegion(new CellRangeAddress(0, 1, 8, 8)); // 合并第0行到第1行，第0列

                Cell cell1111 = headerRow2.createCell(8);
                cell1111.setCellStyle(headerCellStyle);

                Map<String, Object> stringObjectMap9 = titles.get(9);
                String name9 = stringObjectMap9.get("name").toString();
                Cell cell12 = headerRow1.createCell(9);
                cell12.setCellValue(name9);
                cell12.setCellStyle(headerCellStyle);
                sheet.addMergedRegion(new CellRangeAddress(0, 1, 9, 9)); // 合并第0行到第1行，第0列

                Cell cell91 = headerRow2.createCell(9);
                cell91.setCellStyle(headerCellStyle);

                Map<String, Object> stringObjectMap10 = titles.get(10);
                String name10 = stringObjectMap10.get("name").toString();
                Cell cell13 = headerRow1.createCell(10);
                cell13.setCellValue(name10);
                cell13.setCellStyle(headerCellStyle);
                sheet.addMergedRegion(new CellRangeAddress(0, 1, 10, 10)); // 合并第0行到第1行，第0列

                Cell cell1011 = headerRow2.createCell(10);
                cell1011.setCellStyle(headerCellStyle);

                Map<String, Object> stringObjectMap11 = titles.get(11);
                String name11 = stringObjectMap11.get("name").toString();
                Cell cell14 = headerRow1.createCell(11);
                cell14.setCellValue(name11);
                cell14.setCellStyle(headerCellStyle);
                sheet.addMergedRegion(new CellRangeAddress(0, 1, 11, 11)); // 合并第0行到第1行，第0列

                Cell cell1012 = headerRow2.createCell(11);
                cell1012.setCellStyle(headerCellStyle);

                // 用于存储合并区域
                List<CellRangeAddress> mergedRegions = new ArrayList<>();
                int currentRow = 2;  // 数据起始行
                String lastStatus = null;
                int startMergeRow = -1;
                // 填充数据
                for (int l = 0; l < datas.size(); l++) {
                   // Row dataRow = sheet.createRow(l + 2);
                    Row dataRow = sheet.createRow(currentRow + l);
                    Map<String, Object> data = datas.get(l);
                    for (int j = 0; j < titles.size(); j++) {
                        Cell cell2 = dataRow.createCell(j);
                        cell2.setCellValue(data.get(titles.get(j).get("key").toString()) == null ? "" : data.get(titles.get(j).get("key").toString()).toString());
                        cell2.setCellStyle(cellStyle); // 设置数据样式
                        cell2.setCellType(CellType.STRING);
                    }
                    String currentStatus = data.get("建设类型").toString();
                    if (lastStatus == null || !lastStatus.equals(currentStatus)) {
                        if (startMergeRow != -1 && (currentRow + l - 1) > startMergeRow) {
                            // 结束上一个合并区域
                            mergedRegions.add(new CellRangeAddress(startMergeRow, currentRow + l - 1, 1, 1));
                        }
                        startMergeRow = currentRow + l;
                    }
                    lastStatus = currentStatus;
                }
                // 处理最后一个合并区域
                if (startMergeRow != -1 && (currentRow + datas.size() - 1) > startMergeRow) {
                    mergedRegions.add(new CellRangeAddress(startMergeRow, currentRow + datas.size() - 1, 1, 1));
                }
                // 应用合并区域
                for (CellRangeAddress region : mergedRegions) {
                    sheet.addMergedRegion(region);
                }
                //合并第一列从第二行到最后一行
                sheet.addMergedRegion(new CellRangeAddress(2, datas.size() + 1, 0, 0));

                // 自动调整列宽
                int cellMaxWidth = 255 * 256; // Excel 单元格最大宽度
                for (int m = 0; m < titles.size(); m++) {
//                    sheet.autoSizeColumn(m);
                    sheet.setColumnWidth(m,4000);
                    // 获取当前列宽
                    int currentWidth = sheet.getColumnWidth(m);
                    // 如果列宽超过最大值，则设置为最大值
                    if (currentWidth > cellMaxWidth) {
                        sheet.setColumnWidth(m, cellMaxWidth);
                    }
                }
            }
        }

    }


    public static void exportSingleHeaderExcel(Workbook workbook, List<Map<String, Object>> dataList) {
        if(dataList!=null && dataList.size()>0) {
            for (int i = 0; i < dataList.size(); i++) {
                Map<String, Object> dataInfo =  dataList.get(i);
                String sheetName = dataInfo.get("sheetName").toString();
                List<Map<String, Object>> datas= (List<Map<String, Object>>) dataInfo.get("datas");
                List<Map<String, Object>> titles= (List<Map<String, Object>>) dataInfo.get("titles");
                Sheet sheet = workbook.createSheet(sheetName);

                CellStyle headerCellStyle = workbook.createCellStyle();
                CellStyle numberCellStyle = workbook.createCellStyle();
                CellStyle stringCellStyle = workbook.createCellStyle();
                setTableStyleByType(workbook, headerCellStyle, numberCellStyle, stringCellStyle);

                // 创建表头
                Row headerRow = sheet.createRow(0);
                for (int k = 0; k < titles.size(); k++) {
                    Cell cell = headerRow.createCell(k);
                    cell.setCellValue(titles.get(k).get("name") != null ? titles.get(k).get("name").toString() : "");
                    cell.setCellStyle(headerCellStyle); // 设置表头样式
                }

                // 填充数据
                for (int l = 0; l < datas.size(); l++) {
                    Row dataRow = sheet.createRow(l + 1);
                    Map<String, Object> data = datas.get(l);
                    for (int j = 0; j < titles.size(); j++) {
//                        Cell cell2 = dataRow.createCell(j);
//                        cell2.setCellValue(data.get(titles.get(j).get("key").toString()) == null ? "" : data.get(titles.get(j).get("key").toString()).toString());
//                        cell2.setCellStyle(cellStyle); // 设置数据样式
//                        cell2.setCellType(CellType.STRING);
                        Map<String, Object> title = titles.get(j);
                        String key = title.get("key").toString();
                        Object value = data.get(key);
                        Cell cell = dataRow.createCell(j);
                        if (value instanceof Number) {
                            // 处理数字类型
                            cell.setCellStyle(numberCellStyle);
                            cell.setCellValue(((Number) value).doubleValue());
                        } else {
                            // 处理非数字类型
                            cell.setCellStyle(stringCellStyle);
                            cell.setCellValue(value != null ? value.toString() : "");
                        }
                    }
                }

                // 自动调整列宽
                int cellMaxWidth = 255 * 256; // Excel 单元格最大宽度
                for (int m = 0; m < titles.size(); m++) {
                    sheet.autoSizeColumn(m);
                    // 获取当前列宽
                    int currentWidth = sheet.getColumnWidth(m);
                    // 如果列宽超过最大值，则设置为最大值
                    if (currentWidth > cellMaxWidth) {
                        sheet.setColumnWidth(m, cellMaxWidth);
                    }
                    if(m==0){
                        sheet.setColumnWidth(m,6000);
                    }else if( m==1){
                        sheet.setColumnWidth(m,4000);
                    }
                }
            }
        }
    }

    public static void setTableStyle(Workbook workbook, CellStyle headerCellStyle, CellStyle cellStyle){
        // 设置边框
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);// 设置文本水平居中对齐
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 设置文本垂直居中对齐

        // 创建表头样式
        headerCellStyle.setBorderTop(BorderStyle.THIN);
        headerCellStyle.setBorderBottom(BorderStyle.THIN);
        headerCellStyle.setBorderLeft(BorderStyle.THIN);
        headerCellStyle.setBorderRight(BorderStyle.THIN);
        headerCellStyle.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
        headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // 设置表头字体
        Font headerFont = workbook.createFont();
        headerFont.setFontName("Arial");
        headerFont.setFontHeightInPoints((short) 10);
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        headerCellStyle.setFont(headerFont);
    }

    public static void setTableStyleByType(Workbook workbook, CellStyle headerCellStyle, CellStyle numberCellStyle, CellStyle stringCellStyle) {
        // 设置表头样式
        headerCellStyle.setBorderTop(BorderStyle.THIN);
        headerCellStyle.setBorderBottom(BorderStyle.THIN);
        headerCellStyle.setBorderLeft(BorderStyle.THIN);
        headerCellStyle.setBorderRight(BorderStyle.THIN);
        headerCellStyle.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
        headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        Font headerFont = workbook.createFont();
        headerFont.setFontName("Arial");
        headerFont.setFontHeightInPoints((short) 10);
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        headerCellStyle.setFont(headerFont);

        // 数字样式：居中，带边框
        numberCellStyle.setBorderTop(BorderStyle.THIN);
        numberCellStyle.setBorderBottom(BorderStyle.THIN);
        numberCellStyle.setBorderLeft(BorderStyle.THIN);
        numberCellStyle.setBorderRight(BorderStyle.THIN);
        numberCellStyle.setAlignment(HorizontalAlignment.CENTER);
        numberCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // 字符串样式：左对齐，带边框
        stringCellStyle.setBorderTop(BorderStyle.THIN);
        stringCellStyle.setBorderBottom(BorderStyle.THIN);
        stringCellStyle.setBorderLeft(BorderStyle.THIN);
        stringCellStyle.setBorderRight(BorderStyle.THIN);
        stringCellStyle.setAlignment(HorizontalAlignment.LEFT);
        stringCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // 设置数据字体
        Font dataFont = workbook.createFont();
        dataFont.setFontName("Arial");
        dataFont.setFontHeightInPoints((short) 10);
        numberCellStyle.setFont(dataFont);
        stringCellStyle.setFont(dataFont);
    }

}
