package com.wfiis.pz.project.monitor.utils;

import com.wfiis.pz.project.monitor.entity.Metric;

public class MetricPreview {
	String metricId;
	String monitorId;
	
	public String getMetricId() {
		return metricId;
	}
	public void setMetricId(String metricId) {
		this.metricId = metricId;
	}
	public String getMonitorId() {
		return monitorId;
	}
	public void setMonitorId(String monitorId) {
		this.monitorId = monitorId;
	}
	
	public MetricPreview(Metric m) {
		this.metricId = m.getMetricId();
		this.monitorId = m.getMonitorId();
	}
	
}
