package cn.org.ferry.doc.service.impl;

import cn.org.ferry.core.service.impl.BaseServiceImpl;
import cn.org.ferry.doc.dto.DocTemplateParam;
import cn.org.ferry.doc.dto.model.DocTemplateParamQuery;
import cn.org.ferry.doc.exceptions.DocException;
import cn.org.ferry.doc.mapper.DocTemplateParamMapper;
import cn.org.ferry.doc.service.DocTemplateParamService;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ferry ferry_sy@163.com
 * @date 2019/07/13
 * @description 文档模版参数业务实现层
 */

@Service
public class DocTemplateParamServiceImpl extends BaseServiceImpl<DocTemplateParam> implements DocTemplateParamService {
    @Autowired
    private DocTemplateParamMapper docTemplateParamMapper;

    @Override
    public List<DocTemplateParam> query(DocTemplateParamQuery query, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return docTemplateParamMapper.query(query);
    }

    @Override
    public List<DocTemplateParam> queryByTemplateCode(String templateCode) {
        if(StringUtils.isEmpty(templateCode)){
            throw new DocException("模版编码为空");
        }
        return docTemplateParamMapper.queryByTemplateCode(templateCode);
    }
}
