package com.wfiis.pz.project.monitor.service;

import java.util.List;

import com.wfiis.pz.project.monitor.entity.Measurement;


public interface MeasurementService {

	List<Measurement> findAll();

	List<Measurement> findMeasurementByMetricId(String id);

	void insertMeasurment(Measurement m);
	
}



