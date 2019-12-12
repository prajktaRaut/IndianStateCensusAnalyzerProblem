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

    public CensusAnalyser() {
        this.fieldComparatorMap.put(StateCensusField.State, Comparator.comparing(field -> field.state));
        this.fieldComparatorMap.put(StateCensusField.Population, Comparator.comparing(field -> field.population,Comparator.reverseOrder()));
        this.fieldComparatorMap.put(StateCensusField.AreaInSqKm, Comparator.comparing(field -> field.areaInSqKm,Comparator.reverseOrder()));
        this.fieldComparatorMap.put(StateCensusField.DensityPerSqKm, Comparator.comparing(field -> field.densityPerSqKm,Comparator.reverseOrder()));
    }

    public int loadIndiaCensusData(String csvFilePath) throws CSVBuilderException {

       int result = this.loadCensusDats(csvFilePath,IndiaCensusCSV.class);
       return result;
    }

    private <E> int loadCensusDats(String csvFilePath, Class<E> censusCSVClass) throws CSVBuilderException {

        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder icsvBuilder = CSVBuilderFactory.createOpenCSVBuilder();
            Iterator<E> censusCSVIterator = icsvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
            Iterable<E> csvIterable = () -> censusCSVIterator;
            if (censusCSVClass.getName().equals("censusanalyser.IndiaCensusCSV")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(IndiaCensusCSV.class::cast)
                        .forEach(censusCSV -> censusStateMap.put(censusCSV.state, new IndiaCensusDAO(censusCSV)));
            }
            else if (censusCSVClass.getName().equals("censusanalyser.USCensusCSV"))
            {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(USCensusCSV.class::cast)
                        .forEach(censusCSV -> censusStateMap.put(censusCSV.State, new IndiaCensusDAO(censusCSV)));

            }
            return censusStateMap.size();

        } catch (IOException | RuntimeException e) {
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM);
        }

    }

    public int loadIndiaStateCode(String csvFilePath) throws CSVBuilderException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder icsvBuilder = CSVBuilderFactory.createOpenCSVBuilder();
            Iterator<IndiaStateCodeCSV> censusCSVIterator = icsvBuilder.getCSVFileIterator(reader, IndiaStateCodeCSV.class);
            Iterable<IndiaStateCodeCSV> csvIterable=()->censusCSVIterator;
           StreamSupport.stream(csvIterable.spliterator(),false)
                   .filter(csvState->censusStateMap.get(csvState.state)!=null)
                   .forEach(csvState->censusStateMap.get(csvState.state).stateCode=csvState.stateCode);
            return censusStateMap.size();
        } catch (IOException | RuntimeException e) {
            throw new CSVBuilderException(e.getMessage(), CSVBuilderException.ExceptionType.STATE_CODE_FILE_PROBLEM);
        }
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

    public int loadUSCensusData(String usCensusCsvFilePath) throws CSVBuilderException {

        int result = this.loadCensusDats(usCensusCsvFilePath,USCensusCSV.class);
        return result;

    }
}
