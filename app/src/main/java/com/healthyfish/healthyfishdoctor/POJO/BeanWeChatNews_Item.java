package com.healthyfish.healthyfishdoctor.POJO;

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
public class BeanWeChatNews_Item {
	private String title;
	private String thumb_media_id;
	private int  show_cover_pic;
	private String author;
	private String digest;
	private String content;
	private String url;
	private String content_source_url;
	private String thumb_url;
	
	
	public String getThumb_url() {
		return thumb_url;
	}
	public void setThumb_url(String thumb_url) {
		this.thumb_url = thumb_url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getThumb_media_id() {
		return thumb_media_id;
	}
	public void setThumb_media_id(String thumb_media_id) {
		this.thumb_media_id = thumb_media_id;
	}
	public int getShow_cover_pic() {
		return show_cover_pic;
	}
	public void setShow_cover_pic(int show_cover_pic) {
		this.show_cover_pic = show_cover_pic;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getDigest() {
		return digest;
	}
	public void setDigest(String digest) {
		this.digest = digest;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getContent_source_url() {
		return content_source_url;
	}
	public void setContent_source_url(String content_source_url) {
		this.content_source_url = content_source_url;
	}
	
	
}
