package co.dianjiu.minio.controller;

import co.dianjiu.minio.util.MinioUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

@Controller
public class MinIOController {

    @Autowired
    private MinioUtils minioUtils;

    @GetMapping("file")
    public String init() {
        return "file";
    }

    /**
     * 上传
     *
     * @param file
     * @param request
     * @return
     */
    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam(name = "file", required = false) MultipartFile file, HttpServletRequest request) {
        JSONObject res = null;
        try {
            res = minioUtils.uploadFile(file, "test");
        } catch (Exception e) {
            e.printStackTrace();
            res.put("code", 0);
            res.put("msg", "上传失败");
        }
        return res.toJSONString();
    }

    /**
     * 下载文件
     *
     * @param fileUrl  文件绝对路径
     * @param response
     * @throws IOException
     */
    @GetMapping("downloadFile")
    public void downloadFile(String fileUrl, HttpServletResponse response) throws IOException {
        if (StringUtils.isBlank(fileUrl)) {
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            String data = "文件下载失败";
            OutputStream ps = response.getOutputStream();
            ps.write(data.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            return;
        }
        try {
            // http://192.168.31.162:9000/test/test_1600150216537.jpg
            // 拿到文件路径 test_1600150216537.jpg
            //String url = fileUrl.split("9000/")[1];
            String url = fileUrl.split("/")[4];
            // 获取文件对象 bucketName，是指存储桶的名称 objectName，是指文件的路径，即存储桶下文件的相对路径
            InputStream object = minioUtils.getObject("test", url);
            byte[] buf = new byte[1024];
            int length = 0;
            response.reset();
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileUrl.substring(fileUrl.lastIndexOf("/") + 1), java.nio.charset.StandardCharsets.UTF_8));
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("UTF-8");
            OutputStream outputStream = response.getOutputStream();
            // 输出文件
            while ((length = object.read(buf)) > 0) {
                outputStream.write(buf, 0, length);
            }
            // 关闭输出流
            outputStream.close();
        } catch (Exception ex) {
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            String data = "文件下载失败";
            OutputStream ps = response.getOutputStream();
            ps.write(data.getBytes(java.nio.charset.StandardCharsets.UTF_8));
        }
    }
}
