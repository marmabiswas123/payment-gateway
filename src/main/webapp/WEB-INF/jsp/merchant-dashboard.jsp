<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head><meta charset="utf-8"/><title>Merchant Dashboard</title>
  <style>table{border-collapse:collapse;width:100%}td,th{border:1px solid #ddd;padding:8px}</style>
</head>
<body>
  <h2>Merchant Dashboard (demo)</h2>
  <p>Shows last 20 payments (polled).</p>
  <button onclick="loadPayments()">Refresh</button>
  <table id="payments">
    <thead><tr><th>Payment ID</th><th>Order</th><th>Amount</th><th>Status</th><th>Raw PSP</th></tr></thead>
    <tbody></tbody>
  </table>

<script>
async function loadPayments(){
  // For demo we fetch by calling /api/payments (you can implement a listing endpoint)
  try {
    const resp = await fetch('${pageContext.request.contextPath}/api/payments?limit=20');
    if (!resp.ok) { document.querySelector('#payments tbody').innerHTML = '<tr><td colspan="5">Failed to list</td></tr>'; return; }
    const list = await resp.json();
    const body = list.map(p => `<tr>
      <td>${p.paymentId||''}</td>
      <td>${p.orderId||''}</td>
      <td>${p.amount||''} ${p.currency||''}</td>
      <td>${p.status||''}</td>
      <td><pre style="max-height:80px;overflow:auto">${JSON.stringify(p.rawPspResponse||'', null, 2)}</pre></td>
    </tr>`).join('');
    document.querySelector('#payments tbody').innerHTML = body || '<tr><td colspan="5">No payments</td></tr>';
  } catch(e){ document.querySelector('#payments tbody').innerHTML = '<tr><td colspan="5">Error</td></tr>'; }
}
loadPayments();
</script>
</body>
</html>
