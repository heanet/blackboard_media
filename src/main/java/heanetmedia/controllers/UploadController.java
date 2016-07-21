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

import heanetmedia.models.*;

@Controller

public class UploadController {

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String upload(Model model) {
        model.addAttribute("uploadUrl", "https://media.heanet.ie/secure/upload");

        return "upload";
    }

}
