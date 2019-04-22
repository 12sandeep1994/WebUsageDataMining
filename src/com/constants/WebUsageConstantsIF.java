package com.constants;

public interface WebUsageConstantsIF {

	interface SQLS {
		public static final String INSERT_REVIEW_SQL = "INSERT_REVIEW_SQL";
		public static final String RETRIVE_REVIEWS_SQL = "RETRIVE_REVIEWS_SQL";
		public static final String RETRIVE_ALLREVIEWS_SQL = "RETRIVE_ALLREVIEWS_SQL";
		public static final String RETRIVE_UNIQUE_PRODUCTIDS_SQL = "RETRIVE_UNIQUE_PRODUCTIDS_SQL";
		public static final String INSERT_STOPWORD_SQL = "INSERT_STOPWORD_SQL";
		public static final String RETRIVE_STOPWORDS_FULL_SQL = "RETRIVE_STOPWORDS_FULL_SQL";
		public static final String RETRIVE_STOPWORDS_SQL = "RETRIVE_STOPWORDS_SQL";
		public static final String INSERT_CLEANDETAILS_SQL = "INSERT_CLEANDETAILS_SQL";
		public static final String DELETE_CLEANREVIEWS_SQL = "DELETE_CLEANREVIEWS_SQL";
		public static final String DELETE_ALLCLEANREVIEWS_SQL = "DELETE_ALLCLEANREVIEWS_SQL";
		public static final String INSERT_TOKENS_SQL = "INSERT_TOKENS_SQL";
		public static final String INSERT_FEATUREVECTOR_SQL = "INSERT_FEATUREVECTOR_SQL";
		public static final String RETRIVE_FEATUREVO_WHERE_TYPE_SQL = "RETRIVE_FEATUREVO_WHERE_TYPE_SQL";
		public static final String DELETE_FV_WHERE_PRODUCTTYPE_SQL = "DELETE_FV_WHERE_PRODUCTTYPE_SQL";
		public static final String INSERT_BESTFV_SQL = "INSERT_BESTFV_SQL";
		public static final String REMOVE_STOPWORD_SQL = "REMOVE_STOPWORD_SQL";
		public static final String DELETE_COMPLETEREVIEWS_SQL = "DELETE_COMPLETEREVIEWS_SQL";
		public static final String DELETE_COMPLETE_CLEANREVIEWS_SQL = "DELETE_COMPLETE_CLEANREVIEWS_SQL";
		public static final String DELETE_COMPLETE_FREQ_SQL = "DELETE_COMPLETE_CLEANREVIEWS_SQL";
		public static final String DELETE_COMPLETE_FV_SQL = "DELETE_COMPLETE_FV_SQL";
		public static final String DELETE_COMPLETE_BESTFV_SQL = "DELETE_COMPLETE_BESTFV_SQL";
		public static final String DELETE_COMPLETE_TOKENS_SQL = "DELETE_COMPLETE_TOKENS_SQL";
		public static final String RETRIVE_REGISTER_USERIDS_SQL = "RETRIVE_REGISTER_USERIDS_SQL";
		public static final String RETRIVE_PASSWORD_WHERE_USERID_SQL = "RETRIVE_PASSWORD_WHERE_USERID_SQL";
		public static final String INSERT_LOGIN_SQL = "INSERT_LOGIN_SQL";
		public static final String INSERT_SEARCH_SQL = "INSERT_SEARCH_SQL";
		public static final String RETRIVE_SEARCH_SQL = "RETRIVE_SEARCH_SQL";
		public static final String INSERT_CLEANSEARCH_SQL = "INSERT_CLEANSEARCH_SQL";
		public static final String RETRIVE_CLEANSEARCH_SQL = "RETRIVE_CLEANSEARCH_SQL";
		public static final String DELETE_ALL_CLEANSEARCHES_WHERE_USERID_SQL = "DELETE_ALL_CLEANSEARCHES_WHERE_USERID_SQL";
		public static final String RETRIVE_ALLTOKENS_WHERE_USERID_SQL = "RETRIVE_ALLTOKENS_WHERE_USERID_SQL";
		public static final String DELETE_ALL_TOKENS_WHERE_USERID_SQL = "DELETE_ALL_TOKENS_WHERE_USERID_SQL";
		public static final String DELETE_ALL_FREQ_WHERE_USERID_SQL = "DELETE_ALL_FREQ_WHERE_USERID_SQL";
		public static final String RETRIVE_QUERY_SQL_WHERE_USERID_FROM_TOKENS_SQL = "RETRIVE_QUERY_SQL_WHERE_USERID_FROM_TOKENS_SQL";
		public static final String RETRIVE_URL_SQL_WHERE_QUERY_USERID_FROM_TOKENS_SQL = "RETRIVE_URL_SQL_WHERE_QUERY_USERID_FROM_TOKENS_SQL";
		public static final String RETRIVE_TOKENNAMES_SQL_WHERE_URL_QUERY_USERID_FROM_TOKENS_SQL = "RETRIVE_TOKENNAMES_SQL_WHERE_URL_QUERY_USERID_FROM_TOKENS_SQL";
		public static final String RETRIVE_COUNT_SQL_WHERE_TOKENNAME_QUERY_URL = "RETRIVE_COUNT_SQL_WHERE_TOKENNAME_QUERY_URL";
		public static final String RETRIVE_ALLTOKENS_WHERE_URL_QUERY_USERID_SQL = "RETRIVE_ALLTOKENS_WHERE_URL_QUERY_USERID_SQL";
		public static final String RETRIVE_ALLTOKENS_WHERE_TOKENNAME_URL_QUERY_USERID_SQL = "RETRIVE_ALLTOKENS_WHERE_TOKENNAME_URL_QUERY_USERID_SQL";
		public static final String DELETE_ALL_FEATURE_VECTORS_WHERE_USERID_SQL = "DELETE_ALL_FEATURE_VECTORS_WHERE_USERID_SQL";
		public static final String RETRIVE_QUERY_FOR_USERID_SQL_FROMFV = "RETRIVE_QUERY_FOR_USERID_SQL_FROMFV";
		public static final String RETRIVE_URLS_FOR_QUERY_USERID_SQL_FROM_FREQ = "RETRIVE_URLS_FOR_QUERY_USERID_SQL_FROM_FREQ";
		public static final String RETRIVE_DISTINCT_TOKENS_FOR_QUERY_URL_USERID_SQL_FROM_FREQ = "RETRIVE_DISTINCT_TOKENS_FOR_QUERY_URL_USERID_SQL_FROM_FREQ";
		public static final String RETRIVE_COUNT_URLS_FOR_QUERY_URL_TOKENNAME_USERID_SQL_FROM_FREQ = "RETRIVE_COUNT_URLS_FOR_QUERY_URL_TOKENNAME_USERID_SQL_FROM_FREQ";
		public static final String RETRIVE_FREVO_FOR_QUERY_URL_TOKENNAME_USERID_SQL_FROM_FREQ = "RETRIVE_FREVO_FOR_QUERY_URL_TOKENNAME_USERID_SQL_FROM_FREQ";
		public static final String INSERT_FREQUENCY_SQL = "INSERT_FREQUENCY_SQL";
		public static final String RETRIVE_FEATUREVO_WHERE_USERID_SQL = "RETRIVE_FEATUREVO_WHERE_USERID_SQL";
		public static final String RETRIVE_FREQ_WHERE_USERID_SQL = "RETRIVE_FREQ_WHERE_USERID_SQL";
		public static final String DELETE_COMPLETEREVIEWS_FOR_USERID_SQL = "DELETE_COMPLETEREVIEWS_FOR_USERID_SQL";
		public static final String DELETE_CLEANREVIEWS_FOR_USERID_SQL = "DELETE_CLEANREVIEWS_FOR_USERID_SQL";
		public static final String DELETE_FREQ_FOR_USERID_SQL = "DELETE_FREQ_FOR_USERID_SQL";
		public static final String DELETE_FV_FOR_USERID_SQL = "DELETE_FV_FOR_USERID_SQL";
		public static final String DELETE_TOKENSFOR_USERID_SQL = "DELETE_TOKENSFOR_USERID_SQL";
		public static final String RETRIVE_DISTINCTURL_FROM_FV_SQL = "RETRIVE_DISTINCTURL_FROM_FV_SQL";
		public static final String RETRIVE_FV_FOR_URL_TOKEN_USERID_SQL = "RETRIVE_FV_FOR_URL_TOKEN_USERID_SQL";
		public static final String DELETE_FROM_BESTFV_SQL = "DELETE_FROM_BESTFV_SQL";
		public static final String RANK_BESTFV_SQL = "RANK_BESTFV_SQL";
		public static final String RETRIVE_GOOGLERESULT_FOR_URL_USERID_SQL = "RETRIVE_GOOGLERESULT_FOR_URL_USERID_SQL";
		public static final String RETRIVE_SETTINGSUSER_SQL = "RETRIVE_SETTINGSUSER_SQL";
		public static final String INSERT_SETTINGS_SQL = "INSERT_SETTINGS_SQL";
		public static final String UPDATE_SETTINGS_SQL = "UPDATE_SETTINGS_SQL";
		public static final String RETRIVE_SETTINGS_FOR_USERID_SQL = "RETRIVE_SETTINGS_FOR_USERID_SQL";
		public static final String RETRIVE_CONCEPTS_FOR_USERID_SETTINGS_SQL = "RETRIVE_CONCEPTS_FOR_USERID_SETTINGS_SQL";
		public static final String RETRIVE_DISTINCTURL_FOR_USERID_SQL = "RETRIVE_DISTINCTURL_FOR_USERID_SQL";
		public static final String RETRIVE_TOKENS_FOR_URL_USERID_SQL = "RETRIVE_TOKENS_FOR_URL_USERID_SQL";
		public static final String RETRIVE_COUNT_FOR_TOKEN_URL_USERID_SQL = "RETRIVE_COUNT_FOR_TOKEN_URL_USERID_SQL";
		public static final String RETRIVE_ALLTOKENS_WHERE_TOKENNAME_URL_USERID_SQL = "RETRIVE_ALLTOKENS_WHERE_TOKENNAME_URL_USERID_SQL";
		public static final String RETRIVE_DISTINCTURL_FOR_USERID_FROM_FREQ_SQL = "RETRIVE_DISTINCTURL_FOR_USERID_FROM_FREQ_SQL";
		public static final String RETRIVE_DISTINCT_TOKENS_FOR_URL_USERID_FROM_FREQ_SQL = "RETRIVE_DISTINCT_TOKENS_FOR_URL_USERID_FROM_FREQ_SQL";
		public static final String RETRIVE_COUNT_FOR_TOKEN_WHERE_TOKENNAME_USERID_SQL = "RETRIVE_COUNT_FOR_TOKEN_WHERE_TOKENNAME_USERID_SQL";
		public static final String RETRIVE_FREVO_FOR_URL_TOKENNAME_USERID_SQL_FROM_FREQ = "RETRIVE_FREVO_FOR_URL_TOKENNAME_USERID_SQL_FROM_FREQ";
		public static final String RETRIVE_DISTINCT_URLS_FROM_FREQ_FOR_USERID_SQL = "RETRIVE_DISTINCT_URLS_FROM_FREQ_FOR_USERID_SQL";  

	}

