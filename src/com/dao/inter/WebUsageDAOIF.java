package com.dao.inter;

import java.util.List;

import com.model.BestFeatureVector;
import com.model.FeatureVO;
import com.model.FrequencyVO;
import com.model.GoogleResult;
import com.model.RegisterUser;
import com.model.ReviewModel;
import com.model.SearchObj;
import com.model.StatusInfo;
import com.model.StopWordsVO;
import com.model.TokenVO;

public interface WebUsageDAOIF {

	public List<String> retriveStopWordsOnly();

	public StatusInfo insertStopWord(String stopWord);

	public List<StopWordsVO> retriveStopWords();

	public StatusInfo deleteAllCleanReviews();

	public StatusInfo insertToken(TokenVO tokenVO);

	public StatusInfo deleteAllTokens(String userId);

	public StatusInfo insertFrequency(FrequencyVO freqVO);

	public List<FeatureVO> retriveAllFeatureVector(String userId);

	public StatusInfo insertFeatureVector(FeatureVO featureVO);

	public List<FrequencyVO> retriveFreqInfo(String userId);

	public StatusInfo removeStopword(String stopWord);

	public StatusInfo removeReviews(String userId);

	public StatusInfo removeCleanReviews(String userId);

	public StatusInfo removeFrequency(String userId);

	public StatusInfo removeFV(String userId);

	public StatusInfo removeBestFV(String userId);

	public StatusInfo removeTokens(String userId);

	public List<String> retriveUserIds();

	public String retrivePassword(String userId);

	StatusInfo insertLogin(RegisterUser registerUser);

	public StatusInfo insertGoogleResult(SearchObj searchObj);

	public List<SearchObj> retriveSearchesForUserId(String userId);

	public List<SearchObj> retriveCleanReviews(String userId);

	public StatusInfo insertCleanDetails(SearchObj searchObj);

	public StatusInfo deleteAllCleanReviews(String userId);

	List<TokenVO> retriveAllTokens(String userId);

	StatusInfo insertReview(ReviewModel reviewModel);

	List<ReviewModel> retriveAllReviews();

	public StatusInfo deleteFromFrequency(String userId);

	public List<String> retriveQueryForUserIdFromTokenization(String userId);

	public List<String> retriveUrlsForQueryAndUserId(String queryTemp,
			String userId);

	public List<String> retriveTokensFromTokensForUrlQueryAndUserId(String url,
			String queryTemp, String userId);

	int retriveCountForTokenQueryURLUserId(String tokenTemp, String queryTemp,
			String url, String userId);

	public List<TokenVO> retriveTokenInfoFromTokensForUrlQueryAndUserId(
			String url, String queryTemp, String userId);

	public TokenVO retriveTokenInfoForTokenNameUrlueryUserId(String tokenTemp,
			String url, String queryTemp, String userId);

	public StatusInfo deleteAllFeatureVector(String userId);

	public List<String> retriveQueryForUserIdFromFV(String userId);

	public List<String> retriveUrlsForQueryAndUserIdFromFreq(String queryTemp,
			String userId);

	public List<String> retriveDistinctUrlsForQueryAndUserIdFromFreq(
			String queryTemp, String userId);

	public List<String> retriveDistinctQueryForUserIdFromFV(String userId);

	public List<String> retriveDistinctTokensFromFreqForQueryUrlAndUserId(
			String queryTemp, String url, String userId);

	public int retriveCountOfUrlsInWhichTokenIsPresent(String queryTemp,
			String url, String tokenName, String userId);

	public FrequencyVO retriveFreqForTokenNameQueryURLUserId(String queryTemp,
			String url, String tokenName, String userId);

	public List<String> retriveDistinctUrlsForUserIdFromFeatureVector(
			String userId);

	public List<Double> retriveFVForUrlTokenAndUserId(String distinctUrlTemp,
			String tokenTemp, String userId);

	public StatusInfo deleteFromBestFV();

	public StatusInfo insertBestFV(List<BestFeatureVector> bestFV);

	public List<BestFeatureVector> rateUrls();

	SearchObj retriveGoogleResultForUrl(BestFeatureVector urlInfo, String userId);

	public List<String> retriveUsersFromSettings();

	public StatusInfo insertSettings(int threshold, String loginId);

	public StatusInfo updateSettings(int threshold, String loginId);

	public int retriveSettingsForUserId(String userId);

	public List<String> retriveConceptsForUserId(String userId, int settings);

	public List<String> retriveDistinctUrlsForUserId(String userId);

	public List<String> retriveTokensFromTokensForUrlAndUserId(String url,
			String userId);

	public int retriveCountForTokenURLUserId(String tokenTemp, String url,
			String userId);

	public TokenVO retriveTokenInfoForTokenNameUrlUserId(String tokenTemp,
			String url, String userId);

	public List<String> retriveDistinctUrlsForUserIdFromFrequency(String userId);

	public List<String> retriveDistinctTokensFromFreqForUrlAndUserId(
			String url, String userId);

	public int retriveCountOfUrlsInWhichTokenIsPresent(
			String tokenName, String userId);

	public FrequencyVO retriveFreqForURLAndTokenNameAndUserId(String url,
			String tokenName, String userId);   

}
