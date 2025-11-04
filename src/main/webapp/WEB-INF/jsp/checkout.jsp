<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8"/>
  <title>Demo Checkout</title>
  <style>
    body{font-family: Arial; padding:20px; max-width:800px; margin:auto;}
    label{display:block; margin-top:8px;}
    input[type=text]{width:100%; padding:8px;}
    button{margin-top:12px; padding:10px 16px;}
    .note{color:#666; font-size:0.9em;}
  </style>
</head>
<body>
  <h1>Checkout (Prototype)</h1>
  <p class="note">Test card: <code>4111111111111111</code> - prototype only.</p>

  <form id="checkoutForm" onsubmit="return false;">
    <label>Card number
      <input id="card_number" type="text" placeholder="4111 1111 1111 1111" />
    </label>
    <label>Expiry month
      <input id="exp_month" type="text" placeholder="MM" value="12" />
    </label>
    <label>Expiry year
      <input id="exp_year" type="text" placeholder="YYYY" value="2027" />
    </label>
    <label>CVV
      <input id="cvv" type="text" placeholder="123" />
    </label>
    <label>Cardholder name
      <input id="cardholder" type="text" placeholder="Alice Example" />
    </label>

    <button id="payBtn" onclick="runPayment()">Tokenize & Pay (Demo)</button>
  </form>

  <div id="result" style="margin-top:18px;"></div>

<script>
async function runPayment(){
  const card = {
    cardNumber: document.getElementById('card_number').value.trim(),
    expMonth: document.getElementById('exp_month').value.trim(),
    expYear: document.getElementById('exp_year').value.trim(),
    cvv: document.getElementById('cvv').value.trim(),
    cardholderName: document.getElementById('cardholder').value.trim()
  };

  document.getElementById('payBtn').disabled = true;
  document.getElementById('result').innerText = 'Tokenizing...';

  // 1) Tokenize
  const tokResp = await fetch("${pageContext.request.contextPath}/api/tokens", {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(card)
  }).then(r => r.json());

  if (!tokResp.token) {
    document.getElementById('result').innerText = 'Tokenization failed: ' + JSON.stringify(tokResp);
    document.getElementById('payBtn').disabled = false;
    return;
  }

  // 2) Call create payment
  const idempotency = cryptoRandomUUID();
  const ts = Math.floor(Date.now() / 1000).toString();

  // NOTE: demo signature — replace with proper merchant signing in real flow.
  const demoSignature = "demo-signature";

  const paymentReq = {
    merchantId: "m_demo",
    orderId: "order_" + Date.now(),
    amount: 100,    // smallest unit (demo)
    currency: "INR",
    cardToken: tokResp.token,
    capture: true
  };

  document.getElementById('result').innerText = 'Creating payment...';

  const payResp = await fetch("${pageContext.request.contextPath}/api/payments", {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Idempotency-Key': idempotency,
      'X-GW-Timestamp': ts,
      'X-GW-Signature': demoSignature
    },
    body: JSON.stringify(paymentReq)
  }).then(r => {
    if (!r.ok) return r.text().then(t => { throw new Error(t); });
    return r.json();
  }).catch(err => { return {error: err.message}; });

  document.getElementById('result').innerText = JSON.stringify(payResp, null, 2);
  document.getElementById('payBtn').disabled = false;
}

// small UUID generator (works in modern browsers)
function cryptoRandomUUID(){
  if (window.crypto && crypto.randomUUID) return crypto.randomUUID();
  // fallback
  return 'id-' + Math.random().toString(36).slice(2);
}
</script>
</body>
</html>
