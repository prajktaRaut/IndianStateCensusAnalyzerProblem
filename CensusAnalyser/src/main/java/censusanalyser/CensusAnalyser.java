package censusanalyser;

import com.bridgelabzs.CSVBuilderException;
import com.bridgelabzs.CSVBuilderFactory;
import com.bridgelabzs.ICSVBuilder;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    Map<String, IndiaCensusDAO> censusStateMap = new HashMap<>();
    Map<StateCensusField, Comparator<IndiaCensusDAO>> fieldComparatorMap = new HashMap<>();

    public enum Country
    {
        INDIA,US
    }

    public CensusAnalyser() {
        this.fieldComparatorMap.put(StateCensusField.State, Comparator.comparing(field -> field.state));
        this.fieldComparatorMap.put(StateCensusField.Population, Comparator.comparing(field -> field.population,Comparator.reverseOrder()));
        this.fieldComparatorMap.put(StateCensusField.AreaInSqKm, Comparator.comparing(field -> field.areaInSqKm,Comparator.reverseOrder()));
        this.fieldComparatorMap.put(StateCensusField.DensityPerSqKm, Comparator.comparing(field -> field.densityPerSqKm,Comparator.reverseOrder()));
    }

    public int loadIndiaCensusData(Country country,String... csvFilePath) throws CSVBuilderException {

        IndiaCensusAdapter indiaCensusAdapter=new IndiaCensusAdapter();
       censusStateMap=indiaCensusAdapter.loadCensusData(country,csvFilePath);
       return censusStateMap.size();

    }

    public String getSortedCensusDataByGenericSort(StateCensusField field) throws CSVBuilderException {
        if (censusStateMap == null || censusStateMap.size() == 0) {
            throw new CSVBuilderException("No Census Data", CSVBuilderException.ExceptionType.NO_CENSUS_DATA);
        }
        List<IndiaCensusDAO> censusDAOS = censusStateMap.values().stream().collect(Collectors.toList());
        this.sort(censusDAOS, this.fieldComparatorMap.get(field));
        String sortedStateCensusJson = new Gson().toJson(censusDAOS);
        return sortedStateCensusJson;
    }

    private void sort(List<IndiaCensusDAO> censusDAOS, Comparator<IndiaCensusDAO> censusCSVComparator) {
        for (int i = 0; i < censusDAOS.size() - 1; i++) {
            for (int j = 0; j < censusDAOS.size() - i - 1; j++) {
                IndiaCensusDAO census1 = censusDAOS.get(j);
                IndiaCensusDAO census2 = censusDAOS.get(j + 1);
                if (censusCSVComparator.compare(census1, census2) > 0) {
                    censusDAOS.set(j, census2);
                    censusDAOS.set(j + 1, census1);
                }
            }
        }
    }

    public int loadUSCensusData(String... usCensusCsvFilePath) throws CSVBuilderException {

        censusStateMap=new CensusLoader().loadCensusData(USCensusCSV.class,usCensusCsvFilePath);
        return censusStateMap.size();

    }
}
