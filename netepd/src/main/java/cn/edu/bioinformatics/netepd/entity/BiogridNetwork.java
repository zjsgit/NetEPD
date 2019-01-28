package cn.edu.bioinformatics.netepd.entity;

public class BiogridNetwork {
	
	private Integer id;
	private String biogridAid;
	private String biogridBid;
	private String officialA;
	private String officialB;
	private String organismAid;
	private String organismBid;
	
	public BiogridNetwork() {
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBiogridAid() {
		return biogridAid;
	}
	public void setBiogridAid(String biogridAid) {
		this.biogridAid = biogridAid;
	}
	public String getBiogridBid() {
		return biogridBid;
	}
	public void setBiogridBid(String biogridBid) {
		this.biogridBid = biogridBid;
	}
	public String getOfficialA() {
		return officialA;
	}
	public void setOfficialA(String officialA) {
		this.officialA = officialA;
	}
	public String getOfficialB() {
		return officialB;
	}
	public void setOfficialB(String officialB) {
		this.officialB = officialB;
	}
	public String getOrganismAid() {
		return organismAid;
	}
	public void setOrganismAid(String organismAid) {
		this.organismAid = organismAid;
	}
	public String getOrganismBid() {
		return organismBid;
	}
	public void setOrganismBid(String organismBid) {
		this.organismBid = organismBid;
	}

	@Override
	public String toString() {
		return "BiogridNetwork [id=" + id + ", biogridAid=" + biogridAid + ", biogridBid=" + biogridBid + ", officialA="
				+ officialA + ", officialB=" + officialB + ", organismAid=" + organismAid + ", organismBid="
				+ organismBid + "]";
	}
	
}
