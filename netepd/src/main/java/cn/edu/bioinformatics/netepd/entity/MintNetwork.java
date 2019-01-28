package cn.edu.bioinformatics.netepd.entity;

public class MintNetwork {
	private Integer id;
	private String interactorAid;
	private String interactorBid;
	private String organismAid;
	private String organismBid;
	private String hostId;
	
	public MintNetwork() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getInteractorAid() {
		return interactorAid;
	}

	public void setInteractorAid(String interactorAid) {
		this.interactorAid = interactorAid;
	}

	public String getInteractorBid() {
		return interactorBid;
	}

	public void setInteractorBid(String interactorBid) {
		this.interactorBid = interactorBid;
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

	public String getHostId() {
		return hostId;
	}

	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	@Override
	public String toString() {
		return "MintNetwork [id=" + id + ", interactorAid=" + interactorAid + ", interactorBid=" + interactorBid
				+ ", organismAid=" + organismAid + ", organismBid=" + organismBid + ", hostId=" + hostId + "]";
	}
	
}
