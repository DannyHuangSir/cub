package com.bcs.cub.model;

import com.alibaba.fastjson.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HttpRequestResult {

	private int status;

	private String message;

	private JSONObject data;
}
