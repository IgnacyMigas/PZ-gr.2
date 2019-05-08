package com.wfiis.pz.project.monitor.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.wfiis.pz.project.monitor.dao.MeasurementDao;
import com.wfiis.pz.project.monitor.entity.Measurement;

@Component
public class MeasurementServiceImpl implements MeasurementService{
	@Resource 
	MeasurementDao measurementDao;
	@Override
	public List<Measurement> findAll() {
		return measurementDao.findAll();
	}
	@Override
	public List<Measurement> findMeasurementByMetricId(String id) {
		return measurementDao.findAllByMetricId(id);
	}
	@Override
	public void insertMeasurment(Measurement m) {
		
		measurementDao.insertMeasurement(m);
		
	}
	@Override
	public List<Measurement> findTopMeasurementByMetricId(String id, Integer n) {
		return measurementDao.findTopByMetricId(id,n);
	}
	@Override
	public List<Measurement> findByDateMeasurementByMetricId(String id, String from, String to) {
		return measurementDao.findByDateMeasurementByMetricId(id, from, to);
	}
	
}