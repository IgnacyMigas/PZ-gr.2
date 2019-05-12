package com.wfiis.pz.project.monitor.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
import com.wfiis.pz.project.monitor.utils.HostAbstractView;
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
	public ResponseEntity<Object> getHostsWrapper(@RequestHeader(value="access-token", required = false, defaultValue = "") String header){
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("access-token", header);
		
		return new ResponseEntity<Object>(getHosts(), responseHeaders, HttpStatus.OK);
	}
	
	
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
	public ResponseEntity<Object> getHostsWithNameWrapper(	@RequestHeader(value="access-token", required = false, defaultValue = "") String header,
															@RequestParam("name") String name
															) {
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("access-token", header);
		
		return new ResponseEntity<Object>(getHostsWithName(name), responseHeaders, HttpStatus.OK);
		
	}
	
	
	public List<HostView> getHostsWithName(String name) {
		List<Host> hosts;
		hosts = hostService.findAllByName(name);
		List<HostView> views = new ArrayList<HostView>();
		for (Host h : hosts) {
			views.add(new HostView(h));
		}
		return views;
	}
	
	@GetMapping(value = "/hosts", params = {"name_like"})
	public ResponseEntity<Object> getHostsWithNameLikeWrapper(	@RequestHeader(value="access-token", required = false, defaultValue = "") String header,
																@RequestParam("name_like") String name_like
																) {

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("access-token", header);
		
		return new ResponseEntity<Object>(getHostsWithNameLike(name_like), responseHeaders, HttpStatus.OK);

	}
	
	
	
	public List<HostView> getHostsWithNameLike(String name_like) {
		List<Host> hosts;
		hosts = hostService.findAllByNameLike(name_like);
		
		List<HostView> views = new ArrayList<HostView>();
		for (Host h : hosts) {
			views.add(new HostView(h));
		}
		return views;
	}
	
	@GetMapping(value = "/hosts", params = {"top", "metric_type"})
	public ResponseEntity<Object> getHostsWrapper(	@RequestHeader(value="access-token", required = false, defaultValue = "") String header,	
													@RequestParam("top") Integer top, 
													@RequestParam("metric_type") String metric_type
													) {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("access-token", header);
		
		return new ResponseEntity<Object>(getHostsWithMetricType(top, metric_type), responseHeaders, HttpStatus.OK);
		
	}
		
		
	public List<HostView> getHostsWithMetricType(	Integer top, 
									String metric_type
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
	public ResponseEntity<Object> postHostWrapper(	@RequestHeader(value="access-token", required = false, defaultValue = "") String header,
													@RequestBody HostDetails hd) {
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("access-token", header);
		try{
			postHost(hd);
			return new ResponseEntity<Object>(null, responseHeaders, HttpStatus.CREATED);
		}catch(Exception e){
			deleteHost(hd.getHostId());
			return new ResponseEntity<Object>(null, responseHeaders, HttpStatus.CONFLICT);
		}
		
		
		
	}
		
		
	public void postHost(HostDetails hd) {
		Host h = hd.extractHost();
		h.setMonitorId(env.getProperty("MONITORID"));
		List<Metric> metrics = hd.extractMetricList();
		hostService.insertHost(h);
		for(Metric m : metrics) {
			m.setMonitorId(env.getProperty("MONITORID"));
			metricService.insertMetric(m);
		}
	}
	
	@GetMapping(value = "/hosts", params = {"recursive"})
	public ResponseEntity<Object> getHostsReqursiveWrapper(	@RequestHeader(value="access-token", required = false, defaultValue = "") String header,
															@RequestParam("recursive") Boolean recursive) {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("access-token", header);
		
		return new ResponseEntity<Object>(getHostsReqursive(recursive), responseHeaders, HttpStatus.OK);
	}
		
	public List<HostAbstractView> getHostsReqursive(Boolean recursive) {
		List<Host> hosts;
		hosts = hostService.findAll();
		List<HostAbstractView> views = new ArrayList<HostAbstractView>();
		
		if (recursive.booleanValue()==false){
			for (Host h : hosts) {
				views.add(new HostView(h));
			}
			return views;
		}else{
			for (Host h : hosts) {
				views.add(getHost(h.getHostId()));
			}
			return views;
		}
	}
	
	@GetMapping(value = "/hosts/{id}")
	public ResponseEntity<Object> getHostWrapper(	@RequestHeader(value="access-token", required = false, defaultValue = "") String header,
											@PathVariable String id) {
	
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("access-token", header);
		
		return new ResponseEntity<Object>(getHost(id), responseHeaders, HttpStatus.OK);
	}
		
		
	public HostDetailsView getHost(String id) {
		HostDetailsView hdv = new HostDetailsView();
		
		Host h = hostService.findHostById(id);
		
		List<Metric> metrics = metricService.findAllByHostId(id);
		
		hdv.setHostId(h.getHostId());
		hdv.setOs(h.getOs());
		hdv.setMetrics(metrics);
		
		return hdv;
	}
	

	@DeleteMapping(value = "/hosts/{id}")
	public ResponseEntity<Object> deleteHostWrapper(	@RequestHeader(value="access-token", required = false, defaultValue = "") String header,
														@PathVariable String id) {
		
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("access-token", header);
		
		deleteHost(id);
		
		return new ResponseEntity<Object>(null, responseHeaders, HttpStatus.OK);
	}
		
	public void deleteHost(String id) {	
		hostService.deleteHostById(id);
	}
	
	@GetMapping(value = "/metrics")
	public ResponseEntity<Object> getMetricsWrapper(	@RequestHeader(value="access-token", required = false, defaultValue = "") String header,
														@RequestParam(value = "name_like" , required = false, defaultValue = "") String name_like, 
														@RequestParam(value = "type" , required = false, defaultValue = "") String type, 
														@RequestParam(value = "meta" , required = false, defaultValue = "false") String meta, 
														@RequestParam(value = "recursive", required = false, defaultValue = "false") Boolean recursive
													) {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("access-token", header);
		
		return new ResponseEntity<Object>(getMetrics( name_like, type, meta, recursive),responseHeaders, HttpStatus.OK);
		
	}
		
	public MetricAbstractPresenter getMetrics(	String name_like, 
												String type, 
												String meta, 
												Boolean recursive
											) {
		
		
		List<Metric> metrics = new ArrayList<Metric>();
		
		if(!name_like.isEmpty() && !type.isEmpty()){
			List<Metric> metricsByNameLike= metricService.findAllByNameLike(name_like);
			List<Metric> metricsByType= metricService.findAllByType(type);
			
			HashSet<Metric> joinedLists = new HashSet<Metric>(metricsByNameLike);
			joinedLists.addAll(metricsByType);
			
			metrics = new ArrayList<>(joinedLists);
		}else if (!name_like.isEmpty()){
			metrics = metricService.findAllByNameLike(name_like);
		}else if (!type.isEmpty()){
			metrics = metricService.findAllByType(type);
		}else{
			metrics = metricService.findAll();
		}
		
		
		if(recursive.booleanValue() == false){
			MetricPresenter mp = new MetricPresenter();
			List<MetricPreview> previews = new ArrayList<MetricPreview>();
			List<String> types = new ArrayList<String>();
			for (Metric m : metrics) {
				previews.add(new MetricPreview(m));
				types.add(m.getType());
			}
			
			if (meta != null && meta.equals("true") ){
				MetaDataForMetric metas = new MetaDataForMetric();
				metas.setTypes(types);
				mp.setMeta(metas);
			}
			mp.setMetrics(previews);
			
			return mp;
			
			
		}else{
			MetricPresenterReqursive mp = new MetricPresenterReqursive();
			List<String> types = new ArrayList<String>();
			for (Metric m : metrics) {
				types.add(m.getType());
			}
			
			if (meta != null && meta.equals("true") ){
				MetaDataForMetric metas = new MetaDataForMetric();
				metas.setTypes(types);
				mp.setMeta(metas);
			}
			mp.setMetrics(metrics);
			
			return mp;
		}
		
	}
	
	
	public MetricPresenter getMetrics() {
		try{
			MetricPresenter mp = (MetricPresenter) getMetrics("", "", "", false);
			return mp;
		}catch(Exception e){
			return new MetricPresenter();
		}
	}
	
	
	public MetricPresenter getMetrics(String name_like) {
		try{
			MetricPresenter mp = (MetricPresenter) getMetrics(name_like, "", "", false);
			return mp;
		}catch(Exception e){
			return new MetricPresenter();
		}
	}
	
	
	public MetricPresenter getMetricsWithMetas(String meta) {
		try{
			MetricPresenter mp = (MetricPresenter) getMetrics("", "", "true", false);
			return mp;
		}catch(Exception e){
			return new MetricPresenter();
		}
	}
	
	
	public MetricAbstractPresenter getMetrics(Boolean recursive) {
		return getMetrics("", "", "", recursive);
	}
	
	
	@PostMapping(value = "/metrics")
	public ResponseEntity<Object> postMetricWrapper(	@RequestHeader(value="access-token", required = false, defaultValue = "") String header,
														@RequestBody CompoundMetric cm) {
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("access-token", header);
		
		return new ResponseEntity<Object>(postMetric(cm),responseHeaders, HttpStatus.CREATED);
	}	
	
	
	
	
	public MetricPreview postMetric(CompoundMetric cm) {	
		
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
	public ResponseEntity<Object> getMetricDetailsWrapper(	@RequestHeader(value="access-token", required = false, defaultValue = "") String header,
															@PathVariable String id) {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("access-token", header);
		
		return new ResponseEntity<Object>(getMetricDetails(id),responseHeaders, HttpStatus.OK);
	}
		
		
	public Metric getMetricDetails(String id) {	
		Metric m = metricService.findMetricById(id);
		return m;
	}
	
	

	@DeleteMapping(value = "/metrics/{id}")
	public ResponseEntity<Object> deleteMetricWrapper(	@RequestHeader(value="access-token", required = false, defaultValue = "") String header,
														@PathVariable String id) {
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("access-token", header);
		
		deleteMetric(id);
		
		return new ResponseEntity<Object>(null,responseHeaders, HttpStatus.OK);
		
	}
		
	public void deleteMetric(String id) {	
		metricService.deleteMetricById(id);
		return;
	}
	
	
	@GetMapping(value = "/metrics/{id}/measurements")
	public ResponseEntity<Object> getMeasurementsForMetricWrapper(	@RequestHeader(value="access-token", required = false, defaultValue = "") String header,
																	@PathVariable String id) {
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("access-token", header);
		
		return new ResponseEntity<Object>(getMeasurementsForMetric(id),responseHeaders, HttpStatus.OK);
	}
		
	public List<MeasurementView> getMeasurementsForMetric(String id) {	
		List<Measurement> measurements;
		
		measurements= measurementService.findTopMeasurementByMetricId(id, 25);
			
		List<MeasurementView> views = new ArrayList<MeasurementView>();
		for (Measurement m : measurements){
			views.add(new MeasurementView(m));
		}
		
		return views;
	}
	
	
	
	
	@GetMapping(value = "/metrics/{id}/measurements", params = {"n"})
	public ResponseEntity<Object> getNMeasurementsForMetric(	@RequestHeader(value="access-token", required = false, defaultValue = "") String header,
																@PathVariable String id,
																@RequestParam(value = "n", required = false) Integer n
																) {
		
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("access-token", header);
		
		return new ResponseEntity<Object>(getNMeasurementsForMetric(id,n),responseHeaders, HttpStatus.OK);
		
	}
		
		
	public List<MeasurementView> getNMeasurementsForMetric(	String id,
															Integer n
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
	public ResponseEntity<Object> getMeasurementsFromRangeForMetricWrapper(	@RequestHeader(value="access-token", required = false, defaultValue = "") String header,
																			@PathVariable String id,
																			@RequestParam(value = "from", required = false) String from, 
																			@RequestParam(value = "to", required = false) String to
																		) {
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("access-token", header);
		
		return new ResponseEntity<Object>(getMeasurementsFromRangeForMetric(id,from,to),responseHeaders, HttpStatus.OK);
		
	}
		
	public List<MeasurementView> getMeasurementsFromRangeForMetric(		String id,
																		String from, 
																		String to
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
	public ResponseEntity<Object> getMeasurementsForMetric(	@RequestHeader(value="access-token", required = false, defaultValue = "") String header,
															@PathVariable String id,
															@RequestParam(value = "all", required = false) Boolean all
														) {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("access-token", header);
		
		return new ResponseEntity<Object>(getMeasurementsForMetric(id,all),responseHeaders, HttpStatus.OK);
	}
	
		
	public List<MeasurementView> getMeasurementsForMetric(	
															String id,
															Boolean all
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
	public ResponseEntity<Object> postMeasurementsForMetricWrapper(	@RequestHeader(value="access-token", required = false, defaultValue = "") String header,
													@PathVariable String id, 
													@RequestBody List<MeasurementView> views) {
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("access-token", header);
		postMeasurementsForMetric(id,views);
		return new ResponseEntity<Object>(null,responseHeaders, HttpStatus.CREATED);
	}
		
	public void postMeasurementsForMetric(String id, List<MeasurementView> views) {	
		
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