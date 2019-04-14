package com.wfiis.pz.project.monitor.dao;

import java.util.List;

import com.wfiis.pz.project.monitor.entity.Measurement;

public interface MeasurementDao {

	List<Measurement> findAll();

	void insertMeasurement(Measurement m);

	List<Measurement> findAllByMetricId(String id);

	
}