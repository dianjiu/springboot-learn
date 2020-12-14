package co.dianjiu.minio.config;

import com.alibaba.fastjson.JSONObject;
import io.minio.*;
import io.minio.http.Method;
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
    private MinioClient minioClient;
    @Autowired
    private MinioProp minioProp;

    /**
     * 创建bucket
     *
     * @param bucketName bucket名称
     */
    @SneakyThrows
    public void bucketExists(String bucketName) {
        // 检查存储桶是否已经存在
        BucketExistsArgs bucketExistsArgs = BucketExistsArgs.builder().bucket(bucketName).build();
        boolean isExist = minioClient.bucketExists(bucketExistsArgs);
        if(isExist) {
            System.out.println("Bucket already exists.");
        } else {
            // 创建一个名为asiatrip的存储桶，用于存储照片的zip文件。
            MakeBucketArgs makeBucketArgs = MakeBucketArgs.builder().bucket(bucketName).build();
            minioClient.makeBucket(makeBucketArgs);
        }
    }

    /**
     * 获取全部bucket
     */
    @SneakyThrows
    public List<Bucket> getAllBuckets() {
        return minioClient.listBuckets();
    }

    /**
     * 根据bucketName获取信息
     *
     * @param bucketName bucket名称
     */
    @SneakyThrows
    public Optional<Bucket> getBucket(String bucketName) {
        return minioClient.listBuckets().stream().filter(b -> b.name().equals(bucketName)).findFirst();
    }

    /**
     * 根据bucketName删除信息
     *
     * @param bucketName bucket名称
     */
    @SneakyThrows
    public void removeBucket(String bucketName) {
        RemoveBucketArgs removeBucketArgs = RemoveBucketArgs.builder().bucket(bucketName).build();
        minioClient.removeBucket(removeBucketArgs);
    }

    /**
     * 获取文件外链
     * TODO 待优化
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @param expires    过期时间 <=7
     * @return url
     */
    @SneakyThrows
    public String getObjectURL(String bucketName, String objectName, Integer expires) {
        //return minioClient.presignedGetObject(bucketName, objectName, expires);
        GetPresignedObjectUrlArgs objectUrlArgs =
                GetPresignedObjectUrlArgs.builder()
                        .expiry(expires)
                        .method(Method.GET)
                        .build();
        return minioClient.getPresignedObjectUrl(objectUrlArgs);
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
        //return minioClient.getObject(bucketName, objectName);
        GetObjectArgs build = GetObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .build();
        return minioClient.getObject(build);
    }

    /**
     * 上传文件
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @param stream     文件流
     * @throws Exception https://docs.minio.io/cn/java-minioClient-api-reference.html#putObject
     */
    public void putObject(String bucketName, String objectName, InputStream stream) throws Exception {
        //minioClient.putObject(bucketName, objectName, stream, stream.available(), "application/octet-stream");
        PutObjectArgs putObject = PutObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .stream(stream,-1, 6 * 1024 * 1024)
                .contentType("application/octet-stream")
                .build();
        minioClient.putObject(putObject);
    }

    /**
     * 上传文件
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @param stream     文件流
     * @throws Exception https://docs.minio.io/cn/java-minioClient-api-reference.html#putObject
     */
    public void putObject(String bucketName, String objectName, InputStream stream, String contextType) throws Exception {
        //minioClient.putObject(bucketName, objectName, stream, stream.available(), "application/octet-stream");
        PutObjectArgs putObject = PutObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .stream(stream,-1, 6 * 1024 * 1024)
                .contentType(contextType)
                .build();
        minioClient.putObject(putObject);
    }

    /**
     * 上传文件
     *
     * @param bucketName  bucket名称
     * @param objectName  文件名称
     * @param stream      文件流
     * @param size        大小
     * @param contextType 类型
     * @throws Exception https://docs.minio.io/cn/java-minioClient-api-reference.html#putObject
     */
    public void putObject(String bucketName, String objectName, InputStream stream, long size, String contextType) throws Exception {
        //minioClient.putObject(bucketName, objectName, stream, size, contextType);
        PutObjectArgs putObject = PutObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .stream(stream,size, 6 * 1024 * 1024)
                .contentType(contextType)
                .build();
        minioClient.putObject(putObject);
    }

    /**
     * 获取文件信息
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @throws Exception https://docs.minio.io/cn/java-minioClient-api-reference.html#statObject
     */
    public StatObjectResponse getObjectInfo(String bucketName, String objectName) throws Exception {
        //return minioClient.statObject(bucketName, objectName);
        StatObjectArgs statObject = StatObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .build();
        return minioClient.statObject(statObject);
    }

    /**
     * 删除文件
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @throws Exception https://docs.minio.io/cn/java-minioClient-api-reference.html#removeObject
     */
    public void removeObject(String bucketName, String objectName) throws Exception {
        //minioClient.removeObject(bucketName, objectName);
        RemoveObjectArgs removeObject = RemoveObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .build();
        minioClient.removeObject(removeObject);
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
        bucketExists(bucketName);
        // 文件名
        String originalFilename = file.getOriginalFilename();
        // 新的文件名
        String fileName = bucketName + "_" + System.currentTimeMillis() + originalFilename.substring(originalFilename.lastIndexOf("."));
        // 开始上传
        putObject(bucketName, fileName, file.getInputStream(), file.getSize(), file.getContentType());
        //minioClient.putObject(bucketName, fileName, file.getInputStream(), file.getContentType());
        /*PutObjectArgs putObject = PutObjectArgs.builder()
                .bucket(bucketName)
                .object(fileName)
                .stream(file.getInputStream(), file.getSize(), 6 * 1024 * 1024)
                .contentType(file.getContentType())
                .build();
        minioClient.putObject(putObject);*/
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
        /*if (!minioClient.bucketExists("test")) {
            minioClient.makeBucket("test");
        }*/
        bucketExists("test");
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
            //minioClient.putObject("test", fileName, multipartFile.getInputStream(), file.getContentType());
            putObject("test", fileName, multipartFile.getInputStream(), file.getContentType());
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
            //minioClient.putObject("test", fileName, file.getInputStream(), file.getContentType());
            putObject("test", fileName, file.getInputStream(), file.getContentType());
            // 返回状态以及图片路径
            res.put("code", 200);
            res.put("msg", "上传成功");
            res.put("url", minioProp.getEndpoint() + "/" + "test" + "/" + fileName);
        }
        return res;
    }
}

