import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Stack;

import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

/**
 * 
 * @author 82023121
 * @Description CRONTAB을 이용하여 rte_log, message_log, message_log_by_biztx 로그 압축/파일암호화하고 이후 파일복호화/압축해제 할때 사용하는 프로그램
 *
 */
public class LogFileZipEncrypt
{
	public static void main(String[] args)
	{
		LogFileZipEncrypt lfze = new LogFileZipEncrypt();

		String command = args[0];
//		String command = "zip";
		
		System.out.println("param[0]: " + command);
		
		if(command!=null)
		{
			//zip and encryption
			if("zip".equals(command))
			{
				// sh /home/infadm/monitoring_chk_sh/LogFileZipEncrypt/logFilezipEnc.sh "/applog/infinilink/rte_log/eo_cdrm1" "/GW_REPO/applog_bak/infinilink/rte_log/eo_cdrm1/eo_cdrm1_svr1" "-5" "rte_log"
				// java -cp .:/home/infadm/monitoring_chk_sh/LogFileZipEncrypt:/home/infadm/monitoring_chk_sh/LogFileZipEncrypt/commons-compress-1.11.jar:/home/infadm/monitoring_chk_sh/LogFileZipEncrypt/logFileZipEnc.jar LogFileZipEncrypt "zip" "$1" "$2" "$3" "$4"
				
				// sh /home/infadm/monitoring_chk_sh/LogFileZipEncrypt/logFilezipEnc.sh "/applog/infinilink/message_log_by_biztx/io_cdrm1" "/GW_REPO/applog_bak/infinilink/message_log_by_biztx/io_cdrm1/io_cdrm1_svr1" "-5" "message_log_by_biztx"
				// -5 정보를 가지고 /applog/infinilink/message_log_by_biztx/io_cdrm1 => /applog/infinilink/message_log_by_biztx/io_cdrm1/20161025 날짜 폴더 추가하는 로직 존재
				// java -cp .:/home/infadm/monitoring_chk_sh/LogFileZipEncrypt:/home/infadm/monitoring_chk_sh/LogFileZipEncrypt/commons-compress-1.11.jar:/home/infadm/monitoring_chk_sh/LogFileZipEncrypt/logFileZipEnc.jar LogFileZipEncrypt "zip" "$1" "$2" "$3" "$4"
				
				
				
				String srcPath = args[1];
				String trgPath = args[2];
				String minusDay = args[3];
				String logType = args[4];
				
//				String srcPath = "F:\\test\\fileBackupTest\\src";
//				String trgPath = "F:\\test\\fileBackupTest\\trg";
//				String minusDay = "-7";
				
				System.out.println("param[1]: " + srcPath);
				System.out.println("param[2]: " + trgPath);
				
//				if (!logType.equals("message_log_by_biztx")) {
//					System.out.println("param[3]: " + minusDay);
//				} else {
//					System.out.println("param[3]: " + "useless");
//				}
				
				System.out.println("param[3]: " + minusDay);
				
				System.out.println("param[4]: " + logType);
						
				File srcFile = new File(srcPath);
				File trgFile = new File(trgPath);
			    			
    			if(!trgFile.exists() || !trgFile.isDirectory())
    			{
    				trgFile.mkdirs(); // /GW_REPO/applog_bak/infinilink/rte_log/eo_cdrm1/eo_cdrm1_svr1
    			}
				
				
				// 기준일자
				Calendar cal = Calendar.getInstance();
				
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
				
				String now = sdf1.format(cal.getTime());
				// Start Time: 2016-09-11 16:44:00.583
				System.out.println("Start Time: " + now);
				
				String baseDate_String = "";
				
//				if (!logType.equals("message_log_by_biztx")) {
//				
//					//minusDay = "-5"
//					cal.add(Calendar.DATE, Integer.parseInt(minusDay));
//					
//					//baseDate_String: 20160906
//					baseDate_String = sdf2.format(cal.getTime());
//					
//				} else { 
//					
//					// /applog/infinilink/message_log_by_biztx/io_cdrm1/20161026
//					String[] arrSrcPath = srcPath.split("\\/");
//					baseDate_String = arrSrcPath[arrSrcPath.length-1];
//					
//				}
				
				//minusDay = "-5"
				cal.add(Calendar.DATE, Integer.parseInt(minusDay));
				
				//baseDate_String: 20160906
				baseDate_String = sdf2.format(cal.getTime());

				System.out.println("baseDate_String: " + baseDate_String);
				
				int baseDate_Int = Integer.parseInt(baseDate_String);
				
				
				if(srcFile.exists() && srcFile.isDirectory())
				{
					File[] files = srcFile.listFiles();
					
					// 처리될 파일 개수
					int i = 0;
					
					for(File logFile : files)
					{
						String logFileName = logFile.getName();
											
						//if(logFile.exists() && logFile.isFile() && ( logFileName.endsWith(".log") || logFileName.indexOf("gc.log_")>-1 || logFileName.startsWith("access_")) )
						//if( logFile.exists() && logFile.isFile() && ( logFileName.endsWith(".log")) )
						if( logFile.exists() && logFile.isFile() )
						{
							
//							System.out.println("logFileName: " + logFileName);
							
							String YYYYMMDD_String = "";
							int YYYYMMDD_int = 0;
							
							try
							{
								if(logFileName.indexOf("rte_") > -1 )
								{
									//20160506
									YYYYMMDD_String = logFileName.substring(logFileName.indexOf("rte_")+4, logFileName.indexOf("rte_")+4+8);
								}
//								if(logFileName.indexOf("dis_") > -1 )
//								{
//									YYYYMMDD_String = logFileName.substring(logFileName.indexOf("dis_")+4, logFileName.indexOf("dis_")+4+8);
//								}
//								if(logFileName.indexOf("JeusServer_") > -1 )
//								{
//									YYYYMMDD_String = logFileName.substring(logFileName.indexOf("JeusServer_")+11, logFileName.indexOf("JeusServer_")+11+8);
//								}
//								if(logFileName.indexOf("access_") > -1 )
//								{
//									YYYYMMDD_String = logFileName.substring(logFileName.indexOf("access_")+7, logFileName.indexOf("access_")+7+8);
//								}
//								if(logFileName.indexOf("gc.log_") > -1 )
//								{
//									YYYYMMDD_String = logFileName.substring(logFileName.indexOf("gc.log_")+7, logFileName.indexOf("gc.log_")+7+8);
//								}
								if(logFileName.indexOf("message_") > -1 )
								{
									YYYYMMDD_String = logFileName.substring(logFileName.indexOf("message_")+8, logFileName.indexOf("message_")+8+8);
								}
								if(logFileName.indexOf("biztx_") > -1 )
								{
									YYYYMMDD_String = logFileName.substring(logFileName.indexOf("biztx_")+6, logFileName.indexOf("biztx_")+6+8);
								}
								
							} 
							catch (Exception e1)
							{
								e1.printStackTrace();
								//YYYYMMDD_String: 20160908
								System.out.println("YYYYMMDD_String: " + YYYYMMDD_String);
								continue;
							}
														
//							System.out.println("YYYYMMDD_String: " + YYYYMMDD_String);
							
							if(!"".equals(YYYYMMDD_String))
							{						
										
								try
								{					
									YYYYMMDD_int = Integer.parseInt(YYYYMMDD_String);
								}
								catch(NumberFormatException e)
								{
									e.printStackTrace();
									System.out.println("YYYYMMDD_String: " + YYYYMMDD_String);
									continue;
								}
								
								if(baseDate_Int >= YYYYMMDD_int) // 기준일자 포함 이전 날짜의 로그가 대상
								{
									try
									{
										System.out.println("[" + ++i + "]");
										
										System.out.println("Start zip, encryption file name: " + logFile.getAbsolutePath());
										
										//zip 압축하기
										File zippedFile = lfze.zip(logFile, trgFile, Charset.defaultCharset().name(), true);
										
										Thread.sleep(1000);

										//파일 암호화하기
										String[] encFilePathName = lfze.encryptFile(zippedFile.getAbsolutePath());
										//String[] encFilePathName = lfze.encryptFile(logFile.getAbsolutePath());
										
										Thread.sleep(1000);
										
										logFile.delete();
										zippedFile.delete();
										
										//file move to NAS
										String encFileMoveDirPath = trgPath + File.separator + YYYYMMDD_String;
										
										File moveDir = new File(encFileMoveDirPath);
										if(!moveDir.exists())
										{
											moveDir.mkdirs();
											Thread.sleep(500);
										}
										
										File encFile = new File(encFilePathName[0], encFilePathName[1]);
										
										boolean isMoved = encFile.renameTo(new File(encFileMoveDirPath, encFilePathName[1]));
										
										//Because Fail File ReName  -> Do File Copy Move
										if(isMoved == false && encFile.exists())
										{
											byte[] buf = new byte[1024];
											
											File moveFile = new File(encFileMoveDirPath, encFilePathName[1]);
											
											FileInputStream fin = new FileInputStream(encFile);
											FileOutputStream fout = new FileOutputStream(moveFile);
											
											int read = 0;
											while(( read=fin.read(buf, 0, 1024)) !=-1)
											{
												fout.write(buf, 0, read);
											}
											
											fin.close();
											fout.close();
											encFile.delete();
										}			
										
										System.out.println("encFile getAbsolutePath="+encFile.getAbsolutePath());
//										System.out.println("encFile encFileMoveDirPath="+encFileMoveDirPath);
										System.out.println("encFile isMoved: " + isMoved);
//										System.out.println("encFile encFilePathName[0]="+encFilePathName[0]);
//										System.out.println("encFile encFilePathName[1]="+encFilePathName[1]);
										
										System.out.println("End zip, encryption file name: " + logFile.getAbsolutePath());

									}
									catch (Exception e) 
									{
										e.printStackTrace();
										System.exit(-1);
										
									}
								}
								
							}
						}
					}
					
					if (logType.equals("message_log_by_biztx")) {
						
						boolean isRemoveDir = false;
						
						if(srcFile.exists() && srcFile.isDirectory()) {
							
							//applog/infinilink/message_log_by_biztx/io_cdrm1/20161026
							try {
								Thread.sleep(1000);
								isRemoveDir = lfze.removeDir(srcFile);							
								
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							System.out.println("=====================");
							System.out.println("Finally, srcFile Directory delete: " + srcFile.getAbsolutePath() + " => " + isRemoveDir);

						}
					}
						
				}
			}
			
			//decryption and unzip
			else if("unzip".equals(command))
			{
				// sh /home/infadm/monitoring_chk_sh/LogFileZipEncrypt/logFileDecUnzip.sh "/GW_REPO/applog_bak/infinilink/rte_log/eo_cdrm1/eo_cdrm1_svr1" "all"
				// java -cp .:/home/infadm/monitoring_chk_sh/LogFileZipEncrypt:/home/infadm/monitoring_chk_sh/LogFileZipEncrypt/commons-compress-1.11.jar:/home/infadm/monitoring_chk_sh/LogFileZipEncrypt/logFileZipEnc.jar LogFileZipEncrypt "unzip" "$1" "$2"
				
				String filePath = args[1];
				String fileName = args[2];
//				String filePath = "F:\\test\\fileBackupTest\\trg\\20160508";
//				String fileName = "eo_cdrm1_svr1_rte_2016050715.log.zip_enc";
//				String fileName = "all";
				
				System.out.println("param[1]: " + filePath);
				System.out.println("param[2]: " + fileName);
					
				try
				{	
					//폴더 내 모든 파일 unzip, decryption
					if ("all".equals(fileName))
					{
						
						File srcFile = new File(filePath);
										
						if(srcFile.exists() && srcFile.isDirectory())
						{
							System.out.println("decryption, unzip directory: " + srcFile.getAbsolutePath());
							
							File[] files = srcFile.listFiles();
							
							for(File f : files)
							{
								String zipEncFile = f.getAbsolutePath();
													
								if( f.exists() && f.isFile() && ( zipEncFile.endsWith(".zip_enc")) )
								{
									
									System.out.println("*****");
									System.out.println("Start decryption, unzip file name: " + zipEncFile);
									
									String descriptionFile = zipEncFile.substring(0, zipEncFile.length() - 4);
									
									int descryptionResult = lfze.decryptFile(zipEncFile, descriptionFile);
									
									Thread.sleep(1000);
									
									if(descryptionResult == 0 )
									{
										File zippedFile = new File(descriptionFile);
										lfze.unzip(new FileInputStream(zippedFile), new File(filePath), Charset.defaultCharset().name());
										
										zippedFile.delete();
									}
									
									System.out.println("End decryption, unzip file name");
									System.out.println("*****");
									
								}
							}
						}								
						
					}
					// 지정 파일 unzip, decryption
					else
					{
						
						String sourceFile = filePath + File.separator + fileName;
						
						System.out.println("*****");
						System.out.println("Start decryption, unzip file name: " + sourceFile);
						
						String descriptionFile = sourceFile.substring(0, sourceFile.length() - 4);
						
						int descryptionResult = lfze.decryptFile(sourceFile, descriptionFile);
						
						Thread.sleep(1000);
						
						//원본 삭제
						//new File(filePath + File.separator + fileName).delete();
						
						if(descryptionResult == 0 )
						{
							File zippedFile = new File(descriptionFile);
							lfze.unzip(new FileInputStream(zippedFile), new File(filePath), Charset.defaultCharset().name());
							
							zippedFile.delete();
						}
						
						System.out.println("End decryption, unzip file name");
						System.out.println("*****");
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				
			}
			else
			{
				System.out.println("param[0] is not proper");
				System.exit(-1);
			}
		}
		else
		{
			System.out.println("param[0] is null");
			System.exit(-1);
		}
		
		
	}

	public boolean removeDir(File f) {
		
		boolean result = false;
		
		File[] listFiles = f.listFiles();
		
		try {
			
			if (listFiles.length > 0) {
				System.out.println("=====================");
				System.out.println("LIST");
				for(File i:listFiles) {
					System.out.println(i.getAbsolutePath());
				}
				result = false;
			} else {
				result = f.delete();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		
		return result;
		
	}
	
	
	public void unzip(InputStream is, File destDir, String charsetName) throws IOException
	{
		ZipArchiveInputStream zis;
		ZipArchiveEntry entry;
		String name;
		File target;
		int nWritten=0;
		BufferedOutputStream bos;
		byte[] buf = new byte[1024*8];
		zis = new ZipArchiveInputStream(is, charsetName, false);
		
		
		while (( entry = zis.getNextZipEntry()) !=null )
		{
			name = entry.getName();
			target = new File(destDir, name);
			
			System.out.println("Start 2)unzip file name: " + name + ".zip");
			
			if(entry.isDirectory())
			{
				target.mkdir();
			}
			else
			{
				target.createNewFile();
				bos = new BufferedOutputStream(new FileOutputStream(target));
				
				while(( nWritten = zis.read(buf)) >= 0)
				{
					bos.write(buf, 0, nWritten);
				}
				bos.close();
			}
		}
		zis.close();
	}
	
	public File zip(File src, File destDir, String charSetName, boolean includeSrc) throws IOException
	{
		String fileName = src.getName();
		File zippedFile = null;
		
		if(!src.isDirectory())
		{
			
			fileName = fileName + ".zip";
			
			zippedFile = new File(destDir, fileName);
			
			if(!zippedFile.exists())
			{
				zippedFile.createNewFile();
			}
			
			System.out.println("Start 1)zip file name: " + zippedFile.getAbsolutePath());
			
			zip(src, new FileOutputStream(zippedFile), charSetName, includeSrc);
		}
		
		return zippedFile;
	}
	
	public void zip(File src, OutputStream os, String charsetName, boolean includeSrc) throws IOException
	{
		ZipArchiveOutputStream zos = new ZipArchiveOutputStream(os);
		
		zos.setEncoding(charsetName);
		
		//use this if you are writing entries of unknown size(>= 4G)
		zos.setUseZip64(Zip64Mode.Always);
		
		FileInputStream fis;
		int length;
		ZipArchiveEntry ze;
		byte[] buf = new byte[8*1024];
		String name;
		Stack<File> stack = new Stack<File>();
		File root;
		
		if(src.isDirectory())
		{
			if(includeSrc)
			{
				stack.push(src);
				root = src.getParentFile();
			}
			else
			{
				File[] fs = src.listFiles();
				for(int i=0; i<fs.length; i++)
				{
					stack.push(fs[i]);
				}
				
				root = src;
			}
		}
		else
		{
			stack.push(src);
			root = src.getParentFile();
		}
		
		while(!stack.isEmpty())
		{
			File f = stack.pop();
			name = toPath(root, f);
			
			if(f.isDirectory())
			{
				File[] fs = f.listFiles();
				for(int i=0; i<fs.length; i++)
				{
					if(fs[i].isDirectory())
					{
						stack.push(fs[i]);
					}
					else
					{
						stack.add(0, fs[i]);
					}
				}
			}
			else
			{
				ze = new ZipArchiveEntry(name);
				zos.putArchiveEntry(ze);
				fis = new FileInputStream(f);
				
				while( (length=fis.read(buf, 0, buf.length))>=0)
				{
					zos.write(buf, 0, length);
				}
				
				fis.close();
				zos.closeArchiveEntry();
			}
		}
		zos.close();
	}
	
	private String toPath(File root, File dir)
	{
		String path = dir.getAbsolutePath();
		
		path = path.substring(root.getAbsolutePath().length()).replace(File.separatorChar, '/');
		if( path.startsWith("/"))
		{
			path = path.substring(1);
		}
		if( dir.isDirectory() && !path.endsWith("/"))
		{
			path = path + "/";
		}
		
		return path;
	}
	
	private String[] encryptFile(String srcFilePath) throws Exception
	{
		String encFilePath = srcFilePath+"_enc";
		
		File encFile = new File(encFilePath);
		if(encFile.exists()) encFile.delete();
		
		System.out.println("Start 2)encryption file name: " + srcFilePath);
		
		SLEncDecFile encryptor = new SLEncDecFile();
		encryptor.SLEncryptFile(srcFilePath, encFilePath);
		
		return new String[]{encFile.getParent(), encFile.getName()};
	}
	
	private int decryptFile(String srcPath, String destPath) throws Exception
	{
		File encFile = new File(destPath);
		if(encFile.exists()) encFile.delete();
		
		System.out.println("Start 1)decryption file name: " + srcPath);
		
		SLEncDecFile encryptor = new SLEncDecFile();
		int rtn = encryptor.SLDecryptFile(srcPath, destPath);
		  
		return rtn;
	}
}
