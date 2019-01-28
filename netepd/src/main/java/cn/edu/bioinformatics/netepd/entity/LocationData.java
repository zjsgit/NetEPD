package cn.edu.bioinformatics.netepd.entity;

public class LocationData {

	private Integer id;
	private String proteinName;
	private String proteinLocation;
	private String organismId;
	
	public LocationData() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the proteinName
	 */
	public String getProteinName() {
		return proteinName;
	}

	/**
	 * @param proteinName the proteinName to set
	 */
	public void setProteinName(String proteinName) {
		this.proteinName = proteinName;
	}

	/**
	 * @return the proteinLocation
	 */
	public String getProteinLocation() {
		return proteinLocation;
	}

	/**
	 * @param proteinLocation the proteinLocation to set
	 */
	public void setProteinLocation(String proteinLocation) {
		this.proteinLocation = proteinLocation;
	}

	/**
	 * @return the organismId
	 */
	public String getOrganismId() {
		return organismId;
	}

	/**
	 * @param organismId the organismId to set
	 */
	public void setOrganismId(String organismId) {
		this.organismId = organismId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LocationData [id=" + id + ", proteinName=" + proteinName + ", proteinLocation=" + proteinLocation
				+ ", organismId=" + organismId + "]";
	}
	
	
	
	
}