	interface CONSTANTS {

		public static final String SPACE = "  ";
		public static final String REVIEWDIVID_AMAZON = "div.reviewText";
		public static final String REVIEWDIVID_FLIPKART = "p";
		public static final String REVIEWCLASS_FLIPKART = "line bmargin10";
		public static final String AMAZON = "AMAZON";
		public static final String FLIPKART = "FLIPKART";
		public static final String REVIEW = "review";
		public static final String CLEANREVIEW = "cleanreviews";
		public static final String TOKENS = "tokens";
		public static final String FREQUENCY = "freq";
		public static final String FV = "featureVector";
		public static final String BESTFV = "bestFv";

	}

	interface Message {

		public static final String REVIEW_FAILED = "Review could not be Stored";
		public static final String MESSAGE_INTERNAL = "An internal has Ocuured. Please Contact System Administrator";
		public static final String REVIEW_RETRIVE_SUCESSFUL = "Reviews Retrived Sucessfully";
		public static final String NO_REVIEWS_FOUND = "No Reviews Found at this point of time";
		public static final String INTERNAL_ERROR = "Please Contact System Adminitrator. An Internal Error has Ocuurred";
		public static final String FIRSTNAME_EMPTY = "First Name cannot be Empty";
		public static final String LASTNAME_EMPTY = "Last Name cannot be Empty";
		public static final String USERID_EMPTY = "User ID Cannot be Empty";
		public static final String EMAIL_EMPTY = "Email Cannot be Empty";
		public static final String PASSWORD_EMPTY = "Password Cannot be Empty";
		public static final String USERREGISTERED_SUCESS_MSG = "User Has Registered Sucessfully";
		public static final String USERALREADY_EXIST = "User Already Exist";
		public static final String NO_USER_EXISTS = "No User Already Exist";
		public static final String PASSWORD_WRONG = "Password does not exist";
		public static final String USER_LOGIN_SUCESS = "User Login is Sucessful";
		public static final String ACCNO_EMPTY = "Account No Cannot be Empty";
		public static final String IPIN_EMPTY = "IPIN Cannot be Empty";
		public static final String ACCNOLSIT_EMPTY = "Account No Not Found/List is Empty";
		public static final String INSUFFICENT_FUNDS = "Insufficient Funds";
		public static final String BALANCE_UPDATE_FAILED = "Balance Could not be Updated";
		public static final String INSERT_TRANS_FAILED = "Transaction Insertion has Failed";
		public static final String COMPLETE_TOURISM = "Tourist Package has been applied Sucessfully";
		public static final String REVIEWDETAILS_EMPTY = "Review Details Cannot be Empty";
		public static final String REVIEW_STORED_SUCESSFULLY = "Review Stored Sucessfully";
		public static final String KEYWORD_EMPTY = "Keyword Cannot be Empty";
		public static final String KEYWORD_STORAGE_FAILED = "The Keyword Could not be Stored";
		public static final String ADD_POSITIVEKEYWORD_SUCESS = "Storage of Positive Keyword is Sucessful";
		public static final String ADD_NEGATIVEKEYWORD_SUCESS = "Storage of Negative Keyword is Sucessful";
		public static final String EMPTY_REVIEWSLIST = "Reviews List is Empty";
		public static final String REVIEWS_FETCH_SUCESS = "Reviews Fetched Sucessfully";
		public static final String EMPTY_STOPWORD = "Stopword Cannot be Empty";
		public static final String STOPWORD_EXIST = "Stopword Already Exist";
		public static final String EMPTY_STOPWORDS = "Stop Words are Empty";
		public static final String STOPWORD_SUCESS = "Retrival of Stop Words is sucessful";
		public static final String STOPWORD_ADD_FAILED = "Failed to Add Stop Word";
		public static final String STOPWORD_ADD_SUCESS = "Stop Word Added Sucessfully";
		public static final String CLEANMODEL_FAILED = "Clean Model Insertion has Falied";
		public static final String CLEANREVIEWS_SUCESS = "Clean of Reviews is Sucessful";
		public static final String TOKENS_SUCESS = "Tokenization Process has been completed Sucessfully";
		public static final String CLEANREVIEWS_EMPTY = "Clean Reviews are Empty";
		public static final String INSERT_TOKENS_FAILED = "Insertion of Tokens has Failed";
		public static final String EMPTY_TOKENS = "Tokens Are Empty";
		public static final String TOKENRETRIVAL_SUCESS = "Retrival of Tokens is Sucessful";
		public static final String FREQ_SUCESS = "Frequency Computation is Sucessful";
		public static final String TOKENS_EMPTY = "Token List  Cannot be Empty";
		public static final String COULDNOT_DELETE_FREQUENCY = "Could not delete the Frequency Contents";
		public static final String COULDNOT_FIND_REVIEWS = "Could not Find Reviews";
		public static final String COULDNOT_FIND_TOKENS = "Could not Find Tokens For the Review";
		public static final String NO_TOKEN_FOUND = "No Token Found";
		public static final String COULDNOT_INSERT_FREQUENCY = "Could not insert into Frequency";
		public static final String FREQ_COMPUTATION_SUCESS = "Frequency Computation is Sucessful";
		public static final String FREQUENCYLIST_EMPTY = "Frequency List is Empty";
		public static final String COULDNOT_COMPUTE_FREQUENCY = "Could not Compute Frequency";
		public static final String INVALID_TOURPACKID = "Tourist Pack ID is Invalid";
		public static final String FEATUREVECTORSUCESS_VIEW = "Feature Vector has been Sucessfully Computed";
		public static final String COULDNOTFIND_TOURPACKS_FREQ = "Could not find Tour Pack Ids From Frequency";
		public static final String COULDNOTFIND_REVIEWIDS_FREQ = "Could not find Review Ids From Frequency ";
		public static final String COULDNOTFIND_TOKENS_FREQ = "Could not find Tokens for the Specfic Review";
		public static final String COULDNOTFIND_REVIEWLIST = "Could not Find Reviews for Token";
		public static final String FEATURE_VECTOR_EMPTY = "Feature Vector cannot be Empty";
		public static final String EMPTY_FEATUREVECTOR_LIST = "Feature Vector List is Empty";
		public static final String FEATUREVECTOR_FETCH_SUCESS = "Feature Vector Fecthced Sucessfully";
		public static final String SEARCH_EMPTY = "Search Criteria is Empty";
		public static final String COULD_NOT_RANK = "Could not Rank at this Point of time";
		public static final String DELETE_FV = "Could not Delete Feature Vector";
		public static final String WEBURL_EMPTY = "Web URL Cannot be Empty. Please enter a valid URL";
		public static final String REVIEWS_EMPTY = "Submitted Review Cannot be Empty";
		public static final String STOPWORD_REMOVE_SUCESS = "Stopwords Removed Sucessfully";
		public static final String NO_STOPWORD_EXIST = "Nos such Stopwords Exist";
		public static final String STOPWORD_REMOVE_FAILED = "Could not remove Stopword";
		public static final String EMPTY_TABLE = "No Field Selected for Deletion";
		public static final String TABLEDATA_REMOVE_FAILED = "Failed to remove the Data";
		public static final String TABLEDATA_REMOVE_SUCESS = "Data Removed Sucessfully";
		public static final String SESSION_EXPIRED = "Session has Expired";
		public static final String SEARCHRESULTS_EMPTY = "Could not find the Search Results";
		public static final String THRESHOLD_INVALID = "Threshold Cannot be Invalid";
		public static final String SESSION_INVALID = "Session has Expired. Please Re login";
		public static final String SETTINGS_STORE_SUCESS = "Settings has been Stored Sucessfully";

	}

