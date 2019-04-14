package com.wfiis.pz.project.monitor.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.wfiis.pz.project.monitor.dao.HostDao;
import com.wfiis.pz.project.monitor.entity.Host;

@Component
public class HostServiceImpl implements HostService{
	@Resource 
	HostDao hostDao;
	
	@Override
	public List<Host> findAll() {
		return hostDao.findAll();
	}

	@Override
	public List<Host> findAllDemo() {
		return hostDao.findAllDemo();
	}

	@Override
	public void insertHost(Host host) {
		hostDao.insertHost(host);
		
	}

	@Override
	public Host findHostById(String id) {
		return hostDao.findHostById(id);
	}
	
}