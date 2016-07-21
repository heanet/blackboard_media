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
 * @package    heanetmedia.controllers
 * @copyright  2016 Heanet swdev@heanet.ie
 * @author     Geoffrey Crespino
 * @license    http://www.gnu.org/copyleft/gpl.html GNU GPL v3 or later
 */

package heanetmedia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.ArrayList;

import blackboard.platform.plugin.PlugInUtil;

import heanetmedia.models.MediaList;
import heanetmedia.models.Media;

@Controller

public class MediaListController {

    /**
    * Mapping the request to controller/list
    * Getting Media list and values that need to be assigned to the view including the reference url to the calling content editor VTBE of the Blackboard platform
    * This url will be used by the list form to return the media player code with the video url to the editor
    * If the Media list couldn't be generated, meaning the blackboard user doesn't match any in Media, this makes a biding request to media instead of displaying the view
    */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {

        MediaList list = new MediaList();

        if (!list.setMediaList()) {
            model.addAttribute("bindUrl", MediaList.bindUrl+"UserGUID="+list.getUserId());
            return "bind";
        }

        model.addAttribute("medias", list);
        model.addAttribute("thumbnailWidth", Media.thumbnailWidth);
        model.addAttribute("thumbnailHeight", Media.thumbnailHeight);

        String targetUrl = PlugInUtil.getInsertToVtbePostUrl();

        model.addAttribute("targetUrl", targetUrl);

        return "list";
    }

}
