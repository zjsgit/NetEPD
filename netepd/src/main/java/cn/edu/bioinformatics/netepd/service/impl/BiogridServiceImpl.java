package cn.edu.bioinformatics.netepd.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.bioinformatics.netepd.dao.BiogridMapDao;
import cn.edu.bioinformatics.netepd.dao.BiogridNetworkDao;
import cn.edu.bioinformatics.netepd.entity.BiogridMap;
import cn.edu.bioinformatics.netepd.entity.BiogridNetwork;
import cn.edu.bioinformatics.netepd.service.BiogridService;

@Service("biogridService")
public class BiogridServiceImpl implements BiogridService {
	
	@Autowired
	private BiogridNetworkDao biogridNetworkDao;
	@Autowired
	private BiogridMapDao biogridMapDao;
	
	@Override
	public HashMap<String, String> getMappingData(String taxid) {
		
		// 1.先获取对应物种的不重复ID
		List<String> idList = biogridNetworkDao.getUniqueIdByOrganism(taxid);
		System.out.println("mappingID size:"+idList.size());
		
		// 2.获取匹配到Uniprotkb的结果集
		List<BiogridMap> mapped = biogridMapDao.getMappedResult(idList);
		System.out.println("mappedID size:"+mapped.size());
		
		HashMap<String, String> hashMap = new HashMap<>();
		for (BiogridMap biogridMap : mapped) {
			String[] arr = biogridMap.getUniprotkb().split(";");
			for (String str : arr) {
				if (!hashMap.containsKey(str)) {
					hashMap.put(str, biogridMap.getBiogridId());
				} else {
					String newValue = hashMap.get(str) + ";" + biogridMap.getBiogridId();
					hashMap.put(str, newValue);//jdk1.8使用replace方法
				}
			}
		}
		return hashMap;
	}

	@Override
	public List<String> getNetworkByTaxid(String taxid) {
		
		List<BiogridNetwork> listNet = biogridNetworkDao.getNetworkByOrganism(taxid);
		
		List<String> list = new ArrayList<>();
		for (BiogridNetwork net : listNet) {
			list.add(net.getBiogridAid()+"\t"+net.getBiogridBid());
		}
		
		return list;
	}

}
