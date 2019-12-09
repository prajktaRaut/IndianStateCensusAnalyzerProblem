package censusanalyser;

import com.google.gson.Gson;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    List<IndiaCensusCSV> censusCSVList=null;
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {

        try (
                Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
                ){

            ICSVBuilder icsvBuilder= CSVBuilderFactory.createCSVBuilder();
            censusCSVList = icsvBuilder.getCSVFileList(reader,IndiaCensusCSV.class);

            return censusCSVList.size();

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


            if (censusCSVList == null || censusCSVList.size()==0)
            {

                throw new CensusAnalyserException("No Census Data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
            }

            Comparator<IndiaCensusCSV> censusCSVComparator=Comparator.comparing(census->census.state);
            this.sort(censusCSVList,censusCSVComparator);
            String sortedStateCensusJson=new Gson().toJson(censusCSVList);
            return sortedStateCensusJson;

    }

    private void sort(List<IndiaCensusCSV> censusCSVList, Comparator<IndiaCensusCSV> censusCSVComparator) {

        for (int i = 0; i< this.censusCSVList.size()-1; i++)
        {
            for (int j = 0; j< this.censusCSVList.size()-i-1; j++)
            {
                IndiaCensusCSV census1= this.censusCSVList.get(j);
                IndiaCensusCSV census2= this.censusCSVList.get(j+1);

                if (censusCSVComparator.compare(census1,census2)>0)
                {
                    this.censusCSVList.set(j,census2);
                    this.censusCSVList.set(j+1,census1);

                }

            }

        }

    }


}
