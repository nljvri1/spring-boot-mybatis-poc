package nl.litpho.mybatis.service;

import nl.litpho.mybatis.generated.client.FilmTableMapper;
import nl.litpho.mybatis.openapi.model.FilmDto;
import org.mybatis.dynamic.sql.SqlTable;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static nl.litpho.mybatis.generated.client.FilmTableDynamicSqlSupport.filmId;
import static nl.litpho.mybatis.generated.client.FilmTableDynamicSqlSupport.title;
import static org.mybatis.dynamic.sql.SqlBuilder.isLike;
import static org.mybatis.dynamic.sql.SqlBuilder.select;

@Service
public class Feature1ServiceImpl implements Feature1Service {

    @Autowired
    private FilmTableMapper filmTableMapper;

    @Override
    public List<FilmDto> getFilms() {
        return filmTableMapper.select(s -> s.where(title, isLike("A%")))
                .stream()
                .map(f -> {
                    final FilmDto filmDto = new FilmDto();
                    filmDto.setFilmId(Long.valueOf(f.getFilmId()));
                    filmDto.setTitle(f.getTitle());
                    return filmDto;
                })
                .toList();
    }

    public List<FilmDto> getFilmsOld() {

        final SelectStatementProvider selectStatement = select(filmId, title)
                .from(SqlTable.of("film"))
                .where(title, isLike("A%"))
                .build()
                .render(RenderingStrategies.MYBATIS3);

        return filmTableMapper.selectMany(selectStatement).stream()
                .map(f -> {
                    final FilmDto filmDto = new FilmDto();
                    filmDto.setFilmId(Long.valueOf(f.getFilmId()));
                    filmDto.setTitle(f.getTitle());
                    return filmDto;
                })
                .toList();
    }
}
