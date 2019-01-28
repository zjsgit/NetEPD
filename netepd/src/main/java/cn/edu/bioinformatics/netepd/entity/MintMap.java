package cn.edu.bioinformatics.netepd.entity;

public class MintMap {
	private String geneId;
	private String uniprotkb;
	
	public MintMap() {
	}

	public String getGeneId() {
		return geneId;
	}

	public void setGeneId(String geneId) {
		this.geneId = geneId;
	}

	public String getUniprotkb() {
		return uniprotkb;
	}

	public void setUniprotkb(String uniprotkb) {
		this.uniprotkb = uniprotkb;
	}

	@Override
	public String toString() {
		return "MintMap [geneId=" + geneId + ", uniprotkb=" + uniprotkb + "]";
	}
	
}
