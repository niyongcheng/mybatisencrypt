package com.herbalife.is.mybatisencrypt.interceptor;

import com.herbalife.is.mybatisencrypt.annotation.Cellphone;
import com.herbalife.is.mybatisencrypt.config.AESConfig;
import com.herbalife.is.mybatisencrypt.util.AES256Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Map;

@Slf4j
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class CellphoneEncryptInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("被拦截方法执行之前，做的辅助服务······");

        Object[] args = invocation.getArgs();
        Method method = invocation.getMethod();
        Object target = invocation.getTarget();

        MappedStatement mappedStatement = (MappedStatement) args[0];

        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();

        // get parameters
        Object parameter = invocation.getArgs()[1];

        if (parameter != null) {
            // 获取成员变量
            Field[] declaredFields = parameter.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                if (field.getAnnotation(Cellphone.class) != null) { // update 语句插入 updateTime
                    if (SqlCommandType.INSERT.equals(sqlCommandType) || SqlCommandType.UPDATE.equals(sqlCommandType)) {
                        field.setAccessible(true);
                        if (field.get(parameter) != null) {
                            byte[] originalByteArray = (byte[]) field.get(parameter);
                            String originalString = new String(originalByteArray,
                                    StandardCharsets.UTF_8);
                            field.set(parameter, AES256Util.encrypt(originalString,"123456"));
                        }
                    }
                }
            }
        }

        Object proceed = invocation.proceed();
        System.out.println("被拦截方法执行之后，做的辅助服务······");
        return proceed;
    }
}
