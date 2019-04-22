package com.delegate.inter;

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

public interface WebUsageDelegateIF {

	public StatusInfo storeReviews(ReviewModel reviewModel);

	public StatusInfo addStopword(String stopWord);

	public List<StopWordsVO> retriveStopWords();

	public StatusInfo removeStopword(String stopWord);

	public StatusInfo removeTableData(String tableDataToRemove, String userId);

	public StatusInfo checkLogin(RegisterUser registerUser);

	public StatusInfo doRegistration(RegisterUser registerUser);

	public List<GoogleResult> retriveGoogleResultsAndDoUserProfiling(
			String query, String userId);

	public List<SearchObj> retriveUserSearches(String userId);

	public StatusInfo doDataCleaning(String userId);

	public List<SearchObj> retriveCleanReviewList(String userId);

	public StatusInfo doTokens(String userId);

	public List<TokenVO> retriveTokenList(String userId);

	public StatusInfo doFrequency(String userId);

	public StatusInfo doFeatureVector(String userId);

	public List<FeatureVO> retriveFeatureVectorList(String userId);

	public List<FrequencyVO> retriveFreqInfo(String userId);

	public List<SearchObj> retriveOfflineSearch(String query, String userId);

	public StatusInfo storeSettings(int threshold, String loginId);

	public List<ConceptsSearch> conceptResults(String userId);  

}
