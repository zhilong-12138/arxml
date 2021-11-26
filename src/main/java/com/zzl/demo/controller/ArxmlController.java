package com.zzl.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.rmi.runtime.Log;

import java.io.*;
import java.util.List;

/**
 * @author zhiLong
 * @version 1.0
 * @date 2021/11/26 15:14
 */
@Api(tags = "Arxml详情解析")
@RestController
@RequestMapping("/arxml")
public class ArxmlController {

    @Autowired
    ResourceLoader resourceLoader;

    @PostMapping(value = "/arxmlToDo", produces = "application/json;charset=utf-8")
    @ApiOperation(value = "Arxml解析")
    public String arxmlToDo() throws IOException {
        Resource resource = new ClassPathResource("service_interface_calc.arxml");
        InputStream is = resource.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String data = null;
        while ((data = br.readLine()) != null) {
            System.out.println(data);


        }

        br.close();
        isr.close();
        is.close();
        return data;

    }

    @PostMapping(value = "/arxmlJDomDemo", produces = "application/json;charset=utf-8")
    @ApiOperation(value = "jDomDemoArxml解析")
    public String jDomDemo() throws IOException, JDOMException {
        //1.创建SAXBuilder对象
        SAXBuilder saxBuilder = new SAXBuilder();
        //2.创建输入流
        Resource resource = new ClassPathResource("service_interface_calc.arxml");
        InputStream is = resource.getInputStream();
        //3.将输入流加载到build中
        Document document = saxBuilder.build(is);
        //4.获取根节点
        Element rootElement = document.getRootElement();
        //5.获取子节点
        List<Element> children = rootElement.getChildren();
        for (Element child : children) {
            System.out.println("通过rollno获取属性值:" + child.getAttribute("rollno"));
            List<Attribute> attributes = child.getAttributes();
            //打印属性
            for (Attribute attr : attributes) {
                System.out.println(attr.getName() + ":" + attr.getValue());
            }
            List<Element> childrenList = child.getChildren();
            System.out.println("======获取子节点-start======");
            for (Element o : childrenList) {
                System.out.println("节点名:" + o.getName() + "---" + "节点值:" + o.getValue());
            }
            System.out.println("======获取子节点-end======");
        }
        return null;

    }
}
