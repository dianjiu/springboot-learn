package co.dianjiu.minio.util;

import co.dianjiu.minio.config.MinioProp;
import com.alibaba.fastjson.JSONObject;
import io.minio.MinioClient;
import io.minio.ObjectStat;
import io.minio.messages.Bucket;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@Component
public class MinioUtils {

    @Autowired
    private MinioClient client;
    @Autowired
    private MinioProp minioProp;

    /**
     * 创建bucket
     *
     * @param bucketName bucket名称
     */
    @SneakyThrows
    public void createBucket(String bucketName) {
        if (!client.bucketExists(bucketName)) {
            client.makeBucket(bucketName);
        }
    }

    /**
     * 获取全部bucket
     */
    @SneakyThrows
    public List<Bucket> getAllBuckets() {
        return client.listBuckets();
    }

    /**
     * 根据bucketName获取信息
     *
     * @param bucketName bucket名称
     */
    @SneakyThrows
    public Optional<Bucket> getBucket(String bucketName) {
        return client.listBuckets().stream().filter(b -> b.name().equals(bucketName)).findFirst();
    }

    /**
     * 根据bucketName删除信息
     *
     * @param bucketName bucket名称
     */
    @SneakyThrows
    public void removeBucket(String bucketName) {
        client.removeBucket(bucketName);
    }

    /**
     * 获取文件外链
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @param expires    过期时间 <=7
     * @return url
     */
    @SneakyThrows
    public String getObjectURL(String bucketName, String objectName, Integer expires) {
        return client.presignedGetObject(bucketName, objectName, expires);
    }

    /**
     * 获取文件
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @return 二进制流
     */
    @SneakyThrows
    public InputStream getObject(String bucketName, String objectName) {
        return client.getObject(bucketName, objectName);
    }

    /**
     * 上传文件
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @param stream     文件流
     * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#putObject
     */
    public void putObject(String bucketName, String objectName, InputStream stream) throws Exception {
        client.putObject(bucketName, objectName, stream, stream.available(), "application/octet-stream");
    }

    /**
     * 上传文件
     *
     * @param bucketName  bucket名称
     * @param objectName  文件名称
     * @param stream      文件流
     * @param size        大小
     * @param contextType 类型
     * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#putObject
     */
    public void putObject(String bucketName, String objectName, InputStream stream, long size, String contextType) throws Exception {
        client.putObject(bucketName, objectName, stream, size, contextType);
    }

    /**
     * 获取文件信息
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#statObject
     */
    public ObjectStat getObjectInfo(String bucketName, String objectName) throws Exception {
        return client.statObject(bucketName, objectName);
    }

    /**
     * 删除文件
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#removeObject
     */
    public void removeObject(String bucketName, String objectName) throws Exception {
        client.removeObject(bucketName, objectName);
    }

    /**
     * 上传文件
     *
     * @param file       文件
     * @param bucketName 存储桶
     * @return
     */
    public JSONObject uploadFile(MultipartFile file, String bucketName) throws Exception {
        JSONObject res = new JSONObject();
        res.put("code", 0);
        // 判断上传文件是否为空
        if (null == file || 0 == file.getSize()) {
            res.put("msg", "上传文件不能为空");
            return res;
        }
        // 判断存储桶是否存在
        createBucket(bucketName);
        // 文件名
        String originalFilename = file.getOriginalFilename();
        // 新的文件名
        String fileName = bucketName + "_" + System.currentTimeMillis() + originalFilename.substring(originalFilename.lastIndexOf("."));
        // 开始上传
        client.putObject(bucketName, fileName, file.getInputStream(), file.getContentType());
        res.put("code", 1);
        res.put("msg", minioProp.getEndpoint() + "/" + bucketName + "/" + fileName);
        return res;
    }

    /**
     * 判断文件是否为图片
     */
    public boolean isPicture(String imgName) {
        boolean flag = false;
        if (StringUtils.isBlank(imgName)) {
            return false;
        }
        String[] arr = {"bmp", "dib", "gif", "jfif", "jpe", "jpeg", "jpg", "png", "tif", "tiff", "ico"};
        for (String item : arr) {
            if (item.equals(imgName)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 上传文件
     *
     * @param file 文件
     * @return
     */
    public JSONObject uploadFile(MultipartFile file) throws Exception {
        JSONObject res = new JSONObject();
        res.put("code", 500);
        // 判断上传文件是否为空
        if (null == file || 0 == file.getSize()) {
            res.put("msg", "上传文件不能为空");
            return res;
        }
        // 判断存储桶是否存在
        if (!client.bucketExists("test")) {
            client.makeBucket("test");
        }
        // 拿到文件后缀名，例如：png
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        // UUID 作为文件名
        String uuid = String.valueOf(UUID.randomUUID());
        // 新的文件名
        String fileName = System.currentTimeMillis() + "/" + uuid + "." + suffix;
        /**
         * 判断是否是图片
         * 判断是否超过了 100K
         */
        if (isPicture(suffix) && (1024 * 1024 * 0.1) <= file.getSize()) {
            // 在项目根目录下的 upload 目录中生成临时文件
            File newFile = new File(ClassUtils.getDefaultClassLoader().getResource("upload").getPath() + uuid + "." + suffix);
            // 小于 1M 的
            if ((1024 * 1024 * 0.1) <= file.getSize() && file.getSize() <= (1024 * 1024)) {
                Thumbnails.of(file.getInputStream()).scale(1f).outputQuality(0.3f).toFile(newFile);
            }
            // 1 - 2M 的
            else if ((1024 * 1024) < file.getSize() && file.getSize() <= (1024 * 1024 * 2)) {
                Thumbnails.of(file.getInputStream()).scale(1f).outputQuality(0.2f).toFile(newFile);
            }
            // 2M 以上的
            else if ((1024 * 1024 * 2) < file.getSize()) {
                Thumbnails.of(file.getInputStream()).scale(1f).outputQuality(0.1f).toFile(newFile);
            }
            // 获取输入流
            FileInputStream input = new FileInputStream(newFile);
            // 转为 MultipartFile
            MultipartFile multipartFile = new MockMultipartFile("file", newFile.getName(), "text/plain", input);
            // 开始上传
            client.putObject("test", fileName, multipartFile.getInputStream(), file.getContentType());
            // 删除临时文件
            newFile.delete();
            // 返回状态以及图片路径
            res.put("code", 200);
            res.put("msg", "上传成功");
            res.put("url", minioProp.getEndpoint() + "/" + "test" + "/" + fileName);
        }
        // 不需要压缩，直接上传
        else {
            // 开始上传
            client.putObject("test", fileName, file.getInputStream(), file.getContentType());
            // 返回状态以及图片路径
            res.put("code", 200);
            res.put("msg", "上传成功");
            res.put("url", minioProp.getEndpoint() + "/" + "test" + "/" + fileName);
        }
        return res;
    }
}
