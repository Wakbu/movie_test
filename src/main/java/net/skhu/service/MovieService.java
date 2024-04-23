package net.skhu.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.skhu.dto.Movie;
import net.skhu.mapper.MovieMapper;
import net.skhu.model.MovieEdit;

@Service
public class MovieService {

	@Autowired MovieMapper movieMapper;
    ModelMapper modelMapper = new ModelMapper();

    public MovieEdit findOne(int id) {
        Movie studentDto = movieMapper.findOne(id);
        return toEditModel(studentDto);
    }

    public Movie findByMovieNo(int id) {
        return movieMapper.findByMovieNo(id);
    }

    public List<Movie> findAll() {
        return movieMapper.findAll();
    }

    public void insert(MovieEdit movieEdit) {
        Movie movie = toDto(movieEdit);
        movieMapper.insert(movie);
    }

    public void update(MovieEdit movieEdit) {
        Movie movie = toDto(movieEdit);
        movieMapper.update(movie);
    }

    public void delete(int id) {
        movieMapper.delete(id);
    }

    public Movie toDto(MovieEdit movieEdit) {
        return modelMapper.map(movieEdit, Movie.class);
    }

    public MovieEdit toEditModel(Movie movieDto) {
        return modelMapper.map(movieDto, MovieEdit.class);
    }

}
