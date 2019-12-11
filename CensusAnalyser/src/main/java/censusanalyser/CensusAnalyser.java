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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    List<IndiaCensusDAO> censusList=null;

    public CensusAnalyser() {
        this.censusList =new ArrayList<IndiaCensusDAO>();
    }

    public int loadIndiaCensusData(String csvFilePath) throws CSVBuilderException {

        try (
                Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
                ){

            ICSVBuilder icsvBuilder= CSVBuilderFactory.createCommonCSVBuilder();

            Iterator<IndiaStateCodeCSV> censusCSVIterator = icsvBuilder.getCSVFileIterator(reader,IndiaStateCodeCSV.class);

                int namOfEateries =getCount(censusCSVIterator);

                return namOfEateries;

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

            int namOfEateries =getCount(censusCSVIterator);

            return namOfEateries;

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

            if (censusList == null || censusList.size()==0)
            {
                throw new CSVBuilderException("No Census Data", CSVBuilderException.ExceptionType.NO_CENSUS_DATA);
            }

            Comparator<IndiaCensusDAO> censusCSVComparator=Comparator.comparing(census->census.state);
            this.sort(censusCSVComparator);
            String sortedStateCensusJson=new Gson().toJson(this.censusList);
            return sortedStateCensusJson;
    }

    private void sort(Comparator<IndiaCensusDAO> censusCSVComparator) {

        for (int i = 0; i< this.censusList.size()-1; i++)
        {
            for (int j = 0; j< this.censusList.size()-i-1; j++)
            {
                IndiaCensusDAO census1= this.censusList.get(j);
                IndiaCensusDAO census2 = this.censusList.get(j+1);

                if (censusCSVComparator.compare(census1,census2)>0)
                {
                    this.censusList.set(j,census2);
                    this.censusList.set(j+1,census1);
                }
            }
        }
    }

}
