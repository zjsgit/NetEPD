package cn.edu.bioinformatics.netepd.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.bioinformatics.netepd.dao.MintMapDao;
import cn.edu.bioinformatics.netepd.dao.MintNetworkDao;
import cn.edu.bioinformatics.netepd.entity.MintMap;
import cn.edu.bioinformatics.netepd.entity.MintNetwork;
import cn.edu.bioinformatics.netepd.service.MintService;

@Service("mintService")
public class MintServiceImpl implements MintService {
	
	@Autowired
	private MintNetworkDao mintNetworkDao;
	@Autowired
	private MintMapDao mintMapDao;
	
	@Override
	public HashMap<String, String> getMappingData(String taxid) {
		
		// 1.先获取网络中不重复的Interactor_ID值
		List<String> idList = mintNetworkDao.getUniqueIdByOrganism(taxid);
		System.out.println("mappingID size:"+idList.size());
		
		// 2.对不同种类的Interactor_ID进行不同的操作
		HashMap<String, String> hashMap = new HashMap<>();
		List<String> geneList = new ArrayList<>();
		for (String interactorId : idList) {
			if (interactorId.contains("uniprotkb")) {
				// 2.1若Interactor_ID为Uniprotkb则直接提取值
				String ac = interactorId.substring(10);
				hashMap.put(ac, interactorId);
			} else if (interactorId.contains("entrezgene")) {
				// 2.2若Interactor_ID为GeneID则先添加到list集合中
				String geneid = interactorId.substring(11);
				geneList.add(geneid);
			}
		}
		//System.out.println("mint genelist size:"+geneList.size());
		// 2.3当列表中不为空时将GeneID映射到对应的Uniprotkb
		if (geneList.size() > 0) {
			List<MintMap> mapped = mintMapDao.getMappedResult(geneList);
			System.out.println("mappedID size:"+(mapped.size()+hashMap.size()));
			
			for (MintMap mintMap : mapped) {
				String[] arr = mintMap.getUniprotkb().split(";");
				for (String str : arr) {
					if (!hashMap.containsKey(str)) {
						hashMap.put(str, "entrezgene:"+mintMap.getGeneId());
					} else {
						String newValue = hashMap.get(str) + ";entrezgene:" + mintMap.getGeneId();
						hashMap.put(str, newValue);//jdk1.8使用replace方法
					}
				}
			}
		}
		
		return hashMap;
	}

	@Override
	public List<String> getNetworkByTaxid(String taxid) {
		
		List<MintNetwork> listNet = mintNetworkDao.getNetworkByOrganism(taxid);
		
		List<String> list = new ArrayList<>();
		for (MintNetwork net : listNet) {
			list.add(net.getInteractorAid()+"\t"+net.getInteractorBid());
		}
		
		return list;
	}
	

}
