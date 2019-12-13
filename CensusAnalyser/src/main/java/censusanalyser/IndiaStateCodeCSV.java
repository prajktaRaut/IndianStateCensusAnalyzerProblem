package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCodeCSV {

    @CsvBindByName(column = "StateName", required = true)
    public String srNo;

    @CsvBindByName(column = "StateName", required = true)
    public String state;

    @CsvBindByName(column = "StateName", required = true)
    public String TIN;

    @CsvBindByName(column = "StateCode", required = true)
    public String stateCode;

    public String getSrNo() {
        return srNo;
    }

    public void setSrNo(String srNo) {
        this.srNo = srNo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTIN() {
        return TIN;
    }

    public void setTIN(String TIN) {
        this.TIN = TIN;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public IndiaStateCodeCSV() {
    }

    @Override
    public String toString() {
        return "IndiaStateCodeCSV{" +
                "srNo='" + srNo + '\'' +
                ", state='" + state + '\'' +
                ", TIN='" + TIN + '\'' +
                ", stateCode='" + stateCode + '\'' +
                '}';
    }
}
