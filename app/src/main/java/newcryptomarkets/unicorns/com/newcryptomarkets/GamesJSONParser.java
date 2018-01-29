package newcryptomarkets.unicorns.com.newcryptomarkets;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GamesJSONParser {

    static List<Games> gamesList;

    public static List<Games> parseData(String content) {

        JSONArray games_arry = null;
        Games games = null;
        try {

            games_arry = new JSONArray(content);
            gamesList = new ArrayList<>();

            for (int i = 0; i < games_arry.length(); i++) {

                JSONObject obj = games_arry.getJSONObject(i);
                games = new Games();

                games.setId(obj.getString("rank"));
                games.setTitle(obj.getString("name"));
                games.setUsdPrice(obj.getString("price_usd"));
                games.setTitle2(obj.getString("symbol"));
                games.setChange24H(obj.getString("percent_change_24h"));
                //games.setPlatforms(obj.getString("platforms"));
                //games.setImage(obj.getString("image"));

                gamesList.add(games);
            }
            return gamesList;

        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
