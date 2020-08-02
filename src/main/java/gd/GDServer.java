package gd;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Connection with Geometry Dash official servers is managed here
 * Contains utility methods to interact with the game
 */
public abstract class GDServer {

    private static final String GD_SERVER_URL_PREFIX = "http://www.boomlings.com/database/";
    private static final String SECRET = "Wmfd2893gb7";

    /**
     * Submits a POST request to the specified page on the GD server and returns the result as a String
     *
     * @param webpage   relative URL to the page
     * @param urlParams Content of the POST request
     * @return server response as String
     * @throws IOException if a problem occurs while connecting to GD servers
     */
    public static String sendRequest(String webpage, String urlParams) throws IOException {
        HttpURLConnection con;
        con = (HttpURLConnection) new URL(GD_SERVER_URL_PREFIX + webpage).openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);

        // Sending the request to the server
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParams);
        wr.flush();
        wr.close();

        // Fetching response
        StringBuilder result = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line).append("\n");
        }

        return result.toString().replaceAll("\n", "");
    }

    /**
     * Fetches the latest epic levels
     *
     * @param i number of page
     * @return server response as String
     * @throws IOException if a problem occurs while connecting to GD servers
     */
    public static String fetchMostPopularLevels(int i) throws IOException {
        return sendRequest("getGJLevels21.php",
                "gameVersion=21&binaryVersion=34&gdw=0&type=1&str=&diff=-&len=-&page=" + i + "&total=0"
                        + "&uncompleted=0&onlyCompleted=0&featured=0&original=0&twoPlayer=0&coins=0&epic=0"
                        + "&secret=" + SECRET);
    }

    public static String fetchMostLikedLevels(int i) throws IOException {
        return sendRequest("getGJLevels21.php",
                "gameVersion=21&binaryVersion=34&gdw=0&type=2&str=&diff=-&len=-&page=" + i + "&total=0"
                        + "&uncompleted=0&onlyCompleted=0&featured=0&original=0&twoPlayer=0&coins=0&epic=0"
                        + "&secret=" + SECRET);
    }
}
