package com.bcs.cub.api;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bcs.cub.coinenum.Currency;
import com.bcs.cub.model.CoinInfo;
import com.bcs.cub.model.HttpRequestResult;
import com.bcs.cub.repository.CoinInfoRepository;

@RestController
public class CoinApi {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private CoinInfoRepository rep;

	@RequestMapping("/conver")
	public String coindesk() {
		String url = "https://api.coindesk.com/v1/bpi/currentprice.json";
		ResponseEntity<String> rs = this.restTemplate.getForEntity(url, String.class);
		JSONObject json = JSON.parseObject(rs.getBody());
		String updateDateTime = json.getJSONObject("time").getString("updated");

		JSONObject bpi = json.getJSONObject("bpi");

		ArrayList<CoinInfo> list = new ArrayList<>();
		bpi.keySet().forEach(key -> {
			JSONObject content = bpi.getJSONObject(key);
			CoinInfo info = new CoinInfo();
			info.setCurrency(content.getString("code"));
			info.setCurrencyTW(Currency.getCurrency(content.getString("code")).getChinese());
			info.setRate(content.getBigDecimal("rate_float"));
			Date date;
			try {
				date = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss").parse(updateDateTime);
			} catch (ParseException e) {
				date = new Date();
			}
			info.setUpdateDateTime(date);
			list.add(info);
		});

		rep.saveAll(list);
		return json.toString();
	}

	@RequestMapping("/coindesk")
	public String conver() {
		String url = "https://api.coindesk.com/v1/bpi/currentprice.json";
		ResponseEntity<String> rs = this.restTemplate.getForEntity(url, String.class);
		JSONObject json = JSON.parseObject(rs.getBody());
		return json.toString();
	}

	@RequestMapping(method = RequestMethod.POST, value = "/CoinInfo")
	public String insertCoinInfo(@RequestBody CoinInfo info) {
		HttpRequestResult rs = new HttpRequestResult();
		if (info != null) {
			if (StringUtils.isNotBlank(info.getCurrency()) && StringUtils.isNotBlank(info.getCurrencyTW())
					&& info.getRate() != null && info.getRate() != BigDecimal.ZERO) {
				info.setUpdateDateTime(new Date());
				rep.save(info);
				rs.setStatus(1);
				rs.setMessage("成功");

			} else {
				rs.setStatus(0);
				rs.setMessage("資料格式錯誤");
			}

		} else {
			rs.setStatus(0);
			rs.setMessage("資料格式錯誤");
		}

		return JSONObject.toJSONString(rs);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/CoinInfo")
	public String updateCoinInfo(@RequestBody CoinInfo info) {
		HttpRequestResult rs = new HttpRequestResult();
		if (info != null) {
			if (StringUtils.isNotBlank(info.getCurrency()) && StringUtils.isNotBlank(info.getCurrencyTW())
					&& info.getRate() != null && info.getRate() != BigDecimal.ZERO) {
				if (rep.existsById(info.getCurrency())) {
					info.setUpdateDateTime(new Date());
					rep.save(info);
					rs.setStatus(1);
					rs.setMessage("成功");
				} else {
					rs.setStatus(0);
					rs.setMessage("資料不存在");
				}

			} else {
				rs.setStatus(0);
				rs.setMessage("資料格式錯誤");
			}

		} else {
			rs.setStatus(0);
			rs.setMessage("資料格式錯誤");
		}

		return JSONObject.toJSONString(rs);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/CoinInfo")
	public String deleteCoinInfo(@RequestBody CoinInfo info) {
		HttpRequestResult rs = new HttpRequestResult();
		if (StringUtils.isNotBlank(info.getCurrency())) {
			if (rep.existsById(info.getCurrency())) {
				rep.deleteById(info.getCurrency());
				rs.setStatus(1);
				rs.setMessage("成功");
			} else {
				rs.setStatus(0);
				rs.setMessage("資料不存在");
			}
		} else {
			rs.setStatus(0);
			rs.setMessage("資料格式錯誤");
		}

		return JSONObject.toJSONString(rs);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/CoinInfo")
	public String getAllCoinInfo() {
		HttpRequestResult rs = new HttpRequestResult();
		List<CoinInfo> list = rep.findAll();
		if (list.size() > 0) {
			rs.setStatus(1);
			rs.setMessage("取得成功");
			JSONObject data = new JSONObject();
			data.put("data", list);
			rs.setData(data);
		} else {
			rs.setStatus(1);
			rs.setMessage("沒有資料");
		}
		return JSONObject.toJSONString(rs);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/CoinInfo/{currency}")
	public String getCoinInfoByCurrency(@PathVariable("currency") String currency) {
		HttpRequestResult rs = new HttpRequestResult();
		Optional<CoinInfo> d = rep.findById(currency);

		JSONObject data = new JSONObject();
		data.put("data", d.get());
		rs.setStatus(1);
		rs.setMessage("取得成功");
		rs.setData(data);

		return JSONObject.toJSONString(rs);
	}

}
