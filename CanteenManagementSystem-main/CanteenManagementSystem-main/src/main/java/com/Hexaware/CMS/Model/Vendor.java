package com.Hexaware.CMS.Model;

public class Vendor {
	private String vendorId;
	private String vendorName;
	private String vendorPhone;
	private String vendorSpecs;
	
	public Vendor() {
		//default constructor
	}

	public Vendor(String vendorId, String vendorName, String vendorPhone, String vendorSpecs) {
		super();
		this.vendorId = vendorId;
		this.vendorName = vendorName;
		this.vendorPhone = vendorPhone;
		this.vendorSpecs = vendorSpecs;
	}
	public Vendor(String vendorName, String vendorPhone, String vendorSpecs) {
		super();
		this.vendorId = "";
		this.vendorName = vendorName;
		this.vendorPhone = vendorPhone;
		this.vendorSpecs = vendorSpecs;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getVendorPhone() {
		return vendorPhone;
	}

	public void setVendorPhone(String vendorPhone) {
		this.vendorPhone = vendorPhone;
	}

	public String getVendorSpecs() {
		return vendorSpecs;
	}

	public void setVendorSpecs(String vendorSpecs) {
		this.vendorSpecs = vendorSpecs;
	}

	@Override
	public String toString() {
		return ("Vendor [vendorId= "+vendorId+" vendorName= "+vendorName+" vendorPhone= "+vendorPhone+" vendorSpecs= "+vendorSpecs);
	}
	
	
}
