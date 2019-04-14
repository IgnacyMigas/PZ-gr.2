package com.wfiis.pz.project.monitor.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.wfiis.pz.project.monitor.entity.Measurement;
import com.wfiis.pz.project.monitor.mapper.MeasurementRowMapper;
import com.wfiis.pz.project.monitor.utils.Util;


@Repository
public class MeasurementDaoImpl implements MeasurementDao{
	
	public MeasurementDaoImpl(NamedParameterJdbcTemplate template) {  
        this.template = template;  
	}  
	
	
	NamedParameterJdbcTemplate template;  

	@Override
	public List<Measurement> findAll() {
		return template.query("select * from measurements", new MeasurementRowMapper());
	}
	
	
	@Override
	public void insertMeasurement(Measurement m) {
		 final String sql = "insert into measurements(metricId , val, ts) values(:metricId,:val,:ts)"; 
		 
	        KeyHolder holder = new GeneratedKeyHolder();
	        SqlParameterSource param = new MapSqlParameterSource()
					.addValue("metricId", m.getMetricId())
					.addValue("val", m.getVal())
					.addValue("ts", Util.convertStringToTimestamp(m.getTs()));
	        template.update(sql,param, holder);
	 
	}


	@Override
	public List<Measurement> findAllByMetricId(String id) {
		 SqlParameterSource param = new MapSqlParameterSource()
					.addValue("id", id);
		return template.query("select * from measurements where metricId = :id", param, new MeasurementRowMapper());
		
	}
	
	
	
}