	interface Keys {
		public static final String OBJ = "obj";
		public static final int ADMIN_TYPE = 5;
		public static final String STOPWORDS_SYMBOL = "[^a-zA-Z]+";
		public static final String SPACE = "  ";
		public static final String LOGINID_SESSION = "LOGINID_SESSION";

		public static final String USERID_KEY = "userId";

		public static final String QUERY_KEY = "query";

		public static final String URLINFO_KEY = "urlInfo";

		public static final String TITLE_KEY = "title";

		public static final String DESC_KEY = "description";

		public static final String HIDDENURL_KEY = "hiddenUrl";
		public static final String TOKENNAME_KEY = "tokenName";
		public static final String FREQ_KEY = "freq";
		public static final String NO_OF_REVIEWS_KEY = "noOfReviews";
		public static final String IDFT_KEY = "idft";
		public static final String FEATUREVECTOR_KEY = "featureVector";
		public static final String LOGINID_KEY = "loginId";
		public static final String THRESHOLD_KEY = "threshold";

	}

	interface COLUMNNAMES {
		/*
		 * These are the column names for review table
		 */
		public static final String PRODUCTID_COL = "PRODUCTID";
		public static final String PRODUCTTYPE_COL = "CATID";
		public static final String REVIEWDETAILS_COL = "REVIEWDETAILS";
		/*
		 * These are the column names for the product table
		 */
		public static final String PRODUCTID__PK_COL = "PRODUCTID_PK";
		public static final String PRODUCTNAME_COL = "PRODUCTNAME";
		public static final String PRODUCTTYPE_FK_COL = "PRODUCTTYPE_FK";
		/*
		 * These are the column names for the product Type table
		 */
		public static final String PRODUCTYPEID_PK_COL = "PRODUCTYPEID_PK";
		public static final String PRODUCTYPENAME_COL = "PRODUCTYPENAME";

	}

