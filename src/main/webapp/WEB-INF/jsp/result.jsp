<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head><meta charset="utf-8"/><title>Result</title></head>
<body>
  <h3>Operation Result</h3>
  <pre>${param.msg}</pre>
  <p><a href="${pageContext.request.contextPath}/WEB-INF/jsp/checkout.jsp">Back to checkout</a></p>
</body>
</html>
