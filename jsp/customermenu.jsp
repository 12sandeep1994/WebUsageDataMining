<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="<%=request.getContextPath()%>/css/styles.css"
	rel="stylesheet" type="text/css">
</head>
<body>
	<div id='cssmenu'>
		<ul>
			<li class='active '><a
				href="<%=request.getContextPath()%>/jsp/welcome.jsp"><span>Home</span></a></li>
			<li class='has-sub '><a href='#'>Search<span></span></a>
				<ul>
					<li><a href='<%=request.getContextPath()%>/jsp/search.jsp'><span>
								Review Submission</span></a></li>
				</ul></li>

			<li class='has-sub '><a href='#'>My Previous Results<span></span></a>
				<ul>
					<li><a
						href='<%=request.getContextPath()%>/jsp/previoussearch.jsp'><span>
								Previous Search</span></a></li>
				</ul></li>

			<li class='has-sub '><a href='#'>Stopword Analysis<span></span></a>
				<ul>
					<li class='active '><a
						href="<%=request.getContextPath()%>/jsp/addStopword.jsp"><span>Add
								Stopword</span></a></li>
					<li class='active '><a
						href="<%=request.getContextPath()%>/jsp/viewstopwords.jsp"><span>View
								Stopword</span></a></li>
					<li class='active '><a
						href="<%=request.getContextPath()%>/jsp/removeStopword.jsp"><span>Remove
								Stopword</span></a></li>
				</ul>
			<li class='has-sub '><a href='#'>Web Usage Mining <span></span></a>
				<ul>
					<li class='has-sub '><a
						href="<%=request.getContextPath()%>/jsp/datacleaning.jsp"><span>Clean
								Data</span></a></li>
					<li class='has-sub '><a
						href="<%=request.getContextPath()%>/jsp/viewcleanreviews.jsp"><span>View
								Clean Data</span></a></li>
					<li class='has-sub '><a
						href="<%=request.getContextPath()%>/jsp/tokenization.jsp"><span>Tokenization</span></a></li>
					<li class='has-sub '><a
						href="<%=request.getContextPath()%>/jsp/viewtokens.jsp"><span>View
								Tokens</span></a></li>
					<li class='has-sub '><a
						href="<%=request.getContextPath()%>/jsp/frequencycomputation.jsp"><span>Frequency
								Computation</span></a></li>
					<li class='has-sub '><a
						href="<%=request.getContextPath()%>/jsp/viewfrequency.jsp"><span>View
								Frequency</span></a></li>
					<li class='has-sub '><a
						href="<%=request.getContextPath()%>/jsp/idftfv.jsp"><span>Feature
								Vector</span></a></li>
					<li class='has-sub '><a
						href="<%=request.getContextPath()%>/jsp/viewfeaturevector.jsp"><span>View
								Feature Vector</span></a></li>
				</ul>
			<li class='has-sub '><a href='#'>Offline Search<span></span></a>
				<ul>
					<li><a
						href='<%=request.getContextPath()%>/jsp/offlinesearch.jsp'><span>
								Offline Search</span></a></li>

				</ul>
			<li class='has-sub '><a href='#'>Concepts<span></span></a>
				<ul>
					<li><a
						href='<%=request.getContextPath()%>/jsp/personalsettings.jsp'><span>
								Settings</span></a></li>

					<li><a
						href='<%=request.getContextPath()%>/jsp/conceptsInput.jsp'><span>
								Concepts</span></a></li>
				</ul>
			<li class='has-sub '><a href='#'>Delete Data<span></span></a>
				<ul>
					<li><a href='<%=request.getContextPath()%>/jsp/deleteData.jsp'><span>
								Delete Data</span></a></li>

				</ul>
			<li class='has-sub '><a
				href='<%=request.getContextPath()%>/review/logout.do'>Logout<span></span>
			</a>
		</ul>
	</div>
</body>
</html>