package stylepatrick.binance.api.consumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stylepatrick.binance.api.consumer.entity.BinanceBalance;

public interface BinanceBalanceRepository extends JpaRepository<BinanceBalance, Long> {
}
