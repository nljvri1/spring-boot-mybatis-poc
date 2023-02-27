package nl.litpho.mybatis.repository;

import nl.litpho.mybatis.model.CountryOld;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CountryMapperRepository {

   String sql =
          """
          SELECT COUNT(*)
          FROM country
          """;
    @Select(sql)
    Integer select();

    String sqlInsert =
            """
            INSERT INTO country (country, last_update)
            VALUES (#{country}, #{last_update})
            """;

    @Insert(sqlInsert)
    @Options(useGeneratedKeys = true, keyProperty = "country_id")
    void save(CountryOld country);
}


