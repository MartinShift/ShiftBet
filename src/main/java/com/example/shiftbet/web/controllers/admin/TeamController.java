package com.example.shiftbet.web.controllers.admin;

import com.example.shiftbet.domain.entity.Category;
import com.example.shiftbet.domain.entity.Team;
import com.example.shiftbet.domain.service.CategoryService;
import com.example.shiftbet.domain.service.CountryService;
import com.example.shiftbet.domain.service.ImageFileService;
import com.example.shiftbet.domain.service.TeamService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.List;
@Controller
public class TeamController {

    private final TeamService  teamService;
    private final CountryService countryService;
    private final ImageFileService imageFileService;

    public TeamController(TeamService  teamService,  CountryService countryService, ImageFileService imageFileService) {
        this. teamService =  teamService;
        this.countryService = countryService;
        this.imageFileService = imageFileService;
    }
    @GetMapping("/team/add")
    public String showAdd(Model model) {
        model.addAttribute("team", new Team());
        model.addAttribute("countries",countryService.getAll());
        return "admin/team/add";
    }

    @GetMapping("/team/view")
    public String showAllSubcategories(Model model) {
        model.addAttribute("teams", teamService.getAll());
        return "admin/team/view";
    }
    @PostMapping("/team/add")
    public String add(Team team, MultipartHttpServletRequest request) throws IOException {
         team.setLogoUrl(imageFileService.saveImage(request.getFile("image")));
         teamService.add(team);
        return "redirect:/team/view";
    }

    @GetMapping("/team/edit/{id}")
    public String showEditTeamForm(@PathVariable("id") int id, Model model) {
        model.addAttribute("team",  teamService.get(id));
        model.addAttribute("countries", countryService.getAll());
        return "admin/team/edit";
    }

    @PostMapping("/team/edit")
    public String editTeam(Team team, MultipartHttpServletRequest request) throws IOException {
        String url = imageFileService.saveImage(request.getFile("logo"));
        if(url != null)
        {
             team.setLogoUrl(url);
        }
         teamService.update(team.getId(), team);
        return "redirect:/team/view";
    }
    @GetMapping("/team/delete/{id}")
    public String deleteTeam(@PathVariable("id") int id) {
         teamService.delete(id);
        return "redirect:/team/view";
    }
}

