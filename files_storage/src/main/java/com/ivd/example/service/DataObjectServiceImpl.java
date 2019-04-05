package com.ivd.example.service;

import com.ivd.example.dao.DataObjectDao;
import com.ivd.example.entity.DataObject;
import com.ivd.example.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * {@inheritDoc}
 */
@Service
@Transactional
public class DataObjectServiceImpl implements DataObjectService {

    @Value("${upload.path}")
    private String uploadPath;

    private final DataObjectDao dataObjectDao;

    public DataObjectServiceImpl(DataObjectDao dataObjectDao) {
        this.dataObjectDao = dataObjectDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DataObject> findByAuthor(User user) {
        return dataObjectDao.findByAuthor(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long messageCount() {
        return dataObjectDao.count();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addData(User user, MultipartFile file, String name) throws IOException {
        String resultName = UUID.randomUUID().toString() + "." + file.getOriginalFilename();
        if (StringUtils.isEmpty(name)) {
            name = Objects.requireNonNull(file).getOriginalFilename();
        } else {
            name = name + getFileExtension(file.getOriginalFilename());
        }
        file.transferTo(new File(getUploadPath(resultName)));

        DataObject dataObject = new DataObject();
        dataObject.setName(name);
        dataObject.setUuidName(resultName);
        dataObject.setAuthor(user);
        dataObject.setContentType(Objects.requireNonNull(file).getContentType());
        dataObject.setAccessCount(0);

        dataObjectDao.save(dataObject);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveData(DataObject message) {
        dataObjectDao.save(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteDataById(DataObject message) {
        File file = new File(uploadPath + "/" + message.getUuidName());
        if (file != null) {
            file.delete();
        }
        dataObjectDao.deleteById(message.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity downloadData(DataObject message) {
        addAccessCount(message);

        File file = new File(uploadPath + "/" + message.getUuidName());

        String mimeType = message.getContentType();
        if (StringUtils.isEmpty(mimeType)) {
            mimeType = "application/octet-stream";
        }
        try {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + message.getName())
                    .contentLength(file.length())
                    .contentType(MediaType.parseMediaType(mimeType))
                    .body(resource);
        } catch (FileNotFoundException ex) {
            return ResponseEntity.badRequest().body("Ошибка -> " + ex.getMessage());
        }
    }

    private String getUploadPath(String resultName) {
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        return uploadDir.getAbsolutePath() + "\\" + resultName;
    }

    private void addAccessCount(DataObject message) {
        if (message.getAccessCount() == null) {
            message.setAccessCount(1);
        } else {
            message.setAccessCount(message.getAccessCount() + 1);
        }
        dataObjectDao.save(message);
    }

    private String getFileExtension(String name) {

        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        return name.substring(lastIndexOf);
    }
}
