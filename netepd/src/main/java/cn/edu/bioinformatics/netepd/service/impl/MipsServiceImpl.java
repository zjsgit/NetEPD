package cn.edu.bioinformatics.netepd.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.bioinformatics.netepd.dao.MipsMapDao;
import cn.edu.bioinformatics.netepd.dao.MipsNetworkDao;
import cn.edu.bioinformatics.netepd.entity.MipsMap;
import cn.edu.bioinformatics.netepd.entity.MipsNetwork;
import cn.edu.bioinformatics.netepd.service.MipsService;

@Service("mipsService")
public class MipsServiceImpl implements MipsService {
	
	@Autowired
	private MipsNetworkDao mipsNetworkDao;
	@Autowired
	private MipsMapDao mipsMapDao;

	@Override
	public List<String> getNetworkByTaxid(String taxid) {
		
		List<MipsNetwork> listNet = mipsNetworkDao.getNetworkByOrganism(taxid);
		
		List<String> list = new ArrayList<>();
		for (MipsNetwork net : listNet) {
			list.add(net.getMipsAid()+"\t"+net.getMipsBid());
		}
		return list;
		
	}

	@Override
	public HashMap<String, String> getMappingData(String taxid) {
		
		//先获取对应物种的不重复MipsID
		List<String> idList = mipsNetworkDao.getUniqueIdByOrganism(taxid);
		System.out.println("mappingID size:"+idList.size());
		
		//获取匹配到Uniprot的结果集
		List<MipsMap> mapped = mipsMapDao.getMappedResult(idList);
		System.out.println("mappedID size:"+mapped.size());
		
		HashMap<String, String> hashMap = new HashMap<>();
		for (MipsMap mipsMap : mapped) {
			if (!hashMap.containsKey(mipsMap.getUniprotkb())) {
				hashMap.put(mipsMap.getUniprotkb(), mipsMap.getMipsId());
			}else {
				String newValue = hashMap.get(mipsMap.getUniprotkb())+";"+mipsMap.getMipsId();
				hashMap.put(mipsMap.getUniprotkb(), newValue);//jdk1.8使用replace方法
			}
		}
		
		return hashMap;
		
	}

}
