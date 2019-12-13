package censusanalyser;

import com.bridgelabzs.CSVBuilderException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Map;

public class USCensusAdapterTest {

    private static final String US_CENSUS_CSV_FILE_PATH = "./src/test/resources/USCensusData.csv";
    private static final String CENSUS_FILE_FOR_WRONG_DELIMITER_OR_HEADER = "./src/test/resources/NewUSCensusData.csv";
    private static final String WRONG_US_CENSUS_CSV_FILE_PATH = "./src/main/resources/USCensusData.csv";


    USCensusAdapter usCensusAdapter=new USCensusAdapter();

    @Test
    public void givenUSCensusData_ShouldReturnCorrectRecords() {

        try {
            Map<String, IndiaCensusDAO> censusDAOMap = usCensusAdapter.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(51, censusDAOMap.size());
        } catch (CSVBuilderException e) {
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CSVBuilderException.class);
            usCensusAdapter.loadCensusData(CensusAnalyser.Country.US, WRONG_US_CENSUS_CSV_FILE_PATH);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndianCensusCSVFile_WithWrongFileType_ShouldThrowException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CSVBuilderException.class);
            usCensusAdapter.loadCensusData(CensusAnalyser.Country.US, WRONG_US_CENSUS_CSV_FILE_PATH);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndianCensusCSVFile_WithWrongDelimiterPosition_ShouldThrowException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CSVBuilderException.class);
            usCensusAdapter.loadCensusData(CensusAnalyser.Country.US, CENSUS_FILE_FOR_WRONG_DELIMITER_OR_HEADER);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndianCensusCSVFile_WithWrongHeader_ShouldThrowException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CSVBuilderException.class);
            usCensusAdapter.loadCensusData(CensusAnalyser.Country.US, CENSUS_FILE_FOR_WRONG_DELIMITER_OR_HEADER);
        } catch (CSVBuilderException e) {
            Assert.assertEquals(CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

}
