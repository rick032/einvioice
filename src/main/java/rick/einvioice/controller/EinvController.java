package rick.einvioice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import rick.einvioice.model.EinvDetailResponse;
import rick.einvioice.model.EinvTitleBean;
import rick.einvioice.model.EinvTitleResponse;
import rick.einvioice.repository.EinvDetailRepository;
import rick.einvioice.repository.EinvTitleRepository;
import rick.einvioice.repository.ParamsRepository;
import rick.einvioice.utils.EinvConstants;
import rick.einvioice.utils.EinvDetail;
import rick.einvioice.utils.EinvTitle;
import rick.einvioice.utils.EinvUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
public class EinvController {
    private static final Logger LOGGER = LogManager.getLogger(EinvController.class);

    @Autowired
    EinvTitleRepository einvTitleRepository;
    @Autowired
    EinvDetailRepository einvDetailRepository;
    @Autowired
    EinvUtils einvUtils;
    @Autowired
    ParamsRepository paramsRepository;
    final String DATE_FORMAT = "yyyy/MM/dd";

    @GetMapping(value = "/queryMonths")
    public String queryLast6Months(@RequestParam(value = "cardNo") String cardNo,
                                   @RequestParam(value = "cardEncrypt") String cardEncrypt,
                                   @RequestParam(value = "months") Integer months) throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        StringBuilder result = new StringBuilder();
        String API_ID = paramsRepository.findValueByKey(EinvConstants.API_ID);
        //API_KEY = paramsRepository.findValueByKey(EinvConstants.API_KEY);
        for (int i = months*-1; i < 0; i++) {
            Calendar lastMonth = Calendar.getInstance();
            lastMonth.add(Calendar.MONTH, i); // ???6?????????
            lastMonth.set(Calendar.DAY_OF_MONTH, 1); // 1???
            String startDate = dateFormat.format(lastMonth.getTime());
            // ??????
            lastMonth.set(Calendar.DAY_OF_MONTH, lastMonth.getActualMaximum(Calendar.DAY_OF_MONTH));
            String endDate = dateFormat.format(lastMonth.getTime());
            result.append("startDate:").append(startDate).append(",").append("endDate:").append(endDate).append("<br/>");
            LOGGER.debug("startDate:{},endDate:{}", startDate, endDate);
            String json = einvUtils.send(einvUtils.getTitlePairs(API_ID,cardNo, cardEncrypt, startDate, endDate), EinvTitle.EINVIOICE_URL + EinvTitle.??????????????????URL);
            ObjectMapper objectMapper = new ObjectMapper();
            EinvTitleResponse response = objectMapper.readValue(json, EinvTitleResponse.class);
            if ("200".equals(response.getCode())) {
                response.getDetails().forEach(x -> {
                    String jsonDetail = einvUtils.send(einvUtils.getDetailsPairs(API_ID,cardNo, cardEncrypt, dateFormat.format(x.getInv_Date()), x.getInvNum()), EinvDetail.EINVIOICE_URL + EinvDetail.??????????????????URL);
                    try {
                        EinvDetailResponse detailResponse = objectMapper.readValue(jsonDetail, EinvDetailResponse.class);
                        x.setInvDetails(detailResponse.getDetails());
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                });
                einvTitleRepository.saveAll(response.getDetails().stream().filter(x ->
                        x.getInvDetails() != null && einvTitleRepository.findByInvNum(x.getInvNum()) == null
                ).collect(Collectors.toList()));
            }
        }
        return result.toString();
    }

