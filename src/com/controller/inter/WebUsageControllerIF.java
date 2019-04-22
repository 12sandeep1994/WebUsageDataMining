package com.controller.inter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.model.AJAXResponse;
import com.model.RegisterUser;

public interface WebUsageControllerIF {

	public @ResponseBody AJAXResponse viewStopWords(HttpServletRequest request);

	public ModelAndView addStopWord(String stopWord);

	public ModelAndView removeStopWord(String stopWord);

	public ModelAndView doDataCleaning(HttpServletRequest request, String type);

	public ModelAndView searchContents(HttpServletRequest request,
			@RequestParam String query);

	ModelAndView doLogin(HttpServletRequest request, RegisterUser registerUser);

	ModelAndView registerUserInfo(RegisterUser registerUser,
			HttpServletRequest request);

	ModelAndView doLogout(HttpServletRequest request);

	public @ResponseBody AJAXResponse viewProfileResults(
			HttpServletRequest request);

	ModelAndView doTokens(HttpServletRequest request);

	ModelAndView doFrequency(HttpServletRequest request);

	AJAXResponse retriveFeatureVector(HttpServletRequest request);

	AJAXResponse viewCleanReviews(HttpServletRequest request);

	public @ResponseBody AJAXResponse viewTokens(HttpServletRequest request);

	ModelAndView doFeatureVector(HttpServletRequest request);

	public @ResponseBody AJAXResponse retriveFreq(HttpServletRequest request);

	ModelAndView removeTableData(String tableDataToRemove,
			HttpServletRequest request);

	public ModelAndView doSearchContentsOffline(HttpServletRequest request,
			@RequestParam String query);

	public ModelAndView createPersonalSettings(HttpServletRequest request,
			HttpServletResponse response, int threshold);
	
	public  ModelAndView obtainResultsBasedOnConcepts(HttpServletRequest request);
	
	
}
