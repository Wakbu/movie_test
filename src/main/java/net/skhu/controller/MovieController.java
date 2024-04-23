package net.skhu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import net.skhu.dto.Genre;
import net.skhu.dto.Movie;
import net.skhu.model.MovieEdit;
import net.skhu.service.GenreService;
import net.skhu.service.MovieService;

@Controller
@RequestMapping("movie")
public class MovieController {
	@Autowired MovieService movieService;
    @Autowired GenreService genreService;

    @GetMapping("list")
    public String list(Model model) {
        List<Movie> movies = movieService.findAll();
        model.addAttribute("movies", movies);
        return "movie/list";
    }

    @GetMapping("create")
    public String create(Model model) {
        MovieEdit movieEdit = new MovieEdit();
        List<Genre> genres = genreService.findAll();
        model.addAttribute("movieEdit", movieEdit);
        model.addAttribute("genres", genres);
        return "movie/edit";
    }

    @PostMapping("create")
    public String create(Model model,
            @Valid MovieEdit movieEdit, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("genres", movieService.findAll());
            return "movie/edit";
        }
        Movie movie2 = movieService.findByMovieNo(movieEdit.getId());
        if (movie2 != null) {
            bindingResult.rejectValue("id", null, "번이 중복됩니다.");
            model.addAttribute("genres", genreService.findAll());
            return "movie/edit";
        }
        movieService.insert(movieEdit);
        return "redirect:list";
    }

    @GetMapping("edit")
    public String edit(Model model, int id) {
        MovieEdit movieEdit = movieService.findOne(id);
        List<Genre> genres = genreService.findAll();
        model.addAttribute("movieEdit", movieEdit);
        model.addAttribute("genres", genres);
        return "movie/edit";
    }

    @PostMapping("edit")
    public String edit(Model model,
            @Valid MovieEdit movieEdit, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("genres", genreService.findAll());
            return "movie/edit";
        }
        Movie movie2 = movieService.findByMovieNo(movieEdit.getId());
        if (movie2 != null && movie2.getId() != movieEdit.getId()) {
            bindingResult.rejectValue("id", null, "번이 중복됩니다.");
            model.addAttribute("genres", movieService.findAll());
            return "movie/edit";
        }
        movieService.update(movieEdit);
        return "redirect:list";
    }

    @GetMapping("delete")
    public String delete(Model model, int id) {
        movieService.delete(id);
        return "redirect:list";
    }
}
