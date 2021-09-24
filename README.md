# binance-balance-cosumer
 Spring Boot 2 REST API Cosumer for Binance. Is used to get the balance of coins/token from Binance account. Basic Auth used for security. 
 
### Endpoints:
 - api/balance/stats/coins -> returns amount of tokens/coins 
 - api/balance/stats/full -> returns sum amount of all tokens/coins + detail amount for each coin

### Telegram Notification
A scheduler can automatically send the wallet balance via a telegram bot to you.

### DockerHub

https://hub.docker.com/repository/docker/stylepatrick/binance-balance-consumer