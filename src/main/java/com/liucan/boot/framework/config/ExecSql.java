package com.liucan.boot.framework.config;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.nio.charset.Charset;
import java.sql.Connection;

/**
 * @author liucan
 * @version 20-1-1
 */
@Component
public class ExecSql {

    public ExecSql(@Qualifier("javaLearnDataSource") DataSource dataSource) throws Exception {
        Connection conn = dataSource.getConnection();
        ScriptRunner runner = new ScriptRunner(conn);
        Resources.setCharset(Charset.forName("UTF-8"));
        runner.setStopOnError(true); //遇见错误会停止执行，打印并抛出异常
        runner.setLogWriter(null);//设置是否输出日志
        runner.runScript(Resources.getResourceAsReader("sql/java_learn.sql"));
        runner.closeConnection();
        conn.close();
    }
}
