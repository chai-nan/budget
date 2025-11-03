package net.cmkj.common.utils;

import com.github.pagehelper.util.StringUtil;
import jakarta.servlet.http.HttpServletResponse;
import net.cmkj.common.annotation.Excel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ExcelMultipleSheetsUtil {

    /**
     * 导出excel：可多个sheet页
     *
     * @param data          数据：Map 集合【key == 每一个sheet页的名称，value == sheet页数据】
     * @param excelFileName excel文件名
     * @param suffixName    后缀名
     * @param response      响应
     * @throws IOException 异常
     */
    public static void excelMultipleSheets(Map<String, Object> data, String excelFileName, String suffixName, HttpServletResponse response) throws IOException {
        // 创建工作簿
        try (Workbook workbook = new XSSFWorkbook()) {
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                String sheetName = entry.getKey();
                Object sheetData = entry.getValue();
                Sheet sheet = workbook.createSheet(sheetName);
                if (sheetData!=null) {
                    createSheetWithData(sheet, sheetData);
                }
            }

            setResponseHeader(response, excelFileName, suffixName);
            // 写出文件
            workbook.write(response.getOutputStream());
        }
    }


    /**
     * 创建表单并填充数据
     *
     * @param sheet 表单
     * @param data  数据
     */
    private static void createSheetWithData(Sheet sheet, Object data) {
        if (data instanceof List<?>) {
            createSheetWithListData(sheet, (List<?>) data);
        } else {
            createSheetWithObjectData(sheet, data);
        }
    }

    /**
     * 创建列表类型数据对应的Excel表单
     *
     * @param sheet    表单
     * @param dataList 数据列表
     */
    private static void createSheetWithListData(Sheet sheet, List<?> dataList) {
//        if (CollUtil.isNotEmpty(dataList)) {
            if (dataList.size()>0) {
            Object firstItem = dataList.get(0);
            createHeaderRow(sheet, firstItem.getClass());
            int rowIndex = 1;
            for (Object item : dataList) {
                createDataRow(sheet, item, rowIndex++);
            }
        }
    }

    /**
     * 创建对象类型数据对应的Excel表单
     *
     * @param sheet 表单
     * @param data  数据
     */
    private static void createSheetWithObjectData(Sheet sheet, Object data) {
        createHeaderRow(sheet, data.getClass());
        createDataRow(sheet, data, 1);
    }

    /**
     * 创建表头行
     *
     * @param sheet 表单
     * @param clazz 数据类
     */
    private static void createHeaderRow(Sheet sheet, Class<?> clazz) {
        // 创建单元格样式
        CellStyle headerCellStyle = createCellStyle2(sheet.getWorkbook());

        // 创建标题行
        Row headerRow = sheet.createRow(0);
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            createHeaderCell(sheet, headerCellStyle, fields, headerRow, i);
        }
    }

    /**
     * 创建数据行
     *
     * @param sheet    表单
     * @param data     数据
     * @param rowIndex 行号
     */
    private static void createDataRow(Sheet sheet, Object data, int rowIndex) {
        // 创建单元格样式
        CellStyle dataCellStyle = createCellStyle(sheet.getWorkbook());

        // 创建数据行
        Row dataRow = sheet.createRow(rowIndex);
        Field[] fields = data.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            createDataCell(dataCellStyle, fields, dataRow, i, data);
        }
    }

    /**
     * 创建单元格样式
     *
     * @param workbook 工作簿
     * @return 单元格样式
     */
    private static CellStyle createCellStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();

        // 设置 水平和垂直 居中对齐
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // 设置 上 下 左 右 边框及颜色
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        cellStyle.setFillBackgroundColor(IndexedColors.GREY_50_PERCENT.getIndex());

        // 设置字体
        Font dataFont = workbook.createFont();
        dataFont.setFontName("Arial");
        dataFont.setFontHeightInPoints((short) 14);
        cellStyle.setFont(dataFont);

        return cellStyle;
    }


    private static CellStyle createCellStyle2(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();

        // 设置 水平和垂直 居中对齐
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // 设置 上 下 左 右 边框及颜色
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        // 设置背景颜色
        cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
       // 设置填充模式为实心
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // 设置字体
        Font dataFont = workbook.createFont();
        dataFont.setFontName("Arial");
        dataFont.setFontHeightInPoints((short) 18);
        cellStyle.setFont(dataFont);

        return cellStyle;
    }

    /**
     * 创建Excel表头单元格
     *
     * @param sheet           表单
     * @param headerCellStyle 单元格样式
     * @param fields          字段
     * @param headerRow       标题行
     * @param i               序号
     */
    private static void createHeaderCell(Sheet sheet, CellStyle headerCellStyle, Field[] fields, Row headerRow, int i) {
        // 默认宽度
        double width = 16;
        Excel excelAnnotation = fields[i].getAnnotation(Excel.class);
//        if (excelAnnotation != null && !ObjectUtil.isEmpty(excelAnnotation.width())) {
            if (excelAnnotation != null && excelAnnotation.width()!=0.0) {
            width = excelAnnotation.width();
        }

        // 设置宽度
        sheet.setColumnWidth(i, (int) ((width + 0.72) * 256));

        if (excelAnnotation != null) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(excelAnnotation.name());
            cell.setCellStyle(headerCellStyle);
        }
    }

    /**
     * 创建Excel数据单元格
     *
     * @param dataCellStyle 单元格样式
     * @param fields        字段
     * @param dataRow       数据行
     * @param i             序号
     * @param data          数据
     */
    private static void createDataCell(CellStyle dataCellStyle, Field[] fields, Row dataRow, int i, Object data) {
        Cell cell = dataRow.createCell(i);
        cell.setCellStyle(dataCellStyle);
        System.err.println("###########:"+fields[i]);
        try {
            fields[i].setAccessible(true);
            Object value = fields[i].get(data);
            handleAnnotationAndSetValue(cell, fields[i], value);
        } catch (IllegalAccessException e) {
            System.err.println(fields[i]);
            e.printStackTrace();
        }
    }

    /**
     * 处理注解并设置单元格值
     *
     * @param cell  单元格
     * @param field 字段
     * @param value 值
     */
    private static void handleAnnotationAndSetValue(Cell cell, Field field, Object value) {
        if (field.isAnnotationPresent(Excel.class) && field.getAnnotation(Excel.class).dictType().length() > 0) {
            value = DictUtils.getDictLabel(field.getAnnotation(Excel.class).dictType(), String.valueOf(value));
        }
        if (field.isAnnotationPresent(Excel.class) && StringUtil.isNotEmpty(field.getAnnotation(Excel.class).dateFormat())) {
//            value = DateUtil.format(Convert.convert(Date.class, value), field.getAnnotation(Excel.class).dateFormat());

            // 假设field是一个Excel注解的对象，dateFormat()方法返回一个字符串，表示日期格式
            String dateFormat = field.getAnnotation(Excel.class).dateFormat();
           // 创建一个SimpleDateFormat对象，用于格式化日期
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
           // 将value转换为Date类型（这里假设value已经是Date类型）
            Date dateValue = (Date) value;
            // 使用SimpleDateFormat对象格式化日期
            if(null!= dateValue) {
                value = sdf.format(dateValue);
            }
        }
//        cell.setCellValue(ObjectUtil.isEmpty(value) ? null : value.toString());
        cell.setCellValue(value==null ? null : value.toString());
    }

    /**
     * 设置响应头
     *
     * @param response      响应
     * @param excelFileName 文件名
     * @param suffixName    后缀名
     * @throws UnsupportedEncodingException 编码异常
     */
    private static void setResponseHeader(HttpServletResponse response, String excelFileName, String suffixName) throws UnsupportedEncodingException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(excelFileName + suffixName, StandardCharsets.UTF_8));
    }


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
                cell.setCellStyle(cellStyle);
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




        public static void main(String[] args) {
            List<String> originalFilePathList = new ArrayList<>();
            originalFilePathList.add("D:/关键品格个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/核心能力个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/需要申请的数据.xlsx");
            originalFilePathList.add("D:/关键品格个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/核心能力个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/需要申请的数据.xlsx");
            originalFilePathList.add("D:/关键品格个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/核心能力个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/需要申请的数据.xlsx");
            originalFilePathList.add("D:/关键品格个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/核心能力个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/需要申请的数据.xlsx");

            originalFilePathList.add("D:/关键品格个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/核心能力个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/需要申请的数据.xlsx");
            originalFilePathList.add("D:/关键品格个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/核心能力个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/需要申请的数据.xlsx");
            originalFilePathList.add("D:/关键品格个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/核心能力个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/需要申请的数据.xlsx");
            originalFilePathList.add("D:/关键品格个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/核心能力个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/需要申请的数据.xlsx");
            originalFilePathList.add("D:/关键品格个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/核心能力个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/需要申请的数据.xlsx");
            originalFilePathList.add("D:/关键品格个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/核心能力个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/需要申请的数据.xlsx");
            originalFilePathList.add("D:/关键品格个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/核心能力个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/需要申请的数据.xlsx");
            originalFilePathList.add("D:/关键品格个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/核心能力个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/需要申请的数据.xlsx");
            originalFilePathList.add("D:/关键品格个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/核心能力个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/需要申请的数据.xlsx");
            originalFilePathList.add("D:/关键品格个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/核心能力个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/需要申请的数据.xlsx");
            originalFilePathList.add("D:/关键品格个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/核心能力个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/需要申请的数据.xlsx");
            originalFilePathList.add("D:/关键品格个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/核心能力个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/需要申请的数据.xlsx");
            originalFilePathList.add("D:/关键品格个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/核心能力个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/需要申请的数据.xlsx");
            originalFilePathList.add("D:/关键品格个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/核心能力个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/需要申请的数据.xlsx");
            originalFilePathList.add("D:/关键品格个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/核心能力个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/需要申请的数据.xlsx");
            originalFilePathList.add("D:/关键品格个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/核心能力个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/需要申请的数据.xlsx");
            originalFilePathList.add("D:/关键品格个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/核心能力个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/需要申请的数据.xlsx");
            originalFilePathList.add("D:/关键品格个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/核心能力个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/需要申请的数据.xlsx");
            originalFilePathList.add("D:/关键品格个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/核心能力个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/需要申请的数据.xlsx");
            originalFilePathList.add("D:/关键品格个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/核心能力个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/需要申请的数据.xlsx");
            originalFilePathList.add("D:/关键品格个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/核心能力个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/需要申请的数据.xlsx");
            originalFilePathList.add("D:/关键品格个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/核心能力个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/需要申请的数据.xlsx");
            originalFilePathList.add("D:/关键品格个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/核心能力个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/需要申请的数据.xlsx");
            originalFilePathList.add("D:/关键品格个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/核心能力个人报告话术库-河南项目.xlsx");
            originalFilePathList.add("D:/需要申请的数据.xlsx");
//            originalFilePathList.add("D:/核心能力作答数据(2).xlsx");

            FileInputStream originalFile = null;
            Workbook originalWorkbook;
            // 创建新的Excel文件
            // .xls和.xlsx格式在不同版本需要注意是用XSSFWorkbook还是HSSFWorkbook
            Workbook newWorkbook = new XSSFWorkbook();

            try {

                for (int i = 0; i < originalFilePathList.size(); i++) {

                    String originalFilePath = originalFilePathList.get(i);

                    // 读取原始Excel文件
                    originalFile = new FileInputStream(originalFilePath);

                    originalWorkbook = new XSSFWorkbook(originalFile);

                    Sheet originalSheet = originalWorkbook.getSheetAt(0);

                    Sheet newSheet = newWorkbook.createSheet("Sheet" + i);

                    // 复制原始Sheet到新文件（包括内容和样式）
                    copySheet(originalSheet, newSheet, newWorkbook);
                }
                // 保存新文件
                try (FileOutputStream fileOut = new FileOutputStream("D:/123456566.xlsx")) {
                    newWorkbook.write(fileOut);
                }

                System.out.println("复制完成");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // 在finally块中关闭文件流
                if (originalFile != null) {
                    try {
                        originalFile.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        private static void copySheet(Sheet source, Sheet target, Workbook newWorkbook) {
            // 处理合并单元格
            for (int i = 0; i < source.getNumMergedRegions(); i++) {
                CellRangeAddress mergedRegion = source.getMergedRegion(i);
                target.addMergedRegion(mergedRegion);
            }

            // 复制内容和样式
            for (int rowNum = 0; rowNum <= source.getLastRowNum(); rowNum++) {
                Row sourceRow = source.getRow(rowNum);
                Row newRow = target.createRow(rowNum);

                if (sourceRow != null) {
                    // 复制每个单元格的内容和样式
                    for (int colNum = 0; colNum < sourceRow.getLastCellNum(); colNum++) {
                        Cell sourceCell = sourceRow.getCell(colNum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        Cell newCell = newRow.createCell(colNum);

                        // 复制内容
                        copyCellValue(sourceCell, newCell);

                        // 复制样式
                        CellStyle newCellStyle = newWorkbook.createCellStyle();
                        newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
                        newCell.setCellStyle(newCellStyle);

                        // 复制宽度和高度
                        target.setColumnWidth(colNum, source.getColumnWidth(colNum));
                        newRow.setHeight(sourceRow.getHeight());
                    }
                }
            }
        }

        private static void copyCellValue(Cell sourceCell, Cell newCell) {
            if (sourceCell == null) {
                return;
            }

            DataFormatter dataFormatter = new DataFormatter();
            String formattedValue = dataFormatter.formatCellValue(sourceCell);

            if (sourceCell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(sourceCell)) {
                // 处理日期格式
                newCell.setCellValue(sourceCell.getDateCellValue());
            } else {
                newCell.setCellValue(formattedValue);
            }
        }



}

