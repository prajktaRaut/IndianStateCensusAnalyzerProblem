package censusanalyser;

import com.bridgelabzs.CSVBuilderException;

import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;

public class CensusAnalyser {

    Map<String, IndiaCensusDAO> censusDAOMap=new HashMap<>();;
    Map<StateCensusField, Comparator<IndiaCensusDAO>> fieldComparatorMap = new HashMap<>();

    public enum Country {
        INDIA, US
    }

    Country country;

    public CensusAnalyser() {
        this.fieldComparatorMap.put(StateCensusField.State, Comparator.comparing(field -> field.state));
        this.fieldComparatorMap.put(StateCensusField.Population, Comparator.comparing(field -> field.population, Comparator.reverseOrder()));
        this.fieldComparatorMap.put(StateCensusField.AreaInSqKm, Comparator.comparing(field -> field.areaInSqKm, Comparator.reverseOrder()));
        this.fieldComparatorMap.put(StateCensusField.DensityPerSqKm, Comparator.comparing(field -> field.densityPerSqKm, Comparator.reverseOrder()));
    }

    public int loadCensusData(Country country, String... csvFilePath) throws CSVBuilderException {
        CensusAdapter classObject = CensusAdapterFactory.getClassObject(country);
        this.country=country;
        censusDAOMap = classObject.loadCensusData(country, csvFilePath);
        return censusDAOMap.size();
    }

    public String getSortedCensusDataByGenericSort(StateCensusField field) throws CSVBuilderException {
        if (censusDAOMap == null || censusDAOMap.size() == 0) {
            throw new CSVBuilderException("No Census Data", CSVBuilderException.ExceptionType.NO_CENSUS_DATA);
        }
        ArrayList censusDTO=censusDAOMap.values().stream()
                            .sorted(fieldComparatorMap.get(field))
                            .map(IndiaCensusDAO -> IndiaCensusDAO.getCensusDTO(country))
                            .collect(Collectors.toCollection(ArrayList::new));
        String sortedStateCensusJson = new Gson().toJson(censusDTO);
        return sortedStateCensusJson;
    }
}
