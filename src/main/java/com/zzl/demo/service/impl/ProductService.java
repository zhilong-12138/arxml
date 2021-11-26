package com.zzl.demo.service.impl;

import com.zzl.demo.dao.entity.*;
import com.zzl.demo.dao.mapper.*;
import com.zzl.demo.entity.ProductBasePO;
import com.zzl.demo.entity.ProductNewBasePO;
import com.zzl.demo.util.ArrayUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author zhiLong
 * @version 1.0
 * @date 2021/4/22 16:22
 */
@Service
public class ProductService {
    private final static Logger logger = LoggerFactory.getLogger(ProductService.class);
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductSkuMapper productSkuMapper;

    @Autowired
    private ProductDetailMapper productDetailMapper;

    @Autowired
    private ProductImageMapper productImageMapper;

    @Autowired
    private ConfigCategoryMapper configCategoryMapper;


    @Transactional(rollbackFor = Exception.class)
    public int addProduct(List<ProductBasePO> poList) {
        poList.forEach(base -> {
            ProductPO productPo = new ProductPO();
            ProductSkuPO skuPo = new ProductSkuPO();
            ProductDetailPO detailPo = new ProductDetailPO();
            ProductImagePO imagePo = new ProductImagePO();
            if (StringUtils.isBlank(base.getImageOne())) {
                base.setImageOne("https://drp.joyoung.com");
            }
            if (StringUtils.isBlank(base.getImageTwo())) {
                base.setImageTwo("https://drp.joyoung.com");
            }
            if (StringUtils.isBlank(base.getImageThree())) {
                base.setImageThree("https://drp.joyoung.com");
            }
            if (StringUtils.isBlank(base.getPcDetail())) {
                base.setPcDetail("null");
            }
            if (StringUtils.isBlank(base.getProductLine())) {
                base.setProductLine("0");
            }
            Integer price = Integer.valueOf(findPriceChars(base.getPrice()));
            productPo.setDeleted(0);
            productPo.setProductStatus(0);
            productPo.setGmtCreate(getTimestamp());
            productPo.setGmtModified(getTimestamp());
            productPo.setUid(999);
            productPo.setTitle(base.getBrandName() + base.getModel());
            productPo.setEnTitle(base.getBrandName() + base.getModel());
            productPo.setSubtitle(base.getBrandName() + base.getModel());
            productPo.setModel(base.getModel());
            productPo.setBrandId(1);
            productPo.setMaxPrice(price);
            productPo.setMinPrice(price);
            productPo.setImage(base.getImageOne());
            int productFlag = productMapper.insert(productPo);
            Integer productId = productPo.getId();
            logger.info("productId:" + productId);
            skuPo.setPid(productId);
            skuPo.setDeleted(0);
            skuPo.setGmtCreate(getTimestamp());
            skuPo.setGmtModified(getTimestamp());
            skuPo.setImage(base.getImageOne());
            skuPo.setModel(base.getModel());
            skuPo.setPropIdx(0);
            skuPo.setPropName("");
            skuPo.setQtyNum(0);
            skuPo.setPropValue("");
            skuPo.setProductCode(base.getProductCode());
            skuPo.setCatalogId(base.getProductLine());
            skuPo.setFirstCatalogId("");
            skuPo.setSecondCatalogId("");
            skuPo.setForcePrice(0);
            int skuFlag = productSkuMapper.insert(skuPo);
            imagePo.setId(productId);
            imagePo.setDeleted(0);
            imagePo.setGmtCreate(getTimestamp());
            imagePo.setGmtModified(getTimestamp());
            imagePo.setGoodsImages(base.getImageOne().concat(",") + base.getImageTwo().concat(",") + base.getImageThree());
            int imageFlag = productImageMapper.insert(imagePo);
            detailPo.setId(productId);
            detailPo.setDeleted(0);
            detailPo.setGmtCreate(getTimestamp());
            detailPo.setGmtModified(getTimestamp());
            detailPo.setH5Detail(base.getPcDetail());
            detailPo.setPcDetail(base.getPcDetail());
            detailPo.setAfterSaleDetail("");
            int detailFlag = productDetailMapper.insert(detailPo);
        });
        return 1;
    }

