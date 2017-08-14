package com.mvc.controller;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.mvc.entiy.BusNeed;
import com.mvc.entiy.BusTrade;
import com.mvc.entiy.Travel;
import com.mvc.entiy.TravelTrade;
import com.mvc.service.TravelService;

import net.sf.json.JSONObject;
/**
 * 
 * @ClassName: TravelController
 * @Description: TODO
 * @author ycj
 * @date 2017年8月14日 下午2:52:39 
 * 
 *
 */
@Controller
@RequestMapping("/travel")
public class TravelController {
	@Autowired
	TravelService travelService;
	/**
	 * 
	 * 
	 *@Title: selectTravelByDate 
	 *@Description: 按出发时间查询
	 *@param @param request
	 *@param @param session
	 *@param @return
	 *@return String
	 *@throws
	 */
	@RequestMapping(value = "/travelInfo.do")
	public @ResponseBody String selectTravelByDate(HttpServletRequest request, HttpSession session) {
		JSONObject jsonObject = new JSONObject();
		String useDate = request.getParameter("useDate");
		List<Travel> list = travelService.findTravelAlls(useDate);
		jsonObject.put("list", list);
		return jsonObject.toString();
	}
	/**
	 * 
	 * 
	 *@Title: selectTravelByPrice 
	 *@Description: 按成人票价查询
	 *@param @param request
	 *@param @param session
	 *@param @return
	 *@return String
	 *@throws
	 */
	public @ResponseBody String selectTravelByPrice(HttpServletRequest request, HttpSession session) {
		JSONObject jsonObject = new JSONObject();
		String usePrice = request.getParameter("usePrice");
		List<Travel> list = travelService.findTravelAlls1(usePrice);
		jsonObject.put("list", list);
		return jsonObject.toString();
	}
	
	/**
	 * 
	 * 
	 *@Title: addTravelTrade 
	 *@Description: 旅游交易 traveltrade
	 *@param @param request
	 *@param @param session
	 *@param @return
	 *@param @throws ParseException
	 *@return String
	 *@throws
	 */
	@RequestMapping(value = "/TravelTrade.do")
	public @ResponseBody String addTravelTrade(HttpServletRequest request, HttpSession session) throws ParseException {
		JSONObject jsonObject = JSONObject.fromObject(request.getParameter("travelTrade"));
		TravelTrade travelTrade = new TravelTrade();
		if (jsonObject.containsKey("trtr_tel")) {
			travelTrade.setTrtr_tel(jsonObject.getString("trtr_tel"));
		}
		if (jsonObject.containsKey("trtr_price")) {
			travelTrade.setTrtr_price(Float.parseFloat(jsonObject.getString("trtr_price")));
		}
		if (jsonObject.containsKey("trtr_mnum")) {
			travelTrade.setTrtr_mnum(Integer.valueOf(jsonObject.getString("trtr_mnum")));
		}
		if (jsonObject.containsKey("trtr_cnum")) {
			travelTrade.setTrtr_cnum(Integer.valueOf(jsonObject.getString("trtr_cnum")));
		}
		Travel travel = new Travel();
		travel.setTravel_id(Integer.parseInt(jsonObject.getJSONObject("travel").getString("travel_id")));
		travelTrade.setTravel_id(travel);
		boolean result;
		if (jsonObject.containsKey("trtr_id")) {
			travelTrade.setTrtr_id(Integer.valueOf(jsonObject.getString("trtr_id")));
			result = travelService.saveTravelTrade(travelTrade);// 修改交易信息
		} else {
			result = travelService.saveTravelTrade(travelTrade);// 添加交易信息
		}
		return JSON.toJSONString(result);

	}
}