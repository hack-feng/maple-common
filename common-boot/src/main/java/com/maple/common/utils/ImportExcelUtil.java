package com.maple.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 导入excel通用工具类
 *
 * @author ZhangFZ
 * @date 2019-12-20 9:21
 */
public class ImportExcelUtil {

    public static List<Map<String, Object>> importExcel(MultipartFile file, List<String> keyList) throws Exception {

         XSSFWorkbook hw = new XSSFWorkbook(file.getInputStream());

        //获取第一个sheet页
        XSSFSheet sheet = hw.getSheetAt(0);

        //容器
        List<Map<String, Object>> result = new ArrayList<>();

        //遍历行 从下标第二行开始（去除标题
        //int rowCount = sheet.getLastRowNum();  //这里获得的是Num
        //row = sheet.getRow(rowIndex)           //这里使用的是Index
        // 所以要i < sheet.getLastRowNum() + 1
        for (int i = 2; i < sheet.getLastRowNum() + 1; i++) {
            XSSFRow row = sheet.getRow(i);
            if (row != null) {
                //装载obj
                result.add(dataObj(keyList, row));
            }
        }
        return result;
    }

    /**
     * 拼装单个obj
     */
    private static Map<String, Object> dataObj(List<String> keyList, XSSFRow row) {
        if (keyList == null || keyList.size() < 1) {
            return null;
        }

        //容器
        Map<String, Object> map = new HashMap<>(16);

        //注意excel表格字段顺序要和keyList字段顺序对齐 （如果有多余字段请另作特殊下标对应处理）
        for (int j = 0; j < keyList.size(); j++) {
            map.put(keyList.get(j), getVal(row.getCell(j)));
        }
        return map;
    }

    /**
     * 处理val（暂时只处理string和number，可以自己添加自己需要的val类型）
     */
    private static Object getVal(XSSFCell xssfCell) {
        if(xssfCell != null){
            if(xssfCell.getCellType() == CellType.NUMERIC){
                double number = xssfCell.getNumericCellValue();
                // 如果小数部分为0，则强制转成int类型。
                if(number % 1 == 0){
                    return (int) number;
                }else{
                    return number;
                }
            }else if(xssfCell.getCellType() == CellType.STRING){
                if(StringUtils.isBlank(xssfCell.getStringCellValue())){
                    return null;
                }else{
                    return xssfCell.getStringCellValue();
                }
            }else if(xssfCell.getCellType() == CellType.BOOLEAN){
                return xssfCell.getBooleanCellValue();
            }
        }
        return null;
    }
}
