package cn.edu.bioinformatics.netepd.entity;

public class BiogridMap {
	private String biogridId;
	private String uniprotkb;
	
	public BiogridMap() {
	}
	
	public String getBiogridId() {
		return biogridId;
	}
	public void setBiogridId(String biogridId) {
		this.biogridId = biogridId;
	}
	public String getUniprotkb() {
		return uniprotkb;
	}
	public void setUniprotkb(String uniprotkb) {
		this.uniprotkb = uniprotkb;
	}
	
	@Override
	public String toString() {
		return "BiogridMap [biogridId=" + biogridId + ", uniprotkb=" + uniprotkb + "]";
	}
	
}
