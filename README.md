# binance-balance-cosumer
 Spring Boot 2 REST API Consumer for Binance. Used to get spot, stacking and saving balance of coins/token from Binance account. For historical analysis balances are stored to Database storage. Basic Auth used for security.
 
### Endpoints:
- api/balance/stats/full -> returns stats of all tokens/coins + detail amount for each coin
- api/balance/stats/notification -> sends manual message to telegram about summary of balance 

### Telegram Notification
A scheduler can automatically send the wallet balance via a telegram bot to you.

### Database Persistent (PostgresDB)
With the help of JPA Hibernate auto generation, tables and views will be created to store balance data for historical analysis.
For more details check resources/schema.sql file and BinanceBalance entity.

### Tests
Test resource which checks if the API is secured and the connection to the Binance API works. 

### Actuator
Actuator is started on a separate port. Check application.yml. User needs to have the role ADMIN to access it. 
Only /actuator/health and /actuator/info is available for everyone to check if the service is up and running.

### DockerHub
https://hub.docker.com/repository/docker/stylepatrick/binance-api-consumer
