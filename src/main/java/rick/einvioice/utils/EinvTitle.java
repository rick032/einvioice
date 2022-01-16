package rick.einvioice.utils;

public class EinvTitle implements EinvType {
    public static final String 載具發票查詢URL = "/PB2CAPIVAN/invServ/InvServ";
    public static final String 載具發票查詢VER = "0.5";
    public static final String TYPE = "carrierInvChk";
    @Override
    public String getType() {
        return TYPE;
    }
}
