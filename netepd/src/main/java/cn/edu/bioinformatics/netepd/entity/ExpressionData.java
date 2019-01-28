package cn.edu.bioinformatics.netepd.entity;

/**
 * 表示基因表达数据：基因的名字，物种编号，表达数据（用\t分开）
 * 
 * 
 * @author JiashuaiZhang
 *
 */
public class ExpressionData {
	
	private Integer id;
	private String geneName;
	private String organismId;
	private String expressionData;
	
	public ExpressionData() {
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
	 * @return the geneName
	 */
	public String getGeneName() {
		return geneName;
	}

	/**
	 * @param geneName the geneName to set
	 */
	public void setGeneName(String geneName) {
		this.geneName = geneName;
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

	/**
	 * @return the expressionData
	 */
	public String getExpressionData() {
		return expressionData;
	}

	/**
	 * @param expressionData the expressionData to set
	 */
	public void setExpressionData(String expressionData) {
		this.expressionData = expressionData;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ExpressionData [id=" + id + ", geneName=" + geneName + ", organismId=" + organismId
				+ ", expressionData=" + expressionData + "]";
	}
	
	
	

}
