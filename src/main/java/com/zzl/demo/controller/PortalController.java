package com.zzl.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.zzl.demo.entity.CarContants;
import com.zzl.demo.entity.CarString;
import com.zzl.demo.entity.ProductBasePO;
import com.zzl.demo.entity.ProductNewBasePO;
import com.zzl.demo.service.impl.ProductService;
import com.zzl.demo.util.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 处理controller
 *
 * @author zhiLong
 * @version 1.0
 * @date 2021/4/16 15:01
 */
@Api(tags = "经销商后台商品库服务接口")
@RestController
@RequestMapping("/portal")
public class PortalController {
    private final static Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductService productService;

    @ResponseBody
    @RequestMapping(
            value = "/toDo",
            produces = "application/json;charset=utf-8",
            method = RequestMethod.POST
    )
    public String getIndex(CarString string) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("What I do?", CarContants.CODE_MAPS.get(string.getStr()));

        //to do
        return jsonObject.toJSONString();
    }


    @PostMapping(value = "/", produces = "application/json;charset=utf-8")
    @ApiOperation(value = "查询商品列表")
    public String importOrderBatch(@RequestParam("file") MultipartFile file,
                                   HttpServletRequest request) throws IOException {
        InputStream inputStream = file.getInputStream();
        List<ProductBasePO> mainOrderList = new ArrayList<>();
        int num = 0;
        try {
            //1.8以上可使用forEach循环
            List<Cell[]> data = ExcelUtil.getDataFromExcel(Objects.requireNonNull(file.getOriginalFilename()), inputStream, 0);
            data.forEach(cells -> {
                ProductBasePO order = new ProductBasePO();
                order.setProductCode(cells[0] == null ? null : String.valueOf(cells[0].getStringCellValue()));
                order.setModel(cells[1] == null ? null : String.valueOf(cells[1].getStringCellValue()));
                order.setBrandName(cells[2] == null ? null : String.valueOf(cells[2].getStringCellValue()));
                order.setPrice(cells[3] == null ? null : cells[3].getStringCellValue());
                order.setProductLine(cells[4] == null ? null : String.valueOf(cells[4].getStringCellValue()));
                order.setImageOne(cells[5] == null ? null : String.valueOf(cells[5].getStringCellValue()));
                order.setImageTwo(cells[6] == null ? null : String.valueOf(cells[6].getStringCellValue()));
                order.setImageThree(cells[7] == null ? null : String.valueOf(cells[7].getStringCellValue()));
                order.setPcDetail(cells[8] == null ? null : String.valueOf(cells[8].getStringCellValue()));
                mainOrderList.add(order);
            });
            if (mainOrderList.size() > 0) {
                //处理其他逻辑。插入数据库等
                num = productService.addProduct(mainOrderList);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return String.valueOf(num);
    }


    @PostMapping(value = "/import_product_batch", produces = "application/json;charset=utf-8")
    @ApiOperation(value = "批量导入商品列表")
    public String importProductBatch(@RequestParam("file") MultipartFile file,
                                     HttpServletRequest request) throws IOException {
        InputStream inputStream = file.getInputStream();
        List<ProductNewBasePO> productList = new ArrayList<>();
        int num = 0;
        try {
            //1.8以上可使用forEach循环
            List<Cell[]> data = ExcelUtil.getDataFromExcel(Objects.requireNonNull(file.getOriginalFilename()), inputStream, 0);
            data.forEach(cells -> {
                ProductNewBasePO product = new ProductNewBasePO();

                product.setProductLine(cells[0] == null ? null : String.valueOf(cells[0].getStringCellValue()));
                product.setTitle(cells[1] == null ? null : String.valueOf(cells[1].getStringCellValue()));
                product.setSubtitle(cells[2] == null ? null : String.valueOf(cells[2].getStringCellValue()));
                product.setModel(cells[3] == null ? null : String.valueOf(cells[3].getStringCellValue()));
                product.setProductCode(cells[4] == null ? null : String.valueOf(cells[4].getStringCellValue()));
                product.setPropValue(cells[5] == null ? null : String.valueOf(cells[5].getStringCellValue()));
                productList.add(product);
            });
            if (productList.size() > 0) {
                logger.info("importProductBatch productList:{}", JSON.toJSONString(productList));
                //处理其他逻辑。插入数据库等
                num = productService.importProductBatch(productList);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return String.valueOf(num);
    }

    @PostMapping(value = "/import_product_price_batch", produces = "application/json;charset=utf-8")
    @ApiOperation(value = "批量导入商品价格列表")
    public String import_product_price_batch(@RequestParam("file") MultipartFile file,
                                             HttpServletRequest request) throws IOException {
        InputStream inputStream = file.getInputStream();
        List<String> productList = new ArrayList<>();
        int num = 0;
        try {
            //1.8以上可使用forEach循环
            List<Cell[]> data = ExcelUtil.getDataFromExcel(Objects.requireNonNull(file.getOriginalFilename()), inputStream, 0);
            data.forEach(cells -> {
                ProductNewBasePO product = new ProductNewBasePO();

                product.setPropValue(cells[0] == null ? null : String.valueOf(cells[0].getStringCellValue()));
                product.setProductCode(cells[1] == null ? null : String.valueOf(cells[1].getStringCellValue()));
                if (StringUtils.isNotBlank(product.getPropValue())) {
                    product.setPropValue(findPriceChars(product.getPropValue()));
                    String start = "update goods_sku set staff_price = '" + product.getPropValue();
                    String end = "' where product_code = '" + product.getProductCode() + "' and is_deleted = 0;";
                    String result = String.join("", start, end);
                    productList.add(result);
                }
            });
            if (productList.size() > 0) {
                logger.info("importProductBatch productList:{}", JSON.toJSONString(productList));
                //处理其他逻辑。插入数据库等
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return String.valueOf(num);
    }

    public static String findPriceChars(String str) {
        String regEx = "[.]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        if (m.find()) {
            str = m.replaceAll("");
            return str.concat("0");
        }
        return str.concat("00");
    }



}
