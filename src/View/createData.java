package View;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DB.DBConnection;

public class createData {

	public Connection getconnection() {
		Connection conn = null;
		
		DBConnection DBc = DBConnection.getinstance();
		
		conn = DBc.getconnection();

		return conn;
	}
	
	public void closeSQL(Connection con, PreparedStatement pstmt, ResultSet rs) {
		try {
			rs.close();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public ArrayList<String[]> checkPos(String s_date, String e_date, String pos,String str_code)
	{
		ArrayList<String[]> result_data = new ArrayList<>();
		Connection con = getconnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try 
		{
			String sql = "select   " +
					"a.sale_ymd,  " +
					"a.pos_no,  " +
					"a.cash_Count,  " +
					"a.cash_Amount,  " +
					"b.gift_Count,  " +
					"b.gift_Amount,  " +
					"c.goods_Count,  " +
					"c.goods_Amount  " +
					"from  " +
					"(  " +
					"select   " +
					"sale_ymd,  " +
					"pos_no,  " +
					"str_code, " +
					"NVL( EXTRACT(VALUE(V), '/unit/@count').GETNUMBERVAL(),0)           AS cash_Count,        " +
					"NVL( EXTRACT(VALUE(V), '/unit/@amount').GETNUMBERVAL(),0)          AS cash_Amount  " +
					"from sl055 A,  " +
					"TABLE(XMLSEQUENCE(EXTRACT(A.CONTNT, '/document/RegiDetail/unit'))) V  " +
					"where EXTRACT(VALUE(V), '/unit/@type').GETSTRINGVAL() = 'CashRecall'  " +
					") a,  " +
					"(  " +
					"select   " +
					"sale_ymd,  " +
					"pos_no,  " +
					"str_code, " +
					"NVL( EXTRACT(VALUE(V), '/unit/@count').GETNUMBERVAL(),0)           AS gift_Count,        " +
					"NVL( EXTRACT(VALUE(V), '/unit/@amount').GETNUMBERVAL(),0)          AS gift_Amount  " +
					"from sl055 A,  " +
					"TABLE(XMLSEQUENCE(EXTRACT(A.CONTNT, '/document/RegiDetail/unit'))) V  " +
					"where EXTRACT(VALUE(V), '/unit/@type').GETSTRINGVAL() = 'GiftRecall'  " +
					") b,  " +
					"(  " +
					"select   " +
					"sale_ymd,  " +
					"pos_no, " +
					"str_code, " +
					"NVL( EXTRACT(VALUE(V), '/unit/@count').GETNUMBERVAL(),0)           AS goods_Count,        " +
					"NVL( EXTRACT(VALUE(V), '/unit/@amount').GETNUMBERVAL(),0)          AS goods_Amount  " +
					"from sl055 A,  " +
					"TABLE(XMLSEQUENCE(EXTRACT(A.CONTNT, '/document/RegiDetail/unit'))) V  " +
					"where EXTRACT(VALUE(V), '/unit/@type').GETSTRINGVAL() = 'GoodsRecall'  " +
					") c  " +
					"where a.sale_ymd between ? and ? " +
					"and a.sale_ymd = b.sale_ymd  " +
					"and a.sale_ymd = c.sale_ymd  " +
					"and a.pos_no like ? || '%'  " +
					"and a.str_code = ? " +
					"and a.str_code = b.str_code " +
					"and a.str_code = c.str_code " +
					"and a.pos_no = b.pos_no  " +
					"and a.pos_no = c.pos_no ";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, s_date);
			pstmt.setString(2, e_date);
			pstmt.setString(3, pos);
			pstmt.setString(4, str_code);
			
			rs = pstmt.executeQuery();

			while(rs.next())
			{
				String[] in_list = new String[8];
				for(int i = 1; i < 9; i++)
				{
					in_list[i-1] = rs.getString(i);
				}
				result_data.add(in_list);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeSQL(con, pstmt, rs);
		}
		return result_data;
	}
}















