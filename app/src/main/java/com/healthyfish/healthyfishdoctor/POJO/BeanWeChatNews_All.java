package com.healthyfish.healthyfishdoctor.POJO;

import java.util.ArrayList;
import java.util.List;

/*
{
  "total_count": TOTAL_COUNT,
  "item_count": ITEM_COUNT,
  "item": [{
      "media_id": MEDIA_ID,
      "content": {
          "news_item": [{
              "title": TITLE,
              "thumb_media_id": THUMB_MEDIA_ID,
              "show_cover_pic": SHOW_COVER_PIC(0 / 1),
              "author": AUTHOR,
              "digest": DIGEST,
              "content": CONTENT,
              "url": URL,
              "content_source_url": CONTETN_SOURCE_URL
          },
          //多图文消息会在此处有多篇文章
          ]
       },
       "update_time": UPDATE_TIME
   },
   //可能有多个图文消息item结构
 ]
}
*/
public class BeanWeChatNews_All {
	private int total_count;
	private int item_count;
	List<BeanWeChatNews_Date> item = new ArrayList<BeanWeChatNews_Date>();
	
	public int getTotal_count() {
		return total_count;
	}
	public void setTotal_count(int total_count) {
		this.total_count = total_count;
	}
	public int getItem_count() {
		return item_count;
	}
	public void setItem_count(int item_count) {
		this.item_count = item_count;
	}
	public List<BeanWeChatNews_Date> getItem() {
		return item;
	}
	public void setItem(List<BeanWeChatNews_Date> item) {
		this.item = item;
	}
}
