package org.poem;

import org.poem.vo.DataSetVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * @author poem
 */
public class MysqlJdbcUtil {


    /**
     * 执行长时间sql
     *
     * @param sql
     * @return
     * @throws SQLException
     */
    public static DataSetVO queryByLongTimeSql(String sql) throws SQLException {
        List<String> metas = new ArrayList<>();
        List<List<String>> datas = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            connection = MyHikariDataSource.getConnection();
            pstmt = connection.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            pstmt.setFetchSize(Integer.MIN_VALUE);
            pstmt.setFetchDirection(ResultSet.FETCH_REVERSE);
            rs = pstmt.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int colsSize = metaData.getColumnCount();
            for (int i = 1; i <= colsSize; i++) {
                metas.add(metaData.getColumnName(i));
            }
            List<String> row;
            while (rs.next()) {
                row = new ArrayList<>();
                Object value;
                for (int i = 1; i <= colsSize; i++) {
                    value = rs.getObject(i);
                    if (value == null) {
                        row.add(null);
                    } else {
                        row.add(String.valueOf(value));
                    }
                }
                datas.add(row);
            }
        } finally {
            MyHikariDataSource.closeConnection(connection, pstmt, rs);
        }
        return new DataSetVO(metas, datas);
    }

}
