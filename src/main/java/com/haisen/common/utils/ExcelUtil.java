package com.haisen.common.utils;

/**
 * <p>〈功能概述〉.
 *
 * @author haisen /20205/21
 */

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.Assert;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.imageio.ImageIO;

/**
 * Excel工具类
 */
@Slf4j
public class ExcelUtil {

    public static void main(String[] args) throws Throwable {
        String path = "test.xls";

        // sheetNames
        List<String> sheetNames = Lists.newArrayList();
        for (int i = 0; i < 3; i++) {
            sheetNames.add("Sheet_" + i);
        }

        // 表头 & 数据行
        Map<String, List<String>> multiTableHeader = Maps.newHashMap();

        Map<String, List<List<String>>> multiTableData = Maps.newHashMap();

        for (String sheetName : sheetNames) {

            List<String> headers = Lists.newArrayList();

            for (int i = 0; i < 10; i++) {
                headers.add("表头_" + i);
            }
            multiTableHeader.put(sheetName, headers);

            ArrayList<List<String>> data = Lists.newArrayList();
            multiTableData.put(sheetName, data);

            List<String> rowData = Lists.newArrayList();
            for (int i = 0; i < headers.size(); i++) {
                rowData.add("数据" + i);
            }

            data.add(rowData);
            data.add(rowData);
            data.add(rowData);
        }

        ExcelUtil.generateExcelWithMultiSheet(path, sheetNames, multiTableHeader, multiTableData);
    }

    public static void generateExcelWithSingleSheet(String path, String sheetName,
                                                    List<String> tableHeader,
                                                    List<List<String>> tableData) {

        Map<String, List<String>> multiTableHeader = Maps.newHashMap();
        multiTableHeader.put(sheetName, tableHeader);

        Map<String, List<List<String>>> multiTableData = Maps.newHashMap();
        multiTableData.put(sheetName, tableData);

        ExcelUtil.generateExcelWithMultiSheet(
                path,
                Lists.newArrayList(sheetName),
                multiTableHeader,
                multiTableData
        );
    }

    /**
     * @param path
     * @param sheetNames
     * @param multiTableHeader
     * @param multiTableData
     */
    public static void generateExcelWithMultiSheet(String path, List<String> sheetNames,
                                                   Map<String, List<String>> multiTableHeader,
                                                   Map<String, List<List<String>>> multiTableData
    ) {
        try {
            Assert.state(CollectionUtils.isNotEmpty(sheetNames), "List of sheet pages not provided");

            // 检查确保这几个参数的sheet 一一对应
            log.info("generateExcelWithMultiSheet Check params!");
            checkParams(sheetNames, multiTableHeader, multiTableData);

            XSSFWorkbook workbook = new XSSFWorkbook();

            // 创建文件，并生成 sheet 页
            log.info("generateExcelWithMultiSheet createFileAndDeleteOld!");
            Map<String, XSSFSheet> sheetRefCache = createFileAndDeleteOld(path, sheetNames, workbook);
            log.info("generateExcelWithMultiSheet createFileAndDeleteOld finished!");
            appendHeader(multiTableHeader, sheetRefCache, workbook);

            appendData(multiTableHeader, multiTableData, sheetRefCache, workbook);
            File file = new File(path);
            FileOutputStream out = new FileOutputStream(file);
            workbook.write(out);
            out.flush();
            out.close();
            log.info("文件：[" + path + "] 创建完毕... file size: " + file.length());
        } catch (Exception e) {
            log.error("generateExcelWithMultiSheet Error [" + e.getMessage() + "] ", e);
        }
    }

    private static void appendData(Map<String, List<String>> multiTableHeader, Map<String, List<List<String>>> multiTableData,
                                   Map<String, XSSFSheet> sheetRefCache, XSSFWorkbook workbook) {
        XSSFCellStyle cellStyle = getDataRowStyle(workbook);

        multiTableData.keySet().stream()
                .forEach(
                        sheetName -> {
                            List<List<String>> sheetData = multiTableData.get(sheetName);
                            XSSFSheet sheet = sheetRefCache.get(sheetName);

                            // 遍历集合数据，产生数据行
                            for (int i = 0, rowNum = i + 1; i < sheetData.size(); i++, rowNum = i + 1) {
                                XSSFRow dataRow = sheet.createRow(rowNum);
                                dataRow.setHeightInPoints(20);

                                List<String> rowData = sheetData.get(i);

                                for (int cellIndex = 0; cellIndex < rowData.size(); cellIndex++) {
                                    XSSFCell cell = dataRow.createCell(cellIndex);
                                    cell.setCellStyle(cellStyle);
                                    cell.setCellType(CellType.STRING);

                                    String columnValue = String.valueOf(Optional.ofNullable(rowData.get(cellIndex)).orElse(StringUtils.EMPTY));
                                    cell.setCellValue(new XSSFRichTextString(columnValue));
                                }
                            }

                            // int columnLength = multiTableHeader.get(sheetName).size();
                            // sheet.autoSizeColumn(columnLength);

                            log.info("sheetName：[" + sheetName + "] sheetData 填充完毕...");
                        }
                );
    }

