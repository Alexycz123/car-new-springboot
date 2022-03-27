package com.ycz.controller;/*
 @author ycz
 @date 2021-07-07-20:51  文件上传接口
*/

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.ycz.common.CodeMsg;
import com.ycz.common.Constant;
import com.ycz.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("/file")
@Api(tags = "文件上传与下载")
public class FileUploadController {


    private static final String IMG_PATH = "/usr/imageServer/";

    @RequestMapping("/uploadImage.do")
    @ApiOperation(value = "图片上传")
    public Object uploadImage(@RequestParam("image")MultipartFile image, HttpServletRequest request){
        //图片名称
        String originalFilename=image.getOriginalFilename();
        //获取图片的后缀
        String extName=FileUtil.extName(originalFilename);
        //产生图片的名称
        String newFileName=DateUtil.format(new Date(),"yyyyMMddHHmmssSSS");

        newFileName=newFileName+"."+extName;

        //获取upload文件夹的物理路径
        String realPath = request.getSession().getServletContext().getRealPath(Constant.UPLOAD_FOLDER);

//        System.out.println(realPath);
        //文件保存的物理路径
        String fileRealPath=IMG_PATH+ File.separator+ newFileName;
        //文件还需要url路径 upload/xxxx.jpg

//        String url=Constant.UPLOAD_FOLDER+"/"+newFileName;
        String url="http://119.23.241.183:8085/getPhoto"+"/"+newFileName;

        System.out.println(url);
        try {
           // image.transferTo(new File(fileRealPath));
           FileUtils.copyInputStreamToFile(image.getInputStream(), new File(fileRealPath));
            return new Result(url);
        } catch (IOException e) {
            e.printStackTrace();
            //汽车图片上传失败
            return new Result(CodeMsg.CAR_UPLOAD_IMG_ERROR);
        }


    }


}
