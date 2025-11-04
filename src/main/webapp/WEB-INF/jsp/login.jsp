<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head><meta charset="utf-8"/><title>Merchant Login</title></head>
<body>
  <h2>Merchant Login (demo)</h2>
  <form id="login" onsubmit="return false;">
    <label>Merchant ID<input id="merchantId" type="text" value="m_demo" /></label><br/>
    <label>API Key<input id="apiKey" type="text" value="demo_api_key_123" /></label><br/>
    <button onclick="doLogin()">Login (Demo)</button>
  </form>
  <script>
    function doLogin(){
      // demo: store merchant id in sessionStorage for dashboard usage (not secure)
      const m = document.getElementById('merchantId').value;
      sessionStorage.setItem('merchantId', m);
      alert('Logged in as ' + m + ' (demo). Now go to Merchant Dashboard.');
      window.location = '${pageContext.request.contextPath}/WEB-INF/jsp/merchant-dashboard.jsp';
    }
  </script>
</body>
</html>