    /**
     * 有小数点去掉加俩零，没有直接加俩零
     *
     * @param str
     * @return
     */
    public static String findPriceChars(String str) {
        String regEx = "[.]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        if (m.find()) {
            str = str.substring(0, str.indexOf("."));
            return str.concat("00");
        }
        return str.concat("00");
    }

    public static int getTimestamp() {
        return new Long(System.currentTimeMillis() / 1000).intValue();
    }


    @Transactional(rollbackFor = Exception.class)
    public int importProductBatch(List<ProductNewBasePO> productList) {
        List<String> cidNameList = new ArrayList<>();
        productList.forEach(product -> {
            if (StringUtils.isNotBlank(product.getProductLine())) {
                cidNameList.add(product.getProductLine());
            }
        });
        List<ConfigCategoryPO> configCategoryNameList = configCategoryMapper.listCidByNameList(cidNameList);
        if (CollectionUtils.isEmpty(configCategoryNameList)) {
            logger.error("查询不到分类");
        }
        Map<String, ConfigCategoryPO> cidMap;
        try {
            cidMap = ArrayUtil.listToMap(configCategoryNameList, "title");
        } catch (Exception e) {
            logger.error("转换异常");
            return 0;
        }
        int i = 0;
        for (ProductNewBasePO product : productList) {
            ProductPO productPo = new ProductPO();
            ProductSkuPO skuPo = new ProductSkuPO();
            ProductDetailPO detailPo = new ProductDetailPO();
            ProductImagePO imagePo = new ProductImagePO();
            ConfigCategoryPO categoryNameDto = cidMap.get(product.getProductLine());
            if (null != categoryNameDto) {
                productPo.setCid(categoryNameDto.getId());
            }
            productPo.setTitle(product.getTitle());
            productPo.setSubtitle(product.getSubtitle());
            productPo.setImage("");

            productPo.setDeleted(0);
            productPo.setProductStatus(0);
            productPo.setGmtCreate(getTimestamp());
            productPo.setGmtModified(getTimestamp());
            productPo.setUid(128956);
            productPo.setEnTitle("");
            productPo.setBrandId(1);
            productPo.setMaxPrice(0);
            productPo.setMinPrice(0);
            int productFlag = productMapper.insert(productPo);
            Integer productId = productPo.getId();
            logger.info("productId:{}", productId);
            skuPo.setPid(productId);
            skuPo.setImage("");
            skuPo.setModel(product.getModel());
            skuPo.setProductCode(product.getProductCode());
            skuPo.setPropValue(product.getPropValue());
            //默认值
            skuPo.setDeleted(0);
            skuPo.setGmtCreate(getTimestamp());
            skuPo.setGmtModified(getTimestamp());
            skuPo.setPropIdx(0);
            skuPo.setPropName("规格");
            skuPo.setQtyNum(0);
            skuPo.setCatalogId("1");
            skuPo.setFirstCatalogId("1");
            skuPo.setSecondCatalogId("1");
            skuPo.setForcePrice(0);
            int skuFlag = productSkuMapper.insert(skuPo);
            imagePo.setId(productId);
            imagePo.setGoodsImages("");
            //默认值
            imagePo.setDeleted(0);
            imagePo.setGmtCreate(getTimestamp());
            imagePo.setGmtModified(getTimestamp());
            int imageFlag = productImageMapper.insert(imagePo);
            detailPo.setId(productId);
            //默认值
            detailPo.setDeleted(0);
            detailPo.setGmtCreate(getTimestamp());
            detailPo.setGmtModified(getTimestamp());
            detailPo.setPcDetail("1");
            detailPo.setH5Detail("1");
            detailPo.setAfterSaleDetail("1");
            int detailFlag = productDetailMapper.insert(detailPo);
            ++i;
        }

        return i;
    }

    public static String translate(String gps) {
        String a = gps.split("°")[0].replace(" ", "");
        String b = gps.split("°")[1].split("'")[0].replace(" ", "");
        String c = gps.split("°")[1].split("'")[1].replace(" ", "").replace("\"", "");
        double result = Double.parseDouble(a)+Double.parseDouble(b)/60 + Double.parseDouble(c)/60/60;
        return String.valueOf(result);
    }



    public static void main(String[] args) {

        List<Integer> integerList = new ArrayList<>();
        integerList.add(1231);
        integerList.add(2223);
        integerList.add(3121);
        integerList.add(3121);
        integerList.add(1413);
        integerList.add(2131);
        integerList = integerList.stream().filter(item->item.equals(1231)).collect(Collectors.toList());
        System.out.println(integerList);
    }
}
