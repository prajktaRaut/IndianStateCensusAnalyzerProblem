package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {

        try (
                Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
                ){

            Iterator<IndiaCensusCSV> censusCSVIterator = new OpenCSVBuilder().getCSVFileIterator(reader,IndiaCensusCSV.class);

            int namOfEateries = getCount(censusCSVIterator);

            return namOfEateries;

        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }

    }

    public int loadIndiaStateCode(String csvFilePath) throws CensusAnalyserException {

        try (
                Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
        ) {

            Iterator<IndiaStateCodeCSV> censusCSVIterator = new OpenCSVBuilder().getCSVFileIterator(reader,IndiaStateCodeCSV.class);

            int namOfEateries =getCount(censusCSVIterator);

            return namOfEateries;

        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    private <E> int getCount(Iterator<E> iterator)
    {
        Iterable<E> csvIterable = () -> iterator;

        int namOfEateries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();

        return namOfEateries;
    }

}