	interface Views {
		public static final String VIEW_ETOURISM_INPUT = "viewetour";
		public static final String VIEW_ETOURISM_OUTPUT = "etourout";
		public static final String VIEW_REGISTER_INPUT = "registeruser";
		public static final String VIEW_CUSTOMER_WELCOMEPAGE = "welcome";
		public static final String VIEW_LOGIN_INPUT = "login";
		public static final String VIEW_ADMIN_WELCOMEPAGE = "admin";
		public static final String VIEW_BANK_INPUT = "bankinput";
		public static final String VIEW_ERROR_PAGE = "error";
		public static final String APPLICATION_WELCOME_PAGE = "applicationwelcome";
		public static final String VIEW_SUCESS_PAGE = "sucess";
		public static final String APPLYREVIEW_VIEW = "applyReview";
		public static final String ADD_POSITIVEKEYWORD_VIEW = "addpositivekeyword";
		public static final String ADD_NEGATIVEKEYWORD_VIEW = "addnegativekeyword";
		public static final String VIEW_ADMIN_SUCESS_PAGE = "adminsucess";
		public static final String VIEW_ADMIN_ERROR_PAGE = "adminerror";
		public static final String VIEW_POLARITY_PACK = "polarity";
		public static final String STOPWORD_INPUT = "addStopword";
		public static final String RANKFV_INPUT = "rankfv";
		public static final String RANKFV_OUTPUT = "rankfvout";
		public static final String REMOVESTOPWORD_INPUT = "removeStopword";
		public static final String DELETEDATA_INPUT = "deleteData";
		public static final String SEARCH_INPUT = "search";
		public static final String LOGIN_INPUT = "login";
		public static final String OFFLINESEARCH_INPUT = "offlinesearch";
		public static final String VIEW_SETTINGS_INPUT = "personalsettings";
		public static final String SETTINGS_STORE_SUCESS = "sucess";
		public static final String SUCESS_PAGE = "sucess";
		public static final String CONCEPTS_INPUT = "conceptsInput";
		public static final String CONCEPTS_OUTPUT = "conceptOutput";
	}

