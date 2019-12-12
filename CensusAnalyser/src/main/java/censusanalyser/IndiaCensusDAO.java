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

    public IndiaCensusDAO(USCesnsusCSV cesnsusCSV)
    {
        state=cesnsusCSV.State;
        stateId=cesnsusCSV.stateId;
        totalArea=cesnsusCSV.totalArea;
        populationdensity=cesnsusCSV.populationDensity;
        population=cesnsusCSV.population;

    }

}
