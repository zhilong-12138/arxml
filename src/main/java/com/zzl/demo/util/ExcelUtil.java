package com.zzl.demo.util;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * excel导出工具类
 *
 * @author zhiLong
 * @version 1.0
 * @date 2021/4/22 15:01
 */
public class ExcelUtil {

    private static final Logger log = LoggerFactory.getLogger(ExcelUtil.class);

    /**
     * @param filePath   文件保存绝对路径+文件名称
     * @param list       要到导出的数据集合
     * @param titles     excel表头
     * @param headFields 导出的实体类的字段名
     * @param isDownload false时不弹出下载文件，为true时浏览器会弹出下载文件
     * @throws Exception
     */
    public static void exportExcel(String filePath, List list, String[] titles, String[] headFields, boolean isDownload) throws Exception {
        long startTime = System.currentTimeMillis(); // 开始时间
        Sheet sheet = null; // 工作表对象
        Row nRow = null; // 行对象
        Cell nCell = null; // 列对象

        // 内存中只创建500个对象，写临时文件，当超过500条，就将内存中不用的对象释放。
        Workbook wb = new SXSSFWorkbook(500); // 关键语句
        /*Font font = wb.createFont();
        font.setBold(true);
        CellStyle headCellStyle = wb.createCellStyle();
        headCellStyle.setFont(font);*/

        /**
         * 单元格 样式
         */
        CellStyle headCellStyle = wb.createCellStyle();
        headCellStyle.setBorderTop(BorderStyle.THIN);
        headCellStyle.setBorderBottom(BorderStyle.THIN);
        headCellStyle.setBorderLeft(BorderStyle.THIN);
        headCellStyle.setBorderRight(BorderStyle.THIN);
        headCellStyle.setTopBorderColor(IndexedColors.BLACK.index);
        headCellStyle.setBottomBorderColor(IndexedColors.BLACK.index);
        headCellStyle.setLeftBorderColor(IndexedColors.BLACK.index);
        headCellStyle.setRightBorderColor(IndexedColors.BLACK.index);
        headCellStyle.setAlignment(HorizontalAlignment.CENTER); // 水平居中
        headCellStyle.setVerticalAlignment(VerticalAlignment.CENTER); // 上下居中

        /*  设置字体
        Font font = wb.createFont();
        font.setBold(true);
        headCellStyle.setFont(font);*/

        //每个sheet保存多少行
        int sheetNum = 1000000;
        int rowNo = 0; // 总行号
        int pageRowNo = 0; // 页行号


        if (list != null && list.size() > 0) {
            List<Map<String, Object>> dataMap = convertListBeanToListMap(list);

            for (int n = 0; n < dataMap.size(); n++) {
                //打印300000条后切换到下个工作表，可根据需要自行拓展，2百万，3百万...数据一样操作，只要不超过1048576就可以
                if (rowNo % sheetNum == 0) {
                    sheet = wb.createSheet("第" + (rowNo / sheetNum + 1) + "个工作簿");//建立新的sheet对象
                    sheet = wb.getSheetAt(rowNo / sheetNum);        //动态指定当前的工作表
                    pageRowNo = 0;        //每当新建了工作表就将当前工作表的行号重置为0

                    Row row = sheet.createRow(pageRowNo++);    //新建行对象
                    Cell cell = row.createCell(0);
                    for (int k = 0; k < titles.length; k++) {
                        cell = row.createCell(k);
                        cell.setCellStyle(headCellStyle);
                        cell.setCellValue(new XSSFRichTextString(titles[k]));
                    }
                }

                nRow = sheet.createRow(pageRowNo++);    //新建行对象

                // 打印每行, 列属性的个数
                for (int j = 0; j < headFields.length; j++) {
                    nCell = nRow.createCell(j);
                    nCell.setCellValue(dataMap.get(n).get(headFields[j]) == null ? "" : dataMap.get(n).get(headFields[j]).toString());
                    nCell.setCellStyle(headCellStyle);
                }
                rowNo++;
            }
            long finishedTime = System.currentTimeMillis(); // 处理完成时间
            log.info("exportExcel finished execute time: {}s", (finishedTime - startTime) / 1000);

        } else {
            sheet = wb.createSheet("第1个工作簿");//建立新的sheet对象
            sheet = wb.getSheetAt(0);        //动态指定当前的工作表

            Row row = sheet.createRow(pageRowNo++);    //新建行对象
            Cell cell = row.createCell(0);
            for (int k = 0; k < titles.length; k++) {
                cell = row.createCell(k);
                cell.setCellStyle(headCellStyle);
                cell.setCellValue(new XSSFRichTextString(titles[k]));
            }
            nRow = sheet.createRow(pageRowNo++);    //新建行对象
        }

//        OutputStream fOut = new FileOutputStream(filePath);
        OutputStream fOut = java.nio.file.Files.newOutputStream(Paths.get(new URI(filePath)));
        if (fOut != null) {
            try {
                wb.write(fOut);
                fOut.flush(); // 刷新缓冲区
            } catch (Exception e) {
                e.printStackTrace();
            }
            fOut.close();
        }
        if (wb != null) {
            try {
                wb.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        long stopTime = System.currentTimeMillis(); // 写文件时间
        log.info("exportExcel write xlsx file time: {}s", (stopTime - startTime) / 1000);

        //isDownload为true时，让浏览器弹出下载文件
        if (isDownload) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            try {
                File file = new File(filePath);
                String fileName = file.getName();// 获取日志文件名称
                //InputStream fis = new BufferedInputStream(new FileInputStream(filePath));
                InputStream fis = java.nio.file.Files.newInputStream(Paths.get(new URI(filePath)));

                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                fis.close();
                response.reset();
                //然后转换编码格式为utf-8,保证不出现乱码,这个文件名称用于浏览器的下载框中自动显示的文件名
                // 判断是否火狐浏览器
                String agent = request.getHeader("User-Agent");
                boolean isFirefox = (agent != null && agent.contains("Firefox"));
                if (isFirefox) {
                    fileName = new String(fileName.getBytes(StandardCharsets.UTF_8.name()), StandardCharsets.ISO_8859_1.name());
                } else {
                    fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.name());
                }
                response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
                response.addHeader("Content-Length", "" + file.length());
                OutputStream os = new BufferedOutputStream(response.getOutputStream());
                response.setContentType("application/octet-stream");
                os.write(buffer);// 输出文件
                os.flush();
                os.close();
            } catch (Exception e) {
                log.error("exportExcel download出错: {}", e.getMessage());
            }
        }
    }

