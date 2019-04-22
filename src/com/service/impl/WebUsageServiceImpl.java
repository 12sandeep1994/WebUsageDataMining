package com.service.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import com.constants.WebUsageConstantsIF;
import com.dao.inter.WebUsageDAOIF;
import com.model.BestFeatureVector;
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

public class WebUsageServiceImpl implements WebUsageServiceIF {

	@Autowired
	private WebUsageDAOIF webUsageDao;

	public WebUsageDAOIF getWebUsageDao() {
		return webUsageDao;
	}

	public void setWebUsageDao(WebUsageDAOIF webUsageDao) {
		this.webUsageDao = webUsageDao;
	}

	@Override
	public StatusInfo storeReviews(ReviewModel reviewModel) {

		StatusInfo statusInfo = null;
		try {

			statusInfo = webUsageDao.insertReview(reviewModel);

		} catch (Exception e) {
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setExceptionMsg(e.getMessage());
			return statusInfo;
		}
		return statusInfo;
	}

	public String getTextDivData(String url) {

		StringBuilder stringBuilder = new StringBuilder();
		try {
			Document doc;
			doc = Jsoup.connect(url).get();
			Elements links = doc.select("div");
			for (Element link : links) {

				String linkText = link.text();
				stringBuilder.append(linkText);
				stringBuilder.append(WebUsageConstantsIF.CONSTANTS.SPACE);
			}
		} catch (Exception e) {
			System.out.println("EXCEPTION--->" + e);
		}
		return stringBuilder.toString();

	}

	/*
	 * public static void main(String s[]) { try {
	 * ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
	 * "review.xml"); ReviewServiceIF reviewServiceIF = (ReviewServiceIF) ctx
	 * .getBean("reviewServiceBean");
	 * 
	 * List<PolarityModel> rerviewModel=reviewServiceIF.retrivePolarity(1);
	 * 
	 * for(PolarityModel rerviewModelTemp:rerviewModel) {
	 * System.out.println("VALUE"+rerviewModelTemp.getNegativeRating()); }
	 * 
	 * 
	 * } catch (Exception e) { e.printStackTrace();
	 * System.out.println("EXCEPTION--->" + e); } }
	 */

	@Override
	public StatusInfo addStopword(String stopWord) {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();

			List<String> keyWordList = webUsageDao.retriveStopWordsOnly();

			if (null == keyWordList || keyWordList.isEmpty()) {
				statusInfo = webUsageDao.insertStopWord(stopWord);
				statusInfo.setStatus(true);
				return statusInfo;
			}

			if (keyWordList.contains(stopWord)) {
				statusInfo
						.setErrMsg(WebUsageConstantsIF.Message.STOPWORD_EXIST);
				statusInfo.setStatus(false);
				return statusInfo;
			} else {
				statusInfo = webUsageDao.insertStopWord(stopWord);
				statusInfo.setStatus(true);
				return statusInfo;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			statusInfo = new StatusInfo();
			statusInfo.setErrMsg(e.getMessage());
			statusInfo.setStatus(false);
			return statusInfo;
		}
	}

