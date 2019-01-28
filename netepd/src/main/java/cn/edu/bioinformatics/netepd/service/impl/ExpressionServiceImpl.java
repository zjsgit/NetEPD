package cn.edu.bioinformatics.netepd.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.bioinformatics.netepd.dao.ExpressionDataDao;
import cn.edu.bioinformatics.netepd.dao.ExpressionMapDao;
import cn.edu.bioinformatics.netepd.entity.ExpressionData;
import cn.edu.bioinformatics.netepd.entity.ExpressionMap;
import cn.edu.bioinformatics.netepd.service.ExpressionService;
import cn.edu.bioinformatics.netepd.util.FileUtil;


@Service("expressionService")
public class ExpressionServiceImpl implements ExpressionService {

	@Autowired
	private ExpressionDataDao expressionDataDao;
	@Autowired
	private ExpressionMapDao expressionMapDao;
	
	/**
	 * @author JiashuaiZhang
	 */
	@Override
	public List<String> getExpressionByTaxid(String taxid, HashMap<String, String> ppiMapingData) {
		// TODO Auto-generated method stub
		
//		FileUtil.writeMap2File(ppiMapingData, "E:/expression/ppiMappingResult.txt");
		
		List<ExpressionData> expressionDataList = expressionDataDao.getExpressionByOrganism(taxid);
		HashMap <String,String> expressionDataMap = new HashMap<>();
		
		for (ExpressionData sub : expressionDataList) {
			expressionDataMap.put(sub.getGeneName(), sub.getExpressionData());
		}
		
//		FileUtil.writeMap2File(expressionDataMap, "E:/expression/expressionDataResult.txt");
		
		// 1.先获取对应蛋白质的不重复ID
		List<String> idList = expressionDataDao.getUniqueIdByOrganism(taxid);;
//		System.out.println("mappingID size:"+idList.size());
		
		// 2.获取匹配到Uniprotkb的结果集
		List<ExpressionMap> mapped = expressionMapDao.getMappedResult(idList);
//		System.out.println("mappedID size:"+mapped.size());
		
		HashMap<String, String> hashMap = new HashMap<>();
		for (ExpressionMap map : mapped) {
			String[] arr = map.getUniprotkb().split(";");
			for (String str : arr) {
				if (!hashMap.containsKey(str)) {
					hashMap.put(str, map.getExpressionId());
				} else {
					String newValue = hashMap.get(str) + ";" + map.getExpressionId();
					hashMap.put(str, newValue);//jdk1.8使用replace方法
				}
			}
		}
		
//		FileUtil.writeMap2File(hashMap, "E:/expression/mappedResult.txt");
		
		HashMap <String,String> expression2ppiMap = transformMap(ppiMapingData,hashMap);
		System.out.println("expression2ppiMap的大小为\t"+expression2ppiMap.size());
//		FileUtil.writeMap2File(expression2ppiMap, "E:/expression/expression2ppiMapResult.txt");
		
		List<String> list = new ArrayList<>();
		//对亚细胞位置信息进行替换
		for (HashMap.Entry<String, String> entry : expression2ppiMap.entrySet()) {
			if(expressionDataMap.containsKey(entry.getKey())){
				list.add(entry.getValue()+ "\t" + expressionDataMap.get(entry.getKey()));
			}
		}
		
//		System.out.println("转换好的数据量为\t"+list.size());
//		FileUtil.write2File(list, "E:/expression/transformMapResult.txt");
		
		return list;
		
	}
	
	/**
	 * 
	 * @author JiashuaiZhang
	 */

	@Override
	public int getExpressionMapSizeByTaxid(String taxid) {
		// TODO Auto-generated method stub
		List<ExpressionData> expressionDataList = expressionDataDao.getExpressionByOrganism(taxid);
		HashMap <String,String> expressionDataMap = new HashMap<>();
		
		for (ExpressionData sub : expressionDataList) {
			expressionDataMap.put(sub.getGeneName(), sub.getExpressionData());
		}
		
		// 1.先获取对应蛋白质的不重复ID
		List<String> idList = expressionDataDao.getUniqueIdByOrganism(taxid);;
//		System.out.println("mappingID size:"+idList.size());
		
		// 2.获取匹配到Uniprotkb的结果集
		List<ExpressionMap> mapped = expressionMapDao.getMappedResult(idList);
//		System.out.println("mappedID size:"+mapped.size());
		
		HashMap<String, String> hashMap = new HashMap<>();
		for (ExpressionMap map : mapped) {
			String[] arr = map.getUniprotkb().split(";");
			for (String str : arr) {
				if (!hashMap.containsKey(str)) {
					hashMap.put(str, map.getExpressionId());
				} else {
					String newValue = hashMap.get(str) + ";" + map.getExpressionId();
					hashMap.put(str, newValue);//jdk1.8使用replace方法
				}
			}
		}
		
		return hashMap.size();
		
	}

	/**
	 * ppiMap: uniprotKB,DIP protein
	 * expressionMap:protein, uniprotKB
	 * @param ppiMap
	 * @param expressionMap
	 * @return
	 * @author JiashuaiZhang
	 */
	
	private HashMap<String,String> transformMap(Map<String,String> ppiMap,Map<String,String> expressionMap){
		
		HashMap <String,String> targetMap = new HashMap<String,String>();
		
		for (Entry<String, String> entry : expressionMap.entrySet()) { 
			if(ppiMap.containsKey(entry.getKey())){
				targetMap.put(entry.getValue(), ppiMap.get(entry.getKey()));
			}
		}
		
		return targetMap;
		
	}

}
