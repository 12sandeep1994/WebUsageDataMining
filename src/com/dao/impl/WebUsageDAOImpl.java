package com.dao.impl;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.support.SqlLobValue;

import com.constants.WebUsageConstantsIF;
import com.dao.inter.WebUsageDAOIF;
import com.model.BestFeatureVector;
import com.model.FeatureVO;
import com.model.FrequencyVO;
import com.model.RegisterUser;
import com.model.ReviewModel;
import com.model.SearchObj;
import com.model.StatusInfo;
import com.model.StopWordsVO;
import com.model.TokenVO;

public class WebUsageDAOImpl implements WebUsageDAOIF {

	protected SimpleJdbcTemplate template;
	protected NamedParameterJdbcTemplate namedJdbcTemplate;
	private DataSource dataSource;
	@Autowired
	protected MessageSource sqlProperties;
	protected JdbcTemplate jdbcTemplate;

	/**
	 * 
	 */
	public void init() {
		template = new SimpleJdbcTemplate(dataSource);
		namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	protected boolean update(String sqlKey, Map<String, Object> map) {
		System.out.println("Class-->RoutingDaoImpl:Method-->update");
		String sql = sqlProperties.getMessage(sqlKey, null, null);
		System.out.println("SQL" + sql);
		boolean value = false;
		try {
			namedJdbcTemplate.update(sql, map);
			value = true;
		} catch (Exception e) {
			System.out.println("Exception" + e.getMessage());
		}
		return value;
	}

	/**
	 * @param sqlKey
	 * @param map
	 * @return true if sucessfully updated
	 */
	protected boolean insert(String sqlKey, Map<String, Object> map) {
		System.out.println("Class-->RoutingDaoImpl:Method-->update");
		String sql = sqlProperties.getMessage(sqlKey, null, null);
		System.out.println("SQL" + sql);
		boolean value = false;
		try {
			namedJdbcTemplate.update(sql, map);
			value = true;
		} catch (Exception e) {
			System.out.println("Exception" + e.getMessage());
		}
		return value;
	}

	/**
	 * @param sqlQuery
	 * @param sqlKey
	 * @param map
	 * @return true if sucessfully updated
	 */
	protected boolean insertBasedOnQuery(String sqlQuery,
			Map<String, Object> map) {
		System.out.println("Class-->RoutingDaoImpl:Method-->update");
		boolean insertQuery = false;
		try {
			namedJdbcTemplate.update(sqlQuery, map);
			insertQuery = true;
		} catch (Exception e) {
			System.out.println("Exception" + e.getMessage());
		}
		return insertQuery;
	}

	/**
	 * @param <T>
	 * @param sqlKey
	 * @param paramMap
	 * @param rowMapper
	 * @return Object
	 */
	protected <T> T executeForObject(String sqlKey,
			Map<String, ? extends Object> paramMap, RowMapper<T> rowMapper) {
		String sql = sqlProperties.getMessage(sqlKey, null, null);
		return namedJdbcTemplate.queryForObject(sql, paramMap, rowMapper);
	}

	protected <T> T executeForObjectUsingQuery(String sql,
			Map<String, ? extends Object> paramMap, RowMapper<T> rowMapper) {
		return namedJdbcTemplate.queryForObject(sql, paramMap, rowMapper);
	}

	/**
	 * @param sqlKey
	 * @param params
	 * @param whereClause
	 * @return int once the statement gets executed
	 */
	protected int executeForInt(String sqlKey, Map<String, String> params,
			String whereClause) {
		String sql = sqlProperties.getMessage(sqlKey, null, null);
		sql = sql.concat(whereClause);
		System.out.println(sql);

		return namedJdbcTemplate.queryForInt(sql, params);
	}

	/**
	 * @param sqlKey
	 * @param params
	 * @return List of String objects
	 */
	protected List<String> executeForListOfString(String sqlKey,
			Map<String, Object> params) {
		String sql = sqlProperties.getMessage(sqlKey, null, null);
		System.out.println(sql);
		System.out.println(params);

		return namedJdbcTemplate.queryForList(sql, params, String.class);
	}

	/**
	 * @param sqlKey
	 * @param params
	 * @return List of integer values
	 */
	protected List<Integer> executeForListOfInt(String sqlKey,
			Map<String, Object> params) {
		String sql = sqlProperties.getMessage(sqlKey, null, null);
		System.out.println(sql);
		System.out.println(params);

		return namedJdbcTemplate.queryForList(sql, params, Integer.class);
	}

	/**
	 * @return template
	 */
	public SimpleJdbcTemplate getTemplate() {
		return template;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * @param dataSource
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * @return Named Parameter JDBC Template
	 */
	public NamedParameterJdbcTemplate getNamedJdbcTemplate() {
		return namedJdbcTemplate;
	}

	/**
	 * @return the SQL properties
	 */
	public MessageSource getSqlProperties() {
		return sqlProperties;
	}

	/**
	 * @param sqlProperties
	 */
	public void setSqlProperties(MessageSource sqlProperties) {
		this.sqlProperties = sqlProperties;
	}

	@Override
	public StatusInfo insertReview(ReviewModel reviewModel) {

		StatusInfo statusInfo = new StatusInfo();
		try {
			String sqlKey = WebUsageConstantsIF.SQLS.INSERT_REVIEW_SQL;
			String sql = sqlProperties.getMessage(sqlKey, null, null);
			jdbcTemplate.update(
					sql,
					new Object[] {
							new SqlLobValue(reviewModel.getReviewDetails()),
							reviewModel.getProductid(),
							reviewModel.getProducttype() }, new int[] {
							Types.BLOB, Types.INTEGER, Types.INTEGER });
			statusInfo.setStatus(true);

		} catch (Exception e) {
			System.out.println("Exception is:");
			System.out.println(e.getMessage());
			e.printStackTrace();
			statusInfo.setErrMsg(WebUsageConstantsIF.Message.MESSAGE_INTERNAL);
			statusInfo.setExceptionMsg(e.getMessage());
			statusInfo.setStatus(false);
			return statusInfo;

		}
		return statusInfo;
	}

	@Override
	public List<ReviewModel> retriveAllReviews() {

		try {

			String sql = sqlProperties.getMessage(
					WebUsageConstantsIF.SQLS.RETRIVE_REVIEWS_SQL, null, null);
			return jdbcTemplate.query(sql, new ReviewModelVOMapper());
		} catch (Exception e) {
			System.out.println("Exception" + e);
			return null;
		}
	}

	private final class ReviewModelVOMapper implements RowMapper<ReviewModel> {

		public ReviewModel mapRow(ResultSet rs, int arg1) throws SQLException {

			ReviewModel reviewModel = new ReviewModel();

			reviewModel.setProductid(rs
					.getInt(WebUsageConstantsIF.COLUMNNAMES.PRODUCTID_COL));
			reviewModel.setProducttype(rs
					.getInt(WebUsageConstantsIF.COLUMNNAMES.PRODUCTTYPE_COL));

			// Blob to String
			Blob blob = rs
					.getBlob(WebUsageConstantsIF.COLUMNNAMES.REVIEWDETAILS_COL);
			byte[] bdata = blob.getBytes(1, (int) blob.length());
			String reviewDetailsStr = new String(bdata);

			System.out.println("Review Details" + reviewDetailsStr);
			reviewModel.setReviewDetails(reviewDetailsStr);
			return reviewModel;

		}

	}

	@Override
	public StatusInfo insertStopWord(String stopWord) {
		StatusInfo insertStopWordStatus = null;
		try {
			insertStopWordStatus = new StatusInfo();
			String sql = sqlProperties.getMessage(
					WebUsageConstantsIF.SQLS.INSERT_STOPWORD_SQL, null, null);
			System.out.println("SQL----" + sql);

			jdbcTemplate.update(sql, new Object[] { stopWord },
					new int[] { Types.VARCHAR });
			insertStopWordStatus.setStatus(true);
			return insertStopWordStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			insertStopWordStatus = new StatusInfo();
			insertStopWordStatus.setErrMsg(e.getMessage());
			insertStopWordStatus.setStatus(false);
			return insertStopWordStatus;

		}
	}

	@Override
	public List<StopWordsVO> retriveStopWords() {
		try {
			String sql = sqlProperties.getMessage(
					WebUsageConstantsIF.SQLS.RETRIVE_STOPWORDS_FULL_SQL, null,
					null);
			return jdbcTemplate.query(sql, new StopWordsVOMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	private final class StopWordsVOMapper implements RowMapper<StopWordsVO> {

		public StopWordsVO mapRow(ResultSet rs, int arg1) throws SQLException {
			StopWordsVO webSiteDataVO = new StopWordsVO();
			webSiteDataVO
					.setStopWordId(rs
							.getInt(WebUsageConstantsIF.DatabaseColumns.STOPWORDID_COL));
			webSiteDataVO
					.setStopWord(rs
							.getString(WebUsageConstantsIF.DatabaseColumns.STOPWORD_COL));
			return webSiteDataVO;

		}

	}

	@Override
	public List<String> retriveStopWordsOnly() {
		try {
			String sql = sqlProperties.getMessage(
					WebUsageConstantsIF.SQLS.RETRIVE_STOPWORDS_SQL, null, null);
			return jdbcTemplate.queryForList(sql, String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public StatusInfo deleteAllCleanReviews() {
		StatusInfo statusInfo = null;
		try {

			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(
					WebUsageConstantsIF.SQLS.DELETE_ALLCLEANREVIEWS_SQL, null,
					null);
			jdbcTemplate.update(sql);
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
	public StatusInfo insertToken(TokenVO tokenVO) {
		StatusInfo insertTokenStatus = null;
		try {
			insertTokenStatus = new StatusInfo();
			String sql = sqlProperties.getMessage(
					WebUsageConstantsIF.SQLS.INSERT_TOKENS_SQL, null, null);

			Map<String, Object> queryMap = new HashMap<String, Object>();

			queryMap.put(WebUsageConstantsIF.Keys.USERID_KEY,
					tokenVO.getUserId());
			queryMap.put(WebUsageConstantsIF.Keys.QUERY_KEY, tokenVO.getQuery());
			queryMap.put(WebUsageConstantsIF.Keys.URLINFO_KEY, tokenVO.getUrl());
			queryMap.put(WebUsageConstantsIF.Keys.TITLE_KEY, tokenVO.getTitle());
			queryMap.put(WebUsageConstantsIF.Keys.DESC_KEY, tokenVO.getDesc());
			queryMap.put(WebUsageConstantsIF.Keys.HIDDENURL_KEY,
					tokenVO.getHiddenUrl());
			queryMap.put(WebUsageConstantsIF.Keys.TOKENNAME_KEY,
					tokenVO.getTokenName());

			namedJdbcTemplate.update(sql, queryMap);

			insertTokenStatus.setStatus(true);
			return insertTokenStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			insertTokenStatus = new StatusInfo();
			insertTokenStatus.setErrMsg(e.getMessage());
			insertTokenStatus.setStatus(false);
			return insertTokenStatus;

		}
	}

	@Override
	// INSERT INTO
	// frequency(TOKENNAME,FREQ,URL,DESCRIPTION,TITLE,QUERY,USERID,HIDDENURL)
	// VALUES(:tokenName,:freq,:urlInfo,::description,:title,:query,:userId,:hiddenUrl)
	public StatusInfo insertFrequency(FrequencyVO freqVO) {
		StatusInfo freqStatus = null;
		try {
			freqStatus = new StatusInfo();
			String sql = sqlProperties.getMessage(
					WebUsageConstantsIF.SQLS.INSERT_FREQUENCY_SQL, null, null);

			Map<String, Object> queryMap = new HashMap<String, Object>();

			queryMap.put(WebUsageConstantsIF.Keys.TOKENNAME_KEY,
					freqVO.getTokenName());
			queryMap.put(WebUsageConstantsIF.Keys.FREQ_KEY, freqVO.getFreq());
			queryMap.put(WebUsageConstantsIF.Keys.URLINFO_KEY, freqVO.getUrl());
			queryMap.put(WebUsageConstantsIF.Keys.DESC_KEY, freqVO.getDesc());
			queryMap.put(WebUsageConstantsIF.Keys.TITLE_KEY, freqVO.getTitle());
			queryMap.put(WebUsageConstantsIF.Keys.QUERY_KEY, freqVO.getQuery());
			queryMap.put(WebUsageConstantsIF.Keys.USERID_KEY,
					freqVO.getUserId());
			queryMap.put(WebUsageConstantsIF.Keys.HIDDENURL_KEY,
					freqVO.getHiddenUrl());

			namedJdbcTemplate.update(sql, queryMap);

			freqStatus.setStatus(true);
			return freqStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			freqStatus = new StatusInfo();
			freqStatus.setErrMsg(e.getMessage());
			freqStatus.setStatus(false);
			return freqStatus;

		}
	}

	@Override
	public List<TokenVO> retriveAllTokens(String userId) {

		List<TokenVO> tokenList = null;
		try {
			String sql = sqlProperties
					.getMessage(
							WebUsageConstantsIF.SQLS.RETRIVE_ALLTOKENS_WHERE_USERID_SQL,
							null, null);
			tokenList = new ArrayList<TokenVO>();

			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put(WebUsageConstantsIF.Keys.USERID_KEY, userId);

			tokenList = namedJdbcTemplate.query(sql, queryMap,
					new TokenMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
		return tokenList;
	}

	private final class TokenMapper implements RowMapper<TokenVO> {

		public TokenVO mapRow(ResultSet rs, int arg1) throws SQLException {

			TokenVO tokenVO = new TokenVO();

			tokenVO.setTokenId(rs
					.getInt(WebUsageConstantsIF.DatabaseColumns.TOKENID_COL));

			tokenVO.setDesc(rs
					.getString(WebUsageConstantsIF.DatabaseColumns.DESCRIPTION_COL));
			tokenVO.setHiddenUrl(rs
					.getString(WebUsageConstantsIF.DatabaseColumns.HIDDENURL_COL));
			tokenVO.setQuery(rs
					.getString(WebUsageConstantsIF.DatabaseColumns.QUERY_COL));
			tokenVO.setTitle(rs
					.getString(WebUsageConstantsIF.DatabaseColumns.TITLE_COL));
			tokenVO.setTokenName(rs
					.getString(WebUsageConstantsIF.DatabaseColumns.TOKENNAME_COL));
			tokenVO.setUrl(rs
					.getString(WebUsageConstantsIF.DatabaseColumns.URL_COL));
			tokenVO.setUserId(rs
					.getString(WebUsageConstantsIF.DatabaseColumns.USERID_COL));

			return tokenVO;
		}

	}

	// INSERT INTO
	// FEATUREVECTORS(FREQ,TOKENNAME,NOOFREVIEWS,IDFT,FEATUREVECTOR,REVIEWID,PRODUCTID,PRODUCTTYPE)
	// VALUES(?,?,?,?,?,?,?,?)

	@Override
	public List<FeatureVO> retriveAllFeatureVector(String userId) {
		try {
			String sql = sqlProperties
					.getMessage(
							WebUsageConstantsIF.SQLS.RETRIVE_FEATUREVO_WHERE_USERID_SQL,
							null, null);

			Map<String, Object> queryMap = new LinkedHashMap<String, Object>();
			queryMap.put(WebUsageConstantsIF.Keys.USERID_KEY, userId);

			return namedJdbcTemplate
					.query(sql, queryMap, new FeatureVOMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	private final class FeatureVOMapper implements RowMapper<FeatureVO> {

		public FeatureVO mapRow(ResultSet rs, int arg1) throws SQLException {
			FeatureVO freqVO = new FeatureVO();

			freqVO.setFeatureId(rs
					.getInt(WebUsageConstantsIF.DatabaseColumns.FEATUREID_COL));
			freqVO.setFreq(rs
					.getInt(WebUsageConstantsIF.DatabaseColumns.FREQ_COL));
			freqVO.setTokenName(rs
					.getString(WebUsageConstantsIF.DatabaseColumns.TOKENNAME_COL));
			freqVO.setNoOfReviews(rs
					.getInt(WebUsageConstantsIF.DatabaseColumns.NOOFREVIEWS_COL));
			freqVO.setIdft(rs
					.getDouble(WebUsageConstantsIF.DatabaseColumns.IDFT_COL));
			freqVO.setFeatureVector(rs
					.getDouble(WebUsageConstantsIF.DatabaseColumns.FEATUREVECTOR_COL));

			freqVO.setUrl(rs
					.getString(WebUsageConstantsIF.DatabaseColumns.URL_COL));

			freqVO.setQuery(rs
					.getString(WebUsageConstantsIF.DatabaseColumns.QUERY_COL));
			freqVO.setDesc(rs
					.getString(WebUsageConstantsIF.DatabaseColumns.DESCRIPTION_COL));
			freqVO.setUserId(rs
					.getString(WebUsageConstantsIF.DatabaseColumns.USERID_COL));
			freqVO.setHiddenUrl(rs
					.getString(WebUsageConstantsIF.DatabaseColumns.HIDDENURL_COL));
			freqVO.setTitle(rs
					.getString(WebUsageConstantsIF.DatabaseColumns.TITLE_COL));

			freqVO.setHiddenUrl(rs
					.getString(WebUsageConstantsIF.DatabaseColumns.HIDDENURL_COL));

			return freqVO;

		}

	}

	// SELECT FREQID,TOKENNAME,FREQ,URL,DESCRIPTION,TITLE,QUERY,USERID,HIDDENURL
	// FROM frequency
	private final class FrequencyVOMapper implements RowMapper<FrequencyVO> {

		public FrequencyVO mapRow(ResultSet rs, int arg1) throws SQLException {
			FrequencyVO freqVO = new FrequencyVO();

			freqVO.setFreqId(rs
					.getInt(WebUsageConstantsIF.DatabaseColumns.FREQID_COL));
			freqVO.setTokenName(rs
					.getString(WebUsageConstantsIF.DatabaseColumns.TOKENNAME_COL));
			freqVO.setFreq(rs
					.getInt(WebUsageConstantsIF.DatabaseColumns.FREQ_COL));

			freqVO.setDesc(rs
					.getString(WebUsageConstantsIF.DatabaseColumns.DESCRIPTION_COL));
			freqVO.setHiddenUrl(rs
					.getString(WebUsageConstantsIF.DatabaseColumns.HIDDENURL_COL));
			freqVO.setUrl(rs
					.getString(WebUsageConstantsIF.DatabaseColumns.URL_COL));
			freqVO.setQuery(rs
					.getString(WebUsageConstantsIF.DatabaseColumns.QUERY_COL));
			freqVO.setTitle(rs
					.getString(WebUsageConstantsIF.DatabaseColumns.TITLE_COL));
			freqVO.setUserId(rs
					.getString(WebUsageConstantsIF.DatabaseColumns.USERID_COL));
			return freqVO;

		}

	}

	@Override
	public StatusInfo removeStopword(String stopWord) {
		StatusInfo statusInfo = null;

		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(
					WebUsageConstantsIF.SQLS.REMOVE_STOPWORD_SQL, null, null);
			jdbcTemplate.update(sql, stopWord);
			statusInfo.setStatus(true);
			return statusInfo;
		} catch (Exception e) {
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			System.out.println("Exception" + e);
			e.printStackTrace();
			return statusInfo;
		}
	}

	@Override
	public List<String> retriveUserIds() {

		try {
			String sql = sqlProperties.getMessage(
					WebUsageConstantsIF.SQLS.RETRIVE_REGISTER_USERIDS_SQL,
					null, null);
			return jdbcTemplate.queryForList(sql, String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public String retrivePassword(String userId) {
		try {
			String sql = sqlProperties.getMessage(
					WebUsageConstantsIF.SQLS.RETRIVE_PASSWORD_WHERE_USERID_SQL,
					null, null);
			return jdbcTemplate.queryForList(sql, String.class, userId).get(0);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public StatusInfo insertLogin(RegisterUser registerUser) {
		StatusInfo insertLoginStatus = null;
		try {
			insertLoginStatus = new StatusInfo();
			String sql = sqlProperties.getMessage(
					WebUsageConstantsIF.SQLS.INSERT_LOGIN_SQL, null, null);

			jdbcTemplate.update(sql, new Object[] {
					registerUser.getFirstName(), registerUser.getLastName(),
					registerUser.getUserPassword(), registerUser.getEmailId(),
					registerUser.getUserId() },
					new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
							Types.VARCHAR, Types.VARCHAR });
			insertLoginStatus.setStatus(true);
			return insertLoginStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			insertLoginStatus = new StatusInfo();
			insertLoginStatus.setErrMsg(e.getMessage());
			insertLoginStatus.setStatus(false);
			return insertLoginStatus;

		}
	}

	// INSERT_SEARCH_SQL=INSERT INTO
	// SEARCH(USERID,QUERY,URL,TITLE,DESCRIPTION,HIDDENURL)VALUES(:userId,:query,:urlInfo,:title,:description,:hiddenUrl)
	@Override
	public StatusInfo insertGoogleResult(SearchObj searchObj) {

		StatusInfo statusInfo = new StatusInfo();
		try {
			String sqlKey = WebUsageConstantsIF.SQLS.INSERT_SEARCH_SQL;
			String sql = sqlProperties.getMessage(sqlKey, null, null);

			Map<String, Object> queryMap = new HashMap<String, Object>();

			queryMap.put(WebUsageConstantsIF.Keys.USERID_KEY,
					searchObj.getUserId());
			queryMap.put(WebUsageConstantsIF.Keys.QUERY_KEY,
					searchObj.getQuery());
			queryMap.put(WebUsageConstantsIF.Keys.URLINFO_KEY,
					searchObj.getUrl());
			queryMap.put(WebUsageConstantsIF.Keys.TITLE_KEY,
					searchObj.getTitle());
			queryMap.put(WebUsageConstantsIF.Keys.DESC_KEY, searchObj.getDesc());
			queryMap.put(WebUsageConstantsIF.Keys.HIDDENURL_KEY,
					searchObj.getHiddenUrl());

			namedJdbcTemplate.update(sql, queryMap);

			statusInfo.setStatus(true);

		} catch (Exception e) {
			System.out.println("Exception is:");
			System.out.println(e.getMessage());
			e.printStackTrace();
			statusInfo.setErrMsg(WebUsageConstantsIF.Message.MESSAGE_INTERNAL);
			statusInfo.setExceptionMsg(e.getMessage());
			statusInfo.setStatus(false);
			return statusInfo;

		}
		return statusInfo;
	}

	@Override
	public List<SearchObj> retriveSearchesForUserId(String userId) {
		try {

			String sql = sqlProperties.getMessage(
					WebUsageConstantsIF.SQLS.RETRIVE_SEARCH_SQL, null, null);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(WebUsageConstantsIF.Keys.USERID_KEY, userId);
			return namedJdbcTemplate.query(sql, paramMap, new SearchMapper());

		} catch (Exception e) {
			System.out.println("Exception" + e);
			return null;
		}
	}

	private final class SearchMapper implements RowMapper<SearchObj> {

		public SearchObj mapRow(ResultSet rs, int arg1) throws SQLException {
			SearchObj searchObj = new SearchObj();

			searchObj.setSearchId(rs
					.getInt(WebUsageConstantsIF.DatabaseColumns.SEARCHID_COL));
			searchObj
					.setDesc(rs
							.getString(WebUsageConstantsIF.DatabaseColumns.DESCRIPTION_COL));
			searchObj
					.setHiddenUrl(rs
							.getString(WebUsageConstantsIF.DatabaseColumns.HIDDENURL_COL));
			searchObj.setUrl(rs
					.getString(WebUsageConstantsIF.DatabaseColumns.URL_COL));
			searchObj.setQuery(rs
					.getString(WebUsageConstantsIF.DatabaseColumns.QUERY_COL));
			searchObj.setTitle(rs
					.getString(WebUsageConstantsIF.DatabaseColumns.TITLE_COL));
			searchObj.setQuery(rs
					.getString(WebUsageConstantsIF.DatabaseColumns.QUERY_COL));
			searchObj.setUserId(rs
					.getString(WebUsageConstantsIF.DatabaseColumns.USERID_COL));
			return searchObj;
		}

	}

	private final class CleanSearchMapper implements RowMapper<SearchObj> {

		public SearchObj mapRow(ResultSet rs, int arg1) throws SQLException {
			SearchObj searchObj = new SearchObj();

			searchObj.setSearchId(rs
					.getInt(WebUsageConstantsIF.DatabaseColumns.SEARCHID_COL));

			searchObj
					.setDesc(rs
							.getString(WebUsageConstantsIF.DatabaseColumns.DESCRIPTION_COL));
			searchObj
					.setHiddenUrl(rs
							.getString(WebUsageConstantsIF.DatabaseColumns.HIDDENURL_COL));
			searchObj.setUrl(rs
					.getString(WebUsageConstantsIF.DatabaseColumns.URL_COL));
			searchObj.setQuery(rs
					.getString(WebUsageConstantsIF.DatabaseColumns.QUERY_COL));
			searchObj.setTitle(rs
					.getString(WebUsageConstantsIF.DatabaseColumns.TITLE_COL));
			searchObj.setQuery(rs
					.getString(WebUsageConstantsIF.DatabaseColumns.QUERY_COL));
			searchObj.setUserId(rs
					.getString(WebUsageConstantsIF.DatabaseColumns.USERID_COL));
			return searchObj;
		}

	}

	@Override
	public List<SearchObj> retriveCleanReviews(String userId) {
		try {

			String sql = sqlProperties.getMessage(
					WebUsageConstantsIF.SQLS.RETRIVE_CLEANSEARCH_SQL, null,
					null);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(WebUsageConstantsIF.Keys.USERID_KEY, userId);
			return namedJdbcTemplate.query(sql, paramMap,
					new CleanSearchMapper());

		} catch (Exception e) {
			System.out.println("Exception" + e);
			return null;
		}
	}

	@Override
	public StatusInfo insertCleanDetails(SearchObj searchObj) {
		StatusInfo statusInfo = new StatusInfo();
		try {
			String sqlKey = WebUsageConstantsIF.SQLS.INSERT_CLEANSEARCH_SQL;
			String sql = sqlProperties.getMessage(sqlKey, null, null);

			Map<String, Object> queryMap = new HashMap<String, Object>();

			queryMap.put(WebUsageConstantsIF.Keys.USERID_KEY,
					searchObj.getUserId());
			queryMap.put(WebUsageConstantsIF.Keys.QUERY_KEY,
					searchObj.getQuery());
			queryMap.put(WebUsageConstantsIF.Keys.URLINFO_KEY,
					searchObj.getUrl());
			queryMap.put(WebUsageConstantsIF.Keys.TITLE_KEY,
					searchObj.getTitle());
			queryMap.put(WebUsageConstantsIF.Keys.DESC_KEY, searchObj.getDesc());
			queryMap.put(WebUsageConstantsIF.Keys.HIDDENURL_KEY,
					searchObj.getHiddenUrl());

			namedJdbcTemplate.update(sql, queryMap);

			statusInfo.setStatus(true);

		} catch (Exception e) {
			System.out.println("Exception is:");
			System.out.println(e.getMessage());
			e.printStackTrace();
			statusInfo.setErrMsg(WebUsageConstantsIF.Message.MESSAGE_INTERNAL);
			statusInfo.setExceptionMsg(e.getMessage());
			statusInfo.setStatus(false);
			return statusInfo;

		}
		return statusInfo;
	}

	@Override
	public StatusInfo deleteAllCleanReviews(String userId) {
		StatusInfo statusInfo = null;

		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties
					.getMessage(
							WebUsageConstantsIF.SQLS.DELETE_ALL_CLEANSEARCHES_WHERE_USERID_SQL,
							null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(WebUsageConstantsIF.Keys.USERID_KEY, userId);

			namedJdbcTemplate.update(sql, paramMap);
			statusInfo.setStatus(true);
			return statusInfo;
		} catch (Exception e) {
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			System.out.println("Exception" + e);
			e.printStackTrace();
			return statusInfo;
		}
	}

	@Override
	public StatusInfo deleteAllTokens(String userId) {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties
					.getMessage(
							WebUsageConstantsIF.SQLS.DELETE_ALL_TOKENS_WHERE_USERID_SQL,
							null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(WebUsageConstantsIF.Keys.USERID_KEY, userId);

			namedJdbcTemplate.update(sql, paramMap);
			statusInfo.setStatus(true);
			return statusInfo;
		} catch (Exception e) {
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			System.out.println("Exception" + e);
			e.printStackTrace();
			return statusInfo;
		}
	}

	@Override
	public StatusInfo deleteFromFrequency(String userId) {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(
					WebUsageConstantsIF.SQLS.DELETE_ALL_FREQ_WHERE_USERID_SQL,
					null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(WebUsageConstantsIF.Keys.USERID_KEY, userId);

			namedJdbcTemplate.update(sql, paramMap);
			statusInfo.setStatus(true);
			return statusInfo;
		} catch (Exception e) {
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			System.out.println("Exception" + e);
			e.printStackTrace();
			return statusInfo;
		}
	}

	@Override
	public List<String> retriveQueryForUserIdFromTokenization(String userId) {

		List<String> queries = null;
		try {
			String sql = sqlProperties
					.getMessage(
							WebUsageConstantsIF.SQLS.RETRIVE_QUERY_SQL_WHERE_USERID_FROM_TOKENS_SQL,
							null, null);
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put(WebUsageConstantsIF.Keys.USERID_KEY, userId);
			queries = namedJdbcTemplate.queryForList(sql, queryMap,
					String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}

		return queries;
	}

	@Override
	public List<String> retriveUrlsForQueryAndUserId(String queryTemp,
			String userId) {
		List<String> urls = null;
		try {
			String sql = sqlProperties
					.getMessage(
							WebUsageConstantsIF.SQLS.RETRIVE_URL_SQL_WHERE_QUERY_USERID_FROM_TOKENS_SQL,
							null, null);
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put(WebUsageConstantsIF.Keys.QUERY_KEY, queryTemp);
			queryMap.put(WebUsageConstantsIF.Keys.USERID_KEY, userId);
			urls = namedJdbcTemplate.queryForList(sql, queryMap, String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}

		return urls;
	}

	@Override
	public List<String> retriveTokensFromTokensForUrlQueryAndUserId(String url,
			String queryTemp, String userId) {
		List<String> urls = null;
		try {
			String sql = sqlProperties
					.getMessage(
							WebUsageConstantsIF.SQLS.RETRIVE_TOKENNAMES_SQL_WHERE_URL_QUERY_USERID_FROM_TOKENS_SQL,
							null, null);
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put(WebUsageConstantsIF.Keys.URLINFO_KEY, url);
			queryMap.put(WebUsageConstantsIF.Keys.QUERY_KEY, queryTemp);
			queryMap.put(WebUsageConstantsIF.Keys.USERID_KEY, userId);
			urls = namedJdbcTemplate.queryForList(sql, queryMap, String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}

		return urls;
	}

	@Override
	public int retriveCountForTokenQueryURLUserId(String tokenTemp,
			String queryTemp, String url, String userId) {
		int count = 0;
		try {
			String sql = sqlProperties
					.getMessage(
							WebUsageConstantsIF.SQLS.RETRIVE_COUNT_SQL_WHERE_TOKENNAME_QUERY_URL,
							null, null);
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put(WebUsageConstantsIF.Keys.TOKENNAME_KEY, tokenTemp);
			queryMap.put(WebUsageConstantsIF.Keys.QUERY_KEY, queryTemp);
			queryMap.put(WebUsageConstantsIF.Keys.USERID_KEY, userId);
			count = namedJdbcTemplate.queryForInt(sql, queryMap);

		} catch (Exception e) {
			System.out.println("Exception" + e);
			return -1;
		}
		return count;
	}

	@Override
	public List<TokenVO> retriveTokenInfoFromTokensForUrlQueryAndUserId(
			String url, String queryTemp, String userId) {
		List<TokenVO> tokenList = null;
		try {
			String sql = sqlProperties
					.getMessage(
							WebUsageConstantsIF.SQLS.RETRIVE_ALLTOKENS_WHERE_URL_QUERY_USERID_SQL,
							null, null);
			tokenList = new ArrayList<TokenVO>();

			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put(WebUsageConstantsIF.Keys.USERID_KEY, userId);

			tokenList = namedJdbcTemplate.query(sql, queryMap,
					new TokenMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
		return tokenList;
	}

	@Override
	public TokenVO retriveTokenInfoForTokenNameUrlueryUserId(String tokenTemp,
			String url, String queryTemp, String userId) {
		TokenVO tokenVOMain = null;
		List<TokenVO> tokenList = null;
		try {
			tokenVOMain = new TokenVO();
			// SELECT
			// TOKENID,TOKENNAME,USERID,QUERY,URL,TITLE,DESCRIPTION,HIDDENURL
			// FROM TOKEN WHERE TOKENAME=:tokenName AND URL=:urlInfo AND
			// QUERY=:query AND USERID=:userId

			String sql = sqlProperties
					.getMessage(
							WebUsageConstantsIF.SQLS.RETRIVE_ALLTOKENS_WHERE_TOKENNAME_URL_QUERY_USERID_SQL,
							null, null);
			tokenList = new ArrayList<TokenVO>();

			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put(WebUsageConstantsIF.Keys.USERID_KEY, userId);
			queryMap.put(WebUsageConstantsIF.Keys.TOKENNAME_KEY, tokenTemp);
			queryMap.put(WebUsageConstantsIF.Keys.QUERY_KEY, queryTemp);
			queryMap.put(WebUsageConstantsIF.Keys.URLINFO_KEY, url);

			tokenList = namedJdbcTemplate.query(sql, queryMap,
					new TokenMapper());

			if (tokenList != null && !tokenList.isEmpty()) {
				tokenVOMain = tokenList.get(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return tokenVOMain;
		}
		return tokenVOMain;
	}

	@Override
	public StatusInfo deleteAllFeatureVector(String userId) {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties
					.getMessage(
							WebUsageConstantsIF.SQLS.DELETE_ALL_FEATURE_VECTORS_WHERE_USERID_SQL,
							null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(WebUsageConstantsIF.Keys.USERID_KEY, userId);

			namedJdbcTemplate.update(sql, paramMap);
			statusInfo.setStatus(true);
			return statusInfo;
		} catch (Exception e) {
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			System.out.println("Exception" + e);
			e.printStackTrace();
			return statusInfo;
		}
	}

	@Override
	public List<String> retriveQueryForUserIdFromFV(String userId) {

		try {
			String sql = sqlProperties
					.getMessage(
							WebUsageConstantsIF.SQLS.RETRIVE_QUERY_FOR_USERID_SQL_FROMFV,
							null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(WebUsageConstantsIF.Keys.USERID_KEY, userId);
			return namedJdbcTemplate.queryForList(sql, paramMap, String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<String> retriveUrlsForQueryAndUserIdFromFreq(String queryTemp,
			String userId) {
		try {
			String sql = sqlProperties
					.getMessage(
							WebUsageConstantsIF.SQLS.RETRIVE_URLS_FOR_QUERY_USERID_SQL_FROM_FREQ,
							null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(WebUsageConstantsIF.Keys.USERID_KEY, userId);
			paramMap.put(WebUsageConstantsIF.Keys.QUERY_KEY, queryTemp);

			return namedJdbcTemplate.queryForList(sql, paramMap, String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<String> retriveDistinctTokensFromFreqForQueryUrlAndUserId(
			String queryTemp, String url, String userId) {
		try {

			String sql = sqlProperties
					.getMessage(
							WebUsageConstantsIF.SQLS.RETRIVE_DISTINCT_TOKENS_FOR_QUERY_URL_USERID_SQL_FROM_FREQ,
							null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(WebUsageConstantsIF.Keys.USERID_KEY, userId);
			paramMap.put(WebUsageConstantsIF.Keys.URLINFO_KEY, url);
			paramMap.put(WebUsageConstantsIF.Keys.QUERY_KEY, queryTemp);

			return namedJdbcTemplate.queryForList(sql, paramMap, String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public int retriveCountOfUrlsInWhichTokenIsPresent(String queryTemp,
			String url, String tokenName, String userId) {
		try {

			String sql = sqlProperties
					.getMessage(
							WebUsageConstantsIF.SQLS.RETRIVE_COUNT_URLS_FOR_QUERY_URL_TOKENNAME_USERID_SQL_FROM_FREQ,
							null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(WebUsageConstantsIF.Keys.USERID_KEY, userId);
			paramMap.put(WebUsageConstantsIF.Keys.URLINFO_KEY, url);
			paramMap.put(WebUsageConstantsIF.Keys.QUERY_KEY, queryTemp);
			paramMap.put(WebUsageConstantsIF.Keys.TOKENNAME_KEY, queryTemp);

			return namedJdbcTemplate.queryForInt(sql, paramMap);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return -1;
		}
	}

	@Override
	public FrequencyVO retriveFreqForTokenNameQueryURLUserId(String queryTemp,
			String url, String tokenName, String userId) {
		try {

			String sql = sqlProperties
					.getMessage(
							WebUsageConstantsIF.SQLS.RETRIVE_FREVO_FOR_QUERY_URL_TOKENNAME_USERID_SQL_FROM_FREQ,
							null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(WebUsageConstantsIF.Keys.USERID_KEY, userId);
			paramMap.put(WebUsageConstantsIF.Keys.URLINFO_KEY, url);
			paramMap.put(WebUsageConstantsIF.Keys.QUERY_KEY, queryTemp);
			paramMap.put(WebUsageConstantsIF.Keys.TOKENNAME_KEY, queryTemp);

			return namedJdbcTemplate.query(sql, paramMap,
					new FrequencyVOMapper()).get(0);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	// INSERT INTO
	// featurevectors(FREQ,TOKENNAME,NOOFREVIEWS,IDFT,FEATUREVECTOR,URL,QUERY,DESCRIPTION,USERID,HIDDENURL,TITLE)
	// VALUES(:freq,:tokenName,:noOfReviews,:idft,:featureVector,:urlInfo,:query,:description,:userId,:hiddenUrl,:title)
	public StatusInfo insertFeatureVector(FeatureVO featureVO) {

		StatusInfo fvStatus = null;
		try {
			fvStatus = new StatusInfo();
			String sql = sqlProperties.getMessage(
					WebUsageConstantsIF.SQLS.INSERT_FEATUREVECTOR_SQL, null,
					null);

			Map<String, Object> queryMap = new HashMap<String, Object>();

			queryMap.put(WebUsageConstantsIF.Keys.FREQ_KEY, featureVO.getFreq());
			queryMap.put(WebUsageConstantsIF.Keys.TOKENNAME_KEY,
					featureVO.getTokenName());
			queryMap.put(WebUsageConstantsIF.Keys.NO_OF_REVIEWS_KEY,
					featureVO.getNoOfReviews());
			queryMap.put(WebUsageConstantsIF.Keys.IDFT_KEY, featureVO.getIdft());
			queryMap.put(WebUsageConstantsIF.Keys.FEATUREVECTOR_KEY,
					featureVO.getFeatureVector());
			queryMap.put(WebUsageConstantsIF.Keys.URLINFO_KEY,
					featureVO.getUrl());
			queryMap.put(WebUsageConstantsIF.Keys.QUERY_KEY,
					featureVO.getQuery());
			queryMap.put(WebUsageConstantsIF.Keys.DESC_KEY, featureVO.getDesc());
			queryMap.put(WebUsageConstantsIF.Keys.USERID_KEY,
					featureVO.getUserId());
			queryMap.put(WebUsageConstantsIF.Keys.HIDDENURL_KEY,
					featureVO.getHiddenUrl());
			queryMap.put(WebUsageConstantsIF.Keys.TITLE_KEY,
					featureVO.getTitle());

			namedJdbcTemplate.update(sql, queryMap);

			fvStatus.setStatus(true);
			return fvStatus;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION" + e.getMessage());
			fvStatus = new StatusInfo();
			fvStatus.setErrMsg(e.getMessage());
			fvStatus.setStatus(false);
			return fvStatus;

		}
	}

	@Override
	public List<FrequencyVO> retriveFreqInfo(String userId) {
		try {
			String sql = sqlProperties.getMessage(
					WebUsageConstantsIF.SQLS.RETRIVE_FREQ_WHERE_USERID_SQL,
					null, null);

			Map<String, Object> queryMap = new LinkedHashMap<String, Object>();
			queryMap.put(WebUsageConstantsIF.Keys.USERID_KEY, userId);

			return namedJdbcTemplate.query(sql, queryMap,
					new FrequencyVOMapper());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public StatusInfo removeReviews(String userId) {
		StatusInfo statusInfo;
		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties
					.getMessage(
							WebUsageConstantsIF.SQLS.DELETE_COMPLETEREVIEWS_FOR_USERID_SQL,
							null, null);

			Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
			paramMap.put(WebUsageConstantsIF.Keys.USERID_KEY, userId);

			namedJdbcTemplate.update(sql, paramMap);
			statusInfo.setStatus(true);
			return statusInfo;
		} catch (Exception e) {
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			System.out.println("Exception" + e);
			e.printStackTrace();
			return statusInfo;
		}
	}

	@Override
	public StatusInfo removeCleanReviews(String userId) {
		StatusInfo statusInfo;
		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties
					.getMessage(
							WebUsageConstantsIF.SQLS.DELETE_CLEANREVIEWS_FOR_USERID_SQL,
							null, null);

			Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
			paramMap.put(WebUsageConstantsIF.Keys.USERID_KEY, userId);

			namedJdbcTemplate.update(sql, paramMap);
			statusInfo.setStatus(true);
			return statusInfo;
		} catch (Exception e) {
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			System.out.println("Exception" + e);
			e.printStackTrace();
			return statusInfo;
		}
	}

	@Override
	public StatusInfo removeFrequency(String userId) {

		StatusInfo statusInfo;
		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(
					WebUsageConstantsIF.SQLS.DELETE_FREQ_FOR_USERID_SQL, null,
					null);

			Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
			paramMap.put(WebUsageConstantsIF.Keys.USERID_KEY, userId);

			namedJdbcTemplate.update(sql, paramMap);
			statusInfo.setStatus(true);
			return statusInfo;
		} catch (Exception e) {
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			System.out.println("Exception" + e);
			e.printStackTrace();
			return statusInfo;
		}
	}

	@Override
	public StatusInfo removeFV(String userId) {
		StatusInfo statusInfo;
		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(
					WebUsageConstantsIF.SQLS.DELETE_FV_FOR_USERID_SQL, null,
					null);

			Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
			paramMap.put(WebUsageConstantsIF.Keys.USERID_KEY, userId);

			namedJdbcTemplate.update(sql, paramMap);
			statusInfo.setStatus(true);
			return statusInfo;
		} catch (Exception e) {
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			System.out.println("Exception" + e);
			e.printStackTrace();
			return statusInfo;
		}
	}

	@Override
	public StatusInfo removeBestFV(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo removeTokens(String userId) {

		StatusInfo statusInfo;
		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(
					WebUsageConstantsIF.SQLS.DELETE_TOKENSFOR_USERID_SQL, null,
					null);

			Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
			paramMap.put(WebUsageConstantsIF.Keys.USERID_KEY, userId);

			namedJdbcTemplate.update(sql, paramMap);
			statusInfo.setStatus(true);
			return statusInfo;
		} catch (Exception e) {
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
			System.out.println("Exception" + e);
			e.printStackTrace();
			return statusInfo;
		}
	}

	@Override
	public List<String> retriveDistinctUrlsForUserIdFromFeatureVector(
			String userId) {

		try {
			String sql = sqlProperties.getMessage(
					WebUsageConstantsIF.SQLS.RETRIVE_DISTINCTURL_FROM_FV_SQL,
					null, null);

			Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
			paramMap.put(WebUsageConstantsIF.Keys.USERID_KEY, userId);

			return namedJdbcTemplate.queryForList(sql, paramMap, String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}

	}

	@Override
	// SELECT FEATUREVECTOR FROM featurevectors WHERE USERID=:userId AND
	// TOKENNAME=:tokenName AND USERID=:userId
	public List<Double> retriveFVForUrlTokenAndUserId(String distinctUrlTemp,
			String tokenTemp, String userId) {

		try {
			String sql = sqlProperties
					.getMessage(
							WebUsageConstantsIF.SQLS.RETRIVE_FV_FOR_URL_TOKEN_USERID_SQL,
							null, null);

			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put(WebUsageConstantsIF.Keys.URLINFO_KEY, distinctUrlTemp);
			queryMap.put(WebUsageConstantsIF.Keys.TOKENNAME_KEY, tokenTemp);
			queryMap.put(WebUsageConstantsIF.Keys.USERID_KEY, userId);

			return namedJdbcTemplate.queryForList(sql, queryMap, Double.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public StatusInfo deleteFromBestFV() {

		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties
					.getMessage(
							WebUsageConstantsIF.SQLS.DELETE_FROM_BESTFV_SQL,
							null, null);
			Map<String, Object> paramMap = null;
			namedJdbcTemplate.update(sql, paramMap);
			statusInfo.setStatus(true);

		} catch (Exception e) {
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setErrMsg(e.getMessage());
		}

		return statusInfo;
	}

	@Override
	// INSERT INTO BESTFV(FEATUREVECTOR,URL) VALUES(:featureVector,:urlInfo)
	public StatusInfo insertBestFV(List<BestFeatureVector> bestFV) {

		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(
					WebUsageConstantsIF.SQLS.INSERT_BESTFV_SQL, null, null);
			for (BestFeatureVector bfv : bestFV) {
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put(WebUsageConstantsIF.Keys.FEATUREVECTOR_KEY,
						bfv.getFeatureVector());
				paramMap.put(WebUsageConstantsIF.Keys.URLINFO_KEY, bfv.getUrl());
				namedJdbcTemplate.update(sql, paramMap);
			}
			statusInfo.setStatus(true);
		} catch (Exception e) {
			statusInfo = new StatusInfo();
			statusInfo.setStatus(false);
			statusInfo.setExceptionMsg(e.getMessage());
		}
		return statusInfo;
	}

	@Override
	public List<BestFeatureVector> rateUrls() {

		List<BestFeatureVector> urlList = null;
		try {

			urlList = new ArrayList<BestFeatureVector>();
			String sql = sqlProperties.getMessage(
					WebUsageConstantsIF.SQLS.RANK_BESTFV_SQL, null, null);

			Map<String, Object> paramMap = null;
			urlList = namedJdbcTemplate.query(sql, paramMap,
					new BestFeatureVectorMapper());

		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}
		return urlList;

	}

	private final class BestFeatureVectorMapper implements
			RowMapper<BestFeatureVector> {

		public BestFeatureVector mapRow(ResultSet rs, int arg1)
				throws SQLException {

			BestFeatureVector bestFeatureVector = new BestFeatureVector();

			bestFeatureVector
					.setFeatureVector(rs
							.getDouble(WebUsageConstantsIF.DatabaseColumns.FEATUREVECTOR_COL));

			bestFeatureVector.setUrl(rs
					.getString(WebUsageConstantsIF.DatabaseColumns.URL_COL));

			return bestFeatureVector;

		}

	}

	@Override
	public SearchObj retriveGoogleResultForUrl(BestFeatureVector urlInfo,
			String userId) {

		List<SearchObj> googleResult = new ArrayList<SearchObj>();
		try {

			String sql = sqlProperties
					.getMessage(
							WebUsageConstantsIF.SQLS.RETRIVE_GOOGLERESULT_FOR_URL_USERID_SQL,
							null, null);

			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put(WebUsageConstantsIF.Keys.URLINFO_KEY, urlInfo.getUrl());
			queryMap.put(WebUsageConstantsIF.Keys.USERID_KEY, userId);

			googleResult = namedJdbcTemplate.query(sql, queryMap,
					new SearchMapper());

			if (null == googleResult
					|| (googleResult != null && googleResult.isEmpty())) {
				return null;
			}

			SearchObj serObj = googleResult.get(0);
			return serObj;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public List<String> retriveUsersFromSettings() {
		try {
			String sql = sqlProperties.getMessage(
					WebUsageConstantsIF.SQLS.RETRIVE_SETTINGSUSER_SQL, null,
					null);
			SqlParameterSource paramMap = null;
			return namedJdbcTemplate.queryForList(sql, paramMap, String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public StatusInfo insertSettings(int threshold, String loginId) {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(
					WebUsageConstantsIF.SQLS.INSERT_SETTINGS_SQL, null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(WebUsageConstantsIF.Keys.LOGINID_KEY, loginId);
			paramMap.put(WebUsageConstantsIF.Keys.THRESHOLD_KEY, threshold);
			namedJdbcTemplate.update(sql, paramMap);
			statusInfo.setStatus(true);

			return statusInfo;

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
	public StatusInfo updateSettings(int threshold, String loginId) {
		StatusInfo statusInfo = null;
		try {
			statusInfo = new StatusInfo();
			String sql = sqlProperties.getMessage(
					WebUsageConstantsIF.SQLS.UPDATE_SETTINGS_SQL, null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(WebUsageConstantsIF.Keys.THRESHOLD_KEY, threshold);
			paramMap.put(WebUsageConstantsIF.Keys.LOGINID_KEY, loginId);
			namedJdbcTemplate.update(sql, paramMap);
			statusInfo.setStatus(true);

			return statusInfo;

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
	public int retriveSettingsForUserId(String userId) {

		int settings = -1;
		try {

			String sql = sqlProperties.getMessage(
					WebUsageConstantsIF.SQLS.RETRIVE_SETTINGS_FOR_USERID_SQL,
					null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(WebUsageConstantsIF.Keys.LOGINID_KEY, userId);

			settings = namedJdbcTemplate.queryForInt(sql, paramMap);

		} catch (Exception e) {
			e.printStackTrace();
			settings = -1;
		}

		return settings;
	}

	@Override
	public List<String> retriveConceptsForUserId(String userId, int settings) {

		List<String> conceptList = null;
		try {

			String sql = sqlProperties
					.getMessage(
							WebUsageConstantsIF.SQLS.RETRIVE_CONCEPTS_FOR_USERID_SETTINGS_SQL,
							null, null);

			// Hash Map
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(WebUsageConstantsIF.Keys.USERID_KEY, userId);
			paramMap.put(WebUsageConstantsIF.Keys.FREQ_KEY, settings);

			conceptList = namedJdbcTemplate.queryForList(sql, paramMap,
					String.class);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return conceptList;

	}

	@Override
	public List<String> retriveDistinctUrlsForUserId(String userId) {

		List<String> distinctUrls = null;
		try {
			String sql = sqlProperties
					.getMessage(
							WebUsageConstantsIF.SQLS.RETRIVE_DISTINCTURL_FOR_USERID_SQL,
							null, null);

			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put(WebUsageConstantsIF.Keys.USERID_KEY, userId);

			distinctUrls = namedJdbcTemplate.queryForList(sql, queryMap,
					String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
		return distinctUrls;
	}

	@Override
	public List<String> retriveTokensFromTokensForUrlAndUserId(String url,
			String userId) {
		try {
			String sql = sqlProperties.getMessage(
					WebUsageConstantsIF.SQLS.RETRIVE_TOKENS_FOR_URL_USERID_SQL,
					null, null);

			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put(WebUsageConstantsIF.Keys.URLINFO_KEY, url);
			queryMap.put(WebUsageConstantsIF.Keys.USERID_KEY, userId);

			return namedJdbcTemplate.queryForList(sql, queryMap, String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public int retriveCountForTokenURLUserId(String tokenTemp, String url,
			String userId) {

		int count = 0;
		try {

			String sql = sqlProperties
					.getMessage(
							WebUsageConstantsIF.SQLS.RETRIVE_COUNT_FOR_TOKEN_URL_USERID_SQL,
							null, null);

			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put(WebUsageConstantsIF.Keys.URLINFO_KEY, url);
			queryMap.put(WebUsageConstantsIF.Keys.USERID_KEY, userId);
			queryMap.put(WebUsageConstantsIF.Keys.TOKENNAME_KEY, tokenTemp);

			count = namedJdbcTemplate.queryForInt(sql, queryMap);

		} catch (Exception e)

		{
			count = -1;
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public TokenVO retriveTokenInfoForTokenNameUrlUserId(String tokenTemp,
			String url, String userId) {
		TokenVO tokenVOMain = null;
		List<TokenVO> tokenList = null;
		try {
			tokenVOMain = new TokenVO();
			// SELECT
			// TOKENID,TOKENNAME,USERID,QUERY,URL,TITLE,DESCRIPTION,HIDDENURL
			// FROM TOKEN WHERE TOKENAME=:tokenName AND URL=:urlInfo AND
			// QUERY=:query AND USERID=:userId

			String sql = sqlProperties
					.getMessage(
							WebUsageConstantsIF.SQLS.RETRIVE_ALLTOKENS_WHERE_TOKENNAME_URL_USERID_SQL,
							null, null);
			tokenList = new ArrayList<TokenVO>();

			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put(WebUsageConstantsIF.Keys.USERID_KEY, userId);
			queryMap.put(WebUsageConstantsIF.Keys.TOKENNAME_KEY, tokenTemp);
			queryMap.put(WebUsageConstantsIF.Keys.URLINFO_KEY, url);

			tokenList = namedJdbcTemplate.query(sql, queryMap,
					new TokenMapper());

			if (tokenList != null && !tokenList.isEmpty()) {
				tokenVOMain = tokenList.get(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return tokenVOMain;
		}
		return tokenVOMain;
	}

	@Override
	public List<String> retriveDistinctUrlsForQueryAndUserIdFromFreq(
			String queryTemp, String userId) {

		List<String> distinctUrls = null;
		try {
			String sql = sqlProperties
					.getMessage(
							WebUsageConstantsIF.SQLS.RETRIVE_DISTINCTURL_FOR_USERID_FROM_FREQ_SQL,
							null, null);

			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put(WebUsageConstantsIF.Keys.USERID_KEY, userId);

			distinctUrls = namedJdbcTemplate.queryForList(sql, queryMap,
					String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
		return distinctUrls;

	}

	@Override
	public List<String> retriveDistinctQueryForUserIdFromFV(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> retriveDistinctUrlsForUserIdFromFrequency(String userId) {

		try {

			String sql = sqlProperties
					.getMessage(
							WebUsageConstantsIF.SQLS.RETRIVE_DISTINCT_URLS_FROM_FREQ_FOR_USERID_SQL,
							null, null);

			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put(WebUsageConstantsIF.Keys.USERID_KEY, userId);

			return namedJdbcTemplate.queryForList(sql, queryMap, String.class);

		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<String> retriveDistinctTokensFromFreqForUrlAndUserId(
			String url, String userId) {
		try {
			String sql = sqlProperties
					.getMessage(
							WebUsageConstantsIF.SQLS.RETRIVE_DISTINCT_TOKENS_FOR_URL_USERID_FROM_FREQ_SQL,
							null, null);

			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put(WebUsageConstantsIF.Keys.URLINFO_KEY, url);
			queryMap.put(WebUsageConstantsIF.Keys.USERID_KEY, userId);

			return namedJdbcTemplate.queryForList(sql, queryMap, String.class);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}
	}

	@Override
	public int retriveCountOfUrlsInWhichTokenIsPresent(String tokenName,
			String userId) {
		int count = 0;
		try {

			String sql = sqlProperties
					.getMessage(
							WebUsageConstantsIF.SQLS.RETRIVE_COUNT_FOR_TOKEN_WHERE_TOKENNAME_USERID_SQL,
							null, null);

			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put(WebUsageConstantsIF.Keys.USERID_KEY, userId);
			queryMap.put(WebUsageConstantsIF.Keys.TOKENNAME_KEY, tokenName);

			List<String> urls = namedJdbcTemplate.queryForList(sql, queryMap,
					String.class);

			if (null == urls) {
				count = 0;
			} else {
				count = urls.size();
			}

		} catch (Exception e)

		{
			count = -1;
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public FrequencyVO retriveFreqForURLAndTokenNameAndUserId(String url,
			String tokenName, String userId) {

		try {

			String sql = sqlProperties
					.getMessage(
							WebUsageConstantsIF.SQLS.RETRIVE_FREVO_FOR_URL_TOKENNAME_USERID_SQL_FROM_FREQ,
							null, null);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(WebUsageConstantsIF.Keys.USERID_KEY, userId);
			paramMap.put(WebUsageConstantsIF.Keys.URLINFO_KEY, url);
			paramMap.put(WebUsageConstantsIF.Keys.TOKENNAME_KEY, tokenName);

			return namedJdbcTemplate.query(sql, paramMap,
					new FrequencyVOMapper()).get(0);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPTION ----->" + e.getMessage());
			return null;
		}

	}

}