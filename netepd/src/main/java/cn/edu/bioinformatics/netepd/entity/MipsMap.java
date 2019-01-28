package cn.edu.bioinformatics.netepd.entity;

public class MipsMap {
	
	private String mipsId;
	private String uniprotkb;
	
	public MipsMap() {
	}

	public String getMipsId() {
		return mipsId;
	}


	public void setMipsId(String mipsId) {
		this.mipsId = mipsId;
	}


	public String getUniprotkb() {
		return uniprotkb;
	}


	public void setUniprotkb(String uniprotkb) {
		this.uniprotkb = uniprotkb;
	}

	@Override
	public String toString() {
		return "MipsMap [mipsId=" + mipsId + ", uniprotkb=" + uniprotkb + "]";
	}
	
	
	
}
