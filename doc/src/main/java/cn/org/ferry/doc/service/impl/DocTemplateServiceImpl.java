package cn.org.ferry.doc.service.impl;

import cn.org.ferry.core.dto.ResponseData;
import cn.org.ferry.core.service.impl.BaseServiceImpl;
import cn.org.ferry.doc.dto.model.DocTemplateDefinition;
import cn.org.ferry.doc.enums.BookMarkType;
import cn.org.ferry.doc.dto.DocTemplate;
import cn.org.ferry.doc.dto.DocTemplateParam;
import cn.org.ferry.doc.exceptions.DocException;
import cn.org.ferry.doc.mapper.DocTemplateMapper;
import cn.org.ferry.doc.service.DocTemplateParamService;
import cn.org.ferry.doc.service.DocTemplateService;
import cn.org.ferry.doc.utils.Docx4jGenerateUtil;
import cn.org.ferry.sys.dto.SysAttachmentCategory;
import cn.org.ferry.sys.dto.SysFile;
import cn.org.ferry.sys.exceptions.AttachmentException;
import cn.org.ferry.sys.exceptions.FileException;
import cn.org.ferry.sys.service.SysAttachmentCategoryService;
import cn.org.ferry.sys.service.SysFileService;
import cn.org.ferry.sys.service.SysSqlService;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * @author ferry ferry_sy@163.com
 * @date 2019/07/13
 * @description 文档模版业务实现层
 */

@Service
@PropertySource("classpath:config_base.properties")
public class DocTemplateServiceImpl extends BaseServiceImpl<DocTemplate> implements DocTemplateService {
    protected static final Logger logger = LoggerFactory.getLogger(DocTemplateServiceImpl.class);

    @Autowired
    private DocTemplateMapper docTemplateMapper;
    @Autowired
    private DocTemplateParamService docTemplateParamService;
    @Autowired
    private SysSqlService sysSqlService;
    @Autowired
    private SysFileService sysFileService;
    @Autowired
    private SysAttachmentCategoryService sysAttachmentCategoryService;
    @Value("upload.path")
    private String uploadPath;

    @Override
    public List<DocTemplate> queryByCondition(String condition, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return docTemplateMapper.queryByCondition(Optional.ofNullable(condition).orElse("").trim());
    }

    @Override
    public ResponseData definition(DocTemplateDefinition definition) {
        logger.info(("开始定义文档模版"));
        ResponseData responseData = new ResponseData();
        responseData.setSuccess(true);
        responseData.setMessage("模版定义成功");

        Assert.notNull(definition, "模版参数必填！");
        Assert.hasLength(definition.getTemplateCode(), "模版代码必填！");
        Assert.hasLength(definition.getTemplateName(), "模版名称必填！");
        Assert.hasLength(definition.getDescription(), "模版说明必填！");

        DocTemplate docTemplate = docTemplateMapper.queryByTemplateCode(definition.getTemplateCode());
        if(null == docTemplate){
            docTemplate = new DocTemplate();
            BeanUtils.copyProperties(definition, docTemplate);
            int count = mapper.insertSelective(docTemplate);
            logger.info("模版定义{}条", count);
        }else{
            docTemplate.setTemplateName(definition.getTemplateName());
            docTemplate.setDescription(definition.getDescription());
            docTemplate.setTemplateImage(definition.getTemplateImage());
            int count = docTemplateMapper.updateByPrimaryKey(docTemplate);
            logger.info("模版更新{}条", count);
        }
        return responseData;
    }

    @Override
    public DocTemplate queryByTemplateCode(String templateCode) {
        return docTemplateMapper.queryByTemplateCode(templateCode);
    }

    @Override
    public void generateWord(String templateCode, String sourceType, String sourceKey, Map<String, Object> params) {
        if(StringUtils.isEmpty(templateCode)){
            throw new DocException("模版代码为空");
        }
        if(StringUtils.isEmpty(sourceType)){
            throw new AttachmentException("目标附件类型为空");
        }
        // 获取模版文件
        List<SysFile> sysFileList = sysFileService.queryBySourceTypeAndSourceKey(DOC_TEMPLATE_ATTACHMENT_CATEGORY, templateCode);
        if(CollectionUtils.isEmpty(sysFileList)){
            throw new DocException("模版文档不存在");
        }
        SysFile templateSysFile = sysFileList.get(0);
        // 获取模版参数
        List<DocTemplateParam> docTemplateParamList = docTemplateParamService.queryByTemplateCode(templateCode);
        // 目标附件
        SysAttachmentCategory sysAttachmentCategory = sysAttachmentCategoryService.queryBySourceType(sourceType);
        String targetPath = uploadPath+File.separator+sysAttachmentCategory.getAttachmentPath()+File.separator+ UUID.randomUUID().toString();
        // 书签替换
        Map<String, Object> bookMarkParams = new HashMap<>(docTemplateParamList.size());
        Map<String, BookMarkType> bookMarkTypes = new HashMap<>(docTemplateParamList.size());
        try {
            for (DocTemplateParam docTemplateParam : docTemplateParamList) {
                String sql = sysSqlService.querySqlBySqlCode(docTemplateParam.getSqlCode());
                bookMarkParams.put(docTemplateParam.getBookMark(), sysSqlService.execute(sql, params));
                bookMarkTypes.put(docTemplateParam.getBookMark(), docTemplateParam.getParamType());
            }
        }catch (SQLException e){
            DocException docException = new DocException("模版参数查询错误");
            docException.initCause(e);
            throw docException;
        }
        try {
            Docx4jGenerateUtil.generateDocxWithReplaceBookMark(bookMarkParams, bookMarkTypes, templateSysFile.getFilePath(), targetPath, true);
            sysFileService.insertFileAndAttachment(sourceType, sourceKey, targetPath, templateSysFile.getFileName(),
                    "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        } catch (FileException e) {
            DocException docException = new DocException(e.getMessage());
            docException.initCause(e);
            throw docException;
        }
    }

}
