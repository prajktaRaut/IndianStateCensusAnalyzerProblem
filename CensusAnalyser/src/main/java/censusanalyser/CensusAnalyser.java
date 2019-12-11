package censusanalyser;


import com.bridgelabzs.CSVBuilderException;
import com.bridgelabzs.CSVBuilderFactory;
import com.bridgelabzs.ICSVBuilder;
import com.google.gson.Gson;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    Map<String,IndiaCensusDAO> censusStateMap=new HashMap<>();

    public int loadIndiaCensusData(String csvFilePath) throws CSVBuilderException {

        try (
                Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
                ){

            ICSVBuilder icsvBuilder= CSVBuilderFactory.createOpenCSVBuilder();

            Iterator<IndiaCensusCSV> censusCSVIterator = icsvBuilder.getCSVFileIterator(reader,IndiaCensusCSV.class);

            Iterable<IndiaCensusCSV> csvIterable=()-> censusCSVIterator;

            StreamSupport.stream(csvIterable.spliterator(),false).forEach(censusCSV->censusStateMap.put(censusCSV.state,new IndiaCensusDAO(censusCSV)));

            return censusStateMap.size();

        } catch (IOException e) {
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    public int loadIndiaStateCode(String csvFilePath) throws CSVBuilderException {

        try (
                Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
        ) {
            ICSVBuilder icsvBuilder= CSVBuilderFactory.createOpenCSVBuilder();
            Iterator<IndiaStateCodeCSV> censusCSVIterator = icsvBuilder.getCSVFileIterator(reader,IndiaStateCodeCSV.class);

            while (censusCSVIterator.hasNext())
            {
                IndiaStateCodeCSV stateCodeCSV=censusCSVIterator.next();
                IndiaCensusDAO censusDAO=censusStateMap.get(stateCodeCSV.state);
                if (censusDAO==null)continue;
                censusDAO.stateCode=stateCodeCSV.stateCode;

            }

            return censusStateMap.size();

        } catch (IOException | com.bridgelabzs.CSVBuilderException e) {
            throw new CSVBuilderException(e.getMessage(), CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    private <E> int getCount(Iterator<E> iterator)
    {
        Iterable<E> csvIterable = () -> iterator;

        int namOfEateries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();

        return namOfEateries;
    }

    public String getStateWiseSortedCensusData() throws CSVBuilderException {

            if (censusStateMap == null || censusStateMap.size()==0)
            {
                throw new CSVBuilderException("No Census Data", CSVBuilderException.ExceptionType.NO_CENSUS_DATA);
            }

            Comparator<IndiaCensusDAO> censusCSVComparator=Comparator.comparing(census->census.state);
            List<IndiaCensusDAO>  censusDAOS=censusStateMap.values().stream().collect(Collectors.toList());
            this.sort(censusDAOS,censusCSVComparator);
            String sortedStateCensusJson=new Gson().toJson(censusDAOS);
            return sortedStateCensusJson;
    }

    private void sort(List<IndiaCensusDAO> censusDAOS,Comparator<IndiaCensusDAO> censusCSVComparator) {

        for (int i = 0; i< censusDAOS.size()-1; i++)
        {
            for (int j = 0; j< censusDAOS.size()-i-1; j++)
            {
                IndiaCensusDAO census1= censusDAOS.get(j);
                IndiaCensusDAO census2 = censusDAOS.get(j+1);

                if (censusCSVComparator.compare(census1,census2)>0)
                {
                    censusDAOS.set(j,census2);
                    censusDAOS.set(j+1,census1);
                }
            }
        }
    }

}
