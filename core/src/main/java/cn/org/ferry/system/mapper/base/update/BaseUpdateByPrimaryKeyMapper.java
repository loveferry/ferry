package cn.org.ferry.system.mapper.base.update;


import cn.org.ferry.system.mybatis.annotation.RegisterMapper;
import cn.org.ferry.system.mybatis.providers.BaseUpdateProvider;
import org.apache.ibatis.annotations.UpdateProvider;

/**
 * 通用mapper 更新
 * 会更新null值
 */
@RegisterMapper
public interface BaseUpdateByPrimaryKeyMapper<T> {
    @UpdateProvider(type = BaseUpdateProvider.class, method = "dynamicSQL")
    int updateByPrimaryKey(T record);

}
