package censusanalyser;

import com.google.gson.Gson;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

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

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {

        try (
                Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
                ){

            ICSVBuilder icsvBuilder= CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> csvFileIterator=icsvBuilder.getCSVFileIterator(reader,IndiaCensusCSV.class);

            while (csvFileIterator.hasNext())
            {
                this.censusList.add(new IndiaCensusDAO(csvFileIterator.next()));
            }
            return censusList.size();

        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),e.type.name());
        }

    }

    public int loadIndiaStateCode(String csvFilePath) throws CensusAnalyserException {

        try (
                Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
        ) {

            ICSVBuilder icsvBuilder= CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCodeCSV> censusCSVIterator = icsvBuilder.getCSVFileIterator(reader,IndiaStateCodeCSV.class);

            int namOfEateries =getCount(censusCSVIterator);

            return namOfEateries;

        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),e.type.name());
        }
    }

    private <E> int getCount(Iterator<E> iterator)
    {
        Iterable<E> csvIterable = () -> iterator;

        int namOfEateries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();

        return namOfEateries;
    }

    public String getStateWiseSortedCensusData() throws CensusAnalyserException {


            if (censusList == null || censusList.size()==0)
            {

                throw new CensusAnalyserException("No Census Data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
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
