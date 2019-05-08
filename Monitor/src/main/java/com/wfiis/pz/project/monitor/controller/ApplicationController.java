package com.wfiis.pz.project.monitor.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.wfiis.pz.project.monitor.entity.Host;
import com.wfiis.pz.project.monitor.entity.Measurement;
import com.wfiis.pz.project.monitor.entity.Metric;
import com.wfiis.pz.project.monitor.service.HostService;
import com.wfiis.pz.project.monitor.service.MeasurementService;
import com.wfiis.pz.project.monitor.service.MetricService;
import com.wfiis.pz.project.monitor.utils.CompoundMetric;
import com.wfiis.pz.project.monitor.utils.HostDetails;
import com.wfiis.pz.project.monitor.utils.HostDetailsView;
import com.wfiis.pz.project.monitor.utils.HostView;
import com.wfiis.pz.project.monitor.utils.MeasurementView;
import com.wfiis.pz.project.monitor.utils.MetaDataForMetric;
import com.wfiis.pz.project.monitor.utils.MetricAbstractPresenter;
import com.wfiis.pz.project.monitor.utils.MetricPresenter;
import com.wfiis.pz.project.monitor.utils.MetricPresenterReqursive;
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
	@ResponseStatus(HttpStatus.OK)
	public List<HostView> getHosts() {
		List<Host> hosts;
		hosts = hostService.findAll();
		List<HostView> views = new ArrayList<HostView>();
		for (Host h : hosts) {
			views.add(new HostView(h));
		}
		return views;
	}
	
	
	@GetMapping(value = "/hosts", params = {"name"})
	@ResponseStatus(HttpStatus.OK)
	public List<HostView> getHostsWithName(	@RequestParam("name") String name) {
		List<Host> hosts;
		hosts = hostService.findAllByName(name);
		List<HostView> views = new ArrayList<HostView>();
		for (Host h : hosts) {
			views.add(new HostView(h));
		}
		return views;
	}
	
	@GetMapping(value = "/hosts", params = {"name_like"})
	@ResponseStatus(HttpStatus.OK)
	public List<HostView> getHostsWithNameLike(@RequestParam("name_like") String name_like) {
		List<Host> hosts;
		hosts = hostService.findAllByNameLike(name_like);
		
		List<HostView> views = new ArrayList<HostView>();
		for (Host h : hosts) {
			views.add(new HostView(h));
		}
		return views;
	}
	
	@GetMapping(value = "/hosts", params = {"top", "metric_type"})
	@ResponseStatus(HttpStatus.OK)
	public List<HostView> getHosts(	@RequestParam("top") Integer top, 
									@RequestParam("metric_type") String metric_type
									) {
		List<Host> hosts;
		hosts = hostService.findTopByMetricType(top, metric_type);
		
		List<HostView> views = new ArrayList<HostView>();
		for (Host h : hosts) {
			views.add(new HostView(h));
		}
		return views;
	}
	
	
	@PostMapping(value = "/hosts")
	@ResponseStatus(HttpStatus.CREATED)
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
	@ResponseStatus(HttpStatus.OK)
	public HostDetailsView getHost(@PathVariable String id) {
		HostDetailsView hdv = new HostDetailsView();
		
		Host h = hostService.findHostById(id);
		
		List<Metric> metrics = metricService.findAllByHostId(id);
		
		hdv.setHostId(h.getHostId());
		hdv.setOs(h.getOs());
		hdv.setMetrics(metrics);
		
		return hdv;
	}
	
	@GetMapping(value = "/metrics")
	@ResponseStatus(HttpStatus.OK)
	public MetricPresenter getMetrics() {
		MetricPresenter mp = new MetricPresenter();
		List<Metric> tempmetrics;
		tempmetrics = metricService.findAll();
		
		List<MetricPreview> metrics = new ArrayList<MetricPreview>();
		List<String> types = new ArrayList<String>();
		
		for (Metric m : tempmetrics) {
			metrics.add(new MetricPreview(m));
			types.add(m.getType());
		}
		
		mp.setMetrics(metrics);
		return mp;
	}
	
	@GetMapping(value = "/metrics", params = {"name_like"})
	@ResponseStatus(HttpStatus.OK)
	public MetricPresenter getMetrics(	@RequestParam(value = "name_like") String name_like) {
		MetricPresenter mp = new MetricPresenter();
		
		List<Metric> tempmetrics;
		
		tempmetrics = metricService.findAllByNameLike(name_like);
		
		List<MetricPreview> metrics = new ArrayList<MetricPreview>();
		List<String> types = new ArrayList<String>();
		
		for (Metric m : tempmetrics) {
			metrics.add(new MetricPreview(m));
			types.add(m.getType());
		}
		
		mp.setMetrics(metrics);
		return mp;
	}
		
	/*
	@GetMapping(value = "/metrics", params = {"name_like", "type", "meta", "recursive"})
	@ResponseStatus(HttpStatus.OK)
	public MetricPresenter getMetrics(	@RequestParam(value = "name_like" , required = false, defaultValue = "") String name_like, 
										@RequestParam(value = "type" , required = false, defaultValue = "") String type, 
										@RequestParam(value = "meta" , required = false, defaultValue = "false") Boolean meta, 
										@RequestParam(value = "recursive", required = false, defaultValue = "false") Boolean recursive // not yet implemented
									) {
		MetricPresenter mp = new MetricPresenter();
		
		List<Metric> tempmetrics;
		
		if(name_like != null && !name_like.isEmpty()){
			tempmetrics = metricService.findAllByNameLike(name_like);
		}else if (type != null && !type.isEmpty()){
			tempmetrics = metricService.findAllByType(type);
		}
		
		tempmetrics = metricService.findAll();
		
		List<MetricPreview> metrics = new ArrayList<MetricPreview>();
		List<String> types = new ArrayList<String>();
		
		for (Metric m : tempmetrics) {
			metrics.add(new MetricPreview(m));
			types.add(m.getType());
		}
		
		mp.setMetrics(metrics);
		
		if (meta != null && meta.booleanValue() == true){
			MetaDataForMetric metas = new MetaDataForMetric();
			metas.setTypes(types);
			mp.setMeta(metas);
		}
		
		return mp;
	}
	
	*/
	
	
	@GetMapping(value = "/metrics", params = {"meta"})
	@ResponseStatus(HttpStatus.OK)
	public MetricPresenter getMetricsWithMetas(@RequestParam(value = "meta" , required = false, defaultValue = "false") String meta) {
		MetricPresenter mp = new MetricPresenter();
		
		List<Metric> tempmetrics;
		
		tempmetrics = metricService.findAll();
		
		List<MetricPreview> metrics = new ArrayList<MetricPreview>();
		List<String> types = new ArrayList<String>();
		
		for (Metric m : tempmetrics) {
			metrics.add(new MetricPreview(m));
			types.add(m.getType());
		}
		
		mp.setMetrics(metrics);
		
		if (meta != null && meta == "true"){
			MetaDataForMetric metas = new MetaDataForMetric();
			metas.setTypes(types);
			mp.setMeta(metas);
		}
		
		return mp;
	}
	
	
	@GetMapping(value = "/metrics", params = {"recursive"})
	@ResponseStatus(HttpStatus.OK)
	public MetricAbstractPresenter getMetrics(@RequestParam(value = "recursive", required = false, defaultValue = "false") Boolean recursive) {
		
		List<Metric> tempmetrics;
		tempmetrics = metricService.findAll();
		
		if(recursive != null && recursive.booleanValue() == true){
			MetricPresenterReqursive mp = new MetricPresenterReqursive();
			
			List<Metric> metrics = new ArrayList<Metric>();
			List<String> types = new ArrayList<String>();
			
			for (Metric m : tempmetrics) {
				metrics.add(m);
				types.add(m.getType());
			}
			mp.setMetrics(metrics);
			
			return mp;
		}
		else{
			MetricPresenter mp = new MetricPresenter();
			
			List<MetricPreview> metricsPreview = new ArrayList<MetricPreview>();
			List<String> types = new ArrayList<String>();
			
			for (Metric m : tempmetrics) {
				metricsPreview.add(new MetricPreview(m));
				types.add(m.getType());
			}
			mp.setMetrics(metricsPreview);
			
			return mp;
			
		}
		
	}
	
	
	
	@PostMapping(value = "/metrics")
	@ResponseStatus(HttpStatus.CREATED)
	public MetricPreview postMetric(@RequestBody CompoundMetric cm) {
		
		Metric simple = metricService.findAllByNameLike(cm.getMetricIds()).get(0);
		
		Metric m = new Metric();
		Date date= new Date();
		long time = date.getTime();
		Timestamp tstp = new Timestamp(time);
		m.setMetricId("CM " + tstp.toString());
		m.setType(cm.getType());
		m.setUnit(simple.getUnit());
		m.setHostId(simple.getHostId());
		m.setUserId(simple.getUserId());
		
		m.setMonitorId(env.getProperty("MONITORID"));

		m.setKind(cm.getKind());
		m.setSimpleMetricId(cm.getMetricIds());
		
		MetricPreview mp = new MetricPreview(m);
		
		return mp;
	}
	
	@GetMapping(value = "/metrics/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Metric getMetricDetails(@PathVariable String id) {
		Metric m = metricService.findMetricById(id);
		return m;
	}
	
	@DeleteMapping(value = "/metrics/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteMetric(@PathVariable String id) {
		metricService.deleteMetricById(id);
		return;
	}
	
	
	@GetMapping(value = "/metrics/{id}/measurements")
	@ResponseStatus(HttpStatus.OK)
	public List<MeasurementView> getMeasurementsForMetric(	@PathVariable String id) {
		List<Measurement> measurements;
		
		measurements= measurementService.findTopMeasurementByMetricId(id, 25);
			
		List<MeasurementView> views = new ArrayList<MeasurementView>();
		for (Measurement m : measurements){
			views.add(new MeasurementView(m));
		}
		
		return views;
	}
	
	
	
	
	@GetMapping(value = "/metrics/{id}/measurements", params = {"n"})
	@ResponseStatus(HttpStatus.OK)
	public List<MeasurementView> getNMeasurementsForMetric(	@PathVariable String id,
															@RequestParam(value = "n", required = false) Integer n
															) {
		List<Measurement> measurements;
		
		if (n != null){
			measurements= measurementService.findTopMeasurementByMetricId(id, n);
		}else{
			measurements= measurementService.findTopMeasurementByMetricId(id, 25);
		}
			
			
		List<MeasurementView> views = new ArrayList<MeasurementView>();
		for (Measurement m : measurements){
			views.add(new MeasurementView(m));
		}
		
		return views;
	}
	
	
	
	@GetMapping(value = "/metrics/{id}/measurements", params = { "from", "to"})
	@ResponseStatus(HttpStatus.OK)
	public List<MeasurementView> getMeasurementsFromRangeForMetric(	@PathVariable String id,
															@RequestParam(value = "from", required = false) String from, 
															@RequestParam(value = "to", required = false) String to
														) {
		List<Measurement> measurements;
		
		if ((from != null && !from.isEmpty()) || (to != null && !to.isEmpty()) ){
			measurements= measurementService.findByDateMeasurementByMetricId(id, from, to);
		}else{
			measurements= measurementService.findTopMeasurementByMetricId(id, 25);
		}
			
			
		List<MeasurementView> views = new ArrayList<MeasurementView>();
		for (Measurement m : measurements){
			views.add(new MeasurementView(m));
		}
		
		return views;
	}
	
	
	
	@GetMapping(value = "/metrics/{id}/measurements", params = {"all"})
	@ResponseStatus(HttpStatus.OK)
	public List<MeasurementView> getMeasurementsForMetric(	@PathVariable String id,
															@RequestParam(value = "all", required = false) Boolean all
														) {
		List<Measurement> measurements;
		
		if (all != null && all.booleanValue() == true){
			measurements= measurementService.findMeasurementByMetricId(id);
			
		}else{
			measurements= measurementService.findTopMeasurementByMetricId(id, 25);
		}
			
			
		List<MeasurementView> views = new ArrayList<MeasurementView>();
		for (Measurement m : measurements){
			views.add(new MeasurementView(m));
		}
		
		return views;
	}
	
	
	
	
	@PostMapping(value = "/metrics/{id}/measurements")
	@ResponseStatus(HttpStatus.CREATED)
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