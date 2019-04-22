package com.delegate.impl;

import java.util.List;

import com.delegate.inter.WebUsageDelegateIF;
import com.model.ConceptsSearch;
import com.model.FeatureVO;
import com.model.FrequencyVO;
import com.model.GoogleResult;
import com.model.RegisterUser;
import com.model.ReviewModel;
import com.model.SearchObj;
import com.model.StatusInfo;
import com.model.StopWordsVO;
import com.model.TokenVO;
import com.service.inter.WebUsageServiceIF;

public class WebUsageDelegateImpl implements WebUsageDelegateIF {

	private WebUsageServiceIF webUsageService;  

	

	public WebUsageServiceIF getWebUsageService() {
		return webUsageService;
	}


	public void setWebUsageService(WebUsageServiceIF webUsageService) {
		this.webUsageService = webUsageService;
	}


	@Override
	public StatusInfo storeReviews(ReviewModel reviewModel) {
		return webUsageService.storeReviews(reviewModel);
	}

	
	
	
	@Override
	public StatusInfo addStopword(String stopWord) {
		return webUsageService.addStopword(stopWord);
	}

	@Override
	public List<StopWordsVO> retriveStopWords() {
		return webUsageService.retriveStopWords();
	}

	@Override
	public StatusInfo doDataCleaning(String userId) {
		return webUsageService.doDataCleaning(userId);
	}


	


	

	@Override
	public StatusInfo removeStopword(String stopWord) {
		return webUsageService.removeStopword(stopWord);
	}


	@Override
	public StatusInfo removeTableData(String tableDataToRemove,String userId) {
		return webUsageService.removeTableData(tableDataToRemove,userId);
	}


	@Override
	public StatusInfo checkLogin(RegisterUser registerUser) {
		return webUsageService.checkLogin(registerUser);
	}


	@Override
	public StatusInfo doRegistration(RegisterUser registerUser) {
		return webUsageService.doRegistration(registerUser);
	}


	@Override
	public List<GoogleResult> retriveGoogleResultsAndDoUserProfiling(
			String query, String userId) {
		return webUsageService.retriveGoogleResultsAndDoUserProfiling(query,userId);
	}


	@Override
	public List<SearchObj> retriveUserSearches(String userId) {
		return webUsageService.retriveUserSearches(userId);
	}


	@Override
	public List<SearchObj> retriveCleanReviewList(String userId) {
		return webUsageService.retriveCleanReviewList(userId);
	}


	@Override
	public StatusInfo doTokens(String userId) {
		return webUsageService.doTokens(userId);
	}


	@Override
	public List<TokenVO> retriveTokenList(String userId) {
		return webUsageService.retriveTokenList(userId);
	}


	@Override
	public StatusInfo doFrequency(String userId) {
		return webUsageService.doFrequency(userId);
	}


	@Override
	public StatusInfo doFeatureVector(String userId) {
		return webUsageService.doFeatureVector(userId);
	}


	@Override
	public List<FeatureVO> retriveFeatureVectorList(String userId) {
		return webUsageService.retriveFeatureVectorList(userId);
	}


	@Override
	public List<FrequencyVO> retriveFreqInfo(String userId) {
		return webUsageService.retriveFreqInfo(userId);
	}


	@Override
	public List<SearchObj> retriveOfflineSearch(String query, String userId) {
		return webUsageService.retriveOfflineSearch(query,userId);
	}


	@Override
	public StatusInfo storeSettings(int threshold, String loginId) {
		return webUsageService.storeSettings(threshold,loginId);
	}


	@Override
	public List<ConceptsSearch> conceptResults(String userId) {
		return webUsageService.conceptResults(userId);
	}




	

}
