package com.owner.entity.req;

/** 
 * ������Ϣ 
 *  
 * @author owner 
 * @QQ 756699074
 * @date 2014-08-20
 */  
public class LinkMessage extends BaseMessage {  
    // ��Ϣ����  
    private String Title;  
    // ��Ϣ����  
    private String Description;  
    // ��Ϣ����  
    private String Url;  
  
    public String getTitle() {  
        return Title;  
    }  
  
    public void setTitle(String title) {  
        Title = title;  
    }  
  
    public String getDescription() {  
        return Description;  
    }  
  
    public void setDescription(String description) {  
        Description = description;  
    }  
  
    public String getUrl() {  
        return Url;  
    }  
  
    public void setUrl(String url) {  
        Url = url;  
    }  
}
