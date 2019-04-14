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

import com.wfiis.pz.project.monitor.entity.Host;
import com.wfiis.pz.project.monitor.mapper.HostRowMapper;


@Repository
public class HostDaoImpl implements HostDao{
	
	public HostDaoImpl(NamedParameterJdbcTemplate template) {  
        this.template = template;  
}  
	NamedParameterJdbcTemplate template;  

	@Override
	public List<Host> findAll() {
		return template.query("select * from hosts", new HostRowMapper());
		
	}
	
	public List<Host> findAllDemo() {
		Host h = new Host();
		h.setHostId("tempId");
		h.setMonitorId("v1");
		h.setOs("Windows");
		List<Host> hosts = new ArrayList<Host>();
		hosts.add(h);
		return hosts;
	}
	
	
	
	@Override
	public void insertHost(Host h) {
		 final String sql = "insert into hosts(hostId , os, monitorId) values(:hostId,:os,:monitorId)"; 
		 
	        KeyHolder holder = new GeneratedKeyHolder();
	        SqlParameterSource param = new MapSqlParameterSource()
					.addValue("hostId", h.getHostId())
					.addValue("os", h.getOs())
					.addValue("monitorId", h.getMonitorId());
	        template.update(sql,param, holder);
	 
	}

	@Override
	public Host findHostById(String id) {
		final String sql = "select * from hosts where hostId = :id";
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("id", id);
		List<Host> list = template.query(sql, param, new HostRowMapper());
		return list.get(0);
	}
	
	
	
}