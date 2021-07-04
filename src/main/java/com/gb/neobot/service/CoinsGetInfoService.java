package com.gb.neobot.service;

import com.gb.neobot.model.Coins;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Getter
@Setter
public class CoinsGetInfoService {

    private String coinsInfoUrl = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest?symbol=BTC,ETH,BNB,DOGE,DOT,ADA";
    private String coinsApiKey = "672e7cf9-a134-489b-b7b0-3c178db64a4f";

    private HttpHeaders headers = new HttpHeaders();
    private RestTemplate restTemplate = new RestTemplate();
        /*"BTC": {
                "symbol": "BTC",
                "total_supply": 18746781,
                "quote": {
                    "USD":{
                        "price":34531.91286723872,
                        "percent_change_1h":-0.49978819,
                        "percent_change_24h":4.36162777,
                        "percent_change_7d":9.27499585,
                          }
                        }
        }*/
    public Coins getAllCoins(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-CMC_PRO_API_KEY", "672e7cf9-a134-489b-b7b0-3c178db64a4f");
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<Coins> response = restTemplate.exchange(
                coinsInfoUrl, HttpMethod.GET, entity, Coins.class);

        return response.getBody();
    }
    public Coins.Bitki.Coin getCoinByName (String coinName, Coins coins)

    {
        Coins.Bitki.Coin coin;

        switch (coinName) {
            case "BTC":
                coin = coins.getData().getBTC();
                break;
            case "ETH":
                coin = coins.getData().getETH();
                break;
            case "DOGE":
                coin = coins.getData().getDOGE();
                break;
            case "ADA":
                coin = coins.getData().getADA();
                break;
            case "DOT":
                coin = coins.getData().getDOT();
                break;
            case "BNB":
                coin = coins.getData().getBNB();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + coinName);
        }
        return coin;
    }


}
