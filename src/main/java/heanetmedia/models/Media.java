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

import org.json.JSONObject;

public class Media {
    private String id, title, source, thumbnail, description, date;
    private JSONObject json;
    public static final int thumbnailHeight = 128;
    public static final int thumbnailWidth = 128;

    /**
    * Constructor basically reading JSon data to set the Media object, keeping the json as an attribute for toString() method
    */
    public Media (JSONObject pjson) {
        id = pjson.getString("_id");
        title = pjson.optString("title","no_title");
        source = pjson.getString("source");
        thumbnail = pjson.getString("thumbnail");
        description = pjson.optString("description", "No description");
        date = pjson.getString("date");
        json = pjson;
    }

    /**
    * Basic getters
    */
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSource() {
        return source;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String toString() {
        return json.toString();
    }
}