    private static void checkParams(List<String> sheetNames,
                                    Map<String, List<String>> multiTableHeader,
                                    Map<String, List<List<String>>> multiTableData) {

        String s1 = StringUtils.join(sheetNames.stream().sorted().toArray());
        String s2 = StringUtils.join(multiTableHeader.keySet().stream().sorted().toArray());
        String s3 = StringUtils.join(multiTableData.keySet().stream().sorted().toArray());

        if (StringUtils.equals(s1, s2) && StringUtils.equals(s2, s3)) {
            return;
        }

        throw new RuntimeException("Data not aligned");
    }

    private static void appendHeader(Map<String, List<String>> headers, Map<String, XSSFSheet> sheetRefCache, XSSFWorkbook workbook) {
        // 设置这些样式
        final XSSFCellStyle cellStyle = getHeaderRowStyle(workbook);

        // 产生表格标题行
        sheetRefCache.keySet()
                .stream()
                .forEach(
                        sheetName -> {
                            XSSFSheet sheet = sheetRefCache.get(sheetName);
                            XSSFRow headerRow = sheet.createRow(0);
                            // headerRow.setHeightInPoints(20);

                            List<String> columnsNames = headers.get(sheetName);

                            for (int i = 0; i < columnsNames.size(); i++) {
                                XSSFRichTextString columnValue = new XSSFRichTextString(columnsNames.get(i));
                                XSSFCell cell = headerRow.createCell(i);

                                cell.setCellStyle(cellStyle);
                                cell.setCellValue(columnValue);
                            }

                            log.info("sheetName：[" + sheetName + "] headerRow 创建成功...");

                            for (int k = 0; k < headers.size(); k++) {
                                sheet.autoSizeColumn(k);
                            }
                        }
                );
    }

    private static Map<String, XSSFSheet> createFileAndDeleteOld(String path, List<String> sheetNames, XSSFWorkbook workbook) throws IOException {
        File file = new File(path);

        if (file.exists()) {
            log.info("createFileAndDeleteOld file exists, delete it!");
            file.delete();
        } else {
            file.getParentFile().mkdirs();
            file.createNewFile();
            log.info("createFileAndDeleteOld create file, file path {}", file.getAbsolutePath());
        }

        Map<String, XSSFSheet> sheetRefCache = Maps.newHashMap();
        sheetNames.forEach(
                sheetName -> {
                    XSSFSheet sheet = workbook.createSheet(sheetName);
                    sheetRefCache.put(sheetName, sheet);

                    log.info("文件：[" + path + "] [" + sheetName + "] 创建成功...");
                }
        );

        return sheetRefCache;
    }

    private static XSSFCellStyle getHeaderRowStyle(XSSFWorkbook workbook) {
        XSSFFont font = workbook.createFont();
        // font.setFontName(HSSFFont.FONT_ARIAL);          // 字体
        font.setFontHeightInPoints((short) 14);         // 字号
        font.setBold(true);                             // 加粗
        // font.setColor(HSSFColor.BLUE.index);         // 颜色

        XSSFCellStyle cellStyle = workbook.createCellStyle(); // 设置单元格样式
        // cellStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());

        // cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // cellStyle.setAlignment(HorizontalAlignment.CENTER);         // 水平居中
        // cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);   // 垂直居中

        // cellStyle.setWrapText(false);

        // cellStyle.setBorderBottom(BorderStyle.THIN);
        // cellStyle.setBorderLeft(BorderStyle.THIN);
        // cellStyle.setBorderRight(BorderStyle.THIN);
        // cellStyle.setBorderTop(BorderStyle.THIN);

        cellStyle.setFont(font);

        return cellStyle;
    }

    private static XSSFCellStyle getDataRowStyle(XSSFWorkbook workbook) {
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        // cellStyle.setAlignment(HorizontalAlignment.LEFT);

        // cellStyle.setBorderBottom(BorderStyle.THIN);
        // cellStyle.setBorderLeft(BorderStyle.THIN);
        // cellStyle.setBorderRight(BorderStyle.THIN);
        // cellStyle.setBorderTop(BorderStyle.THIN);

        cellStyle.setDataFormat((short) 0x31);              // 设置显示格式，避免点击后变成科学计数法了
        cellStyle.setWrapText(false);                       // 设置自动换行
        return cellStyle;
    }

    public static void downloadImage(String picUrl) {

    }

    public static void insertImageByColumn(Workbook workBook, Sheet topPic, String picPath) {
        BufferedImage bufferImg = null; // 图片
        try {
            // 先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray
            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
            //将图片读到BufferedImage
            bufferImg = ImageIO.read(new File(picPath));
            // 将图片写入流中
            ImageIO.write(bufferImg, "png", byteArrayOut);
            // 利用HSSFPatriarch将图片写入EXCEL
            XSSFDrawing patriarch = (XSSFDrawing) topPic.createDrawingPatriarch();
            //图片一导出到单元格B2中
            XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 0, 0,
                    (short) 0, 0, (short) 10, 30);
            // 插入图片
            patriarch.createPicture(anchor, workBook.addPicture(byteArrayOut
                    .toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));
            System.out.println("插入成功");
        } catch (IOException io) {
            io.printStackTrace();
            System.out.println("插入失败 : " + io.getMessage());
        } finally {

        }
    }
}