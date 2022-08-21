-----------------------------------------
--- Tot balance of all assets in USDT ---
-----------------------------------------

--- per request ---
create or replace view binance_balance_tot_request(sum_in_usdt, crdt) as
SELECT sum(binance_balance.amount_in_usdt) AS sum_in_usdt,
       binance_balance.crdt
FROM binance_balance
GROUP BY binance_balance.crdt;

--- AVG per day ---
create or replace view binance_balance_tot_avg_daily(to_char, avg_is_usdt) as
SELECT DISTINCT to_char(tot_request.crdt, 'YYYY-MM-DD'::text) AS to_char,
                avg(tot_request.sum)                          AS avg_is_usdt
FROM (SELECT sum(binance_balance.amount_in_usdt) AS sum,
             binance_balance.crdt
      FROM binance_balance
      GROUP BY binance_balance.crdt) tot_request
GROUP BY (to_char(tot_request.crdt, 'YYYY-MM-DD'::text));

--- AVG per month ---
create or replace view binance_balance_tot_avg_monthly(to_char, avg_is_usdt) as
SELECT DISTINCT to_char(tot_request.crdt, 'YYYY-MM'::text) AS to_char,
                avg(tot_request.sum)                       AS avg_is_usdt
FROM (SELECT sum(binance_balance.amount_in_usdt) AS sum,
             binance_balance.crdt
      FROM binance_balance
      GROUP BY binance_balance.crdt) tot_request
GROUP BY (to_char(tot_request.crdt, 'YYYY-MM'::text));

--- AVG per year ---
create or replace view binance_balance_tot_avg_yearly(to_char, avg_is_usdt) as
SELECT DISTINCT to_char(tot_request.crdt, 'YYYY'::text) AS to_char,
                avg(tot_request.sum)                    AS avg_is_usdt
FROM (SELECT sum(binance_balance.amount_in_usdt) AS sum,
             binance_balance.crdt
      FROM binance_balance
      GROUP BY binance_balance.crdt) tot_request
GROUP BY (to_char(tot_request.crdt, 'YYYY'::text));


-----------------------------------------
--- Tot balance in USDT per asset -------
-----------------------------------------

--- per request ---
create or replace view binance_balance_asset_request(asset, sum_in_usdt, crdt) as
SELECT binance_balance.asset,
       sum(binance_balance.amount_in_usdt) AS sum_in_usdt,
       binance_balance.crdt
FROM binance_balance
GROUP BY binance_balance.asset, binance_balance.crdt;

--- AVG per day ---
create view binance_balance_asset_avg_daily(to_char, avg_in_usdt, asset) as
SELECT DISTINCT to_char(asset_request.crdt, 'YYYY-MM-DD'::text) AS to_char,
                avg(asset_request.sum)                          AS avg_in_usdt,
                asset_request.asset
FROM (SELECT binance_balance.asset,
             sum(binance_balance.amount_in_usdt) AS sum,
             binance_balance.crdt
      FROM binance_balance
      GROUP BY binance_balance.asset, binance_balance.crdt) asset_request
GROUP BY asset_request.asset, (to_char(asset_request.crdt, 'YYYY-MM-DD'::text));

--- AVG per month ---
create or replace view binance_balance_asset_avg_monthly(to_char, avg_in_usdt, asset) as
SELECT DISTINCT to_char(asset_request.crdt, 'YYYY-MM'::text) AS to_char,
                avg(asset_request.sum)                       AS avg_in_usdt,
                asset_request.asset
FROM (SELECT binance_balance.asset,
             sum(binance_balance.amount_in_usdt) AS sum,
             binance_balance.crdt
      FROM binance_balance
      GROUP BY binance_balance.asset, binance_balance.crdt) asset_request
GROUP BY asset_request.asset, (to_char(asset_request.crdt, 'YYYY-MM'::text));

--- AVG per year ---
create or replace view binance_balance_asset_avg_yearly(to_char, avg_in_usdt, asset) as
SELECT DISTINCT to_char(asset_request.crdt, 'YYYY'::text) AS to_char,
                avg(asset_request.sum)                    AS avg_in_usdt,
                asset_request.asset
FROM (SELECT binance_balance.asset,
             sum(binance_balance.amount_in_usdt) AS sum,
             binance_balance.crdt
      FROM binance_balance
      GROUP BY binance_balance.asset, binance_balance.crdt) asset_request
GROUP BY asset_request.asset, (to_char(asset_request.crdt, 'YYYY'::text));