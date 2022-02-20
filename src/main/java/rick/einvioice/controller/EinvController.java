package rick.einvioice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
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
        for (int i = months * -1; i < 0; i++) {
            Calendar lastMonth = Calendar.getInstance();
            lastMonth.add(Calendar.MONTH, i); // 前6個月前
            lastMonth.set(Calendar.DAY_OF_MONTH, 1); // 1日
            String startDate = dateFormat.format(lastMonth.getTime());
            // 月底
            lastMonth.set(Calendar.DAY_OF_MONTH, lastMonth.getActualMaximum(Calendar.DAY_OF_MONTH));
            String endDate = dateFormat.format(lastMonth.getTime());
            result.append("startDate:").append(startDate).append(",").append("endDate:").append(endDate).append("<br/>");
            LOGGER.debug("startDate:{},endDate:{}", startDate, endDate);
            String json = einvUtils.send(einvUtils.getTitlePairs(API_ID, cardNo, cardEncrypt, startDate, endDate), EinvTitle.EINVIOICE_URL + EinvTitle.載具發票查詢URL);
            ObjectMapper objectMapper = new ObjectMapper();
            EinvTitleResponse response = objectMapper.readValue(json, EinvTitleResponse.class);
            if ("200".equals(response.getCode())) {
                response.getDetails().forEach(x -> {
                    String jsonDetail = einvUtils.send(einvUtils.getDetailsPairs(API_ID, cardNo, cardEncrypt, dateFormat.format(x.getInv_Date()), x.getInvNum()), EinvDetail.EINVIOICE_URL + EinvDetail.載具發票查詢URL);
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
    public String einvDetail(@RequestParam(required = false, value = "startDate") String startDate,
                             @RequestParam(required = false, value = "endDate") String endDate,
                             @RequestParam(required = false, value = "desc") String desc,
                             @RequestParam(required = false, value = "sellerName") String sellerName, Model model) {
        if (startDate == null) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -1);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            startDate = dateFormat.format(cal.getTime());
        }
        if (endDate == null) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_MONTH, 1);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            endDate = dateFormat.format(cal.getTime());
        }
        List<EinvTitleBean> einvList = null;
        if (desc != null) {
            einvList = einvTitleRepository.findByDesc(startDate, endDate, "%" + desc.trim() + "%");
        } else if (sellerName != null) {
            einvList = einvTitleRepository.findBySellerName(startDate, endDate, "%" + sellerName.trim() + "%");
        } else {
            einvList = einvTitleRepository.findByInvDate(startDate, endDate);
        }
        model.addAttribute("einvList", einvList);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("search", desc != null ? desc : sellerName != null ? sellerName : "");
        return "queryDetail";
    }

    @RequestMapping("/")
    public ModelAndView welcome(Map<String, Object> model) {
        return new ModelAndView("redirect:/queryDetail");
    }
}