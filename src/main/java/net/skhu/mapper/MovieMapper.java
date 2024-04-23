package net.skhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import net.skhu.dto.Movie;

@Mapper
public interface MovieMapper {

	@Select("SELECT * FROM Movie WHERE id = #{id}")
	Movie findOne(int id);

	@Select("SELECT * FROM Movie WHERE id = #{id}")
	Movie findByMovieNo(int id);

	@Select("""
			select m.*, g.title genreTitle
			from movie m
			join genre g on m.genreId =  g.id
			order by id;
			""")
	List<Movie> findAll();

	@Insert("""
			INSERT movie (id, title, director, genreId, year, country)
			VALUES (#{id}, #{title}, #{director}, #{genreId}, #{year}, #{country}) """)
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void insert(Movie movie);

	@Update("""
			UPDATE movie SET
			  title = #{title},
			  director = #{director},
			  genreId = #{genreId},
			  year = #{year},
			  country = #{country}
			WHERE id = #{id} """)
	void update(Movie movie);

	@Delete("DELETE FROM movie WHERE id = #{id}")
	void delete(int id);
}
