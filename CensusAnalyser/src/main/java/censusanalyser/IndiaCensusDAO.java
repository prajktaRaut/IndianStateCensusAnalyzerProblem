package censusanalyser;

public class IndiaCensusDAO {


    public int population;
    public String state;
    public String stateCode;
    public int stateId;
    public int areaInSqKm;
    public int densityPerSqKm;
    public int populationdensity;
    public int totalArea;


    public IndiaCensusDAO(IndiaCensusCSV indiaCensusCSV) {

        state = indiaCensusCSV.state;
        areaInSqKm = indiaCensusCSV.areaInSqKm;
        population = indiaCensusCSV.population;

    }

    public IndiaCensusDAO(USCensusCSV censusCSV)
    {
        state=censusCSV.State;
        stateId=censusCSV.stateId;
        totalArea=censusCSV.totalArea;
        populationdensity=censusCSV.populationDensity;
        population=censusCSV.population;

    }

}
