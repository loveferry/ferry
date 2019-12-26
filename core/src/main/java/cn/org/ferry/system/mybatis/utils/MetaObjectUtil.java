/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2018 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package cn.org.ferry.system.mybatis.utils;

import cn.org.ferry.system.exception.MybatisException;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.lang.reflect.Method;

/**
 * @author ferry
 */
public class MetaObjectUtil {
    public static Method method;

    private static final String METHOD_NAME = "forObject";

    static {
        try {
            Class<?> metaClass = SystemMetaObject.class;
            method = metaClass.getDeclaredMethod(METHOD_NAME, Object.class);
        } catch (Exception e1) {
            try {
                Class<?> metaClass = MetaObject.class;
                method = metaClass.getDeclaredMethod(METHOD_NAME, Object.class);
            } catch (Exception e2) {
                throw new MybatisException(e2);
            }
        }

    }

    public static MetaObject forObject(Object object) {
        try {
            return (MetaObject) method.invoke(null, object);
        } catch (Exception e) {
            throw new MybatisException(e);
        }
    }

}
