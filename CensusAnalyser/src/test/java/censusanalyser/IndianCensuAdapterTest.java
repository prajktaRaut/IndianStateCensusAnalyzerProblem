package censusanalyser;

import com.bridgelabzs.CSVBuilderException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Map;

public class IndianCensuAdapterTest {

        private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
        private static final String INDIA_STATE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
        private static final String WRONG_CENSUS_CSV_FILE_PATH = "./src/test/resources/NewIndianStateCensusData.csv";
        private static final String WRONG_CENSUS_CSV_FILE_TYPE = "./src/test/resources/IndiaStateCensusData.txt";
        private static final String CENSUS_FILE_FOR_WRONG_DELIMITER_OR_HEADER = "./src/test/resources/NewIndianStateCensusData.csv";

        IndiaCensusAdapter indiaCensusAdapter = new IndiaCensusAdapter();

        @Test
        public void givenIndianStateData_ShouldReturnCorrectRecords() {
            try {
                Map<String, IndiaCensusDAO> censusDAOMap = indiaCensusAdapter.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CSV_FILE_PATH);
                Assert.assertEquals(29, censusDAOMap.size());
            } catch (CSVBuilderException e) {
            }
        }

        @Test
        public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
            try {
                ExpectedException exceptionRule = ExpectedException.none();
                exceptionRule.expect(CSVBuilderException.class);
                indiaCensusAdapter.loadCensusData(CensusAnalyser.Country.INDIA, WRONG_CENSUS_CSV_FILE_PATH);
            } catch (CSVBuilderException e) {
                Assert.assertEquals(CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
            }
        }

        @Test
        public void givenIndianCensusCSVFile_WithWrongFileType_ShouldThrowException() {
            try {
                ExpectedException exceptionRule = ExpectedException.none();
                exceptionRule.expect(CSVBuilderException.class);
                indiaCensusAdapter.loadCensusData(CensusAnalyser.Country.INDIA, WRONG_CENSUS_CSV_FILE_TYPE);
            } catch (CSVBuilderException e) {
                Assert.assertEquals(CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
            }
        }

        @Test
        public void givenIndianCensusCSVFile_WithWrongDelimiterPosition_ShouldThrowException() {
            try {
                ExpectedException exceptionRule = ExpectedException.none();
                exceptionRule.expect(CSVBuilderException.class);
                indiaCensusAdapter.loadCensusData(CensusAnalyser.Country.INDIA, CENSUS_FILE_FOR_WRONG_DELIMITER_OR_HEADER);
            } catch (CSVBuilderException e) {
                Assert.assertEquals(CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
            }
        }

        @Test
        public void givenIndianCensusCSVFile_WithWrongHeader_ShouldThrowException() {
            try {
                ExpectedException exceptionRule = ExpectedException.none();
                exceptionRule.expect(CSVBuilderException.class);
                indiaCensusAdapter.loadCensusData(CensusAnalyser.Country.INDIA, CENSUS_FILE_FOR_WRONG_DELIMITER_OR_HEADER);
            } catch (CSVBuilderException e) {
                Assert.assertEquals(CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
            }
        }
    }
