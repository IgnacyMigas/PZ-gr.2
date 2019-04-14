package com.wfiis.pz.project.monitor.service;

import java.util.List;

import com.wfiis.pz.project.monitor.entity.Host;

public interface HostService {

	List<Host> findAll();
	List<Host> findAllDemo();
	void insertHost(Host host);
	Host findHostById(String id);
	
}



