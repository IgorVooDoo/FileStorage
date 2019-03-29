package com.ivd.example.service;

import com.ivd.example.dao.DataObjectDao;
import com.ivd.example.entity.DataObject;
import com.ivd.example.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * {@inheritDoc}
 */
@Service
public class DataObjectServiceImpl implements DataObjectService{
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
}
