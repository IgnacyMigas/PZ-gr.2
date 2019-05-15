package com.wfiis.pz.project.monitor.dao;

import java.util.List;

import com.wfiis.pz.project.monitor.entity.Measurement;

public interface MeasurementDao {

	List<Measurement> findAll();

	void insertMeasurement(Measurement m);

	List<Measurement> findAllByMetricId(String id);

	List<Measurement> findTopByMetricId(String id, Integer n);

	List<Measurement> findByDateMeasurementByMetricId(String id, String from, String to);

	
}