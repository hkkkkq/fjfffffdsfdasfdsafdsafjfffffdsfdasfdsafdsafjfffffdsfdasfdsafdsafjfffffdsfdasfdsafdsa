package com.henc.cdrs.common.mybatis.type;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Null 값을 빈 문자열로 처리할 수 있도록 함.
 */
@MappedJdbcTypes(JdbcType.VARCHAR)
public class NullStringTypeHandler implements TypeHandler<String> {

    @Override
    public String getResult(ResultSet rs, String columnName) throws SQLException {
        return (rs.getString(columnName) == null) ? "" : rs.getString(columnName);
    }

    @Override
    public String getResult(ResultSet rs, int columnIndex) throws SQLException {
        return (rs.getString(columnIndex) == null) ? "" : rs.getString(columnIndex);
    }

    @Override
    public String getResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        return (cs.getString(columnIndex) == null) ? "" : cs.getString(columnIndex);
    }

    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, String s, JdbcType jdbcType) throws SQLException {
        if (s == null) {
            preparedStatement.setString(i, "");
        } else {
            preparedStatement.setString(i, s);
        }
    }
}
