package cn.edu.bioinformatics.netepd.entity;

public class MipsNetwork {
	
	private Integer id;
	private String mipsAid;
	private String mipsBid;
	private String fullnameA;
	private String fullnameB;
	private String xrefA;
	private String xrefB;
	private String organismAid;
	private String organismBid;
	
	public MipsNetwork() {
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMipsAid() {
		return mipsAid;
	}
	public void setMipsAid(String mipsAid) {
		this.mipsAid = mipsAid;
	}
	public String getMipsBid() {
		return mipsBid;
	}
	public void setMipsBid(String mipsBid) {
		this.mipsBid = mipsBid;
	}
	public String getFullnameA() {
		return fullnameA;
	}
	public void setFullnameA(String fullnameA) {
		this.fullnameA = fullnameA;
	}
	public String getFullnameB() {
		return fullnameB;
	}
	public void setFullnameB(String fullnameB) {
		this.fullnameB = fullnameB;
	}
	public String getXrefA() {
		return xrefA;
	}
	public void setXrefA(String xrefA) {
		this.xrefA = xrefA;
	}
	public String getXrefB() {
		return xrefB;
	}
	public void setXrefB(String xrefB) {
		this.xrefB = xrefB;
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
		return "MipsNetwork [id=" + id + ", mipsAid=" + mipsAid + ", mipsBid=" + mipsBid + ", fullnameA=" + fullnameA
				+ ", fullnameB=" + fullnameB + ", xrefA=" + xrefA + ", xrefB=" + xrefB + ", organismAid=" + organismAid
				+ ", organismBid=" + organismBid + "]";
	}
	
}
