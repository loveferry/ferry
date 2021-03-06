package cn.org.ferry.soap.service.impl;

import cn.org.ferry.soap.dto.InHeaderMessage;
import cn.org.ferry.soap.dto.OutHeaderMessage;
import cn.org.ferry.soap.dto.PrjProject;
import cn.org.ferry.soap.service.PrjProjectSoapService;
import org.springframework.stereotype.Service;

import java.util.List;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

/**
 * <p>基于 soap 协议的签约投放实现类
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/11/29 09:48
 */

@Service
@BindingType(value = SOAPBinding.SOAP12HTTP_BINDING)
public class PrjProjectSoapServiceImpl implements PrjProjectSoapService {

    @Override
    public OutHeaderMessage<PrjProject> projectReview(InHeaderMessage inHeaderMessage, List<PrjProject> prjProjectList) {
        return null;
    }
}
