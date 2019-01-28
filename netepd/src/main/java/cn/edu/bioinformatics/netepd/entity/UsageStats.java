package cn.edu.bioinformatics.netepd.entity;

public class UsageStats {
	private Integer id;
	private String userIp;
	private String userEmail;
	private String countryCode;
	private String countryName;
	private String createdTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserIp() {
		return userIp;
	}
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	public UsageStats() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UsageStats(String userIp, String userEmail, String countryCode, String countryName, String createdTime) {
		super();
		this.userIp = userIp;
		this.userEmail = userEmail;
		this.countryCode = countryCode;
		this.countryName = countryName;
		this.createdTime = createdTime;
	}
	

}
