package com.healthyfish.healthyfishdoctor.POJO;

import java.util.ArrayList;
import java.util.List;

public class BeanHomeImgSlideResp extends BeanBaseResp {
	//内部类导致JSON失败
	/*	public class Image{
		String img;
		String desc;
		
		public String getImg() {return img;}
		public void setImg(String img) {this.img = img;}
		public String getDesc() {return desc;}
		public void setDesc(String desc) {this.desc = desc;}
	}
//*/	
	private List<BeanHomeImgSlideRespItem> imgList = new ArrayList<BeanHomeImgSlideRespItem>();
	
	public List<BeanHomeImgSlideRespItem> getImgList() {
		return imgList;
	}
	public void setImgList(List<BeanHomeImgSlideRespItem> imgList) {
		this.imgList = imgList;
	}
/*	
	public void addImg(String url, String desc){
		BeanHomeImgSlideRespImage img = new BeanHomeImgSlideRespImage();
		img.setImg(url);
		img.setDesc(desc);
		imgList.add(img);
	}
*/	
}
