package sn.sonatel.dsi.dif.api.webservice.config;

import java.io.Serializable;

public class Operation implements Serializable {
    private Integer leftOperand;
    private Integer rightOperand;

    public Operation(){

    }

    public Operation(Integer leftOperand, Integer rightOperand){
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    public Integer getLeftOperand() {
        return leftOperand;
    }

    public Integer getRightOperand() {
        return rightOperand;
    }
}
