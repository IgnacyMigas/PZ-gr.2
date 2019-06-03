package com.wfiis.pz.project.monitor.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.wfiis.pz.project.monitor.entity.Measurement;



public class MeasurementRowMapper implements RowMapper<Measurement> {

	@Override
	public Measurement mapRow(ResultSet rs, int arg1) throws SQLException {
		Measurement m = new Measurement();
		
		m.setMetricId(rs.getString("metricId"));
		m.setVal(rs.getString("val"));
		m.setTs(rs.getString("ts"));
 
        return m;
	}


}