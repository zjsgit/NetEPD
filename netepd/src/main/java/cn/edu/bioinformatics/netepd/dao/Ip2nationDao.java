package cn.edu.bioinformatics.netepd.dao;

public interface Ip2nationDao {
	
	/**
	 * 根据IP地址获取位置信息
	 * @param ip
	 * @return
	 */
	public String[] getCountryByIp(String ip);
}
