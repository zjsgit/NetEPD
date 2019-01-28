package cn.edu.bioinformatics.netepd.service;

import java.util.HashMap;
import java.util.HashSet;

public interface DegService {
	/**
	 * 识别关键蛋白质
	 * @param mapACs 所有的蛋白质节点映射键值对<AC,IDs>
	 * @return HashSet<String> 关键蛋白质InteractorID
	 */
	public HashSet<String> getMappedList(HashMap<String, String> mapACs);
	
}
