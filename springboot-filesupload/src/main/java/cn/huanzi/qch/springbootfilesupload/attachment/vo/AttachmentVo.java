package cn.huanzi.qch.springbootfilesupload.attachment.vo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * 附件管理Vo
 */
@Data
public class AttachmentVo {
    private String id;//表id

    private String applyId;//关联的业务id

    private Date uploadTime;//上传时间

    private String[] fileUrl;//文件存储完整路径

    private MultipartFile[] files;//上传的文件

    private String[] deletes;//编辑时，删除的文件
}
