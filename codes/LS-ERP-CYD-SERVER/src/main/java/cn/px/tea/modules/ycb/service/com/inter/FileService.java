package cn.px.tea.modules.ycb.service.com.inter;

import cn.px.tea.common.minio.bo.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;

public interface FileService {

    boolean existBucket(String bucketName);
    boolean makeBucket(String bucketName);
    boolean removeBucket(String bucketName);

    boolean exist(String sid);
    FileInfo view(String sid);
    void preview(HttpServletResponse response, String sid);
    FileInfo getFileInfo(String sid);
    List<FileInfo> listFileInfo(List<String> listSid);
    List<FileInfo> listDir(String dir);

    FileInfo upload(InputStream in, String fileName);
    FileInfo upload(MultipartFile file);
    List<FileInfo> multiUpload(MultipartFile[] files);
    FileInfo freshUpload(String sid, MultipartFile file);
    List<FileInfo> freshMultiUpload(List<String> listSid, MultipartFile[] files);

    void download(HttpServletResponse response, String sid);
    InputStream getFileStream(String sid);

    FileInfo remove(String sid);
    List<FileInfo> remove(List<String> listSid);
    List<FileInfo> removeDir(String dir);
}