    /**
     * 将 List<JavaBean>对象转化为List<Map>
     *
     * @param beanList
     * @return
     * @throws Exception
     */
    public static <T> List<Map<String, Object>> convertListBeanToListMap(List<T> beanList) throws Exception {
        List<Map<String, Object>> mapList = new ArrayList<>(beanList.size());
        for (int i = 0; i < beanList.size(); i++) {
            Object bean = beanList.get(i);
            Map<String, Object> map = convertBeanToMap(bean);
            mapList.add(map);
        }
        return mapList;
    }

    /**
     * 将一个 JavaBean 对象转化为一个 Map
     *
     * @param bean
     * @return
     * @throws InvocationTargetException
     */
    public static Map<String, Object> convertBeanToMap(Object bean)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        Class<? extends Object> type = bean.getClass();
        Map<String, Object> returnMap = new HashMap<>();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);

        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!"class".equals(propertyName)) {
                Method readMethod = descriptor.getReadMethod();
                readMethod.setAccessible(true);
                Object result = readMethod.invoke(bean, new Object[0]);
                if (result != null) {
                    returnMap.put(propertyName, result);
                } else {
                    returnMap.put(propertyName, null);
                }
            }
        }
        return returnMap;
    }

    /**
     * 读取出filePath中的所有数据信息
     *
     * @param fileName   excel文件的绝对路径
     * @param sheetIndex 第几个sheet
     * @throws IOException
     */
    public static List<Cell[]> getDataFromExcel(String fileName, InputStream inputStream, int sheetIndex) throws IOException {
        List<Cell[]> data = null;
        //判断是否为excel类型文件
        if (!fileName.endsWith(".xls") && !fileName.endsWith(".xlsx")) {
            log.error("Excel格式无效");
        }

        Workbook wookbook = null;

        try {

            if (fileName.endsWith(".xls")) {
                //2003版本的excel，用.xls结尾
                wookbook = new HSSFWorkbook(inputStream);
            } else if (fileName.endsWith(".xlsx")) {
                //2007版本的excel，用.xlsx结尾
                wookbook = new XSSFWorkbook(inputStream);
            }

            if (wookbook == null) {
                log.error("excel读取报错：null exception");
                //自定义异常类
                //new BusinessException.code("00001", "excel解析异常",e);
            }
            //得到一个工作表
            Sheet sheet = wookbook.getSheetAt(sheetIndex);

            //获得表头
            Row rowHead = sheet.getRow(0);

            log.info("表头的数量：{}", rowHead.getPhysicalNumberOfCells());

            //获得数据的总行数
            int totalRowNum = sheet.getLastRowNum();
            log.info("总数量：{}", totalRowNum);
//                if (totalRowNum > 200000) {
//                      log.error("Excel读取数量不能大于200000行");
//                }
            data = new ArrayList<>(totalRowNum);
            Cell[] rowData = null;
            //获得所有数据
            for (int i = 1; i <= totalRowNum; i++) {
                //获得第i行对象
                Row row = sheet.getRow(i);
                //         //获得获得第i行第0列的 String类型对象
                //         Cell cell = row.getCell((short)0);
                //         name = cell.getStringCellValue().toString();
                //
                //         //获得一个数字类型的数据
                //         cell = row.getCell((short)1);
                //         latitude = cell.getStringCellValue();

                rowData = new Cell[rowHead.getPhysicalNumberOfCells()];
                for (int k = 0; k < row.getLastCellNum(); k++) {
                    Cell cell = row.getCell((short) k);
                    if (cell != null) {
                        cell.setCellType(CellType.STRING);
                    }
                    rowData[k] = cell;
                }
                data.add(rowData);
            }
        } catch (Exception e) {
            log.error("excel读取报错：{}", e.getMessage(), e);
            //自定义异常类
            //new BusinessException.code("00001", "excel解析异常",e);
        } finally {
            if (wookbook != null) {
                wookbook.close();
            }
        }
        return data;
    }
}

