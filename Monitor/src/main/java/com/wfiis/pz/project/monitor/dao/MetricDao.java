package com.wfiis.pz.project.monitor.dao;

import java.util.List;

import com.wfiis.pz.project.monitor.entity.Metric;

public interface MetricDao {

	List<Metric> findAll();

	void insertMetric(Metric m);

	List<Metric> findAllByHostId(String id);

	Metric findById(String id);

	void deleteMetric(String id);

	
}