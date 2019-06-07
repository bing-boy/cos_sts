<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>index</title>
</head>
<body>
	<form action="/sts/getSts" method="post">
		bucket:<input name="bucket" type="text">
		region:<input name="region" type="text">
		exprience:<input name="exp" type="text">
		secretdId:<input name="secId" type="text">
		secretdKey:<input name="secKey" type="text">
		policy:<input name="policy" type="text">
		<input type="submit" value="提交">
	</form>
</body>
</html>