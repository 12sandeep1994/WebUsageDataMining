package com.controller.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.constants.WebUsageConstantsIF;
import com.controller.inter.WebUsageControllerIF;
import com.delegate.inter.WebUsageDelegateIF;
import com.model.AJAXResponse;
import com.model.ConceptsSearch;
import com.model.FeatureVO;
import com.model.FrequencyVO;
import com.model.GoogleResult;
import com.model.Message;
import com.model.RegisterUser;
import com.model.SearchObj;
import com.model.StatusInfo;
import com.model.StopWordsVO;
import com.model.TokenVO;

@Controller
public class WebUsageControllerImpl implements WebUsageControllerIF {

	@Autowired
	private WebUsageDelegateIF webUsageDelegate;

	public WebUsageDelegateIF getWebUsageDelegate() {
		return webUsageDelegate;
	}

	public void setWebUsageDelegate(WebUsageDelegateIF webUsageDelegate) {
		this.webUsageDelegate = webUsageDelegate;
	}

	@Override
	@RequestMapping(value = "/addStopword.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView addStopWord(String stopWord) {
		AJAXResponse ajaxRes = null;
		try {
			ajaxRes = new AJAXResponse();
			if (null == stopWord || stopWord.isEmpty()) {
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg.setMsg(WebUsageConstantsIF.Message.EMPTY_STOPWORD);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						WebUsageConstantsIF.Views.STOPWORD_INPUT,
						WebUsageConstantsIF.Keys.OBJ, ajaxRes);

			}
			StatusInfo statusInfo = webUsageDelegate.addStopword(stopWord);
			if (!statusInfo.isStatus()) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(WebUsageConstantsIF.Message.STOPWORD_ADD_FAILED);

				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						WebUsageConstantsIF.Views.STOPWORD_INPUT,
						WebUsageConstantsIF.Keys.OBJ, ajaxRes);

			} else {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(WebUsageConstantsIF.Message.STOPWORD_ADD_SUCESS);

				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						WebUsageConstantsIF.Views.VIEW_ADMIN_SUCESS_PAGE,
						WebUsageConstantsIF.Keys.OBJ, ajaxRes);
			}

		} catch (Exception e) {
			ajaxRes = new AJAXResponse();
			List<Message> ebErrors = new ArrayList<Message>();
			ajaxRes.setStatus(false);
			Message webSiteUrlMsg = new Message();
			webSiteUrlMsg.setMsg(WebUsageConstantsIF.Message.INTERNAL_ERROR);

			ebErrors.add(webSiteUrlMsg);
			ajaxRes.setEbErrors(ebErrors);
			return new ModelAndView(WebUsageConstantsIF.Views.STOPWORD_INPUT,
					WebUsageConstantsIF.Keys.OBJ, ajaxRes);
		}
	}

	@Override
	@RequestMapping(value = "/viewStopwords.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody AJAXResponse viewStopWords(HttpServletRequest request) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			List<StopWordsVO> keyWordList = webUsageDelegate.retriveStopWords();
			if (null == keyWordList) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(WebUsageConstantsIF.Message.EMPTY_STOPWORDS);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(keyWordList);
			ajaxResponse.setMessage(WebUsageConstantsIF.Message.STOPWORD_SUCESS);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message();
			msg.setMsg(WebUsageConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}

	@RequestMapping(value = "/cleandata.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	@Override
	public ModelAndView doDataCleaning(HttpServletRequest request,
			@RequestParam String type) {
		try {

			AJAXResponse ajaxRes = new AJAXResponse();

			HttpSession session = request.getSession(false);

			if (session == null) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(WebUsageConstantsIF.Message.SESSION_EXPIRED);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						WebUsageConstantsIF.Views.VIEW_LOGIN_INPUT,
						WebUsageConstantsIF.Keys.OBJ, ajaxRes);
			}

			// Obtain User Id from Session

			String userId = (String) session
					.getAttribute(WebUsageConstantsIF.Keys.LOGINID_SESSION);

			if (userId == null || userId.isEmpty()) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(WebUsageConstantsIF.Message.SESSION_EXPIRED);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						WebUsageConstantsIF.Views.VIEW_LOGIN_INPUT,
						WebUsageConstantsIF.Keys.OBJ, ajaxRes);

			}

			StatusInfo statusInfo = webUsageDelegate.doDataCleaning(userId);

			if (!statusInfo.isStatus()) {
				AJAXResponse ajax = new AJAXResponse();
				ajax.setStatus(false);
				Message msg = new Message();
				msg.setMsg(statusInfo.getErrMsg());
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajax.setEbErrors(ebErrors);
				return new ModelAndView(
						WebUsageConstantsIF.Views.VIEW_ADMIN_ERROR_PAGE,
						WebUsageConstantsIF.Keys.OBJ, ajax);
			}
			AJAXResponse ajax = new AJAXResponse();
			ajax.setStatus(false);
			Message msg = new Message();
			msg.setMsg(WebUsageConstantsIF.Message.CLEANREVIEWS_SUCESS);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajax.setEbErrors(ebErrors);

			return new ModelAndView(
					WebUsageConstantsIF.Views.VIEW_ADMIN_SUCESS_PAGE,
					WebUsageConstantsIF.Keys.OBJ, ajax);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			AJAXResponse ajax = new AJAXResponse();
			ajax.setStatus(false);
			Message msg = new Message();
			msg.setMsg(WebUsageConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajax.setEbErrors(ebErrors);
			return new ModelAndView(
					WebUsageConstantsIF.Views.VIEW_ADMIN_ERROR_PAGE,
					WebUsageConstantsIF.Keys.OBJ, ajax);
		}
	}

	@Override
	@RequestMapping(value = "/viewCleanReviews.do", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse viewCleanReviews(
			HttpServletRequest request) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			HttpSession session = request.getSession(false);

			if (session == null) {
				ajaxResponse = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxResponse.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(WebUsageConstantsIF.Message.SESSION_EXPIRED);
				ebErrors.add(webSiteUrlMsg);
			}
			// Obtain User Id from Session

			String userId = (String) session
					.getAttribute(WebUsageConstantsIF.Keys.LOGINID_SESSION);

			if (userId == null || userId.isEmpty()) {
				ajaxResponse = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxResponse.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(WebUsageConstantsIF.Message.SESSION_EXPIRED);
				ebErrors.add(webSiteUrlMsg);
				ajaxResponse.setEbErrors(ebErrors);
			}
			List<SearchObj> reviewList = webUsageDelegate
					.retriveCleanReviewList(userId);
			if (null == reviewList) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(WebUsageConstantsIF.Message.EMPTY_REVIEWSLIST);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(reviewList);
			ajaxResponse
					.setMessage(WebUsageConstantsIF.Message.REVIEWS_FETCH_SUCESS);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message();
			msg.setMsg(WebUsageConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}

	@Override
	@RequestMapping(value = "/doTokens.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView doTokens(HttpServletRequest request) {
		try {

			HttpSession session = request.getSession(false);
			AJAXResponse ajaxRes = null;
			if (session == null) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(WebUsageConstantsIF.Message.SESSION_EXPIRED);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						WebUsageConstantsIF.Views.VIEW_LOGIN_INPUT,
						WebUsageConstantsIF.Keys.OBJ, ajaxRes);
			}

			// Obtain User Id from Session

			String userId = (String) session
					.getAttribute(WebUsageConstantsIF.Keys.LOGINID_SESSION);

			if (userId == null || userId.isEmpty()) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(WebUsageConstantsIF.Message.SESSION_EXPIRED);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						WebUsageConstantsIF.Views.VIEW_LOGIN_INPUT,
						WebUsageConstantsIF.Keys.OBJ, ajaxRes);

			}

			StatusInfo statusInfo = webUsageDelegate.doTokens(userId);

			if (!statusInfo.isStatus()) {
				AJAXResponse ajax = new AJAXResponse();
				ajax.setStatus(false);
				Message msg = new Message();
				msg.setMsg(statusInfo.getErrMsg());
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajax.setEbErrors(ebErrors);
				return new ModelAndView(
						WebUsageConstantsIF.Views.VIEW_ADMIN_ERROR_PAGE,
						WebUsageConstantsIF.Keys.OBJ, ajax);
			}
			AJAXResponse ajax = new AJAXResponse();
			ajax.setStatus(false);
			Message msg = new Message();
			msg.setMsg(WebUsageConstantsIF.Message.TOKENS_SUCESS);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajax.setEbErrors(ebErrors);

			return new ModelAndView(
					WebUsageConstantsIF.Views.VIEW_ADMIN_SUCESS_PAGE,
					WebUsageConstantsIF.Keys.OBJ, ajax);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			AJAXResponse ajax = new AJAXResponse();
			ajax.setStatus(false);
			Message msg = new Message();
			msg.setMsg(WebUsageConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajax.setEbErrors(ebErrors);
			return new ModelAndView(
					WebUsageConstantsIF.Views.VIEW_ADMIN_ERROR_PAGE,
					WebUsageConstantsIF.Keys.OBJ, ajax);
		}
	}

	@Override
	@RequestMapping(value = "/viewTokens.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody AJAXResponse viewTokens(HttpServletRequest request) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			AJAXResponse ajaxRes = new AJAXResponse();

			HttpSession session = request.getSession(false);

			if (session == null) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(WebUsageConstantsIF.Message.SESSION_EXPIRED);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return ajaxRes;
			}

			// Obtain User Id from Session

			String userId = (String) session
					.getAttribute(WebUsageConstantsIF.Keys.LOGINID_SESSION);

			if (userId == null || userId.isEmpty()) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(WebUsageConstantsIF.Message.SESSION_EXPIRED);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return ajaxRes;

			}

			List<TokenVO> tokenList = webUsageDelegate.retriveTokenList(userId);
			if (null == tokenList) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(WebUsageConstantsIF.Message.EMPTY_TOKENS);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(tokenList);
			ajaxResponse
					.setMessage(WebUsageConstantsIF.Message.TOKENRETRIVAL_SUCESS);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message();
			msg.setMsg(WebUsageConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}

	@Override
	@RequestMapping(value = "/doFreqs.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView doFrequency(HttpServletRequest request) {
		try {

			AJAXResponse ajaxRes = new AJAXResponse();

			HttpSession session = request.getSession(false);

			if (session == null) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(WebUsageConstantsIF.Message.SESSION_EXPIRED);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						WebUsageConstantsIF.Views.VIEW_LOGIN_INPUT,
						WebUsageConstantsIF.Keys.OBJ, ajaxRes);
			}

			// Obtain User Id from Session

			String userId = (String) session
					.getAttribute(WebUsageConstantsIF.Keys.LOGINID_SESSION);

			if (userId == null || userId.isEmpty()) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(WebUsageConstantsIF.Message.SESSION_EXPIRED);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						WebUsageConstantsIF.Views.VIEW_LOGIN_INPUT,
						WebUsageConstantsIF.Keys.OBJ, ajaxRes);

			}

			StatusInfo statusInfo = webUsageDelegate.doFrequency(userId);

			if (!statusInfo.isStatus()) {
				AJAXResponse ajax = new AJAXResponse();
				ajax.setStatus(false);
				Message msg = new Message();
				msg.setMsg(statusInfo.getErrMsg());
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajax.setEbErrors(ebErrors);
				return new ModelAndView(
						WebUsageConstantsIF.Views.VIEW_ADMIN_ERROR_PAGE,
						WebUsageConstantsIF.Keys.OBJ, ajax);
			}
			AJAXResponse ajax = new AJAXResponse();
			ajax.setStatus(false);
			Message msg = new Message();
			msg.setMsg(WebUsageConstantsIF.Message.FREQ_SUCESS);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajax.setEbErrors(ebErrors);

			return new ModelAndView(
					WebUsageConstantsIF.Views.VIEW_ADMIN_SUCESS_PAGE,
					WebUsageConstantsIF.Keys.OBJ, ajax);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			AJAXResponse ajax = new AJAXResponse();
			ajax.setStatus(false);
			Message msg = new Message();
			msg.setMsg(WebUsageConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajax.setEbErrors(ebErrors);
			return new ModelAndView(
					WebUsageConstantsIF.Views.VIEW_ADMIN_ERROR_PAGE,
					WebUsageConstantsIF.Keys.OBJ, ajax);
		}
	}

	@Override
	@RequestMapping(value = "/doFeatureVector.do", method = {
			RequestMethod.POST, RequestMethod.GET })
	public ModelAndView doFeatureVector(HttpServletRequest request) {
		try {
			AJAXResponse ajaxRes = new AJAXResponse();

			HttpSession session = request.getSession(false);

			if (session == null) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(WebUsageConstantsIF.Message.SESSION_EXPIRED);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						WebUsageConstantsIF.Views.VIEW_LOGIN_INPUT,
						WebUsageConstantsIF.Keys.OBJ, ajaxRes);
			}

			// Obtain User Id from Session

			String userId = (String) session
					.getAttribute(WebUsageConstantsIF.Keys.LOGINID_SESSION);

			if (userId == null || userId.isEmpty()) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(WebUsageConstantsIF.Message.SESSION_EXPIRED);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						WebUsageConstantsIF.Views.VIEW_LOGIN_INPUT,
						WebUsageConstantsIF.Keys.OBJ, ajaxRes);

			}

			StatusInfo statusInfo = webUsageDelegate.doFeatureVector(userId);

			if (!statusInfo.isStatus()) {
				AJAXResponse ajax = new AJAXResponse();
				ajax.setStatus(false);
				Message msg = new Message();
				msg.setMsg(statusInfo.getErrMsg());
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajax.setEbErrors(ebErrors);
				return new ModelAndView(
						WebUsageConstantsIF.Views.VIEW_ADMIN_ERROR_PAGE,
						WebUsageConstantsIF.Keys.OBJ, ajax);
			}
			AJAXResponse ajax = new AJAXResponse();
			ajax.setStatus(false);
			Message msg = new Message();
			msg.setMsg(WebUsageConstantsIF.Message.FEATUREVECTORSUCESS_VIEW);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajax.setEbErrors(ebErrors);

			return new ModelAndView(
					WebUsageConstantsIF.Views.VIEW_ADMIN_SUCESS_PAGE,
					WebUsageConstantsIF.Keys.OBJ, ajax);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			AJAXResponse ajax = new AJAXResponse();
			ajax.setStatus(false);
			Message msg = new Message();
			msg.setMsg(WebUsageConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajax.setEbErrors(ebErrors);
			return new ModelAndView(
					WebUsageConstantsIF.Views.VIEW_ADMIN_ERROR_PAGE,
					WebUsageConstantsIF.Keys.OBJ, ajax);
		}
	}

	@Override
	@RequestMapping(value = "/viewFeatureVector.do", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody AJAXResponse retriveFeatureVector(
			HttpServletRequest request) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			AJAXResponse ajaxRes = new AJAXResponse();

			HttpSession session = request.getSession(false);

			if (session == null) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(WebUsageConstantsIF.Message.SESSION_EXPIRED);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return ajaxRes;
			}

			// Obtain User Id from Session

			String userId = (String) session
					.getAttribute(WebUsageConstantsIF.Keys.LOGINID_SESSION);

			if (userId == null || userId.isEmpty()) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(WebUsageConstantsIF.Message.SESSION_EXPIRED);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return ajaxRes;

			}

			List<FeatureVO> featureVectorList = webUsageDelegate
					.retriveFeatureVectorList(userId);
			if (null == featureVectorList) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(WebUsageConstantsIF.Message.EMPTY_FEATUREVECTOR_LIST);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return ajaxResponse;
			}
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			ajaxResponse.setModel(featureVectorList);
			ajaxResponse
					.setMessage(WebUsageConstantsIF.Message.FEATUREVECTOR_FETCH_SUCESS);
			return ajaxResponse;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message();
			msg.setMsg(WebUsageConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return ajaxResponse;
		}
	}

	@Override
	@RequestMapping(value = "/viewFrequency.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody AJAXResponse retriveFreq(HttpServletRequest request) {
		AJAXResponse ajaxRes = null;
		try {
			ajaxRes = new AJAXResponse();

			HttpSession session = request.getSession(false);

			if (session == null) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(WebUsageConstantsIF.Message.SESSION_EXPIRED);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return ajaxRes;
			}

			// Obtain User Id from Session

			String userId = (String) session
					.getAttribute(WebUsageConstantsIF.Keys.LOGINID_SESSION);

			if (userId == null || userId.isEmpty()) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(WebUsageConstantsIF.Message.SESSION_EXPIRED);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return ajaxRes;

			}

			List<FrequencyVO> freqVOList = webUsageDelegate
					.retriveFreqInfo(userId);

			if (null == freqVOList) {
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message msg = new Message();
				msg.setMsg(WebUsageConstantsIF.Message.FREQUENCYLIST_EMPTY);
				ebErrors.add(msg);
				ajaxRes.setEbErrors(ebErrors);
				return ajaxRes;
			}

			ajaxRes.setStatus(true);
			ajaxRes.setModel(freqVOList);
			return ajaxRes;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception" + e.getMessage());
			ajaxRes = new AJAXResponse();
			List<Message> ebErrors = new ArrayList<Message>();
			ajaxRes.setStatus(false);
			Message webSiteUrlMsg = new Message();
			webSiteUrlMsg.setMsg(WebUsageConstantsIF.Message.INTERNAL_ERROR);

			ebErrors.add(webSiteUrlMsg);
			ajaxRes.setEbErrors(ebErrors);
			return ajaxRes;
		}
	}

	@Override
	@RequestMapping(value = "/search.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView searchContents(HttpServletRequest request,
			@RequestParam String query) {

		AJAXResponse ajaxRes = null;
		try {

			if (null == query || query.isEmpty()) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg.setMsg(WebUsageConstantsIF.Message.SEARCH_EMPTY);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(WebUsageConstantsIF.Views.SEARCH_INPUT,
						WebUsageConstantsIF.Keys.OBJ, ajaxRes);

			}
			// Session

			HttpSession session = request.getSession(false);

			if (session == null) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(WebUsageConstantsIF.Message.SESSION_EXPIRED);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(WebUsageConstantsIF.Views.LOGIN_INPUT,
						WebUsageConstantsIF.Keys.OBJ, ajaxRes);
			}

			// Obtain User Id from Session

			String userId = (String) session
					.getAttribute(WebUsageConstantsIF.Keys.LOGINID_SESSION);

			if (userId == null || userId.isEmpty()) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(WebUsageConstantsIF.Message.SESSION_EXPIRED);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(WebUsageConstantsIF.Views.LOGIN_INPUT,
						WebUsageConstantsIF.Keys.OBJ, ajaxRes);
			}

			List<GoogleResult> googleResults = webUsageDelegate
					.retriveGoogleResultsAndDoUserProfiling(query, userId);

			if (null == googleResults || googleResults.isEmpty()) {

				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message msg = new Message();
				msg.setMsg(WebUsageConstantsIF.Message.SEARCHRESULTS_EMPTY);
				ebErrors.add(msg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(WebUsageConstantsIF.Views.SEARCH_INPUT,
						WebUsageConstantsIF.Keys.OBJ, ajaxRes);

			}

			ajaxRes = new AJAXResponse();
			ajaxRes.setStatus(true);
			ajaxRes.setModel(googleResults);
			return new ModelAndView(WebUsageConstantsIF.Views.SEARCH_INPUT,
					WebUsageConstantsIF.Keys.OBJ, ajaxRes);

		} catch (Exception e) {
			ajaxRes = new AJAXResponse();
			List<Message> ebErrors = new ArrayList<Message>();
			ajaxRes.setStatus(false);
			Message webSiteUrlMsg = new Message();
			webSiteUrlMsg.setMsg(WebUsageConstantsIF.Message.INTERNAL_ERROR);
			ebErrors.add(webSiteUrlMsg);
			ajaxRes.setEbErrors(ebErrors);
			return new ModelAndView(WebUsageConstantsIF.Views.SEARCH_INPUT,
					WebUsageConstantsIF.Keys.OBJ, ajaxRes);
		}
	}

	

	@Override
	@RequestMapping(value = "/removeStopword.do", method = {
			RequestMethod.POST, RequestMethod.GET })
	public ModelAndView removeStopWord(String stopWord) {
		
		AJAXResponse ajaxRes = null;
		try {
			ajaxRes = new AJAXResponse();
			if (null == stopWord || stopWord.isEmpty()) {
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg.setMsg(WebUsageConstantsIF.Message.EMPTY_STOPWORD);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						WebUsageConstantsIF.Views.REMOVESTOPWORD_INPUT,
						WebUsageConstantsIF.Keys.OBJ, ajaxRes);

			}
			StatusInfo statusInfo = webUsageDelegate.removeStopword(stopWord);
			if (!statusInfo.isStatus()) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(WebUsageConstantsIF.Message.STOPWORD_REMOVE_FAILED);

				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						WebUsageConstantsIF.Views.REMOVESTOPWORD_INPUT,
						WebUsageConstantsIF.Keys.OBJ, ajaxRes);

			} else {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(WebUsageConstantsIF.Message.STOPWORD_REMOVE_SUCESS);

				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						WebUsageConstantsIF.Views.VIEW_ADMIN_SUCESS_PAGE,
						WebUsageConstantsIF.Keys.OBJ, ajaxRes);
			}

		} catch (Exception e) {
			ajaxRes = new AJAXResponse();
			List<Message> ebErrors = new ArrayList<Message>();
			ajaxRes.setStatus(false);
			Message webSiteUrlMsg = new Message();
			webSiteUrlMsg.setMsg(WebUsageConstantsIF.Message.INTERNAL_ERROR);

			ebErrors.add(webSiteUrlMsg);
			ajaxRes.setEbErrors(ebErrors);
			return new ModelAndView(
					WebUsageConstantsIF.Views.REMOVESTOPWORD_INPUT,
					WebUsageConstantsIF.Keys.OBJ, ajaxRes);
		}
	}

	@Override
	@RequestMapping(value = "/deleteData.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView removeTableData(@RequestParam String tableDataToRemove,HttpServletRequest request) {

		AJAXResponse ajaxRes = null;
		try {
			ajaxRes = new AJAXResponse();
			if (null == tableDataToRemove || tableDataToRemove.isEmpty()) {
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg.setMsg(WebUsageConstantsIF.Message.EMPTY_TABLE);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						WebUsageConstantsIF.Views.DELETEDATA_INPUT,
						WebUsageConstantsIF.Keys.OBJ, ajaxRes);

			}
			
			HttpSession session = request.getSession(false);
			if (session == null) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(WebUsageConstantsIF.Message.SESSION_EXPIRED);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						WebUsageConstantsIF.Views.VIEW_LOGIN_INPUT,
						WebUsageConstantsIF.Keys.OBJ, ajaxRes);
			}

			// Obtain User Id from Session

			String userId = (String) session
					.getAttribute(WebUsageConstantsIF.Keys.LOGINID_SESSION);

			if (userId == null || userId.isEmpty()) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(WebUsageConstantsIF.Message.SESSION_EXPIRED);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						WebUsageConstantsIF.Views.VIEW_LOGIN_INPUT,
						WebUsageConstantsIF.Keys.OBJ, ajaxRes);

			}
			
			StatusInfo statusInfo = webUsageDelegate
					.removeTableData(tableDataToRemove,userId);
			if (!statusInfo.isStatus()) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(WebUsageConstantsIF.Message.TABLEDATA_REMOVE_FAILED);

				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						WebUsageConstantsIF.Views.DELETEDATA_INPUT,
						WebUsageConstantsIF.Keys.OBJ, ajaxRes);

			} else {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(WebUsageConstantsIF.Message.TABLEDATA_REMOVE_SUCESS);

				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(
						WebUsageConstantsIF.Views.VIEW_ADMIN_SUCESS_PAGE,
						WebUsageConstantsIF.Keys.OBJ, ajaxRes);
			}

		} catch (Exception e) {
			ajaxRes = new AJAXResponse();
			List<Message> ebErrors = new ArrayList<Message>();
			ajaxRes.setStatus(false);
			Message webSiteUrlMsg = new Message();
			webSiteUrlMsg.setMsg(WebUsageConstantsIF.Message.INTERNAL_ERROR);

			ebErrors.add(webSiteUrlMsg);
			ajaxRes.setEbErrors(ebErrors);
			return new ModelAndView(WebUsageConstantsIF.Views.DELETEDATA_INPUT,
					WebUsageConstantsIF.Keys.OBJ, ajaxRes);
		}
	}

	@Override
	@RequestMapping(value = "/login.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView doLogin(HttpServletRequest request,
			@ModelAttribute RegisterUser registerUser) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			String userId = registerUser.getUserId();

			if (null == userId || userId.isEmpty()
					|| userId.trim().length() == 0) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(WebUsageConstantsIF.Message.USERID_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return new ModelAndView(
						WebUsageConstantsIF.Views.VIEW_LOGIN_INPUT,
						WebUsageConstantsIF.Keys.OBJ, ajaxResponse);
			}

			String password = registerUser.getUserPassword();

			if (null == password || password.isEmpty()
					|| password.trim().length() == 0) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(WebUsageConstantsIF.Message.PASSWORD_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return new ModelAndView(
						WebUsageConstantsIF.Views.VIEW_LOGIN_INPUT,
						WebUsageConstantsIF.Keys.OBJ, ajaxResponse);
			}

			StatusInfo statusInfo = webUsageDelegate.checkLogin(registerUser);

			if (!statusInfo.isStatus()) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(statusInfo.getErrMsg());
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return new ModelAndView(
						WebUsageConstantsIF.Views.VIEW_LOGIN_INPUT,
						WebUsageConstantsIF.Keys.OBJ, ajaxResponse);
			} else if (statusInfo.isStatus()) {

				HttpSession session = request.getSession(true);

				session.setAttribute(WebUsageConstantsIF.Keys.LOGINID_SESSION,
						registerUser.getUserId());

				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(true);
				Message msg = new Message();
				msg.setMsg(WebUsageConstantsIF.Message.USERREGISTERED_SUCESS_MSG);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return new ModelAndView(
						WebUsageConstantsIF.Views.VIEW_CUSTOMER_WELCOMEPAGE,
						WebUsageConstantsIF.Keys.OBJ, ajaxResponse);

			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message();
			msg.setMsg(WebUsageConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return new ModelAndView(WebUsageConstantsIF.Views.VIEW_LOGIN_INPUT,
					WebUsageConstantsIF.Keys.OBJ, ajaxResponse);
		}
		return null;

	}

	@Override
	@RequestMapping(value = "/registerUser.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView registerUserInfo(
			@ModelAttribute RegisterUser registerUser,
			HttpServletRequest request) {

		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			String firstName = registerUser.getFirstName();
			if (null == firstName || firstName.isEmpty()
					|| firstName.trim().length() == 0) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(WebUsageConstantsIF.Message.FIRSTNAME_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return new ModelAndView(
						WebUsageConstantsIF.Views.VIEW_REGISTER_INPUT,
						WebUsageConstantsIF.Keys.OBJ, ajaxResponse);

			}
			String lastName = registerUser.getLastName();
			if (null == lastName || lastName.isEmpty()
					|| lastName.trim().length() == 0) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(WebUsageConstantsIF.Message.LASTNAME_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return new ModelAndView(
						WebUsageConstantsIF.Views.VIEW_REGISTER_INPUT,
						WebUsageConstantsIF.Keys.OBJ, ajaxResponse);
			}

			String userId = registerUser.getUserId();

			if (null == userId || userId.isEmpty()
					|| userId.trim().length() == 0) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(WebUsageConstantsIF.Message.USERID_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return new ModelAndView(
						WebUsageConstantsIF.Views.VIEW_REGISTER_INPUT,
						WebUsageConstantsIF.Keys.OBJ, ajaxResponse);
			}

			String email = registerUser.getEmailId();

			if (null == email || email.isEmpty() || email.trim().length() == 0) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(WebUsageConstantsIF.Message.EMAIL_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return new ModelAndView(
						WebUsageConstantsIF.Views.VIEW_REGISTER_INPUT,
						WebUsageConstantsIF.Keys.OBJ, ajaxResponse);
			}

			String password = registerUser.getUserPassword();

			if (null == password || password.isEmpty()
					|| password.trim().length() == 0) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(WebUsageConstantsIF.Message.PASSWORD_EMPTY);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return new ModelAndView(
						WebUsageConstantsIF.Views.VIEW_REGISTER_INPUT,
						WebUsageConstantsIF.Keys.OBJ, ajaxResponse);
			}

			StatusInfo statusInfo = webUsageDelegate
					.doRegistration(registerUser);

			if (!statusInfo.isStatus()) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(statusInfo.getErrMsg());
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return new ModelAndView(
						WebUsageConstantsIF.Views.VIEW_REGISTER_INPUT,
						WebUsageConstantsIF.Keys.OBJ, ajaxResponse);
			}

			HttpSession session = request.getSession(true);

			session.setAttribute(WebUsageConstantsIF.Keys.LOGINID_SESSION,
					registerUser.getUserId());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message();
			msg.setMsg(WebUsageConstantsIF.Message.USERREGISTERED_SUCESS_MSG);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return new ModelAndView(
					WebUsageConstantsIF.Views.VIEW_CUSTOMER_WELCOMEPAGE,
					WebUsageConstantsIF.Keys.OBJ, ajaxResponse);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message();
			msg.setMsg(WebUsageConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return new ModelAndView(
					WebUsageConstantsIF.Views.VIEW_REGISTER_INPUT,
					WebUsageConstantsIF.Keys.OBJ, ajaxResponse);
		}

	}

	@Override
	@RequestMapping(value = "/logout.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView doLogout(HttpServletRequest request) {
		try {

			HttpSession session = request.getSession(false);
			session.invalidate();
			return new ModelAndView(
					WebUsageConstantsIF.Views.APPLICATION_WELCOME_PAGE,
					WebUsageConstantsIF.Keys.OBJ, null);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			return new ModelAndView(
					WebUsageConstantsIF.Views.APPLICATION_WELCOME_PAGE,
					WebUsageConstantsIF.Keys.OBJ, null);
		}
	}

	@Override
	@RequestMapping(value = "/retriveAllSearches.do", method = {
			RequestMethod.POST, RequestMethod.GET })
	public AJAXResponse viewProfileResults(HttpServletRequest request) {
		AJAXResponse ajaxRes = null;
		try {
			ajaxRes = new AJAXResponse();

			HttpSession session = request.getSession(false);

			if (session == null) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(WebUsageConstantsIF.Message.SESSION_EXPIRED);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return ajaxRes;
			}

			// Obtain User Id from Session

			String userId = (String) session
					.getAttribute(WebUsageConstantsIF.Keys.LOGINID_SESSION);

			if (userId == null || userId.isEmpty()) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(WebUsageConstantsIF.Message.SESSION_EXPIRED);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return ajaxRes;

			}

			List<SearchObj> reviewList = webUsageDelegate
					.retriveUserSearches(userId);
			if (null == reviewList) {
				ajaxRes = new AJAXResponse();
				ajaxRes.setStatus(false);
				Message msg = new Message();
				msg.setMsg(WebUsageConstantsIF.Message.EMPTY_REVIEWSLIST);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxRes.setEbErrors(ebErrors);
				return ajaxRes;
			}
			ajaxRes = new AJAXResponse();
			ajaxRes.setStatus(true);
			ajaxRes.setModel(reviewList);
			ajaxRes.setMessage(WebUsageConstantsIF.Message.REVIEWS_FETCH_SUCESS);
			return ajaxRes;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxRes = new AJAXResponse();
			ajaxRes.setStatus(true);
			Message msg = new Message();
			msg.setMsg(WebUsageConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxRes.setEbErrors(ebErrors);
			return ajaxRes;
		}
	}

	@Override
	@RequestMapping(value = "/doOfflineSearch.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView doSearchContentsOffline(HttpServletRequest request,
			String query) {
		AJAXResponse ajaxRes = null;
		try {

			if (null == query || query.isEmpty()) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg.setMsg(WebUsageConstantsIF.Message.SEARCH_EMPTY);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(WebUsageConstantsIF.Views.SEARCH_INPUT,
						WebUsageConstantsIF.Keys.OBJ, ajaxRes);

			}
			// Session

			HttpSession session = request.getSession(false);

			if (session == null) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(WebUsageConstantsIF.Message.SESSION_EXPIRED);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(WebUsageConstantsIF.Views.LOGIN_INPUT,
						WebUsageConstantsIF.Keys.OBJ, ajaxRes);
			}

			// Obtain User Id from Session

			String userId = (String) session
					.getAttribute(WebUsageConstantsIF.Keys.LOGINID_SESSION);

			if (userId == null || userId.isEmpty()) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(WebUsageConstantsIF.Message.SESSION_EXPIRED);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(WebUsageConstantsIF.Views.LOGIN_INPUT,
						WebUsageConstantsIF.Keys.OBJ, ajaxRes);
			}

			List<SearchObj> googleResults = webUsageDelegate
					.retriveOfflineSearch(query, userId);

			if (null == googleResults || googleResults.isEmpty()) {

				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message msg = new Message();
				msg.setMsg(WebUsageConstantsIF.Message.SEARCHRESULTS_EMPTY);
				ebErrors.add(msg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(WebUsageConstantsIF.Views.OFFLINESEARCH_INPUT,
						WebUsageConstantsIF.Keys.OBJ, ajaxRes);

			}

			ajaxRes = new AJAXResponse();
			ajaxRes.setStatus(true);
			ajaxRes.setModel(googleResults);
			return new ModelAndView(WebUsageConstantsIF.Views.OFFLINESEARCH_INPUT,
					WebUsageConstantsIF.Keys.OBJ, ajaxRes);

		} catch (Exception e) {
			ajaxRes = new AJAXResponse();
			List<Message> ebErrors = new ArrayList<Message>();
			ajaxRes.setStatus(false);
			Message webSiteUrlMsg = new Message();
			webSiteUrlMsg.setMsg(WebUsageConstantsIF.Message.INTERNAL_ERROR);
			ebErrors.add(webSiteUrlMsg);
			ajaxRes.setEbErrors(ebErrors);
			return new ModelAndView(WebUsageConstantsIF.Views.SEARCH_INPUT,
					WebUsageConstantsIF.Keys.OBJ, ajaxRes);
		}
	}
	
	@Override
	@RequestMapping(value = "/settings.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView createPersonalSettings(HttpServletRequest request,
			HttpServletResponse response,@RequestParam int threshold) {
		AJAXResponse ajaxResponse = null;
		try {
			ajaxResponse = new AJAXResponse();

			if(threshold<=0)
			{
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(WebUsageConstantsIF.Message.THRESHOLD_INVALID);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return new ModelAndView(WebUsageConstantsIF.Views.VIEW_SETTINGS_INPUT,
						WebUsageConstantsIF.Keys.OBJ, ajaxResponse);
			}
			
			
			HttpSession session = request.getSession(false);

			if (session == null
					|| null == session
							.getAttribute(WebUsageConstantsIF.Keys.LOGINID_SESSION)) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(WebUsageConstantsIF.Message.SESSION_INVALID);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return new ModelAndView(WebUsageConstantsIF.Views.VIEW_LOGIN_INPUT,
						WebUsageConstantsIF.Keys.OBJ, ajaxResponse);
			}

			
			String loginId = (String) session
					.getAttribute(WebUsageConstantsIF.Keys.LOGINID_SESSION);

			if (null == loginId) {
				ajaxResponse = new AJAXResponse();
				ajaxResponse.setStatus(false);
				Message msg = new Message();
				msg.setMsg(WebUsageConstantsIF.Message.SESSION_INVALID);
				List<Message> ebErrors = new ArrayList<Message>();
				ebErrors.add(msg);
				ajaxResponse.setEbErrors(ebErrors);
				return new ModelAndView(WebUsageConstantsIF.Views.VIEW_LOGIN_INPUT,
						WebUsageConstantsIF.Keys.OBJ, ajaxResponse);
			}
			
			
			StatusInfo statusInfo = webUsageDelegate
					.storeSettings(threshold,loginId);

			if (!statusInfo.isStatus()) {
				ajaxResponse = new AJAXResponse();

				ajaxResponse.setStatus(false);
				List<Message> ebErrors = new ArrayList<Message>();
				Message m = new Message();
				if (statusInfo.getErrMsg() != null) {
					m.setMsg(statusInfo.getErrMsg());
				} else {
					m.setMsg(WebUsageConstantsIF.Message.INTERNAL_ERROR);
				}
				ebErrors.add(m);
				ajaxResponse.setEbErrors(ebErrors);

				return new ModelAndView(WebUsageConstantsIF.Views.VIEW_SETTINGS_INPUT,
						WebUsageConstantsIF.Keys.OBJ, ajaxResponse);

			}
			ajaxResponse=new AJAXResponse();
			ajaxResponse.setStatus(true);
			ajaxResponse.setMessage(WebUsageConstantsIF.Message.SETTINGS_STORE_SUCESS);
			
			return new ModelAndView(WebUsageConstantsIF.Views.SUCESS_PAGE,
					WebUsageConstantsIF.Keys.OBJ, ajaxResponse);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			ajaxResponse = new AJAXResponse();
			ajaxResponse.setStatus(true);
			Message msg = new Message();
			msg.setMsg(WebUsageConstantsIF.Message.INTERNAL_ERROR);
			List<Message> ebErrors = new ArrayList<Message>();
			ebErrors.add(msg);
			ajaxResponse.setEbErrors(ebErrors);
			return new ModelAndView(WebUsageConstantsIF.Views.VIEW_SETTINGS_INPUT,
					WebUsageConstantsIF.Keys.OBJ, ajaxResponse);
		}
	}

	@Override
	@RequestMapping(value = "/conceptSearch.do", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView obtainResultsBasedOnConcepts(HttpServletRequest request) {
		AJAXResponse ajaxRes = null;
		try {

			// Session

			HttpSession session = request.getSession(false);

			if (session == null) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(WebUsageConstantsIF.Message.SESSION_EXPIRED);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(WebUsageConstantsIF.Views.LOGIN_INPUT,
						WebUsageConstantsIF.Keys.OBJ, ajaxRes);
			}

			// Obtain User Id from Session

			String userId = (String) session
					.getAttribute(WebUsageConstantsIF.Keys.LOGINID_SESSION);

			if (userId == null || userId.isEmpty()) {
				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message webSiteUrlMsg = new Message();
				webSiteUrlMsg
						.setMsg(WebUsageConstantsIF.Message.SESSION_EXPIRED);
				ebErrors.add(webSiteUrlMsg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(WebUsageConstantsIF.Views.LOGIN_INPUT,
						WebUsageConstantsIF.Keys.OBJ, ajaxRes);
			}

			List<ConceptsSearch> conceptsResults = webUsageDelegate
					.conceptResults(userId);

			if (null == conceptsResults || conceptsResults.isEmpty()) {

				ajaxRes = new AJAXResponse();
				List<Message> ebErrors = new ArrayList<Message>();
				ajaxRes.setStatus(false);
				Message msg = new Message();
				msg.setMsg(WebUsageConstantsIF.Message.SEARCHRESULTS_EMPTY);
				ebErrors.add(msg);
				ajaxRes.setEbErrors(ebErrors);
				return new ModelAndView(WebUsageConstantsIF.Views.CONCEPTS_INPUT,
						WebUsageConstantsIF.Keys.OBJ, ajaxRes);

			}

			ajaxRes = new AJAXResponse();
			ajaxRes.setStatus(true);
			ajaxRes.setModel(conceptsResults);
			return new ModelAndView(WebUsageConstantsIF.Views.CONCEPTS_OUTPUT,
					WebUsageConstantsIF.Keys.OBJ, ajaxRes);

		} catch (Exception e) {
			ajaxRes = new AJAXResponse();
			List<Message> ebErrors = new ArrayList<Message>();
			ajaxRes.setStatus(false);
			Message webSiteUrlMsg = new Message();
			webSiteUrlMsg.setMsg(WebUsageConstantsIF.Message.INTERNAL_ERROR);
			ebErrors.add(webSiteUrlMsg);
			ajaxRes.setEbErrors(ebErrors);
			return new ModelAndView(WebUsageConstantsIF.Views.SEARCH_INPUT,
					WebUsageConstantsIF.Keys.OBJ, ajaxRes);
		}
	}

		
}
