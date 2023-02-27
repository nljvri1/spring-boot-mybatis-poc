package nl.litpho.mybatis.repository;

import nl.litpho.mybatis.generated.client.CountryTableMapper;
import nl.litpho.mybatis.generated.model.CountryTable;
import nl.litpho.mybatis.model.CountryOld;
import org.mybatis.dynamic.sql.select.CountDSLCompleter;
import org.springframework.stereotype.Repository;

@Repository
public class CountryMapperRepository2 {

    private final CountryTableMapper mapper;

    public CountryMapperRepository2(final CountryTableMapper mapper) {
        this.mapper = mapper;
    }

    public int select() {
        return (int) mapper.count(CountDSLCompleter.allRows());
    }

    public void save(final CountryOld countryOld) {
        final CountryTable c = new CountryTable();
        c.setCountry(countryOld.getCountry());
        c.setLastUpdate(countryOld.getLast_update());
        mapper.insert(c);
        countryOld.setCountry_id(c.getCountryId().longValue());
    }
}


