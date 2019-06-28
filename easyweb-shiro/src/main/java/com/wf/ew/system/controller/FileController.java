package com.wf.ew.system.controller;

import com.wf.ew.common.JsonResult;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.tika.Tika;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 文件服务器
 * Created by wangfan on 2018-12-24 下午 4:10.
 */
@CrossOrigin
@Controller
@RequestMapping("/file")
public class FileController {
    private static final int UPLOAD_DIS_INDEX = 0;  // 上传到第几个磁盘下面
    private static final String UPLOAD_DIR = "/upload/";  // 上传的目录

    /**
     * 上传文件
     */
    @ResponseBody
    @PostMapping("/upload")
    public JsonResult upload(@RequestParam MultipartFile file) {
        // 文件原始名称
        String originalFileName = file.getOriginalFilename();
        String suffix = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        // 保存到磁盘
        String path = getDate() + UUID.randomUUID().toString().replaceAll("-", "") + "." + suffix;
        File outFile = new File(File.listRoots()[UPLOAD_DIS_INDEX], UPLOAD_DIR + path);
        try {
            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdirs();
            }
            long fileSize = file.getSize();
            if ((fileSize / 1024 / 1024) >= 1 && isImgFile(outFile)) {  // 图片超过1Mb压缩到1Mb以下
                Thumbnails.of(file.getInputStream()).scale(1f).outputQuality(1f / (fileSize / 1024 / 1024)).toFile(outFile);
            } else {
                file.transferTo(outFile);
            }
            return JsonResult.ok("上传成功").put("url", path);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.error("上传失败").put("error", e.getMessage());
        }
    }

    /**
     * 查看原文件
     */
    @GetMapping("/{y}/{m}/{d}/{file:.+}")
    public void file(@PathVariable("y") String y, @PathVariable("m") String m, @PathVariable("d") String d, @PathVariable("file") String filename, HttpServletResponse response) {
        String filePath = y + "/" + m + "/" + d + "/" + filename;
        outputFile(UPLOAD_DIR + filePath, response);
    }

    // 输出文件流
    private void outputFile(String file, HttpServletResponse response) {
        // 判断文件是否存在
        File inFile = new File(File.listRoots()[UPLOAD_DIS_INDEX], file);
        if (!inFile.exists()) {
            PrintWriter writer;
            try {
                response.setContentType("text/html;charset=UTF-8");
                writer = response.getWriter();
                writer.write("<!doctype html><title>404 Not Found</title><h1 style=\"text-align: center\">404 Not Found</h1><hr/><p style=\"text-align: center\">Easy File Server</p>");
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        // 获取文件类型
        String contentType = getFileType(inFile);
        if (contentType != null) {
            response.setContentType(contentType);
        } else {  // 没有类型弹出下载
            response.setContentType("application/force-download");
            String newName;
            try {
                newName = URLEncoder.encode(inFile.getName(), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                newName = inFile.getName();
            }
            response.setHeader("Content-Disposition", "attachment;fileName=" + newName);
        }
        // 输出文件流
        OutputStream os = null;
        FileInputStream is = null;
        try {
            is = new FileInputStream(inFile);
            os = response.getOutputStream();
            byte[] bytes = new byte[1024];
            int len;
            while ((len = is.read(bytes)) != -1) {
                os.write(bytes, 0, len);
            }
            os.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 获取当前日期
    private String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
        return sdf.format(new Date());
    }

    // 获取文件类型
    private String getFileType(File file) {
        String contentType = null;
        try {
            contentType = new Tika().detect(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentType;
    }

    // 是否是图片类型
    private boolean isImgFile(File file) {
        String contentType = getFileType(file);
        if (contentType != null && contentType.startsWith("image/")) {
            return true;
        }
        return false;
    }

}