	interface DatabaseColumns {
		public static final String TOURISTPACKID_COL = "TOURISTPACKID";

		public static final String REVIEWID_COL = "REVIEWID";

		public static final String REVIEWDETAILS_COL = "REVIEWDETAILS";


		public static final String STOPWORDID_COL = "STOPWORDID";

		public static final String STOPWORD_COL = "STOPWORD";

		public static final String CLEANID_COL = "CLEANID";

		public static final String CLEANREVIEW_COL = "CLEANREVIEW";

		public static final String TOKENID_COL = "TOKENID";

		public static final String TOKENNAME_COL = "TOKENNAME";

		public static final String FREQID_COL = "FREQID";

		public static final String FREQ_COL = "FREQ";

		public static final String NOOFREVIEWS_COL = "NOOFREVIEWS";

		public static final String IDFT_COL = "IDFT";

		public static final String FEATUREVECTOR_COL = "FEATUREVECTOR";

		public static final String FEATUREID_COL = "FEATUREID";


		public static final String DESCRIPTION_COL = "DESCRIPTION";

		public static final String HIDDENURL_COL = "HIDDENURL";

		public static final String URL_COL = "URL";

		public static final String QUERY_COL = "QUERY";

		public static final String TITLE_COL = "TITLE";

		public static final String USERID_COL = "USERID";

		public static final String SEARCHID_COL = "SEARCHID";

	}

	interface GoogleKeys {
		public static final String GOOGLE_SEARCH_URL = "https://www.googleapis.com/customsearch/v1?";

		// api key
		public static final String API_KEY = "AIzaSyA7MiF_4C536_2L680zEtDa5v3U1-_96IE";
		// custom search engine ID
		public static final String SEARCH_ENGINE_ID = "003376408562708904164:jh97o-pafss";

		public static final String FINAL_URL = GOOGLE_SEARCH_URL + "key="
				+ API_KEY + "&cx=" + SEARCH_ENGINE_ID;

	}

}
