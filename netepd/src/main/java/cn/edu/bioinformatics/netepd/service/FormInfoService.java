package cn.edu.bioinformatics.netepd.service;

import java.util.List;

import cn.edu.bioinformatics.netepd.entity.FormInfo;

public interface FormInfoService {
	
	/**
	 * 插入表单信息
	 * @param FormInfo
	 * @return ID
	 */
	public String addFormInfo(FormInfo info);
	/**
	 * 根据数据表ID查询表单信息
	 * @param ID
	 * @return FormInfo
	 */
	public FormInfo getById(int id);
	/**
	 * 根据任务ID查询表单信息
	 * @param jobId
	 * @return List
	 */
	public List<FormInfo> getByJobId(int jobId);
	
}
