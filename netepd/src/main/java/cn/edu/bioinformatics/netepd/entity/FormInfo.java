package cn.edu.bioinformatics.netepd.entity;

/**
 * 表单信息类，用来存储用户提交的信息
 * @author lwk
 *
 */
public class FormInfo {

	private Integer id;
	private String jobId;
	private String formatType;
	private String algorithms;
	private String filePath;
	private String taxid;
	private String datasets;
	private String jobNote;
	private String location;
	private String email;
	private String createdTime;

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getFormatType() {
		return formatType;
	}

	public void setFormatType(String formatType) {
		this.formatType = formatType;
	}

	public String getFilePath() {
		return filePath;
	}

	public String getAlgorithms() {
		return algorithms;
	}

	public void setAlgorithms(String algorithms) {
		this.algorithms = algorithms;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getTaxid() {
		return taxid;
	}

	public void setTaxid(String taxid) {
		this.taxid = taxid;
	}

	public String getDatasets() {
		return datasets;
	}

	public void setDatasets(String datasets) {
		this.datasets = datasets;
	}

	public String getJobNote() {
		return jobNote;
	}

	public void setJobNote(String jobNote) {
		this.jobNote = jobNote;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public FormInfo() {
	}

	public FormInfo(String jobId, String formatType, String algorithms, String filePath, String taxid, String datasets,
			String jobNote, String location, String email, String createdTime) {
		super();
		this.jobId = jobId;
		this.formatType = formatType;
		this.algorithms = algorithms;
		this.filePath = filePath;
		this.taxid = taxid;
		this.datasets = datasets;
		this.jobNote = jobNote;
		this.location = location;
		this.email = email;
		this.createdTime = createdTime;
	}

	@Override
	public String toString() {
		return "FormInfo [id=" + id + ", jobId=" + jobId + ", formatType=" + formatType + ", algorithms=" + algorithms
				+ ", filePath=" + filePath + ", taxid=" + taxid + ", datasets=" + datasets + ", jobNote=" + jobNote + ", location="
				+ location + ", email=" + email + ", createdTime=" + createdTime + "]";
	}
	
	

}
