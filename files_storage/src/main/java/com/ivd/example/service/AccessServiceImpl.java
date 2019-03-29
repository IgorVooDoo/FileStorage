package com.ivd.example.service;

import com.ivd.example.dao.AccessDao;
import com.ivd.example.entity.Access;
import com.ivd.example.entity.TypeAccess;
import com.ivd.example.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@inheritDoc}
 */
@Service
@Transactional
public class AccessServiceImpl implements AccessService {

    private final AccessDao accessDao;

    public AccessServiceImpl(AccessDao accessDao) {
        this.accessDao = accessDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(User user, User member, TypeAccess typeAccess, boolean isQuery) {
        Access access = new Access(user, member);
        access.setTypeAccess(typeAccess);
        access.setQuery(isQuery);
        accessDao.save(access);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<User> findByUserAndType(User user, TypeAccess type) {
        return accessDao.findByUserAndType(user, type);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<User> findByMemberAndTypeAndQuery(User user, TypeAccess read, boolean b) {
        return accessDao.findByMemberAndTypeAndQuery(user, read, b);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void updateQueryById(User user, User member) {
        accessDao.updateQueryById(user, member);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteById(User user, User member) {
        accessDao.deleteByUserAndMember(user, member);
    }
}
