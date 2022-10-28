package cn.huanzi.qch.springbootfilesupload.attachment.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

/**
 * 文件上传、下载、显示
 */
@RestController
@RequestMapping("/file/")
public class AttachmentController2 {
    //模拟数据库
    private final HashMap<String,HashMap<String,String>> map = new HashMap<>();

    //日期格式化
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");

    /**
     * 文件存储根路径
     */
    @Value("${file.upload-path}")
    private String uploadPath;

    /**
     * 上传
     */
    @PostMapping("upload")
    public HashMap<String, String> upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();

        /*
            把附件表id当做文件名保存文件
            1、可以避免上传同名文件时造成冲突
            2、文件名无业务含义，附件安全性高
         */
        String fileid = getUUID();

        //文件名称
        String filename = originalFilename.substring(0,originalFilename.lastIndexOf("."));

        //文件类型，后缀名
        String filetype = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

        //文件大小（MB），保留两位小数点
        double size = file.getSize() / 1024.00 / 1024.00;
        String filesize = String.format("%.2f",size)+"MB";

        //保存路径，按年月份文件夹
        String path = simpleDateFormat.format(new Date());
        //如果文件夹不存在，创建文件夹
        File pathFile = new File(uploadPath + path);
        if(!pathFile.exists()){
            pathFile.mkdir();
        }

        //保存文件，例如：E:\fj\20221027\123.txt
        file.transferTo(new File(uploadPath  + path + "\\" + fileid + "." + filetype));

        //保存附件表，做好映射关联关系
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("fileid",fileid);
        hashMap.put("filename",filename);
        hashMap.put("filetype",filetype);
        hashMap.put("filesize",filesize);
        hashMap.put("path",path);
        map.put(fileid,hashMap);

        return hashMap;
    }

    /**
     * 下载
     */
    @PostMapping("download/{fileid}")
    public ResponseEntity<byte[]> downLoad(@PathVariable String fileid) throws IOException {
        //根据查询附件表（此处为模拟数据）
        HashMap<String,String> hashMap = map.get(fileid);
        if(hashMap == null){
            throw new RuntimeException("下载错误：附件可能已经不存在！");
        }
        String filename = hashMap.get("filename");
        String filetype = hashMap.get("filetype");
        String path = hashMap.get("path");

        File file = new File(uploadPath  + path + "\\" + fileid + "." + filetype);
        byte[] fileBytes = new byte[Math.toIntExact(file.length())];
        new FileInputStream(file).read(fileBytes);

        //设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", new String(filename.getBytes(StandardCharsets.UTF_8),StandardCharsets.ISO_8859_1) + "." + filetype);

        //下载文件
        return new ResponseEntity<>(fileBytes, headers, HttpStatus.CREATED);
    }

    /**
     * 显示
     * 浏览器预览，仅支持图片、txt、pdf等，不支持Word、Excel
     */
    @GetMapping("show/{fileid}")
    public void show(HttpServletRequest request, HttpServletResponse response, @PathVariable String fileid) throws ServletException, IOException {
        HashMap<String,String> hashMap = map.get(fileid);
        if(hashMap == null){
            throw new RuntimeException("显示错误：附件可能已经不存在！");
        }
        String filetype = hashMap.get("filetype");
        String path = hashMap.get("path");

        //转发附件路径映射接口
        request.getRequestDispatcher("/api/file/" + path + "/" + fileid + "." + filetype).forward(request,response);
    }

    /**
     * 生成32位UUID编码
     */
    private String getUUID(){
        return UUID.randomUUID().toString().trim().replaceAll("-", "");
    }

}
