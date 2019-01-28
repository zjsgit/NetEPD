package cn.edu.bioinformatics.netepd.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.bioinformatics.netepd.dao.DegMapDao;
import cn.edu.bioinformatics.netepd.entity.DegMap;
import cn.edu.bioinformatics.netepd.service.DegService;

@Service("degService")
public class DegServiceImpl implements DegService {
	
	@Autowired
	private DegMapDao degMapDao;

	@Override
	public HashSet<String> getMappedList(HashMap<String, String> mapACs) {
		
		List<String> mappingList = new ArrayList<>();
		for (String interactorAC : mapACs.keySet()) {
			mappingList.add(interactorAC);
		}
		List<DegMap> listDeg = degMapDao.getMappedResult(mappingList);
		
		HashSet<String> set = new HashSet<>();
		for (DegMap degMap : listDeg) {
			
			if (mapACs.containsKey(degMap.getUniprotkb())) {
				String ids = mapACs.get(degMap.getUniprotkb());
				if (ids.contains(";")) {
					String[] arr = ids.split(";");
					for (String str : arr) {
						set.add(str);
					}
				} else {
					set.add(ids);
				}
			}
		}
		
		return set;
	}

}
