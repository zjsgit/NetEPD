package cn.edu.bioinformatics.netepd.entity;

public class DegMap {
	private String degAC;
	private String uniprotkb;
	public DegMap() {
	}
	public String getDegAC() {
		return degAC;
	}
	public void setDegAC(String degAC) {
		this.degAC = degAC;
	}
	public String getUniprotkb() {
		return uniprotkb;
	}
	public void setUniprotkb(String uniprotkb) {
		this.uniprotkb = uniprotkb;
	}
	@Override
	public String toString() {
		return "DegMap [degAC=" + degAC + ", uniprotkb=" + uniprotkb + "]";
	}
	
}
