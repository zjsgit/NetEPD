package cn.edu.bioinformatics.netepd.entity;

/**
 * 基因表达数据映射
 * @author JiashuaiZhang
 *
 */


public class ExpressionMap {

	private String expressionId;
	private String uniprotkb;
	
	public ExpressionMap() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the expressionId
	 */
	public String getExpressionId() {
		return expressionId;
	}
	/**
	 * @param expressionId the expressionId to set
	 */
	public void setExpressionId(String expressionId) {
		this.expressionId = expressionId;
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
		return "ExpressionMap [expressionId=" + expressionId + ", uniprotkb=" + uniprotkb + "]";
	}
	
	
	
}
