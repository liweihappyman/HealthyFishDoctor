package com.healthyfish.healthyfishdoctor.POJO;

import java.util.ArrayList;
import java.util.List;

public class BeanBlogGroupListResp extends BeanBaseResp {
	
	private List<BeanBlogGroupListRespItem> blogGroupList = new ArrayList<BeanBlogGroupListRespItem>();

	public List<BeanBlogGroupListRespItem> getBlogGroupList() {return blogGroupList;}
	public void setBlogGroupList(List<BeanBlogGroupListRespItem> blogGroupList) {this.blogGroupList = blogGroupList;}

}
