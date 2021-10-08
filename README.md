# binance-balance-cosumer
 Spring Boot 2 REST API Consumer for Binance. Is used to get the balance of coins/token from Binance account. Basic Auth used for security. 
 
### Endpoints:
 - api/balance/stats/coins -> returns amount of tokens/coins 
 - api/balance/stats/full -> returns sum amount of all tokens/coins + detail amount for each coin

### Telegram Notification
A scheduler can automatically send the wallet balance via a telegram bot to you.

### Tests
Test resource which checks if the API is secured and the connection to the Binance API works. 

### Actuator
Actuator is started on a separate port. Check application.yml. User needs to have the role ADMIN to access it. 
Only /actuator/health and /actuator/info is available for everyone to check if the service is up and running.

### DockerHub
https://hub.docker.com/repository/docker/stylepatrick/binance-api-consumer
