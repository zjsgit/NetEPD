package cn.edu.bioinformatics.netepd.entity;

public class DipMap {
	private String dipId;
	private String uniprotkb;
	
	public DipMap() {
	}
	
	public String getDipId() {
		return dipId;
	}
	public void setDipId(String dipId) {
		this.dipId = dipId;
	}
	public String getUniprotkb() {
		return uniprotkb;
	}
	public void setUniprotkb(String uniprotkb) {
		this.uniprotkb = uniprotkb;
	}
	@Override
	public String toString() {
		return "DipMap [dipId=" + dipId + ", uniprotkb=" + uniprotkb + "]";
	}
	
}
