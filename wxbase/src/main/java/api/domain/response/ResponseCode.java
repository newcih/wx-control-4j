package api.domain.response;

/**
 * 微信请求全局返回码
 *
 * @author NEWCIH
 */
public class ResponseCode {

    public static final Integer SYSTEM_BUSY = -1;
    public static final Integer SUCCESS = 0;
    public static final Integer ACCESSTOKEN_INVALID = 40001;
    public static final Integer INVALID_CREDENTIAL_TYPE = 40002;
    public static final Integer INVALID_OPENID = 40003;
    public static final Integer INVALID_MEDIA_FILE_TYPE = 40004;
    public static final Integer INVALID_FILE_TYPE = 40005;
    public static final Integer INVALID_FILE_SIZE = 40006;
    public static final Integer INVALID_MEDIAID = 40007;
    public static final Integer INVALID_MESSAGE_TYPE = 40008;
    public static final Integer INVALID_IMAGE_FILE_SIZE = 40009;
    public static final Integer INVALID_AUDIO_FILE_SIZE = 40010;
    public static final Integer INVALID_VIDEO_FILE_SIZE = 40011;
    public static final Integer INVALID_THUMBNAIL_FILE_SIZE = 40012;
    public static final Integer INVALID_APPID = 40013;
    public static final Integer INVALID_ACCESS_TOKEN = 40014;
    public static final Integer INVALID_OAUTH_CODE = 40029;
    public static final Integer INVALID_REFRESH_TOKEN = 40030;
    public static final Integer INVALID_OPENID_List = 40031;
    public static final Integer INVALID_OPENID_LIST_LENGTH = 40032;
    public static final Integer INVALID_WECHAT_ID = 40132;
    public static final Integer REFRESH_TOKEN_HAS_TIMED_OUT = 42002;
    public static final Integer OAUTH_CODE_HAS_TIMED_OUT = 42003;

}
