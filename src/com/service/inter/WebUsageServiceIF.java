package com.service.inter;

import java.util.List;

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

public interface WebUsageServiceIF {

	public StatusInfo storeReviews(ReviewModel reviewModel);
	public StatusInfo addStopword(String stopWord);
	public List<StopWordsVO> retriveStopWords();
	public boolean storeStopWordsFromTxtFile(String stopword);
	
	public List<FeatureVO> retriveFeatureVectorList(String userId); 
	public List<FrequencyVO> retriveFreqInfo(String userId); 
	public StatusInfo removeStopword(String stopWord); 
	public StatusInfo removeTableData(String tableDataToRemove, String userId); 
	public StatusInfo checkLogin(RegisterUser registerUser);
	StatusInfo doRegistration(RegisterUser registerUser);
	public List<GoogleResult> retriveGoogleResultsAndDoUserProfiling(
			String query, String userId);
	public List<SearchObj> retriveUserSearches(String userId);
	public StatusInfo doDataCleaning(String userId); 
	List<SearchObj> retriveCleanReviewList(String userId);
	StatusInfo doTokens(String userId);
	List<TokenVO> retriveTokenList(String userId);
	StatusInfo doFrequency(String userId);
	StatusInfo doFeatureVector(String type);
	public List<SearchObj> retriveOfflineSearch(String query, String userId);
	public StatusInfo storeSettings(int threshold, String loginId);
	public List<ConceptsSearch> conceptResults(String userId);                  
	
}
