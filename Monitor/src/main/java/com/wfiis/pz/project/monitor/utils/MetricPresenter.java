package com.wfiis.pz.project.monitor.utils;

import java.util.List;

import com.wfiis.pz.project.monitor.entity.Metric;

public class MetricPresenter {
	List<MetricPreview> metrics;
	MetaDataForMetric meta;
	
	
	public List<MetricPreview> getMetrics() {
		return metrics;
	}
	public void setMetrics(List<MetricPreview> metrics) {
		this.metrics = metrics;
	}
	public MetaDataForMetric getMeta() {
		return meta;
	}
	public void setMeta(MetaDataForMetric meta) {
		this.meta = meta;
	}
	
	
}
