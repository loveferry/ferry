package cn.org.ferry.soap.service;

import cn.org.ferry.soap.dto.EveryDayPlan;
import cn.org.ferry.soap.dto.InHeaderMessage;
import cn.org.ferry.soap.dto.OutHeaderMessage;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.ws.RequestWrapper;

/**
 * <p>基于 soap 协议的每日还款计划接口
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/11/29 09:32
 */

@WebService(
        name = "EveryPlan",
        serviceName = "EveryDayPlanService",   // wsdl:service
        portName = "EveryDayPlanServicePort", // wsdl:port name
        endpointInterface = "cn.org.ferry.soap.service.EveryDayPlanSoapService"
)
public interface EveryDayPlanSoapService {

    @WebMethod(operationName = "everyPlan")  // wsdl:operation
    @WebResult(name = "result")
    @RequestWrapper(localName = "EVERY_DAY_PLAN", targetNamespace = "prj")
    OutHeaderMessage<EveryDayPlan> everyPlan(@XmlElement(required = true) @WebParam(name = "baseInfo", header = true, targetNamespace = "head") InHeaderMessage inHeaderMessage,
                                             @XmlElement(required = true, name = "PROJECT") @WebParam List<EveryDayPlan> everyDayPlanList);
}
