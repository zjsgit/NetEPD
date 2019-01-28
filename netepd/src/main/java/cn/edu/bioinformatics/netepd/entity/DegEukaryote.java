package cn.edu.bioinformatics.netepd.entity;

public class DegEukaryote {
	private String degAC;
	private String geneName;
	private String geneRef;
	private String function;
	private String organism;
	public DegEukaryote() {
	}
	public String getDegAC() {
		return degAC;
	}
	public void setDegAC(String degAC) {
		this.degAC = degAC;
	}
	public String getGeneName() {
		return geneName;
	}
	public void setGeneName(String geneName) {
		this.geneName = geneName;
	}
	public String getGeneRef() {
		return geneRef;
	}
	public void setGeneRef(String geneRef) {
		this.geneRef = geneRef;
	}
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	public String getOrganism() {
		return organism;
	}
	public void setOrganism(String organism) {
		this.organism = organism;
	}
	@Override
	public String toString() {
		return "DegEukaryote [degAC=" + degAC + ", geneName=" + geneName + ", geneRef=" + geneRef + ", function="
				+ function + ", organism=" + organism + "]";
	}
	
}
