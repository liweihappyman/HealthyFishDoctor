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
public class BeanWeChatNews_DateContent {
	private List<BeanWeChatNews_Item> news_item = new ArrayList<BeanWeChatNews_Item>();
	
	public List<BeanWeChatNews_Item> getNews_item() {
		return news_item;
	}
	public void setNews_item(List<BeanWeChatNews_Item> news_item) {
		this.news_item = news_item;
	}
}
