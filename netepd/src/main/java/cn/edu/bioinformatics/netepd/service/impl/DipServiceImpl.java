package cn.edu.bioinformatics.netepd.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.bioinformatics.netepd.dao.DipMapDao;
import cn.edu.bioinformatics.netepd.dao.DipNetworkDao;
import cn.edu.bioinformatics.netepd.entity.DipMap;
import cn.edu.bioinformatics.netepd.entity.DipNetwork;
import cn.edu.bioinformatics.netepd.service.DipService;

@Service("dipService")
public class DipServiceImpl implements DipService {
	
	@Autowired
	private DipNetworkDao dipNetworkDao;
	@Autowired
	private DipMapDao dipMapDao;

	@Override
	public HashMap<String, String> getMappingData(String taxid) {

		List<String> idList = dipNetworkDao.getUniqueIdByOrganism(taxid);
		System.out.println("mappingID size:"+idList.size());

		List<DipMap> mapped = dipMapDao.getMappedResult(idList);
		System.out.println("mappedID size:"+mapped.size());
		
		// 蛋白质网络中的ID映射集合
		HashMap<String, String> hashMap = new HashMap<>();
		for (DipMap dipMap : mapped) {
			String[] arr = dipMap.getUniprotkb().split(";");
			for (String str : arr) {
				if (!hashMap.containsKey(str)) {
					hashMap.put(str, dipMap.getDipId());
				} else {
					String newValue = hashMap.get(str) + ";" + dipMap.getDipId();
					hashMap.put(str, newValue);//jdk1.8使用replace方法
				}
			}
		}

		return hashMap;
	}

	@Override
	public List<String> getNetworkByTaxid(String taxid) {
		
		List<DipNetwork> listNet = dipNetworkDao.getNetworkByOrganism(taxid);
		
		List<String> list = new ArrayList<>();
		for (DipNetwork net : listNet) {
			list.add(net.getDipAid()+"\t"+net.getDipBid());
		}
		
		return list;
	}

}
