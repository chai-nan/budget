package net.cmkj.common.utils;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ExcelUtils {

    /**
     * 单表头Excel
     * @param titles
     * @param datas
     * @return
     * @throws IOException
     */
    public static byte[] generateSingleHeaderExcel(List<Map<String, Object>> titles, List<Map<String, Object>> datas) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");
        CellStyle headerCellStyle = workbook.createCellStyle();
        CellStyle cellStyle = workbook.createCellStyle();
        setTableStyle(workbook, headerCellStyle, cellStyle);

        // 设置文本靠左对齐样式
        CellStyle textLeftCellStyle = workbook.createCellStyle(); // 新增：文本靠左样式
        textLeftCellStyle.cloneStyleFrom(cellStyle);
        textLeftCellStyle.setAlignment(HorizontalAlignment.LEFT);
        textLeftCellStyle.setDataFormat(workbook.createDataFormat().getFormat("@"));

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < titles.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(titles.get(i).get("name")!=null ? titles.get(i).get("name").toString() : "");
            // 应用表头样式
            cell.setCellStyle(headerCellStyle);
        }
        for (int i = 0; i < datas.size(); i++) {
            Row dataRow = sheet.createRow(i + 1);
            Map<String, Object> data = datas.get(i);
            for (int j = 0; j < titles.size(); j++) {
                Cell cell = dataRow.createCell(j);
                cell.setCellValue(data.get(titles.get(j).get("key").toString())==null ? "" : data.get(titles.get(j).get("key").toString()).toString());
                // 应用单元格样式
                if ("fylx".equals(titles.get(j).get("key").toString()) || "fymc".equals(titles.get(j).get("key").toString())) {
                    cell.setCellStyle(textLeftCellStyle); // 设置为文本靠左样式
                } else {
                    cell.setCellStyle(cellStyle);
                }
            }
        }
        // 自动调整列宽
        for (int i = 0; i < titles.size(); i++) {
            sheet.autoSizeColumn(i);
            // 稍微增加列宽以适应内容
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) + 1500);
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        workbook.close();
        return bos.toByteArray();
    }

    /**
     * 多表头Excel
     * @param titles
     * @param datas
     * @return
     * @throws IOException
     */
    public static byte[] generateMultiHeaderExcel(List<Map<String, Object>> titles, List<Map<String, Object>> datas) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");
        CellStyle headerCellStyle = workbook.createCellStyle();
        CellStyle stringCellStyle = workbook.createCellStyle();
        CellStyle numberCellStyle = workbook.createCellStyle();
        setTableStyleByType(workbook, headerCellStyle, numberCellStyle, stringCellStyle);


        Row primaryRow = sheet.createRow(0);
        boolean hasSecondaryTitles = false;
        int columnIndex = 0;
        for (Map<String, Object> title : titles) {
            int startColumnIndex = columnIndex;
            Cell cell = primaryRow.createCell(columnIndex);
            cell.setCellValue(title.get("name") != null ? title.get("name").toString() : "");
            // 应用表头样式
            cell.setCellStyle(headerCellStyle);
            if (title.get("children") != null) {
                List<Map<String, Object>> children = (List<Map<String, Object>>) title.get("children");
                hasSecondaryTitles = true;
                for (Map<String, Object> child : children) {
                    Cell childCell = sheet.getRow(1) == null ? sheet.createRow(1).createCell(columnIndex) : sheet.getRow(1).createCell(columnIndex);
                    childCell.setCellValue(child.get("name") != null ? child.get("name").toString() : "");
                    // 应用表头样式
                    childCell.setCellStyle(headerCellStyle);
                    columnIndex++;
                }
                if (columnIndex - startColumnIndex > 1) {
                    sheet.addMergedRegion(new CellRangeAddress(0, 0, startColumnIndex, columnIndex - 1));
                }
            } else {
                sheet.addMergedRegion(new CellRangeAddress(0, 1, columnIndex, columnIndex));
                columnIndex++;
            }
        }
        if (hasSecondaryTitles) {
            Row secondaryRow = sheet.createRow(1);
            columnIndex = 0;
            for (Map<String, Object> title : titles) {
                if (title.get("children") != null) {
                    List<Map<String, Object>> children = (List<Map<String, Object>>) title.get("children");
                    for (Map<String, Object> child : children) {
                        Cell cell = secondaryRow.createCell(columnIndex);
                        cell.setCellValue(child.get("name") != null ? child.get("name").toString() : "");
                        // 应用表头样式
                        cell.setCellStyle(headerCellStyle);
                        columnIndex++;
                    }
                } else {
                    columnIndex++;
                }
            }
        }
        for (int i = 0; i < datas.size(); i++) {
            Row dataRow = sheet.createRow(i + (hasSecondaryTitles ? 2 : 1));
            Map<String, Object> data = datas.get(i);
            columnIndex = 0;
            for (Map<String, Object> title : titles) {
                if (title.get("children") != null) {
                    List<Map<String, Object>> children = (List<Map<String, Object>>) title.get("children");
                    for (Map<String, Object> child : children) {
                        Cell cell = dataRow.createCell(columnIndex);
                        Object value = data.get(child.get("key").toString());
//                        cell.setCellValue(value != null ? value.toString() : "");
//                        // 应用单元格样式
//                        if ("fylx".equals(child.get("key").toString())) {
//                            cell.setCellStyle(textLeftCellStyle); // 设置为文本靠左样式
//                        } else {
//                            cell.setCellStyle(cellStyle);
//                        }
                        if (value instanceof Number) {
                            // 处理数字类型
                            cell.setCellStyle(numberCellStyle);
                            cell.setCellValue(((Number) value).doubleValue());
                        } else {
                            // 处理非数字类型
                            cell.setCellStyle(stringCellStyle);
                            cell.setCellValue(value != null ? value.toString() : "");
                        }
                        columnIndex++;
                    }
                } else {
                    Cell cell = dataRow.createCell(columnIndex);
                    Object value = data.get(title.get("key").toString());
                    /*
                    cell.setCellValue(value != null ? value.toString() : "");
                    // 应用单元格样式
                    if ("fylx".equals(title.get("key").toString())) {
                        cell.setCellStyle(textLeftCellStyle); // 设置为文本靠左样式
                    } else {
                        cell.setCellStyle(cellStyle);
                    }
                    */
                    if (value instanceof Number) {
                        // 处理数字类型
                        cell.setCellStyle(numberCellStyle);
                        cell.setCellValue(((Number) value).doubleValue());
                    } else {
                        // 处理非数字类型
                        cell.setCellStyle(stringCellStyle);
                        cell.setCellValue(value != null ? value.toString() : "");
                    }
                    columnIndex++;
                }
            }
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        workbook.close();
        return bos.toByteArray();
    }

    public static void exportSingleHeaderExcel(HttpServletResponse response, List<Map<String, Object>> titles, List<Map<String, Object>> datas) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");
        CellStyle headerCellStyle = workbook.createCellStyle();
        CellStyle cellStyle = workbook.createCellStyle();
        setTableStyle(workbook, headerCellStyle, cellStyle);
        // 创建表头
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < titles.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(titles.get(i).get("name") != null ? titles.get(i).get("name").toString() : "");
            cell.setCellStyle(headerCellStyle); // 设置表头样式
        }

        // 填充数据
        for (int i = 0; i < datas.size(); i++) {
            Row dataRow = sheet.createRow(i + 1);
            Map<String, Object> data = datas.get(i);
            for (int j = 0; j < titles.size(); j++) {
                Cell cell = dataRow.createCell(j);
                cell.setCellValue(data.get(titles.get(j).get("key").toString()) == null ? "" : data.get(titles.get(j).get("key").toString()).toString());
                cell.setCellStyle(cellStyle); // 设置数据样式
                cell.setCellType(CellType.STRING);
            }
        }

        // 自动调整列宽
        int cellMaxWidth = 255 * 256; // Excel 单元格最大宽度
        for (int i = 0; i < titles.size(); i++) {
            sheet.autoSizeColumn(i);
            // 获取当前列宽
            int currentWidth = sheet.getColumnWidth(i);
            // 如果列宽超过最大值，则设置为最大值
            if (currentWidth > cellMaxWidth) {
                sheet.setColumnWidth(i, cellMaxWidth);
            }
        }

        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String currentDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
        response.setHeader("Content-Disposition", "attachment; filename=" + currentDate+ ".xlsx");

        // 写入数据并关闭工作簿
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            workbook.write(bos);
            response.getOutputStream().write(bos.toByteArray());
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void exportMultiHeaderExcel(HttpServletResponse response, List<Map<String, Object>> titles, List<Map<String, Object>> datas) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");
        CellStyle headerCellStyle = workbook.createCellStyle();
        CellStyle cellStyle = workbook.createCellStyle();
        setTableStyle(workbook, headerCellStyle, cellStyle);

        Row primaryRow = sheet.createRow(0);
        boolean hasSecondaryTitles = false;
        int columnIndex = 0;
        for (Map<String, Object> title : titles) {
            int startColumnIndex = columnIndex;
            Cell cell = primaryRow.createCell(columnIndex);
            cell.setCellValue(title.get("name") != null ? title.get("name").toString() : "");
            // 应用表头样式
            cell.setCellStyle(headerCellStyle);
            if (title.get("children") != null) {
                List<Map<String, Object>> children = (List<Map<String, Object>>) title.get("children");
                hasSecondaryTitles = true;
                for (Map<String, Object> child : children) {
                    Cell childCell = sheet.getRow(1) == null ? sheet.createRow(1).createCell(columnIndex) : sheet.getRow(1).createCell(columnIndex);
                    childCell.setCellValue(child.get("name") != null ? child.get("name").toString() : "");
                    // 应用表头样式
                    childCell.setCellStyle(headerCellStyle);
                    columnIndex++;
                }
                if (columnIndex - startColumnIndex > 1) {
                    sheet.addMergedRegion(new CellRangeAddress(0, 0, startColumnIndex, columnIndex - 1));
                }
            } else {
                sheet.addMergedRegion(new CellRangeAddress(0, 1, columnIndex, columnIndex));
                columnIndex++;
            }
        }
        if (hasSecondaryTitles) {
            Row secondaryRow = sheet.createRow(1);
            columnIndex = 0;
            for (Map<String, Object> title : titles) {
                if (title.get("children") != null) {
                    List<Map<String, Object>> children = (List<Map<String, Object>>) title.get("children");
                    for (Map<String, Object> child : children) {
                        Cell cell = secondaryRow.createCell(columnIndex);
                        cell.setCellValue(child.get("name") != null ? child.get("name").toString() : "");
                        // 应用表头样式
                        cell.setCellStyle(headerCellStyle);
                        columnIndex++;
                    }
                } else {
                    columnIndex++;
                }
            }
        }
        for (int i = 0; i < datas.size(); i++) {
            Row dataRow = sheet.createRow(i + (hasSecondaryTitles ? 2 : 1));
            Map<String, Object> data = datas.get(i);
            columnIndex = 0;
            for (Map<String, Object> title : titles) {
                if (title.get("children") != null) {
                    List<Map<String, Object>> children = (List<Map<String, Object>>) title.get("children");
                    for (Map<String, Object> child : children) {
                        Cell cell = dataRow.createCell(columnIndex);
                        Object value = data.get(child.get("key").toString());
                        cell.setCellValue(value != null ? value.toString() : "");
                        // 应用单元格样式
                        cell.setCellStyle(cellStyle);
                        columnIndex++;
                    }
                } else {
                    Cell cell = dataRow.createCell(columnIndex);
                    Object value = data.get(title.get("key").toString());
                    cell.setCellValue(value != null ? value.toString() : "");
                    // 应用单元格样式
                    cell.setCellStyle(cellStyle);
                    columnIndex++;
                }
            }
        }

        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String currentDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
        response.setHeader("Content-Disposition", "attachment; filename=" + currentDate+ ".xlsx");

        // 写入数据并关闭工作簿
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            workbook.write(bos);
            response.getOutputStream().write(bos.toByteArray());
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
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
    public static void generateSheet(Workbook workbook, Sheet sheet, Map<String, Object> data) {
        List<Map<String, Object>> titles = (List<Map<String, Object>>) data.get("titleName");
        List<Map<String, Object>> datas = (List<Map<String, Object>>) data.get("titleDate");

        CellStyle headerCellStyle = workbook.createCellStyle();
        CellStyle cellStyle = workbook.createCellStyle();
        setTableStyle(workbook, headerCellStyle, cellStyle);

        Row primaryRow = sheet.createRow(0);
        boolean hasSecondaryTitles = false;
        int columnIndex = 0;
        for (Map<String, Object> title : titles) {
            int startColumnIndex = columnIndex;
            Cell cell = primaryRow.createCell(columnIndex);
            cell.setCellValue(title.get("name") != null ? title.get("name").toString() : "");
            cell.setCellStyle(headerCellStyle);
            if (title.get("children") != null) {
                List<Map<String, Object>> children = (List<Map<String, Object>>) title.get("children");
                hasSecondaryTitles = true;
                for (Map<String, Object> child : children) {
                    Cell childCell = sheet.getRow(1) == null ? sheet.createRow(1).createCell(columnIndex) : sheet.getRow(1).createCell(columnIndex);
                    childCell.setCellValue(child.get("name") != null ? child.get("name").toString() : "");
                    childCell.setCellStyle(headerCellStyle);
                    columnIndex++;
                }
                if (columnIndex - startColumnIndex > 1) {
                    sheet.addMergedRegion(new CellRangeAddress(0, 0, startColumnIndex, columnIndex - 1));
                }
            } else {
                sheet.addMergedRegion(new CellRangeAddress(0, 1, columnIndex, columnIndex));
                columnIndex++;
            }
        }

        if (hasSecondaryTitles) {
            Row secondaryRow = sheet.createRow(1);
            columnIndex = 0;
            for (Map<String, Object> title : titles) {
                if (title.get("children") != null) {
                    List<Map<String, Object>> children = (List<Map<String, Object>>) title.get("children");
                    for (Map<String, Object> child : children) {
                        Cell cell = secondaryRow.createCell(columnIndex);
                        cell.setCellValue(child.get("name") != null ? child.get("name").toString() : "");
                        cell.setCellStyle(headerCellStyle);
                        columnIndex++;
                    }
                } else {
                    columnIndex++;
                }
            }
        }

        for (int i = 0; i < datas.size(); i++) {
            Row dataRow = sheet.createRow(i + (hasSecondaryTitles ? 2 : 1));
            Map<String, Object> dataEntry = datas.get(i);
            columnIndex = 0;
            for (Map<String, Object> title : titles) {
                if (title.get("children") != null) {
                    List<Map<String, Object>> children = (List<Map<String, Object>>) title.get("children");
                    for (Map<String, Object> child : children) {
                        Cell cell = dataRow.createCell(columnIndex);
                        Object value = dataEntry.get(child.get("key").toString());
                        cell.setCellValue(value != null ? value.toString() : "");
                        cell.setCellStyle(cellStyle);
                        columnIndex++;
                    }
                } else {
                    Cell cell = dataRow.createCell(columnIndex);
                    Object value = dataEntry.get(title.get("key").toString());
                    cell.setCellValue(value != null ? value.toString() : "");
                    cell.setCellStyle(cellStyle);
                    columnIndex++;
                }
            }
        }
    }
}