    @GetMapping(value = "/queryDetail")
    public String einvDetail(@RequestParam(required=false,value = "invDate") String invDate) {
        if(invDate==null){
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -1);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            invDate = dateFormat.format(cal.getTime());
        }
        List<EinvTitleBean> list = einvTitleRepository.findByInvDate(invDate);
        StringBuilder result = new StringBuilder(
                "<table class=\"table table-striped\">" +
                        "<thead>" +
                        "<tr>");
        Map<String, String> headerMap = new LinkedHashMap<>();
        headerMap.put("#", "#");
        headerMap.put("id", "ID");
        headerMap.put("amount", "?????????");
        headerMap.put("cardType", "??????");
        headerMap.put("invNum", "????????????");
        headerMap.put("inv_Date", "????????????");
        headerMap.put("invoiceTime", "????????????");
        headerMap.put("rowNum", "??????");
        headerMap.put("sellerName", "???????????????");
        headerMap.put("sellerBan", "?????????????????????");
        headerMap.put("sellerAddress", "?????????????????????");
        headerMap.values().forEach(x -> result.append("<th class=\"table-dark\" scope=\"col\">").append(x).append("</th>"));
        result.append("</tr></thead><tbody>");
        AtomicInteger rowNum = new AtomicInteger(1);
        list.forEach(x -> {
            result.append("<tr class=\"table-primary\">");
            StringBuilder rowBuilder = new StringBuilder("<th scope=\"row\">" + rowNum.getAndIncrement() + "</th>");
            headerMap.keySet().forEach(y -> {
                try {
                    if (!"#".equals(y)) {
                        Field field = x.getClass().getDeclaredField(y);
                        field.setAccessible(true);
                        rowBuilder.append("<td>").append(field.get(x)).append("</td>");
                    }
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
            result.append(rowBuilder);

            result.append("</tr><tr><td colspan=\"" + headerMap.size() + "\">");
            result.append("<table class=\"table\">" +
                    "<thead>" +
                    "<tr>");
            Map<String, String> detailMap = new LinkedHashMap<>();
            detailMap.put("rowNum", "????????????");
            detailMap.put("amount", "??????");
            detailMap.put("description", "??????");
            detailMap.put("quantity", "??????");
            detailMap.put("unitPrice", "??????");
            detailMap.values().forEach(g -> result.append("<th scope=\"col\">").append(g).append("</th>"));
            result.append("</tr></thead><tbody>");
            x.getInvDetails().forEach(z -> {
                result.append("<tr>");
                detailMap.keySet().forEach(i -> {
                    try {
                        Field fieldDetail = z.getClass().getDeclaredField(i);
                        fieldDetail.setAccessible(true);
                        result.append("<td>").append(fieldDetail.get(z)).append("</td>");
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
                result.append("</tr>");
            });
            result.append("<tbody></table>");
            result.append("</tr></td>");
        });
        result.append("<tbody></table>");


        return bootstrapCode("Einv Query", result.toString());
    }

    public String bootstrapCode(String title, String body) {
        StringBuilder result = new StringBuilder("<html lang=\"en\">\n" + "<head><title>" + title + "</title>" +
                "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3\" crossorigin=\"anonymous\">" +
                "<style>\n" +
                "//color\n" +
                "$primary: #3d82ad;\n" +
                "$white: #fff;\n" +
                "$text: #333;\n" +
                "$blue: #5fbfff;\n" +
                "$green: #00aa00;\n" +
                "$yellow: #f3da9a;\n" +
                "$orange: #ff8800;\n" +
                "$pink: #faaad1;\n" +
                "$red: #fa5858;\n" +
                "$darkRed: #a11313;\n" +
                "\n" +
                "//layout\n" +
                "body {\n" +
                "box-sizing: border-box;\n" +
                "font-family: \"Segoe UI\", Tahoma, Geneva, Verdana, sans-serif, \"???????????????\";\n" +
                "}\n" +
                "\n" +
                ".wrap {\n" +
                "width: 500px;\n" +
                "margin: 0 auto;\n" +
                "}\n" +
                "\n" +
                ".question {\n" +
                "h2 {\n" +
                "color: $primary;\n" +
                "border: 1px solid $primary;\n" +
                "padding: 10px 5px;\n" +
                "margin-bottom: 10px;\n" +
                "margin-top: 10px;\n" +
                "&:hover {\n" +
                "background-color: $primary;\n" +
                "color: $white;\n" +
                "}\n" +
                "&.active {\n" +
                "p {\n" +
                "display: block;\n" +
                "}\n" +
                "}\n" +
                "}\n" +
                "p {\n" +
                "display: none;\n" +
                "line-height: 1.5;\n" +
                "}\n" +
                "}" +
                "</style>" +
                "</head>\n" +
                "<body>\n");
        result.append(body);
        result.append("<script src=\"https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js\" integrity=\"sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB\" crossorigin=\"anonymous\"></script>\n" +
                "<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js\" integrity=\"sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13\" crossorigin=\"anonymous\"></script>" +
                "<script src=\"js/einv.js\"></script>" +
                "</body>\n" + "</html>");
        return result.toString();
    }

    @RequestMapping("/")
    public ModelAndView welcome(Map<String, Object> model) {
        return new ModelAndView("redirect:/queryDetail");
    }
}