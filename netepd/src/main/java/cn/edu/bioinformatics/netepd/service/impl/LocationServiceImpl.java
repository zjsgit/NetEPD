package cn.edu.bioinformatics.netepd.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.bioinformatics.netepd.dao.LocationDataDao;
import cn.edu.bioinformatics.netepd.dao.LocationMapDao;
import cn.edu.bioinformatics.netepd.entity.LocationMap;
import cn.edu.bioinformatics.netepd.entity.LocationData;
import cn.edu.bioinformatics.netepd.service.LocationService;
import cn.edu.bioinformatics.netepd.util.FileUtil;

@Service("locationService")
public class LocationServiceImpl implements LocationService {

	@Autowired
	private LocationDataDao locationDataDao;
	@Autowired
	private LocationMapDao locationMapDao;
	
	@Override
	public List<String> getLocationByTaxid(String taxid, HashMap<String,String> ppiMappingData) {
		// TODO Auto-generated method stub
		
//		FileUtil.writeMap2File(ppiMappingData, "E:/ppiMappingResult.txt");
		
		List<LocationData> locationDataList = locationDataDao.getLocationByOrganism(taxid);
		HashMap <String,String> locationDataMap = new HashMap<>();
		
		for (LocationData sub : locationDataList) {
			locationDataMap.put(sub.getProteinName(), sub.getProteinLocation());
		}
		
//		FileUtil.writeMap2File(locationDataMap, "E:/locationDataResult.txt");
		
		// 1.先获取对应蛋白质的不重复ID
		List<String> idList = locationDataDao.getUniqueIdByOrganism(taxid);;
		System.out.println("mappingID size:"+idList.size());
		
		// 2.获取匹配到Uniprotkb的结果集
		List<LocationMap> mapped = locationMapDao.getMappedResult(idList);
		System.out.println("mappedID size:"+mapped.size());
		
		HashMap<String, String> hashMap = new HashMap<>();
		for (LocationMap map : mapped) {
			String[] arr = map.getUniprotkb().split(";");
			for (String str : arr) {
				if (!hashMap.containsKey(str)) {
					hashMap.put(str, map.getLocationId());
				} else {
					String newValue = hashMap.get(str) + ";" + map.getLocationId();
					hashMap.put(str, newValue);//jdk1.8使用replace方法
				}
			}
		}
		
//		FileUtil.writeMap2File(hashMap, "E:/mappedResult.txt");
		
		HashMap <String,String> location2ppiMap = transformMap(ppiMappingData,hashMap);
//		System.out.println("location2ppiMap的大小为\t"+location2ppiMap.size());
//		FileUtil.writeMap2File(location2ppiMap, "E:/location2ppiMapResult.txt");
		
		List<String> list = new ArrayList<>();
		//对亚细胞位置信息进行替换
		for (HashMap.Entry<String, String> entry : location2ppiMap.entrySet()) {
			if(locationDataMap.containsKey(entry.getKey())){
				list.add(entry.getValue()+ "\t" + locationDataMap.get(entry.getKey()));
			}
		}
//		System.out.println("转换好的数据量为\t"+list.size());
//		FileUtil.write2File(list, "E:/transformMapResult.txt");
		
		return list;
	}

	@Override
	public int getLocationMapSizeByTaxid(String taxid) {
		// TODO Auto-generated method stub
		
		List<LocationData> locationDataList = locationDataDao.getLocationByOrganism(taxid);
		HashMap <String,String> locationDataMap = new HashMap<>();
		
		for (LocationData sub : locationDataList) {
			locationDataMap.put(sub.getProteinName(), sub.getProteinLocation());
		}
		
		// 1.先获取对应蛋白质的不重复ID
		List<String> idList = locationDataDao.getUniqueIdByOrganism(taxid);;
//		System.out.println("mappingID size:"+idList.size());
		
		// 2.获取匹配到Uniprotkb的结果集
		List<LocationMap> mapped = locationMapDao.getMappedResult(idList);
//		System.out.println("mappedID size:"+mapped.size());
		
		HashMap<String, String> hashMap = new HashMap<>();
		for (LocationMap map : mapped) {
			String[] arr = map.getUniprotkb().split(";");
			for (String str : arr) {
				if (!hashMap.containsKey(str)) {
					hashMap.put(str, map.getLocationId());
				} else {
					String newValue = hashMap.get(str) + ";" + map.getLocationId();
					hashMap.put(str, newValue);//jdk1.8使用replace方法
				}
			}
		}
		
		return hashMap.size();
	}
	
	
	/**
	 * ppiMap: uniprotKB,DIP protein
	 * locationMap:protein, uniprotKB
	 * @param ppiMap
	 * @param locationMap
	 * @return
	 * @author JiashuaiZhang
	 */
	
	private HashMap<String,String> transformMap(Map<String,String> ppiMap,Map<String,String> locationMap){
		
		HashMap <String,String> targetMap = new HashMap<String,String>();
		
		for (Entry<String, String> entry : locationMap.entrySet()) { 
			if(ppiMap.containsKey(entry.getKey())){
				targetMap.put(entry.getValue(), ppiMap.get(entry.getKey()));
			}
		}
		
		return targetMap;
		
	}

}
