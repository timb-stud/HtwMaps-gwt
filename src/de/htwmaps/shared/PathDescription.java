package de.htwmaps.shared;

import java.io.Serializable;

public class PathDescription implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private long routeToTextRuntime;
	private String timeOnMotorWay;
	private String timeOnPrimary;
	private String timeOnResidential;
	private String timeTotal;
	private String[] wayDescriptions;
	private String distanceOnMotorWay;
	private String distanceOnPrimary;
	private String distanceOnResidential;
	private String distanceTotal;

	
	
	public String getDistanceOnMotorWay() {
		return distanceOnMotorWay;
	}

	public void setDistanceOnMotorWay(String distanceOnMotorWay) {
		this.distanceOnMotorWay = distanceOnMotorWay;
	}

	public String getDistanceOnPrimary() {
		return distanceOnPrimary;
	}

	public void setDistanceOnPrimary(String distanceOnPrimary) {
		this.distanceOnPrimary = distanceOnPrimary;
	}

	public String getDistanceOnResidential() {
		return distanceOnResidential;
	}

	public void setDistanceOnResidential(String distanceOnResidential) {
		this.distanceOnResidential = distanceOnResidential;
	}

	public String getDistanceTotal() {
		return distanceTotal;
	}

	public void setDistanceTotal(String distanceTotal) {
		this.distanceTotal = distanceTotal;
	}

	public String getTimeOnMotorWay() {
		return timeOnMotorWay;
	}

	public void setTimeOnMotorWay(String timeOnMotorWay) {
		this.timeOnMotorWay = timeOnMotorWay;
	}

	public String getTimeOnPrimary() {
		return timeOnPrimary;
	}

	public void setTimeOnPrimary(String timeOnPrimary) {
		this.timeOnPrimary = timeOnPrimary;
	}

	public String getTimeOnResidential() {
		return timeOnResidential;
	}

	public void setTimeOnResidential(String timeOnResidential) {
		this.timeOnResidential = timeOnResidential;
	}

	public String getTimeTotal() {
		return timeTotal;
	}

	public void setTimeTotal(String timeTotal) {
		this.timeTotal = timeTotal;
	}

	public String[] getWayDescriptions() {
		return wayDescriptions;
	}

	public void setWayDescriptions(String[] wayDescriptions) {
		this.wayDescriptions = wayDescriptions;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public long getRouteToTextRuntime() {
		return routeToTextRuntime;
	}

	public void setRouteToTextRuntime(long routeToTextRuntime) {
		this.routeToTextRuntime = routeToTextRuntime;
	}
	
}
