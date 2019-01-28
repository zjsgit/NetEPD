package cn.edu.bioinformatics.netepd.entity;

/**
 * DIP数据集提取得到的网络数据
 * 
 * @author lwk
 *
 */
public class DipNetwork {
	private Integer id;
	private String dipAid;
	private String dipBid;
	private String organismAid;
	private String organismBid;
	
	public DipNetwork() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDipAid() {
		return dipAid;
	}

	public void setDipAid(String dipAid) {
		this.dipAid = dipAid;
	}

	public String getDipBid() {
		return dipBid;
	}

	public void setDipBid(String dipBid) {
		this.dipBid = dipBid;
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
		return "DipNetwork [id=" + id + ", dipAid=" + dipAid + ", dipBid=" + dipBid + ", organismAid=" + organismAid
				+ ", organismBid=" + organismBid + "]";
	}
	
}
