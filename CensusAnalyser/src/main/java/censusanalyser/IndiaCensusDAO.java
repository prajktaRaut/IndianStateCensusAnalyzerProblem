package censusanalyser;

public class IndiaCensusDAO {


    public int population;
    public String state;
    public String stateCode;
    public int areaInSqKm;
    public int densityPerSqKm;

    public IndiaCensusDAO(IndiaCensusCSV indiaCensusCSV) {

        state=indiaCensusCSV.state;
        areaInSqKm=indiaCensusCSV.areaInSqKm;
        densityPerSqKm=indiaCensusCSV.densityPerSqKm;
        population=indiaCensusCSV.population;
    }
}
