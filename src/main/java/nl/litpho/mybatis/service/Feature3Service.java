package nl.litpho.mybatis.service;

public interface Feature3Service {

    Long getCountryCounter();
    Long getCityCounter();
    void addCountryAndCity(Boolean flag);
}
