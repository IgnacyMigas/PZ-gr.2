package com.wfiis.pz.project.monitor.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wfiis.pz.project.monitor.entity.Host;
import com.wfiis.pz.project.monitor.entity.Measurement;
import com.wfiis.pz.project.monitor.entity.Metric;
import com.wfiis.pz.project.monitor.service.HostService;
import com.wfiis.pz.project.monitor.service.MeasurementService;
import com.wfiis.pz.project.monitor.service.MetricService;
import com.wfiis.pz.project.monitor.utils.HostDetails;
import com.wfiis.pz.project.monitor.utils.HostDetailsView;
import com.wfiis.pz.project.monitor.utils.HostView;
import com.wfiis.pz.project.monitor.utils.MeasurementView;
import com.wfiis.pz.project.monitor.utils.MetaDataForMetric;
import com.wfiis.pz.project.monitor.utils.MetricPresenter;
import com.wfiis.pz.project.monitor.utils.MetricPreview;



@RestController
@RequestMapping("/${MONITORID}")
public class ApplicationController {

	@Resource 
	HostService hostService;
	
	@Resource 
	MetricService metricService;
	
	@Resource 
	MeasurementService measurementService;
	
	@Autowired
	private Environment env;
	
	
	@GetMapping(value = "/hosts")
	public List<HostView> getHosts() {
		List<Host> hosts = hostService.findAll();
		List<HostView> views = new ArrayList<HostView>();
		for (Host h : hosts) {
			views.add(new HostView(h));
		}
		return views;
	}
	
	@PostMapping(value = "/hosts")
	public void postHost(@RequestBody HostDetails hd) {
		Host h = hd.extractHost();
		h.setMonitorId(env.getProperty("MONITORID"));
		List<Metric> metrics = hd.extractMetricList();
		hostService.insertHost(h);
		for(Metric m : metrics) {
			m.setMonitorId(env.getProperty("MONITORID"));
			metricService.insertMetric(m);
		}
	}
	
	
	@GetMapping(value = "/hosts/{id}")
	public HostDetailsView getHosts(@PathVariable String id) {
		HostDetailsView hdv = new HostDetailsView();
		
		Host h = hostService.findHostById(id);
		
		List<Metric> metrics = metricService.findAllByHostId(id);
		
		hdv.setHostId(h.getHostId());
		hdv.setOs(h.getOs());
		hdv.setMetrics(metrics);
		
		return hdv;
	}
	
	@GetMapping(value = "/metrics")
	public MetricPresenter getMetrics() {
		MetricPresenter mp = new MetricPresenter();
		
		List<Metric> tempmetrics = metricService.findAll();
		List<MetricPreview> metrics = new ArrayList<MetricPreview>();
		List<String> types = new ArrayList<String>();
		
		for (Metric m : tempmetrics) {
			metrics.add(new MetricPreview(m));
			types.add(m.getType());
		}
		
		mp.setMetrics(metrics);
		MetaDataForMetric meta = new MetaDataForMetric();
		meta.setTypes(types);
		mp.setMeta(meta);
		
		return mp;
	}
	
	@PostMapping(value = "/metrics")
	public void postMetric(@RequestBody HostDetails hd) {
		// not yet implemented
	}
	
	@GetMapping(value = "/metrics/{id}")
	public Metric getMetricDetails(@PathVariable String id) {
		Metric m = metricService.findMetricById(id);
		return m;
	}
	
	@DeleteMapping(value = "/metrics/{id}")
	public void deleteMetric(@PathVariable String id) {
		metricService.deleteMetricById(id);
		return;
	}
	
	@GetMapping(value = "/metrics/{id}/measurements")
	public List<MeasurementView> getMeasurementsForMetric(@PathVariable String id) {
		List<Measurement> measurements = measurementService.findMeasurementByMetricId(id);
		List<MeasurementView> views = new ArrayList<MeasurementView>();
		for (Measurement m : measurements){
			views.add(new MeasurementView(m));
		}
		
		return views;
	}
	
	@PostMapping(value = "/metrics/{id}/measurements")
	public void postMeasurementsForMetric(@PathVariable String id, @RequestBody List<MeasurementView> views) {
		for (MeasurementView mv : views){
			Measurement m = new Measurement();
			m.setMetricId(id);
			m.setVal(mv.getVal());
			m.setTs(mv.getTs());
			
			measurementService.insertMeasurment(m);
			
		}
		
		return;
	}
	
	
}