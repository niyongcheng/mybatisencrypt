package com.herbalife.is.mybatisencrypt.interceptor;

import com.herbalife.is.mybatisencrypt.annotation.Cellphone;
import com.herbalife.is.mybatisencrypt.config.AESConfig;
import com.herbalife.is.mybatisencrypt.util.AES256Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Intercepts(@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class}))
public class CellphoneDecryptInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("被拦截方法执行之前，做的辅助服务······");
        Object[] args = invocation.getArgs();
        DefaultResultSetHandler target =  (DefaultResultSetHandler)invocation.getTarget();
        List<Object> objects = target.handleResultSets((Statement) args[0]);
        if(objects.size() > 0){
            Field[] fields = objects.get(0).getClass().getDeclaredFields();
            objects.stream().forEach((item) ->{
                for (Field field : fields) {
                    if (field.getAnnotation(Cellphone.class) != null) {
                        field.setAccessible(true);
                        try {
                            if(field.getType() == String.class){
                                String mobilephone = (String)field.get(item);
                                field.set(item,AES256Util.decryptToString(mobilephone.getBytes(StandardCharsets.UTF_8), "123456"));
                            }
                            else {
                                byte[] cellphone = (byte[]) field.get(item);
                                field.set(item,AES256Util.decrypt(cellphone, "123456"));
                            }

                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                    }
            });
            return objects;
        }

        Object proceed = invocation.proceed();
        System.out.println("被拦截方法执行之后，做的辅助服务······");
        return proceed;
    }
}
