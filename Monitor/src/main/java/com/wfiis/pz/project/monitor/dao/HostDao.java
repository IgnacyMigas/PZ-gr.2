package com.wfiis.pz.project.monitor.dao;

import java.util.List;

import com.wfiis.pz.project.monitor.entity.Host;

public interface HostDao {

	List<Host> findAll();
	List<Host> findAllDemo();

	void insertHost(Host host);
	Host findHostById(String id);

	
}