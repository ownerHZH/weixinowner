package com.owner.entity.req;

/** 
 * ��Ƶ��Ϣ 
 *  
 * @author owner 
 * @QQ 756699074
 * @date 2014-08-20
 */  
public class VoiceMessage extends BaseMessage {  
    // ý��ID  
    private String MediaId;  
    // ������ʽ  
    private String Format;  
  
    public String getMediaId() {  
        return MediaId;  
    }  
  
    public void setMediaId(String mediaId) {  
        MediaId = mediaId;  
    }  
  
    public String getFormat() {  
        return Format;  
    }  
  
    public void setFormat(String format) {  
        Format = format;  
    }  
}
