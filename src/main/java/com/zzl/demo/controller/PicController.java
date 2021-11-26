package com.zzl.demo.controller;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author zhiLong
 * @version 1.0
 * @date 2021/7/19 13:38
 */
@Api(tags = "图片详情解析")
@RestController
@RequestMapping("/pic")
public class PicController {

    @PostMapping(value = "/picAnalysis", produces = "application/json;charset=utf-8")
    @ApiOperation(value = "分析图片获取地址")
    public Map<String, String> picAnalysis(@RequestParam("file") String file,
                                           HttpServletRequest request) throws JpegProcessingException, IOException {
        Map<String, String> map = new HashMap(16);
        System.out.println("强大的语言正在识别图片地址...");
        File picFile = new File(file);
        Metadata metadata = JpegMetadataReader.readMetadata(picFile);
        Iterator<Directory> it = metadata.getDirectories().iterator();
        while (it.hasNext()) {
            Directory exif = it.next();
            Iterator<Tag> tags = exif.getTags().iterator();
            while (tags.hasNext()) {
                Tag tag = tags.next();
                map.put(tag.getTagName(), tag.getDescription());
                System.out.println(tag.getTagName() + ":" + tag.getDescription());
            }
        }
        System.out.println("图片分析完毕！");
        getAddress(map);
        return map;
    }


    public static void getAddress(Map<String, String> param) {
        String longitude = translate(param.get("GPS Longitude"));
        String latitude = translate(param.get("GPS Latitude"));
        System.out.println("longitude:[" + longitude + "]latitude:[" + latitude + "]");
    }

    public static String translate(String gps) {
        String a = gps.split("°")[0].replace(" ", "");
        String b = gps.split("°")[1].split("'")[0].replace(" ", "");
        String c = gps.split("°")[1].split("'")[1].replace(" ", "").replace("\"", "");
        double result = Double.parseDouble(a) + Double.parseDouble(b) / 60 + Double.parseDouble(c) / 60 / 60;
        return String.valueOf(result);
    }
}
