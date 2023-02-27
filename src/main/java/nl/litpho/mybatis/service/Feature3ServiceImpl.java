package nl.litpho.mybatis.service;

import nl.litpho.mybatis.generated.client.CityTableMapper;
import nl.litpho.mybatis.generated.client.CountryTableMapper;
import nl.litpho.mybatis.generated.model.CityTable;
import nl.litpho.mybatis.model.CountryOld;
import nl.litpho.mybatis.repository.CountryMapperRepository2;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.dynamic.sql.select.CountDSLCompleter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class Feature3ServiceImpl implements Feature3Service {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    private CountryMapperRepository2 countryMapperRepository;

    @Autowired
    private CountryTableMapper countryMapper;

    @Autowired
    private CityTableMapper cityMapper;

    @Override
    public Long getCountryCounter() {
        return countryMapper.count(CountDSLCompleter.allRows());
    }

    @Override
    public Long getCityCounter() {
        return cityMapper.count(CountDSLCompleter.allRows());
    }

    @Transactional
    @Override
    public void addCountryAndCity(Boolean flag) {

        //TODO: How to Insert & Return an ID?
        /*
        var row = new CountryTable();
        row.setCountry("Tabarnia");
        row.setLastUpdate(LocalDateTime.now());

        InsertStatementProvider<CountryTable> insertStatement = insert(row)
                .into(SqlTable.of("country"))
                .map(country).toProperty("country")
                .map(lastUpdate).toProperty("lastUpdate")
                .build()
                .render(RenderingStrategies.MYBATIS3);
        int rows = countryMapper.insert(insertStatement);
        */

        CountryOld countryToAdd = new CountryOld();
        countryToAdd.setCountry("Tabarnia");
        countryToAdd.setLast_update(LocalDateTime.now());
        countryMapperRepository.save(countryToAdd);

        System.out.println(countryToAdd);

        var id = countryToAdd.getCountry_id();

        try {
            var countryIdValue = (flag == Boolean.FALSE) ? id : 999L;

            var record = new CityTable();
            record.setCity("TabarniaCity");
            record.setCountryId((int) countryIdValue);
            record.setLastUpdate(LocalDateTime.now());

            int rows = cityMapper.insert(record);

        } catch (DataAccessException ex) {
            System.out.println("Katakroker");
            throw new RuntimeException("Katakroker");
        }
    }
}