	@Override
	public List<StopWordsVO> retriveStopWords() {
		List<StopWordsVO> stopWordList = null;
		try {
			stopWordList = webUsageDao.retriveStopWords();
			if (null == stopWordList) {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			return null;
		}
		return stopWordList;
	}

	public boolean storeStopWordsFromTxtFile(String fileLocation) {

		boolean intial = false;
		BufferedReader reader1;
		try {
			reader1 = new BufferedReader(new InputStreamReader(
					new FileInputStream(fileLocation)));
			String line1;
			line1 = reader1.readLine();
			while (line1 != null) {
				System.out.println("Line1" + line1);
				StringTokenizer tok1 = new StringTokenizer(line1, " ");
				String str1;

				while (tok1.hasMoreTokens()) {
					str1 = tok1.nextToken();
					webUsageDao.insertStopWord(str1);
				}
				line1 = reader1.readLine();

				reader1.close();
			}
			intial = false;
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			return false;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
		return intial;
	}

	@Override
	public StatusInfo doDataCleaning(String userId) {

		StatusInfo statusInfo = null;
		try {

			statusInfo = new StatusInfo();

			// Remove All Clean Reviews
			statusInfo = webUsageDao.deleteAllCleanReviews(userId);
			if (!statusInfo.isStatus()) {
				return statusInfo;
			}

			List<SearchObj> reviewList = retriveUserSearches(userId);

			List<String> stopWordsList = webUsageDao.retriveStopWordsOnly();

			for (SearchObj reviewModelObj : reviewList) {

				String desc = reviewModelObj.getDesc();
				String str = desc.replaceAll("\\s", " ");

				String descCleanTemp = str.replaceAll(
						WebUsageConstantsIF.Keys.STOPWORDS_SYMBOL,
						WebUsageConstantsIF.Keys.SPACE);

				StringTokenizer tok1 = new StringTokenizer(descCleanTemp);
				StringBuilder buffer = new StringBuilder();
				String str1 = null;
				while (tok1.hasMoreTokens()) {
					str1 = (String) tok1.nextElement();
					str1 = str1.toLowerCase();

					if (null == str1 || str1.isEmpty() || str1.length() <= 0
							|| str1.trim().length() == 0) {
						continue;
					}
					if (str1 != null) {
						str1 = str1.trim();
					}
					if (stopWordsList.contains(str1)) {
						continue;
					} else {
						str1 = str1.replaceAll(
								WebUsageConstantsIF.Keys.STOPWORDS_SYMBOL,
								WebUsageConstantsIF.Keys.SPACE);
						str1 = str1.trim();
						if (str1.length() > 0) {
							buffer.append(WebUsageConstantsIF.Keys.SPACE);
							buffer.append(str1);
						}
						System.out.println("BUFFER" + buffer);
					}
				}

				// Now Create an Object of Clean Review

				SearchObj searchObj = new SearchObj();

				searchObj.setHiddenUrl(reviewModelObj.getHiddenUrl());
				searchObj.setUrl(reviewModelObj.getUrl());
				searchObj.setQuery(reviewModelObj.getQuery());
				searchObj.setTitle(reviewModelObj.getTitle());
				searchObj.setDesc(buffer.toString());
				searchObj.setUserId(userId);
				searchObj.setHiddenUrl(reviewModelObj.getHiddenUrl());

				statusInfo = webUsageDao.insertCleanDetails(searchObj);

				if (!statusInfo.isStatus()) {
					statusInfo.setStatus(false);
					statusInfo
							.setErrMsg(WebUsageConstantsIF.Message.CLEANMODEL_FAILED);
					return statusInfo;
				}

			}
			statusInfo.setStatus(true);
			return statusInfo;
			// end of for Loop

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception " + e.getMessage());
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;

		}

	}

	@Override
	public List<SearchObj> retriveCleanReviewList(String userId) {
		List<SearchObj> cleanReviewUIList = null;
		try {

			cleanReviewUIList = webUsageDao.retriveCleanReviews(userId);

			if (null == cleanReviewUIList) {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			return null;
		}
		return cleanReviewUIList;
	}

	@Override
	public StatusInfo doTokens(String userId) {
		StatusInfo statusInfo = null;
		try {

			statusInfo = new StatusInfo();

			// Remove All Tokens
			statusInfo = webUsageDao.deleteAllTokens(userId);
			if (!statusInfo.isStatus()) {
				return statusInfo;
			}

			List<SearchObj> searchObjList = retriveCleanReviewList(userId);

			if (null == searchObjList || searchObjList.isEmpty()) {
				statusInfo.setStatus(false);
				statusInfo
						.setErrMsg(WebUsageConstantsIF.Message.CLEANREVIEWS_EMPTY);
				return statusInfo;
			}

			for (SearchObj searchObj : searchObjList) {

				StringTokenizer tok1 = new StringTokenizer(searchObj.getDesc(),
						WebUsageConstantsIF.Keys.SPACE);
				String tokenName;
				while (tok1.hasMoreTokens()) {
					tokenName = tok1.nextToken();
					tokenName = tokenName.toLowerCase();

					TokenVO tokenVO = new TokenVO();
					tokenVO.setTokenName(tokenName);
					tokenVO.setDesc(searchObj.getDesc());
					tokenVO.setHiddenUrl(searchObj.getHiddenUrl());
					tokenVO.setQuery(searchObj.getQuery());
					tokenVO.setTitle(searchObj.getTitle());
					tokenVO.setUrl(searchObj.getUrl());
					tokenVO.setUserId(userId);

					statusInfo = webUsageDao.insertToken(tokenVO);
					if (!statusInfo.isStatus()) {
						statusInfo.setStatus(false);
						statusInfo
								.setErrMsg(WebUsageConstantsIF.Message.INSERT_TOKENS_FAILED);
						return statusInfo;
					}

				}
				// end of for Loop
			}
			statusInfo.setStatus(true);
			return statusInfo;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception " + e.getMessage());
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;

		}
	}

	@Override
	public List<TokenVO> retriveTokenList(String userId) {
		List<TokenVO> tokenVOList = null;
		try {
			tokenVOList = webUsageDao.retriveAllTokens(userId);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception " + e.getMessage());
			return null;
		}
		return tokenVOList;
	}

	@Override
	public StatusInfo doFrequency(String userId) {

		StatusInfo statusInfo = null;
		try {
			// obtain a list of data from tokens
			List<TokenVO> tokensList = webUsageDao.retriveAllTokens(userId);

			if (null == tokensList || tokensList.size() == 0) {

				statusInfo = new StatusInfo();
				statusInfo.setStatus(false);
				statusInfo.setErrMsg(WebUsageConstantsIF.Message.TOKENS_EMPTY);
				return statusInfo;

			} else {

				/*
				 * Remove existing data
				 */
				statusInfo = webUsageDao.deleteFromFrequency(userId);

				if (!statusInfo.isStatus()) {
					statusInfo = new StatusInfo();
					statusInfo.setStatus(false);
					statusInfo
							.setErrMsg(WebUsageConstantsIF.Message.COULDNOT_DELETE_FREQUENCY);
					return statusInfo;
				} else {

					List<String> urls = webUsageDao
							.retriveDistinctUrlsForUserId(userId);

					for (String url : urls) {
						List<String> tokenNames = webUsageDao
								.retriveTokensFromTokensForUrlAndUserId(url,
										userId);
						for (String tokenTemp : tokenNames) {

							int count = webUsageDao
									.retriveCountForTokenURLUserId(tokenTemp,
											url, userId);

							FrequencyVO freqVO = new FrequencyVO();

							TokenVO tokenVO = webUsageDao
									.retriveTokenInfoForTokenNameUrlUserId(
											tokenTemp, url, userId);

							freqVO.setDesc(tokenVO.getDesc());
							freqVO.setFreq(count);
							freqVO.setHiddenUrl(tokenVO.getHiddenUrl());
							freqVO.setQuery(tokenVO.getQuery());
							freqVO.setTitle(tokenVO.getTitle());
							freqVO.setTokenName(tokenTemp);
							freqVO.setUrl(tokenVO.getUrl());
							freqVO.setUserId(tokenVO.getUserId());

							webUsageDao.insertFrequency(freqVO);

						}

					}

				}// End
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("MESSAGE EXCEPTION" + e.getMessage());
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;
		}
		statusInfo = new StatusInfo();
		statusInfo.setStatus(true);
		statusInfo
				.setErrMsg(WebUsageConstantsIF.Message.FREQ_COMPUTATION_SUCESS);
		return statusInfo;
	}

	@Override
	public StatusInfo doFeatureVector(String userId) {

		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();

			statusInfo = webUsageDao.deleteAllFeatureVector(userId);
			if (!statusInfo.isStatus()) {
				statusInfo.setStatus(false);
				statusInfo.setErrMsg(WebUsageConstantsIF.Message.DELETE_FV);
				return statusInfo;
			}

			// Now get the distinct queries
			List<String> urls = webUsageDao
					.retriveDistinctUrlsForUserIdFromFrequency(userId);

			for (String url : urls) {

				List<String> tokenNames = webUsageDao
						.retriveDistinctTokensFromFreqForUrlAndUserId(url,
								userId);

				for (String tokenName : tokenNames) {

					// Count of URLS
					int countOfUrls = webUsageDao
							.retriveCountOfUrlsInWhichTokenIsPresent(tokenName,
									userId);

					FrequencyVO frequencyVO = webUsageDao
							.retriveFreqForURLAndTokenNameAndUserId(url,
									tokenName, userId);

					int freq = frequencyVO.getFreq();

					if (countOfUrls <= 0) {
						countOfUrls = 1;
					}

					double idftTemp = ((double) freq / (double) countOfUrls);

					double idft = 10 * Math.log(idftTemp);

					if (idft < 0) {
						idft = idft * -1;
					}

					double featureVector = idft * freq;

					FeatureVO featureVO = new FeatureVO();

					featureVO.setFreq(frequencyVO.getFreq());
					featureVO.setNoOfReviews(countOfUrls);
					featureVO.setHiddenUrl(frequencyVO.getHiddenUrl());
					featureVO.setQuery(frequencyVO.getQuery());
					featureVO.setTokenName(frequencyVO.getTokenName());
					featureVO.setUrl(frequencyVO.getUrl());
					featureVO.setUserId(frequencyVO.getUserId());
					featureVO.setDesc(frequencyVO.getDesc());
					featureVO.setIdft(idft);
					featureVO.setTitle(frequencyVO.getTitle());
					featureVO.setFeatureVector(featureVector);

					statusInfo = webUsageDao.insertFeatureVector(featureVO);
					if (!statusInfo.isStatus()) {
						statusInfo.setStatus(false);
						statusInfo
								.setErrMsg(WebUsageConstantsIF.Message.FEATURE_VECTOR_EMPTY);
						return statusInfo;
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("MESSAGE EXCEPTION" + e.getMessage());
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;
		}
		return statusInfo;

	}

	@Override
	public List<FeatureVO> retriveFeatureVectorList(String userId) {
		List<FeatureVO> featureVectorList = null;
		try {
			featureVectorList = webUsageDao.retriveAllFeatureVector(userId);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception " + e.getMessage());
			return null;
		}
		return featureVectorList;
	}

	@Override
	public List<FrequencyVO> retriveFreqInfo(String userId) {
		List<FrequencyVO> freqVOList = null;
		try {
			freqVOList = webUsageDao.retriveFreqInfo(userId);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception " + e.getMessage());
			return null;
		}
		return freqVOList;
	}

	@Override
	public StatusInfo removeStopword(String stopWord) {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();

			List<String> keyWordList = webUsageDao.retriveStopWordsOnly();

			if (!keyWordList.contains(stopWord)) {
				statusInfo
						.setErrMsg(WebUsageConstantsIF.Message.NO_STOPWORD_EXIST);
				statusInfo.setStatus(false);
				return statusInfo;
			} else {
				statusInfo = webUsageDao.removeStopword(stopWord);
				statusInfo.setStatus(true);
				return statusInfo;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			statusInfo = new StatusInfo();
			statusInfo.setErrMsg(e.getMessage());
			statusInfo.setStatus(false);
			return statusInfo;
		}
	}

	@Override
	public StatusInfo removeTableData(String tableDataToRemove, String userId) {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();

			if (null == tableDataToRemove || tableDataToRemove.isEmpty()) {
				statusInfo.setStatus(false);
				statusInfo.setErrMsg(WebUsageConstantsIF.Message.EMPTY_TABLE);

				return statusInfo;
			}

			if (tableDataToRemove.equals(WebUsageConstantsIF.CONSTANTS.REVIEW)) {
				statusInfo = webUsageDao.removeReviews(userId);
			}

			if (tableDataToRemove
					.equals(WebUsageConstantsIF.CONSTANTS.CLEANREVIEW)) {
				statusInfo = webUsageDao.removeCleanReviews(userId);
			}

			if (tableDataToRemove.equals(WebUsageConstantsIF.CONSTANTS.TOKENS)) {
				statusInfo = webUsageDao.removeTokens(userId);
			}

			if (tableDataToRemove
					.equals(WebUsageConstantsIF.CONSTANTS.FREQUENCY)) {
				statusInfo = webUsageDao.removeFrequency(userId);
			}

			if (tableDataToRemove.equals(WebUsageConstantsIF.CONSTANTS.FV)) {
				statusInfo = webUsageDao.removeFV(userId);
			}

			if (tableDataToRemove.equals(WebUsageConstantsIF.CONSTANTS.BESTFV)) {
				statusInfo = webUsageDao.removeBestFV(userId);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			statusInfo = new StatusInfo();
			statusInfo.setErrMsg(e.getMessage());
			statusInfo.setStatus(false);
			return statusInfo;
		}
		return statusInfo;
	}

	@Override
	public StatusInfo checkLogin(RegisterUser registerUser) {
		StatusInfo statusInfo = null;

		try {
			statusInfo = new StatusInfo();
			System.out.println("Inside Verify Login Service");
			boolean status = checkUserInformation(registerUser.getUserId());
			if (!status) {
				statusInfo
						.setErrMsg(WebUsageConstantsIF.Message.NO_USER_EXISTS);
				statusInfo.setStatus(false);
				return statusInfo;
			} else {
				String password = webUsageDao.retrivePassword(registerUser
						.getUserId());

				if (null == password || password.isEmpty()) {
					statusInfo
							.setErrMsg(WebUsageConstantsIF.Message.PASSWORD_WRONG);
					statusInfo.setStatus(false);
					return statusInfo;
				}
				if (!password.equals(registerUser.getUserPassword())) {
					statusInfo
							.setErrMsg(WebUsageConstantsIF.Message.PASSWORD_WRONG);
					statusInfo.setStatus(false);
					return statusInfo;
				}
				if (password.equals(registerUser.getUserPassword())) {
					statusInfo
							.setErrMsg(WebUsageConstantsIF.Message.USER_LOGIN_SUCESS);
					statusInfo.setStatus(true);
					return statusInfo;
				}
			}
		} catch (Exception e) {
			statusInfo = new StatusInfo();
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			return statusInfo;

		}
		return statusInfo;
	}

	private boolean checkUserInformation(String registerUser) {
		try {
			List<String> userNameList = webUsageDao.retriveUserIds();

			System.out.println("LIST" + userNameList);
			if (null == userNameList || userNameList.isEmpty()
					|| userNameList.size() == 0) {
				return false;
			}
			if (userNameList.contains(registerUser)) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			return true;
		}
	}

	@Override
	public StatusInfo doRegistration(RegisterUser registerUser) {

		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();

			List<String> userIdList = webUsageDao.retriveUserIds();
			if (null == userIdList) {
				statusInfo = webUsageDao.insertLogin(registerUser);

				if (!statusInfo.isStatus()) {
					statusInfo
							.setErrMsg(WebUsageConstantsIF.Message.INTERNAL_ERROR);
					statusInfo.setStatus(false);
					return statusInfo;
				} else {
					return statusInfo;
				}

			}

			if (userIdList.contains(registerUser.getUserId())) {
				statusInfo
						.setErrMsg(WebUsageConstantsIF.Message.USERALREADY_EXIST);
				statusInfo.setStatus(false);
				return statusInfo;
			} else {
				statusInfo = webUsageDao.insertLogin(registerUser);

				if (!statusInfo.isStatus()) {
					statusInfo
							.setErrMsg(WebUsageConstantsIF.Message.INTERNAL_ERROR);
					statusInfo.setStatus(false);
					return statusInfo;
				} else {
					return statusInfo;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			statusInfo = new StatusInfo();
			statusInfo.setErrMsg(WebUsageConstantsIF.Message.INTERNAL_ERROR);
			statusInfo.setStatus(false);
			return statusInfo;

		}

	}

	@Override
	public List<GoogleResult> retriveGoogleResultsAndDoUserProfiling(
			String query, String userId) {

		List<GoogleResult> googleResultList = null;
		try {
			googleResultList = new ArrayList<GoogleResult>();

			if (null == query || query.isEmpty() || query.trim().isEmpty()) {
				return null;
			}

			if (null == userId || userId.isEmpty() || userId.trim().isEmpty()) {
				return null;
			}

			googleResultList = GoogleHelper.getGoogleResults(query);

			// Storing the Google Results
			if (googleResultList != null && !googleResultList.isEmpty()) {
				for (GoogleResult result : googleResultList) {
					SearchObj searchObj = new SearchObj();

					searchObj.setUserId(userId);
					searchObj.setQuery(query);
					searchObj.setDesc(result.getDesc());
					searchObj.setHiddenUrl(result.getHiddenUrl());
					searchObj.setUrl(result.getUrl());
					searchObj.setTitle(result.getTitle());

					StatusInfo statusInfo = webUsageDao
							.insertGoogleResult(searchObj);

					if (!statusInfo.isStatus()) {
						return null;
					}

				}
			}
		} catch (Exception e) {
			System.out.println("Exception " + e.getMessage());
			e.printStackTrace();
			return null;
		}
		return googleResultList;
	}

	@Override
	public List<SearchObj> retriveUserSearches(String userId) {

		List<SearchObj> searchObjResults = null;
		try {
			searchObjResults = new ArrayList<SearchObj>();

			if (null == userId || (userId != null && userId.isEmpty())) {
				return null;
			}

			searchObjResults = webUsageDao.retriveSearchesForUserId(userId);

		} catch (Exception e) {

			System.out.println("Exception " + e.getMessage());
			e.printStackTrace();
			return null;

		}
		return searchObjResults;
	}

	@Override
	public List<SearchObj> retriveOfflineSearch(String query, String userId) {

		List<SearchObj> googleResultList = null;
		try {
			googleResultList = new ArrayList<SearchObj>();

			if (null == query || query.isEmpty() || query.trim().isEmpty()) {
				return null;
			}

			if (null == userId || userId.isEmpty() || userId.trim().isEmpty()) {
				return null;
			}

			String tokens[] = query.split(" ");

			List<String> distinctUrls = webUsageDao
					.retriveDistinctUrlsForUserIdFromFeatureVector(userId);

			List<BestFeatureVector> bestFV = new ArrayList<BestFeatureVector>();
			for (String distinctUrlTemp : distinctUrls) {

				double totalFV = 0;
				for (String tokenTemp : tokens) {
					double fvdouble = 0.0;
					List<Double> featureVector = webUsageDao
							.retriveFVForUrlTokenAndUserId(distinctUrlTemp,
									tokenTemp, userId);

					if (null == featureVector) {
						fvdouble = 0;
					} else {
						for (Double fv1 : featureVector) {
							fvdouble = fvdouble + fv1;
						}
					}

					totalFV = totalFV + fvdouble;
				}

				BestFeatureVector bfv = new BestFeatureVector();
				bfv.setFeatureVector(totalFV);
				bfv.setUrl(distinctUrlTemp);
				bestFV.add(bfv);
			}

			// Now Insert the Best FV
			StatusInfo st = webUsageDao.deleteFromBestFV();
			if (!st.isStatus()) {
				return null;
			}

			// Now Insert Into Best FV
			StatusInfo statusInfo = webUsageDao.insertBestFV(bestFV);

			if (!statusInfo.isStatus()) {
				return null;
			}

			List<BestFeatureVector> urlList = webUsageDao.rateUrls();

			if (null == urlList) {
				return null;
			}

			for (BestFeatureVector urlInfo : urlList) {
				SearchObj googleResult = webUsageDao.retriveGoogleResultForUrl(
						urlInfo, userId);
				googleResult.setFeatureVector(urlInfo.getFeatureVector());

				googleResultList.add(googleResult);
			}

		} catch (Exception e) {
			System.out.println("Exception " + e.getMessage());
			e.printStackTrace();
			return null;
		}
		return googleResultList;
	}

	@Override
	public StatusInfo storeSettings(int threshold, String loginId) {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();

			List<String> userIdList = webUsageDao.retriveUserIds();

			if (null == userIdList) {
				statusInfo
						.setErrMsg(WebUsageConstantsIF.Message.SESSION_INVALID);
				statusInfo.setStatus(false);
				return statusInfo;
			}

			List<String> settingsUser = webUsageDao.retriveUsersFromSettings();
			if (null == settingsUser || settingsUser.isEmpty()) {
				statusInfo = webUsageDao.insertSettings(threshold, loginId);
				return statusInfo;
			}

			if (settingsUser.contains(loginId)) {
				statusInfo = webUsageDao.updateSettings(threshold, loginId);
				return statusInfo;
			} else {
				statusInfo = webUsageDao.insertSettings(threshold, loginId);
				return statusInfo;
			}

		} catch (Exception e) {
			e.printStackTrace();
			statusInfo = new StatusInfo();
			statusInfo.setErrMsg(WebUsageConstantsIF.Message.INTERNAL_ERROR);
			statusInfo.setStatus(false);
			return statusInfo;

		}
	}

	@Override
	public List<ConceptsSearch> conceptResults(String userId) {

		List<ConceptsSearch> conceptSearchList = null;
		try {
			conceptSearchList = new ArrayList<ConceptsSearch>();

			List<String> settingsUser = webUsageDao.retriveUsersFromSettings();
			if (null == settingsUser || settingsUser.isEmpty()) {
				return null;
			}

			if (!settingsUser.contains(userId)) {
				return null;
			}

			int count = webUsageDao.retriveSettingsForUserId(userId);

			if (count <= 0) {
				return null;
			}

			List<String> diffrentConcepts = webUsageDao
					.retriveConceptsForUserId(userId, count);

			for (String concept : diffrentConcepts) {
				ConceptsSearch conceptsSearch = new ConceptsSearch();
				conceptsSearch.setConcept(concept);

				List<SearchObj> searchList = retriveOfflineSearch(concept,
						userId);

				conceptsSearch.setSearchList(searchList);

				conceptSearchList.add(conceptsSearch);
			}

		} catch (Exception e) {
			e.printStackTrace();
			conceptSearchList = null;
		}
		return conceptSearchList;

	}

}
