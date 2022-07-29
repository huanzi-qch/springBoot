package cn.huanzi.qch.springbootfilesupload.attachment.controller;

import cn.huanzi.qch.springbootfilesupload.attachment.vo.AttachmentVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * 附件管理
 */
@RestController
@RequestMapping("/attachment/")
public class AttachmentController {
    /**
     * 附件存储路径
     */
    @Value("${file.upload-path}")
    private String uploadPath;

    /**
     * 上传附件
     */
    @PostMapping("upload")
    public AttachmentVo upload(AttachmentVo attachmentVo) throws IOException {
        MultipartFile[] files = attachmentVo.getFiles();
        attachmentVo.setFileUrl(new String[files.length]);
        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            String filename = file.getOriginalFilename();
            file.transferTo(new File(uploadPath+filename)); //保存文件
            attachmentVo.getFileUrl()[i] = filename;
        }

        attachmentVo.setId(UUID.randomUUID().toString());
        attachmentVo.setUploadTime(new Date());
        attachmentVo.setFiles(null);
        return attachmentVo;
    }

    /**
     * 下载附件
     */
    @RequestMapping("/download")
    public ResponseEntity downLoad(String url) throws IOException {
        //文件数据、文件名
        File file = new File(uploadPath+url);
        byte[] fileBytes = new byte[Math.toIntExact(file.length())];
        String fileName = file.getName();
        new FileInputStream(file).read(fileBytes);

        //设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", fileName);

        //下载文件
        return new ResponseEntity<>(fileBytes, headers, HttpStatus.CREATED);
    }

    /**
     * 根据业务编号获取附件信息
     */
    @PostMapping("/getAttachmentByApplyId")
    public AttachmentVo getAttachmentByApplyId(AttachmentVo attachmentVo){
        //模拟数据
        attachmentVo.setFileUrl(new String[]{"123.png","123.txt"});
        return attachmentVo;
    }
}
