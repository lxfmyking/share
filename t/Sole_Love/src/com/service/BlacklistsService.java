package com.service;

import javax.annotation.Resource;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import org.springframework.stereotype.Service;

import com.dao.BlacklistsDao;
import com.model.Blacklists;
@Service
public class BlacklistsService {

	@Resource
	BlacklistsDao blacklistsDao;

	public Blacklists logErrCount(String ip) {
		return blacklistsDao.logErrCount(ip);
	}

	public void clearErrCount(String ip) {
		blacklistsDao.clearErrCount(ip);
	}
	public Blacklists findBlacklistsInfo(String ip) {
		return blacklistsDao.findBlacklistsInfo(ip);
	}
}
