package com.qcj.common.util.localImageHelper;

/**
 * 
 */
/***
 * eg:"PhotoDirInfo [dirId=" + -1313584517 + ", dirName=" + screenshots +
 * ", firstPicPath=" + /storage/emulated/0/dcim/screenshot/xxxx.png + ", ",
 * picCount=" + 172 + "]"
 * 
 * */
public class PhotoDirInfo {
	private String dirId; // 该文件夹id
	private String dirName;// 文件夹名字
	private String firstPicPath;// 该文件夹的第一照片的路径
	private boolean isUserOtherPicSoft;//
	private int picCount = 0; // 该文件夹照片数量

	public PhotoDirInfo() {
	}

	public PhotoDirInfo(String paramString, boolean paramBoolean) {
		this.dirName = paramString;
		this.isUserOtherPicSoft = paramBoolean;
	}

	public String getDirId() {
		return this.dirId;
	}

	public String getDirName() {
		return this.dirName;
	}

	public String getFirstPicPath() {
		return this.firstPicPath;
	}

	public int getPicCount() {
		return this.picCount;
	}

	public boolean isUserOtherPicSoft() {
		return this.isUserOtherPicSoft;
	}

	public void setDirId(String paramString) {
		this.dirId = paramString;
	}

	public void setDirName(String paramString) {
		this.dirName = paramString;
	}

	public void setFirstPicPath(String paramString) {
		this.firstPicPath = paramString;
	}

	public void setPicCount(int paramInt) {
		this.picCount = paramInt;
	}

	public void setUserOtherPicSoft(boolean paramBoolean) {
		this.isUserOtherPicSoft = paramBoolean;
	}

	@Override
	public String toString() {
		return "PhotoDirInfo [dirId=" + dirId + ", dirName=" + dirName
				+ ", firstPicPath=" + firstPicPath + ", isUserOtherPicSoft="
				+ isUserOtherPicSoft + ", picCount=" + picCount + "]";
	}

}
