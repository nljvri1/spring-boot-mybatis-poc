package nl.litpho.mybatis.controller;

import nl.litpho.mybatis.openapi.FilmsApi;
import nl.litpho.mybatis.openapi.model.FilmDto;
import nl.litpho.mybatis.service.Feature1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FilmController implements FilmsApi {
    @Autowired
    private Feature1Service filmService;

    @Override
    public ResponseEntity<List<FilmDto>> getFilms() {
        return ResponseEntity.ok().body(filmService.getFilms());
    }
}
