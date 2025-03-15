package com.stdio.mobiles;

public class msg {
	
	
	private String teacherfactor;
	private String schools;
	private String courseSquareUrl;
	private String imageurl;
	private String key;
	private String courseId;
	private String name;
	private String cookie;
	private String personId;
	private String classId;
	
	public msg(String teacherfactor, String schools, String imageurl, String name, String courseSquareUrl, String courseId, String personId, String classId, String key, String cookie) {
		this.teacherfactor = teacherfactor;
		this.schools = schools;
		this.courseSquareUrl = courseSquareUrl;
		this.imageurl = imageurl;
		this.name = name;
		this.courseId = courseId;
		this.personId = personId;
		this.classId = classId;
		this.key = key;
		this.cookie = cookie;
	}
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getCourseId() {
		return courseId;
	}
	
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	
	public String getCookie() {
		return cookie;
	}
	
	public void setCookie(String cookie) {
		this.cookie = cookie;
	}
	
	public String getPersonId() {
		return personId;
	}
	
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	
	public String getClassId() {
		return classId;
	}
	
	public void setClassId(String classId) {
		this.classId = classId;
	}
	
	public String getTeacherfactor() {
		return teacherfactor;
	}
	
	public void setTeacherfactor(String teacherfactor) {
		this.teacherfactor = teacherfactor;
	}
	
	public String getSchools() {
		return schools;
	}
	
	public void setSchools(String schools) {
		this.schools = schools;
	}
	
	public String getCourseSquareUrl() {
		return courseSquareUrl;
	}
	
	public void setCourseSquareUrl(String courseSquareUrl) {
		this.courseSquareUrl = courseSquareUrl;
	}
	
	public String getImageurl() {
		return imageurl;
	}
	
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
