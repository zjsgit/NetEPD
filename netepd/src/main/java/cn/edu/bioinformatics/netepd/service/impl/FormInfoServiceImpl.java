package cn.edu.bioinformatics.netepd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.bioinformatics.netepd.dao.FormInfoDao;
import cn.edu.bioinformatics.netepd.entity.FormInfo;
import cn.edu.bioinformatics.netepd.service.FormInfoService;

@Service("formInfoService")
public class FormInfoServiceImpl implements FormInfoService {
	
	@Autowired
	private FormInfoDao formInfoDao;

	@Override
	public String addFormInfo(FormInfo info) {
		
		String id = formInfoDao.saveFormInfo(info);
		return id;
	}

	@Override
	public FormInfo getById(int id) {
		
		FormInfo info = formInfoDao.queryById(id);

		//System.out.println("queryById:\n"+info);
		return info;
	}

	@Override
	public List<FormInfo> getByJobId(int jobId) {
		
		List<FormInfo> infos = formInfoDao.queryByJobId(jobId);
		
		return infos;
	}

}
