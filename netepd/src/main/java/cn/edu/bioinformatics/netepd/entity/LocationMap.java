package cn.edu.bioinformatics.netepd.entity;

/**
 * 亚细胞位置数据
 * @author JiashuaiZhang
 *
 */

public class LocationMap {

	private String locationId;
	private String uniprotkb;
	
	public LocationMap() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the locationId
	 */
	public String getLocationId() {
		return locationId;
	}

	/**
	 * @param locationId the locationId to set
	 */
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	/**
	 * @return the uniprotkb
	 */
	public String getUniprotkb() {
		return uniprotkb;
	}

	/**
	 * @param uniprotkb the uniprotkb to set
	 */
	public void setUniprotkb(String uniprotkb) {
		this.uniprotkb = uniprotkb;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LocationMap [locationId=" + locationId + ", uniprotkb=" + uniprotkb + "]";
	}
	
	
	
}
