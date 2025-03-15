package com.stdio.mobiles;

public class sponsor_msg {
	
	private String sponsor_name;
	private String sponsor_lx;
	private String sponsor_je;
	
	public sponsor_msg(String sponsor_name, String sponsor_lx, String sponsor_je) {
		this.sponsor_name = sponsor_name;
		this.sponsor_lx = sponsor_lx;
		this.sponsor_je = sponsor_je;
	}
	
	public String getsponsor_Name() {
		return sponsor_name;
	}
	
	public void setsponsor_Name(String sponsor_name) {
		this.sponsor_name = sponsor_name;
	}
	
	public String getsponsor_Lx() {
		return sponsor_lx;
	}
	
	public void setsponsor_Lx(String sponsor_lx) {
		this.sponsor_lx = sponsor_lx;
	}
	
	public String getsponsor_Je() {
		return sponsor_je;
	}
	
	public void setsponsor_e(String sponsor_je) {
		sponsor_je = sponsor_je;
	}
	
}
