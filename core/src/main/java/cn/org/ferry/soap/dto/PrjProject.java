package cn.org.ferry.soap.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>项目实体类
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/11/29 09:39
 */

@XmlRootElement(name = "chinesePeople")
@XmlType(propOrder = {"APPROVAL_STATUS", "PARTNERS", "PARTNERS_CONTRACT_NUMBER", "REJECTED_DESCRIPTION"})
public class PrjProject {
    private String APPROVAL_STATUS;
    private String PARTNERS;
    private String PARTNERS_CONTRACT_NUMBER;
    private String REJECTED_DESCRIPTION;

    @XmlElement(required = true)
    public String getAPPROVAL_STATUS() {
        return APPROVAL_STATUS;
    }

    public void setAPPROVAL_STATUS(String APPROVAL_STATUS) {
        this.APPROVAL_STATUS = APPROVAL_STATUS;
    }

    @XmlElement(required = true)
    public String getPARTNERS() {
        return PARTNERS;
    }

    public void setPARTNERS(String PARTNERS) {
        this.PARTNERS = PARTNERS;
    }

    @XmlElement(required = true)
    public String getPARTNERS_CONTRACT_NUMBER() {
        return PARTNERS_CONTRACT_NUMBER;
    }

    public void setPARTNERS_CONTRACT_NUMBER(String PARTNERS_CONTRACT_NUMBER) {
        this.PARTNERS_CONTRACT_NUMBER = PARTNERS_CONTRACT_NUMBER;
    }

    @XmlElement(required = true)
    public String getREJECTED_DESCRIPTION() {
        return REJECTED_DESCRIPTION;
    }

    public void setREJECTED_DESCRIPTION(String REJECTED_DESCRIPTION) {
        this.REJECTED_DESCRIPTION = REJECTED_DESCRIPTION;
    }
}
