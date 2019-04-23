// Heanet Media plugin
// Copyright (C) 2016 swdev@heanet.ie

// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.

// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.

// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.

/**
 * Heanet Media repository list
 *
 * @package    heanetmedia.models
 * @copyright  2016 Heanet swdev@heanet.ie
 * @author     Geoffrey Crespino
 * @license    http://www.gnu.org/copyleft/gpl.html GNU GPL v3 or later
 */

package heanetmedia.models;

//Java imports
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.security.MessageDigest;
import java.math.BigInteger;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.json.JSONObject;

//Session and context imports
import blackboard.platform.session.BbSession;
import blackboard.platform.context.Context;
import blackboard.platform.context.ContextManager;
import blackboard.platform.context.ContextManagerFactory;
import blackboard.platform.persistence.PersistenceServiceFactory;
import blackboard.persist.user.UserDbLoader;
import blackboard.persist.BbPersistenceManager;

//User imports
import blackboard.persist.Id;
import blackboard.data.user.User;

public class MediaList extends ArrayList<Media> {

    private static final String listUrl = "https://media.heanet.ie/api/1.0/media_list.php?";
    private static final String checkLoginUrl = "https://media.heanet.ie/api/1.0/media_list.php?";
    public static final String bindUrl = "https://media.heanet.ie/secure/account/user_setguid.php?";
    private String userId;

    public MediaList() {
        super();
        setUserId();
    }

    /**
    * Setting the Media list by making a request to media with the proper id, splitting results and adding them to the list as Media objects 
    */
    public boolean setMediaList() {
        //Set url(s) with md5 encoded id
        String guid = hashUserId();
        String response = curl(MediaList.listUrl+"UserGUID="+guid);

        // Response is empty
        if(response.length() < 1) return false;

        JSONObject j = new JSONObject(response);
        String status = j.get("Status").toString();

        // Check login and check whether files exist
        switch(status) {
            case "Found":
                // If we find files, continue to parsing them
                break;
            case "No Files":
                // If there are no files, display an empty page with error
                return true;
            default:
                // Otherwise 
                return false;
        }

        String dataString = j.get("Data").toString();

        //Splitting response
        StringTokenizer st = new StringTokenizer(dataString,"[]");

        // Add data to list
        if (st.hasMoreTokens()) {
            StringTokenizer data = new StringTokenizer(st.nextToken(),"{}");
            String s = null;

            while (data.hasMoreTokens()) {
                s = data.nextToken();

                if (!s.equals(",")) {
                    add(new Media(new JSONObject("{"+s+"}")));
                }
            }
        }

        return true;
    }

    public String getUserId() {
        return hashUserId();
    }

    /**
     * Setting current user unique id from Blackboard session
     */
    private void setUserId() {
        //Get context and session data from Blackboard
        Id id = ContextManagerFactory.getInstance().getContext().getSession().getUserId();
        BbPersistenceManager pm = PersistenceServiceFactory.getInstance().getDbPersistenceManager();

        //Get user info and id
        try {
            UserDbLoader udl = (UserDbLoader) (pm.getLoader(UserDbLoader.TYPE));

            //Retrieves the Blackboard unique user id (could be made better)
            String userinfo = udl.loadById(id).toString();
            userId = userinfo.substring(userinfo.indexOf("Uuid")+6, userinfo.indexOf("Uuid")+38);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Encodes the user id into md5
     * @return String md5 value for the user id
     */
    private String hashUserId() {
        String uid = userId;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(uid.getBytes(), 0, uid.length());

            uid = new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }

        uid = padLeadingZeros(uid, 32);

        return uid;
    }

    /**
    * Checks whether the user is connected with Media through Edugate.
    * @params String ps - Response from media.heanet.ie to evaluate
    * @return boolean True if the user is connected
    */
    private boolean checkLogin(String ps) {
        return ps.contains("\"Status\":\"Found\"");
    }

    /**
    * Simple curl function adapted for https to make a GET request to media.heanet.ie and retrieve the output
    * @params String purl - full url for the request
    * @return String Output from the request
    */
    private String curl(String purl) {
        String curl = "", line = null;

        try {
            URL url = new URL(purl);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

            while ((line = br.readLine()) != null) {
                curl += line;
            }

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return curl;
    }

    private String padLeadingZeros(String inputString, int desiredLength) {
        return String.format("%" + desiredLength + "s", inputString).replace(' ', '0');
    }
}
