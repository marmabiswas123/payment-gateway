# Payment Gateway Prototype (JSP + Tomcat + MySQL)

## Requirements
- Java 11
- Maven
- Apache Tomcat 9+
- MySQL 8+
- Python 3 (for PSP simulator)

## Quick start
1. Set up MySQL and run `sql/schema.sql` and `sql/seed.sql`.
2. Set env vars:
   - APP_ENC_KEY (32-byte key hex or base64)
   - APP_HMAC_KEY (merchant secret)
   - DB_* (DB_HOST, DB_PORT, DB_NAME, DB_USER, DB_PASS)
3. Start PSP simulator:
# payment-gateway