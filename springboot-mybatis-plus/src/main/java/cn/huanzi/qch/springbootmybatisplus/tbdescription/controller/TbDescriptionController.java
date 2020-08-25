package cn.huanzi.qch.springbootmybatisplus.tbdescription.controller;

import cn.huanzi.qch.springbootmybatisplus.common.controller.CommonController;
import cn.huanzi.qch.springbootmybatisplus.tbdescription.entity.TbDescription;
import cn.huanzi.qch.springbootmybatisplus.tbdescription.entity.TbDescriptionVo;
import cn.huanzi.qch.springbootmybatisplus.tbdescription.service.TbDescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户描述表 前端控制器
 * </p>
 *
 * @author huanzi-qch
 * @since 2020-08-25
 */
@RestController
@RequestMapping("/tbDescription/")
public class TbDescriptionController extends CommonController<TbDescriptionVo,TbDescription> {

    @Autowired
    private TbDescriptionService tbDescriptionService;

}