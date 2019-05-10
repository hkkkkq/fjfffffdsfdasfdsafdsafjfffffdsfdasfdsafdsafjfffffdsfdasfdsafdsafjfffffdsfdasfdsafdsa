import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class PStmtTest {
	
	
	
	public static void main(String[] args) 
	{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		String dbUrl = "jdbc:tibero:thin:@10.219.4.88:8634:GWIDB";  //prd internal
//		String dbUsr = "infinilink";
//		String dbPwd = "Prd$inf1";
		
		String dbUsr = "e2einfinilink";
		String dbPwd = "New1234!";
		
		try
		{
			Class.forName("com.tmax.tibero.jdbc.TbDriver");
			con = DriverManager.getConnection(dbUrl, dbUsr, dbPwd);
			
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT                       																																					                   ");
			sb.append("	GUID,                                                                                                                    ");
			sb.append("	GLOBAL_NO,                                                                                                               ");
			sb.append("	SERVER_ID,                                                                                                               ");
			sb.append("	BIZSYS_ID                                                                                                                ");
			sb.append("	,                                                                                                                        ");
			sb.append("	INTF_ID,                                                                                                                 ");
			sb.append("	INTF_NAME,                                                                                                               ");
			sb.append("	BIZTX_ID                                                                                                                 ");
			sb.append("	,                                                                                                                        ");
			sb.append("	ORG_ID,                                                                                                                  ");
			sb.append("	ORG_NAME                                                                                                                 ");
			sb.append("	,                                                                                                                        ");
			sb.append("	SOURCE_SYSTEM_ID,                                                                                                        ");
			sb.append("	SOURCE_SYSTEM_NAME,                                                                                                      ");
			sb.append("	SOURCE_CONN_TYPE_CD,                                                                                                     ");
			sb.append("	SOURCE_SYNC_TYPE_CD                                                                                                      ");
			sb.append("	,                                                                                                                        ");
			sb.append("	TARGET_SYSTEM_ID,                                                                                                        ");
			sb.append("	TARGET_SYSTEM_NAME,                                                                                                      ");
			sb.append("	TARGET_CONN_TYPE_CD,                                                                                                     ");
			sb.append("	TARGET_SYNC_TYPE_CD                                                                                                      ");
			sb.append("	,                                                                                                                        ");
			sb.append("	SYNC_TYPE_CD,                                                                                                            ");
			sb.append("	DIRECTION_CD,                                                                                                            ");
			sb.append("	ONLINE_TYPE_CD,                                                                                                          ");
			sb.append("	INNER_TYPE_CD,                                                                                                           ");
			sb.append("	TRANSACTION_TYPE_CD,                                                                                                     ");
			sb.append("	USE_YN                                                                                                                   ");
			sb.append("	,                                                                                                                        ");
			sb.append("	CREATE_DATE,                                                                                                             ");
			sb.append("	to_char(CREATE_DATE,                                                                                                     ");
			sb.append("	'DD') DAY,                                                                                                               ");
			sb.append("	STATE,                                                                                                                   ");
			sb.append("	ERRORMESSAGE,                                                                                                            ");
			sb.append("	ELAPSEDTIME                                                                                                              ");
			sb.append("	,                                                                                                                        ");
			sb.append("	STARTTIME,                                                                                                               ");
			sb.append("	ENDTIME                                                                                                                  ");
			sb.append("	from (                                                                                                                   ");
			sb.append("	select rownum rnum                                                                                                       ");
			sb.append("	,                                                                                                                        ");
			sb.append("	GUID,                                                                                                                    ");
			sb.append("	GLOBAL_NO,                                                                                                               ");
			sb.append("	SERVER_ID,                                                                                                               ");
			sb.append("	BIZSYS_ID                                                                                                                ");
			sb.append("	,                                                                                                                        ");
			sb.append("	INTF_ID,                                                                                                                 ");
			sb.append("	INTF_NAME,                                                                                                               ");
			sb.append("	BIZTX_ID                                                                                                                 ");
			sb.append("	,                                                                                                                        ");
			sb.append("	ORG_ID,                                                                                                                  ");
			sb.append("	ORG_NAME                                                                                                                 ");
			sb.append("	,                                                                                                                        ");
			sb.append("	SOURCE_SYSTEM_ID,                                                                                                        ");
			sb.append("	SOURCE_SYSTEM_NAME,                                                                                                      ");
			sb.append("	SOURCE_CONN_TYPE_CD,                                                                                                     ");
			sb.append("	SOURCE_SYNC_TYPE_CD                                                                                                      ");
			sb.append("	,                                                                                                                        ");
			sb.append("	TARGET_SYSTEM_ID,                                                                                                        ");
			sb.append("	TARGET_SYSTEM_NAME,                                                                                                      ");
			sb.append("	TARGET_CONN_TYPE_CD,                                                                                                     ");
			sb.append("	TARGET_SYNC_TYPE_CD                                                                                                      ");
			sb.append("	,                                                                                                                        ");
			sb.append("	SYNC_TYPE_CD,                                                                                                            ");
			sb.append("	DIRECTION_CD,                                                                                                            ");
			sb.append("	ONLINE_TYPE_CD,                                                                                                          ");
			sb.append("	INNER_TYPE_CD,                                                                                                           ");
			sb.append("	TRANSACTION_TYPE_CD,                                                                                                     ");
			sb.append("	USE_YN                                                                                                                   ");
			sb.append("	,                                                                                                                        ");
			sb.append("	CREATE_DATE,                                                                                                             ");
			sb.append("	STATE,                                                                                                                   ");
			sb.append("	ERRORMESSAGE,                                                                                                            ");
			sb.append("	ELAPSEDTIME,                                                                                                             ");
			sb.append("	STARTTIME,                                                                                                               ");
			sb.append("	ENDTIME                                                                                                                  ");
			sb.append("	from (                                                                                                                   ");
			sb.append("	select                                                                                                                   ");
			sb.append("	GUID,                                                                                                                    ");
			sb.append("	GLOBAL_NO,                                                                                                               ");
			sb.append("	SERVER_ID,                                                                                                               ");
			sb.append("	BIZSYS_ID                                                                                                                ");
			sb.append("	,                                                                                                                        ");
			sb.append("	INTF_ID,                                                                                                                 ");
			sb.append("	INTF_NAME,                                                                                                               ");
			sb.append("	BIZTX_ID                                                                                                                 ");
			sb.append("	,                                                                                                                        ");
			sb.append("	ORG_ID,                                                                                                                  ");
			sb.append("	ORG_NAME                                                                                                                 ");
			sb.append("	,                                                                                                                        ");
			sb.append("	SOURCE_SYSTEM_ID,                                                                                                        ");
			sb.append("	SOURCE_SYSTEM_NAME,                                                                                                      ");
			sb.append("	SOURCE_CONN_TYPE_CD,                                                                                                     ");
			sb.append("	SOURCE_SYNC_TYPE_CD                                                                                                      ");
			sb.append("	,                                                                                                                        ");
			sb.append("	TARGET_SYSTEM_ID,                                                                                                        ");
			sb.append("	TARGET_SYSTEM_NAME,                                                                                                      ");
			sb.append("	TARGET_CONN_TYPE_CD,                                                                                                     ");
			sb.append("	TARGET_SYNC_TYPE_CD                                                                                                      ");
			sb.append("	,                                                                                                                        ");
			sb.append("	SYNC_TYPE_CD,                                                                                                            ");
			sb.append("	DIRECTION_CD,                                                                                                            ");
			sb.append("	ONLINE_TYPE_CD,                                                                                                          ");
			sb.append("	INNER_TYPE_CD,                                                                                                           ");
			sb.append("	TRANSACTION_TYPE_CD,                                                                                                     ");
			sb.append("	USE_YN                                                                                                                   ");
			sb.append("	,                                                                                                                        ");
			sb.append("	CREATE_DATE,                                                                                                             ");
			sb.append("	STATE,                                                                                                                   ");
			sb.append("	ERRORMESSAGE,                                                                                                            ");
			sb.append("	ELAPSEDTIME,                                                                                                             ");
			sb.append("	STARTTIME,                                                                                                               ");
			sb.append("	ENDTIME                                                                                                                  ");
			sb.append("	from (                                                                                                                   ");
			sb.append("	select /*+ LEADING(log) INDEX_DESC(log,(REG_DT)) INDEX(tx,(GUID)) USE_NL(org,intf,log,tx) */                             ");
			sb.append("	log.GUID,                                                                                                                ");
			sb.append("	GLOBAL_NO,                                                                                                               ");
			sb.append("	log.BIZTX_ID,                                                                                                            ");
			sb.append("	SVR_INSTANCE_ID SERVER_ID,                                                                                               ");
			sb.append("	INTF_ID,                                                                                                                 ");
			sb.append("	INTF_NAME,                                                                                                               ");
			sb.append("	org.ORG_ID,                                                                                                              ");
			sb.append("	org.ORG_NAME,                                                                                                            ");
			sb.append("	intf.BIZSYS_ID,                                                                                                          ");
			sb.append("	SOURCE_SYSTEM_ID,                                                                                                        ");
			sb.append("	srcsys.SYSTEM_NAME SOURCE_SYSTEM_NAME,                                                                                   ");
			sb.append("	srcsys.SYNC_TYPE_CD SOURCE_SYNC_TYPE_CD,                                                                                 ");
			sb.append("	srcsys.CONN_TYPE_CD SOURCE_CONN_TYPE_CD,                                                                                 ");
			sb.append("	TARGET_SYSTEM_ID,                                                                                                        ");
			sb.append("	trgsys.SYSTEM_NAME TARGET_SYSTEM_NAME,                                                                                   ");
			sb.append("	trgsys.SYNC_TYPE_CD TARGET_SYNC_TYPE_CD,                                                                                 ");
			sb.append("	trgsys.CONN_TYPE_CD TARGET_CONN_TYPE_CD,                                                                                 ");
			sb.append("	intf.SYNC_TYPE_CD,                                                                                                       ");
			sb.append("	DIRECTION_CD,                                                                                                            ");
			sb.append("	ONLINE_TYPE_CD,                                                                                                          ");
			sb.append("	intf.INNER_TYPE_CD,                                                                                                      ");
			sb.append("	intf.USE_YN,                                                                                                             ");
			sb.append("	REG_DT CREATE_DATE,                                                                                                      ");
			sb.append("	IF_TYPE_CD TRANSACTION_TYPE_CD,                                                                                          ");
			sb.append("	STATE,                                                                                                                   ");
			sb.append("	ERRORMESSAGE,                                                                                                            ");
			sb.append("	ELAPSEDTIME,                                                                                                             ");
			sb.append("	STARTTIME,                                                                                                               ");
			sb.append("	ENDTIME                                                                                                                  ");
			sb.append("	from INFL_MON_INTF_META_BAS intf                                                                                         ");
			sb.append("	left OUTER JOIN INFL_MON_SYS_META_BAS srcsys on intf.SOURCE_SYSTEM_ID = srcsys.SYSTEM_ID AND srcsys.USE_YN = 'Y'         ");
			sb.append("	left OUTER JOIN INFL_MON_SYS_META_BAS trgsys on intf.TARGET_SYSTEM_ID = trgsys.SYSTEM_ID AND trgsys.USE_YN = 'Y'         ");
			sb.append("	,INFL_MON_ORG_META_BAS org                                                                                               ");
			sb.append("	,INFINILINK.INFL_CUSTOM_LOG_TXN log                                                                                      ");
			sb.append("	,INFINILINK.IL_TRANSACTION_LOG_04 tx                                                                                     ");
			sb.append("	where intf.ORG_ID = org.ORG_ID                                                                                           ");
			sb.append("	and log.GUID = tx.GUID                                                                                                   ");
			sb.append("	and log.BIZTX_ID = intf.BIZTX_ID                                                                                         ");
			sb.append("	and intf.BIZTX_ID IS NOT null                                                                                            ");
			sb.append("	and intf.USE_YN='Y'                                                                                                      ");
			sb.append("	AND org.USE_YN='Y'                                                                                                       ");
			sb.append("	and tx.PGUID IS null                                                                                                     ");
			sb.append("	and tx.STATE = 'ERROR'                                                                                                   ");
			sb.append("	and intf.INTF_ID = 'NIFE_NCRC_LNMM_S_0001_00'                                                                            ");
			sb.append("	and log.REG_DT >= '2017-06-04 00:00:00'                                                                                  ");
			sb.append("	AND REG_DT < '2017-06-05 00:00:00'                                                                                       ");
			sb.append(")                                                                                                                         ");
			sb.append(")                                                                                                                         ");
			sb.append(")                                                                                                                         ");
			sb.append("WHERE                                                                                                                     ");
			sb.append("rnum BETWEEN 1 AND 10;																								     ");
			
			StringBuffer sb_2 = new StringBuffer();
			sb_2.append("SELECT                       																																					                   ");
			sb_2.append("	GUID,                                                                                                                    ");
			sb_2.append("	GLOBAL_NO,                                                                                                               ");
			sb_2.append("	SERVER_ID,                                                                                                               ");
			sb_2.append("	BIZSYS_ID                                                                                                                ");
			sb_2.append("	,                                                                                                                        ");
			sb_2.append("	INTF_ID,                                                                                                                 ");
			sb_2.append("	INTF_NAME,                                                                                                               ");
			sb_2.append("	BIZTX_ID                                                                                                                 ");
			sb_2.append("	,                                                                                                                        ");
			sb_2.append("	ORG_ID,                                                                                                                  ");
			sb_2.append("	ORG_NAME                                                                                                                 ");
			sb_2.append("	,                                                                                                                        ");
			sb_2.append("	SOURCE_SYSTEM_ID,                                                                                                        ");
			sb_2.append("	SOURCE_SYSTEM_NAME,                                                                                                      ");
			sb_2.append("	SOURCE_CONN_TYPE_CD,                                                                                                     ");
			sb_2.append("	SOURCE_SYNC_TYPE_CD                                                                                                      ");
			sb_2.append("	,                                                                                                                        ");
			sb_2.append("	TARGET_SYSTEM_ID,                                                                                                        ");
			sb_2.append("	TARGET_SYSTEM_NAME,                                                                                                      ");
			sb_2.append("	TARGET_CONN_TYPE_CD,                                                                                                     ");
			sb_2.append("	TARGET_SYNC_TYPE_CD                                                                                                      ");
			sb_2.append("	,                                                                                                                        ");
			sb_2.append("	SYNC_TYPE_CD,                                                                                                            ");
			sb_2.append("	DIRECTION_CD,                                                                                                            ");
			sb_2.append("	ONLINE_TYPE_CD,                                                                                                          ");
			sb_2.append("	INNER_TYPE_CD,                                                                                                           ");
			sb_2.append("	TRANSACTION_TYPE_CD,                                                                                                     ");
			sb_2.append("	USE_YN                                                                                                                   ");
			sb_2.append("	,                                                                                                                        ");
			sb_2.append("	CREATE_DATE,                                                                                                             ");
			sb_2.append("	to_char(CREATE_DATE,                                                                                                     ");
			sb_2.append("	'DD') DAY,                                                                                                               ");
			sb_2.append("	STATE,                                                                                                                   ");
			sb_2.append("	ERRORMESSAGE,                                                                                                            ");
			sb_2.append("	ELAPSEDTIME                                                                                                              ");
			sb_2.append("	,                                                                                                                        ");
			sb_2.append("	STARTTIME,                                                                                                               ");
			sb_2.append("	ENDTIME                                                                                                                  ");
			sb_2.append("	from (                                                                                                                   ");
			sb_2.append("	select rownum rnum                                                                                                       ");
			sb_2.append("	,                                                                                                                        ");
			sb_2.append("	GUID,                                                                                                                    ");
			sb_2.append("	GLOBAL_NO,                                                                                                               ");
			sb_2.append("	SERVER_ID,                                                                                                               ");
			sb_2.append("	BIZSYS_ID                                                                                                                ");
			sb_2.append("	,                                                                                                                        ");
			sb_2.append("	INTF_ID,                                                                                                                 ");
			sb_2.append("	INTF_NAME,                                                                                                               ");
			sb_2.append("	BIZTX_ID                                                                                                                 ");
			sb_2.append("	,                                                                                                                        ");
			sb_2.append("	ORG_ID,                                                                                                                  ");
			sb_2.append("	ORG_NAME                                                                                                                 ");
			sb_2.append("	,                                                                                                                        ");
			sb_2.append("	SOURCE_SYSTEM_ID,                                                                                                        ");
			sb_2.append("	SOURCE_SYSTEM_NAME,                                                                                                      ");
			sb_2.append("	SOURCE_CONN_TYPE_CD,                                                                                                     ");
			sb_2.append("	SOURCE_SYNC_TYPE_CD                                                                                                      ");
			sb_2.append("	,                                                                                                                        ");
			sb_2.append("	TARGET_SYSTEM_ID,                                                                                                        ");
			sb_2.append("	TARGET_SYSTEM_NAME,                                                                                                      ");
			sb_2.append("	TARGET_CONN_TYPE_CD,                                                                                                     ");
			sb_2.append("	TARGET_SYNC_TYPE_CD                                                                                                      ");
			sb_2.append("	,                                                                                                                        ");
			sb_2.append("	SYNC_TYPE_CD,                                                                                                            ");
			sb_2.append("	DIRECTION_CD,                                                                                                            ");
			sb_2.append("	ONLINE_TYPE_CD,                                                                                                          ");
			sb_2.append("	INNER_TYPE_CD,                                                                                                           ");
			sb_2.append("	TRANSACTION_TYPE_CD,                                                                                                     ");
			sb_2.append("	USE_YN                                                                                                                   ");
			sb_2.append("	,                                                                                                                        ");
			sb_2.append("	CREATE_DATE,                                                                                                             ");
			sb_2.append("	STATE,                                                                                                                   ");
			sb_2.append("	ERRORMESSAGE,                                                                                                            ");
			sb_2.append("	ELAPSEDTIME,                                                                                                             ");
			sb_2.append("	STARTTIME,                                                                                                               ");
			sb_2.append("	ENDTIME                                                                                                                  ");
			sb_2.append("	from (                                                                                                                   ");
			sb_2.append("	select                                                                                                                   ");
			sb_2.append("	GUID,                                                                                                                    ");
			sb_2.append("	GLOBAL_NO,                                                                                                               ");
			sb_2.append("	SERVER_ID,                                                                                                               ");
			sb_2.append("	BIZSYS_ID                                                                                                                ");
			sb_2.append("	,                                                                                                                        ");
			sb_2.append("	INTF_ID,                                                                                                                 ");
			sb_2.append("	INTF_NAME,                                                                                                               ");
			sb_2.append("	BIZTX_ID                                                                                                                 ");
			sb_2.append("	,                                                                                                                        ");
			sb_2.append("	ORG_ID,                                                                                                                  ");
			sb_2.append("	ORG_NAME                                                                                                                 ");
			sb_2.append("	,                                                                                                                        ");
			sb_2.append("	SOURCE_SYSTEM_ID,                                                                                                        ");
			sb_2.append("	SOURCE_SYSTEM_NAME,                                                                                                      ");
			sb_2.append("	SOURCE_CONN_TYPE_CD,                                                                                                     ");
			sb_2.append("	SOURCE_SYNC_TYPE_CD                                                                                                      ");
			sb_2.append("	,                                                                                                                        ");
			sb_2.append("	TARGET_SYSTEM_ID,                                                                                                        ");
			sb_2.append("	TARGET_SYSTEM_NAME,                                                                                                      ");
			sb_2.append("	TARGET_CONN_TYPE_CD,                                                                                                     ");
			sb_2.append("	TARGET_SYNC_TYPE_CD                                                                                                      ");
			sb_2.append("	,                                                                                                                        ");
			sb_2.append("	SYNC_TYPE_CD,                                                                                                            ");
			sb_2.append("	DIRECTION_CD,                                                                                                            ");
			sb_2.append("	ONLINE_TYPE_CD,                                                                                                          ");
			sb_2.append("	INNER_TYPE_CD,                                                                                                           ");
			sb_2.append("	TRANSACTION_TYPE_CD,                                                                                                     ");
			sb_2.append("	USE_YN                                                                                                                   ");
			sb_2.append("	,                                                                                                                        ");
			sb_2.append("	CREATE_DATE,                                                                                                             ");
			sb_2.append("	STATE,                                                                                                                   ");
			sb_2.append("	ERRORMESSAGE,                                                                                                            ");
			sb_2.append("	ELAPSEDTIME,                                                                                                             ");
			sb_2.append("	STARTTIME,                                                                                                               ");
			sb_2.append("	ENDTIME                                                                                                                  ");
			sb_2.append("	from (                                                                                                                   ");
			sb_2.append("	select /*+ INDEX(log,(REG_DT)) INDEX(tx,(GUID)) */                             ");
			sb_2.append("	log.GUID,                                                                                                                ");
			sb_2.append("	GLOBAL_NO,                                                                                                               ");
			sb_2.append("	log.BIZTX_ID,                                                                                                            ");
			sb_2.append("	SVR_INSTANCE_ID SERVER_ID,                                                                                               ");
			sb_2.append("	INTF_ID,                                                                                                                 ");
			sb_2.append("	INTF_NAME,                                                                                                               ");
			sb_2.append("	org.ORG_ID,                                                                                                              ");
			sb_2.append("	org.ORG_NAME,                                                                                                            ");
			sb_2.append("	intf.BIZSYS_ID,                                                                                                          ");
			sb_2.append("	SOURCE_SYSTEM_ID,                                                                                                        ");
			sb_2.append("	srcsys.SYSTEM_NAME SOURCE_SYSTEM_NAME,                                                                                   ");
			sb_2.append("	srcsys.SYNC_TYPE_CD SOURCE_SYNC_TYPE_CD,                                                                                 ");
			sb_2.append("	srcsys.CONN_TYPE_CD SOURCE_CONN_TYPE_CD,                                                                                 ");
			sb_2.append("	TARGET_SYSTEM_ID,                                                                                                        ");
			sb_2.append("	trgsys.SYSTEM_NAME TARGET_SYSTEM_NAME,                                                                                   ");
			sb_2.append("	trgsys.SYNC_TYPE_CD TARGET_SYNC_TYPE_CD,                                                                                 ");
			sb_2.append("	trgsys.CONN_TYPE_CD TARGET_CONN_TYPE_CD,                                                                                 ");
			sb_2.append("	intf.SYNC_TYPE_CD,                                                                                                       ");
			sb_2.append("	DIRECTION_CD,                                                                                                            ");
			sb_2.append("	ONLINE_TYPE_CD,                                                                                                          ");
			sb_2.append("	intf.INNER_TYPE_CD,                                                                                                      ");
			sb_2.append("	intf.USE_YN,                                                                                                             ");
			sb_2.append("	REG_DT CREATE_DATE,                                                                                                      ");
			sb_2.append("	IF_TYPE_CD TRANSACTION_TYPE_CD,                                                                                          ");
			sb_2.append("	STATE,                                                                                                                   ");
			sb_2.append("	ERRORMESSAGE,                                                                                                            ");
			sb_2.append("	ELAPSEDTIME,                                                                                                             ");
			sb_2.append("	STARTTIME,                                                                                                               ");
			sb_2.append("	ENDTIME                                                                                                                  ");
			sb_2.append("	from INFL_MON_INTF_META_BAS intf                                                                                         ");
			sb_2.append("	left OUTER JOIN INFL_MON_SYS_META_BAS srcsys on intf.SOURCE_SYSTEM_ID = srcsys.SYSTEM_ID AND srcsys.USE_YN = 'Y'         ");
			sb_2.append("	left OUTER JOIN INFL_MON_SYS_META_BAS trgsys on intf.TARGET_SYSTEM_ID = trgsys.SYSTEM_ID AND trgsys.USE_YN = 'Y'         ");
			sb_2.append("	,INFL_MON_ORG_META_BAS org                                                                                               ");
			sb_2.append("	,INFINILINK.INFL_CUSTOM_LOG_TXN log                                                                                      ");
			sb_2.append("	,INFINILINK.IL_TRANSACTION_LOG_04 tx                                                                                     ");
			sb_2.append("	where intf.ORG_ID = org.ORG_ID                                                                                           ");
			sb_2.append("	and log.GUID = tx.GUID                                                                                                   ");
			sb_2.append("	and log.BIZTX_ID = intf.BIZTX_ID                                                                                         ");
			sb_2.append("	and intf.BIZTX_ID IS NOT null                                                                                            ");
			sb_2.append("	and intf.USE_YN=?                                                                                                      ");
			sb_2.append("	AND org.USE_YN=?                                                                                                       ");
			sb_2.append("	and tx.PGUID IS null                                                                                                     ");
			sb_2.append("	and tx.STATE = ?                                                                                                   ");
			sb_2.append("	and intf.INTF_ID = ?                                                                            ");
			sb_2.append("	and log.REG_DT >= ?                                                                                 ");
			sb_2.append("	AND REG_DT < ?                                                                                       ");
//			sb_2.append("	and log.REG_DT >= to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.FF9')                                                                                  ");
//			sb_2.append("	AND REG_DT < to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.FF9')                                                                                       ");
			sb_2.append(")                                                                                                                         ");
			sb_2.append(")                                                                                                                         ");
			sb_2.append(")                                                                                                                         ");
			sb_2.append("WHERE                                                                                                                     ");
			sb_2.append("rnum BETWEEN ? AND ? 																								     ");
			
//			pstmt = con.prepareStatement(sb.toString());
			
			pstmt = con.prepareStatement(sb_2.toString());
			
			String useYn_1 = "Y";
			String useYn_2 = "Y";
			String STATE = "ERROR";
			String INTF_ID = "NIFE_NCRC_LNMM_S_0001_00";
			String startTime = "2017-06-04 00:00:00";
			String endTime = "2017-06-05 00:00:00";
			String rnum1 = "1";
			String rnum2 = "10";
			
			
			pstmt.setString(1, useYn_1);
			pstmt.setString(2, useYn_2);
			pstmt.setString(3, STATE);
			pstmt.setString(4, INTF_ID);
			pstmt.setString(5, startTime);
			pstmt.setString(6, endTime);
			pstmt.setInt(7, Integer.parseInt(rnum1));
			pstmt.setInt(8, Integer.parseInt(rnum2));

//			Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+09:00"));
//			
//			pstmt.setTimestamp(5, Timestamp.valueOf("2017-06-04 00:00:00"), c);
//			pstmt.setTimestamp(6, Timestamp.valueOf("2017-06-05 00:00:00"), c);
			
//			Calendar cal_1 = Calendar.getInstance();
//			Calendar cal_2 = Calendar.getInstance();
//			
//			cal_1.set(Calendar.YEAR, 2017);
//			cal_1.set(Calendar.MONTH, 6-1);
//			cal_1.set(Calendar.DATE, 4);
//			cal_1.set(Calendar.HOUR_OF_DAY, 0);
//			cal_1.set(Calendar.MINUTE, 0);
//			cal_1.set(Calendar.SECOND, 0);
//			
//			cal_2.set(Calendar.YEAR, 2017);
//			cal_2.set(Calendar.MONTH, 6-1);
//			cal_2.set(Calendar.DATE, 5);
//			cal_2.set(Calendar.HOUR_OF_DAY, 0);
//			cal_2.set(Calendar.MINUTE, 0);
//			cal_2.set(Calendar.SECOND, 0);
			
//			pstmt.setTimestamp(5, new java.sql.Timestamp(cal_1.getTime().getTime()));
//			pstmt.setTimestamp(6, new java.sql.Timestamp(cal_2.getTime().getTime()));
			
			
//			pstmt.setInt(5, Integer.parseInt(rnum1));
//			pstmt.setInt(6, Integer.parseInt(rnum2));
			
//			System.out.println(sb_2.toString());
			System.out.println("=>START: " + getCurrentTime());
			System.out.println("===============================");
	
			rs = pstmt.executeQuery();
			
			int count=0;
			while(rs.next())
			{
				
				count++;
				
				System.out.println("count="+count);
				
				String guid      = rs.getString(1);
				String grobalno  = rs.getString(2);
				
				
				System.out.println("guid="+guid);
				System.out.println("grobalno="+grobalno);
				
			}
			
			System.out.println("===============================");
			System.out.println("=>END: " + getCurrentTime());
			
			if(rs!=null)  { rs.close();   rs = null;  }
			if(pstmt!=null){ pstmt.close(); pstmt = null;}
			if(con!=null) { con.close();  con = null; }
//			if(ctx!=null) { ctx.close();  ctx = null; }
		}
		catch(Exception e)
		{
			e.printStackTrace();
			if(rs!=null)  { try {	rs.close();	} catch (Exception e1) {	e1.printStackTrace();} rs = null;}
			if(pstmt!=null){ try {	pstmt.close();	} catch (Exception e1) {	e1.printStackTrace();} pstmt = null;}
			if(con!=null) { try {	con.rollback(); con.setAutoCommit(true);	con.close(); con = null;	} catch (Exception e1) {	e1.printStackTrace();} con = null;}
		}
		finally
		{
			if(rs!=null)  { try {	rs.close();	} catch (Exception e1) {	e1.printStackTrace();} rs = null;}
			if(pstmt!=null){ try {	pstmt.close();	} catch (Exception e1) {	e1.printStackTrace();} pstmt = null;}
			if(con!=null){ try { con.setAutoCommit(true);	con.close(); con = null;	} catch (Exception e1) {	e1.printStackTrace();} con = null;}
//			if(ctx!=null){ try {	ctx.close();	} catch (Exception e1) {	e1.printStackTrace();} ctx = null;}
		}
		
	}
	
	public static String getCurrentTime() {
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	
	    Date date = new Date();
	    String dateString = formatter.format(date);
	    return dateString;
	}

}