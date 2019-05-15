package com.wfiis.pz.project.monitor.utils;

import com.wfiis.pz.project.monitor.entity.Host;

public class HostView {
	String hostId;
	String monitorId;
	
	
	public String getHostId() {
		return hostId;
	}
	public void setHostId(String hostId) {
		this.hostId = hostId;
	}
	public String getMonitorId() {
		return monitorId;
	}
	public void setMonitorId(String monitorId) {
		this.monitorId = monitorId;
	}
	
	public HostView() {}
	public HostView(Host h) {
		this.hostId = h.getHostId();
		this.monitorId = h.getMonitorId();
	}
}
