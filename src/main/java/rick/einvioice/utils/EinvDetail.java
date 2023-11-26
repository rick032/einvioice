package rick.einvioice.utils;

public class EinvDetail implements EinvType {
    public static final String 載具發票查詢URL = "/PB2CAPIVAN/invServ/InvServ";
    public static final String 載具發票查詢VER = "0.5";
    public static final String 查詢發票明細VER = "0.6";
    public static final String 查詢發票明細TYPE = "qryInvDetail";
    public static final String TYPE = "carrierInvDetail";

    @Override
    public String getType() {
        return TYPE;
    }
}
