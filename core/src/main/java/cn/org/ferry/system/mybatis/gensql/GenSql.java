package cn.org.ferry.system.mybatis.gensql;


import cn.org.ferry.system.mybatis.entity.EntityColumn;
import cn.org.ferry.system.mybatis.entity.EntityTable;

/**
 * 生成 SQL，初始化时执行
 *
 * @author liuzh
 */
public interface GenSql {

    String genSql(EntityTable entityTable, EntityColumn entityColumn);

    class NULL implements GenSql {
        @Override
        public String genSql(EntityTable entityTable, EntityColumn entityColumn) {
            throw new UnsupportedOperationException();
        }
    }
}
