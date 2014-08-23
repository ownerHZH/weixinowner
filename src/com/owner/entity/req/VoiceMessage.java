package com.owner.entity.req;

/** 
 * 音频消息 
 *  
 * @author owner 
 * @QQ 756699074
 * @date 2014-08-20
 */  
public class VoiceMessage extends BaseMessage {  
    // 媒体ID  
    private String MediaId;  
    // 语音格式  
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
