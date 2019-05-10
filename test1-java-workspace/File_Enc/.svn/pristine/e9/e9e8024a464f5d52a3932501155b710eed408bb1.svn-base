import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Stack;

import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

/**
 * 
 * @author 82023121
 * @Description LogFileZipEncrypt의 SUB 프로그램으로 모든 파일 형태의 특정 폴더 및 파일을 압축/파일암호화하고 이후 파일복호화/압축해제 할때 사용하는 프로그램
 *
 */
public class SimpleLogFileZipEncrypt
{
	public static void main(String[] args)
	{
		SimpleLogFileZipEncrypt lfze = new SimpleLogFileZipEncrypt();

		String command = args[0];
//		String command = "unzip";
		
		System.out.println("param[0]: " + command);
		
		if(command!=null)
		{
			// ZIP and FileEncryption
			if("zip".equals(command))
			{
				
				// sh /home/infadm/monitoring_chk_sh/LogFileZipEncrypt/SimpleLogFileZipEncrypt.sh "/GW_REPO/applog_bak/infinilink/rte_log/eo_cdrm1/eo_cdrm1_svr1" "all"
				// java -cp .:/home/infadm/monitoring_chk_sh/LogFileZipEncrypt:/home/infadm/monitoring_chk_sh/LogFileZipEncrypt/commons-compress-1.11.jar:/home/infadm/monitoring_chk_sh/LogFileZipEncrypt/logFileZipEnc.jar SimpleLogFileZipEncrypt "unzip" "$1" "$2"
				
				String filePath = args[1];
				String fileName = args[2];
//				String filePath = "E:\\test\\fileBackupTest\\src";
//				String fileName = "eo_cdrm1_svr1_rte_2016051815.log";
//				String fileName = "all";
				
				System.out.println("param[1]: " + filePath);
				System.out.println("param[2]: " + fileName);
				
				try
				{	
					//폴더 내 모든 파일 ZIP and FileEncryption
					if ("all".equals(fileName))
					{
						
						File srcFile = new File(filePath);
										
						if(srcFile.exists() && srcFile.isDirectory())
						{
							System.out.println("ZIP and FileEncryption directory: " + srcFile.getAbsolutePath());
							
							File[] files = srcFile.listFiles();
							
							for(File f : files)
							{
								String zipEncFile = f.getAbsolutePath();
													
								if( f.exists() && f.isFile() )
								{
									System.out.println("*****");
									System.out.println("Start ZIP and FileEncryption file name: " + zipEncFile);
									
									//zip 압축하기
									File zippedFile = lfze.zip(f, srcFile, Charset.defaultCharset().name(), true);
									
									Thread.sleep(1000);

									//파일 암호화하기
									String[] encFilePathName = lfze.encryptFile(zippedFile.getAbsolutePath());
									//String[] encFilePathName = lfze.encryptFile(logFile.getAbsolutePath());
									
									Thread.sleep(1000);
									
									//원본 파일 삭제
									f.delete();
									
									//zip 파일 삭제
									zippedFile.delete();
																											
									System.out.println("End ZIP and FileEncryption file name: " + encFilePathName[1]);
									System.out.println("*****");
								}
							}
						} else {
							System.out.println("filePath does not exist or is not a directory");
						}
						
					}
					// 지정 파일 ZIP and FileEncryption
					else
					{
						File trgDir = new File(filePath);
						File srcFile = new File(filePath,fileName);
						
						if(srcFile.exists() && srcFile.isFile()) {
							
							System.out.println("*****");
							System.out.println("Start ZIP and FileEncryption file name: " + srcFile.getAbsolutePath());
							
							//zip 압축하기
							File zippedFile = lfze.zip(srcFile, trgDir, Charset.defaultCharset().name(), true);
							
							Thread.sleep(1000);

							//파일 암호화하기
							String[] encFilePathName = lfze.encryptFile(zippedFile.getAbsolutePath());
							//String[] encFilePathName = lfze.encryptFile(logFile.getAbsolutePath());
							
							Thread.sleep(1000);
							
							//원본 파일 삭제
							srcFile.delete();
							
							//zip 파일 삭제
							zippedFile.delete();
																									
							System.out.println("End ZIP and FileEncryption file name: " + encFilePathName[1]);
							System.out.println("*****");

						} else {
							System.out.println("file does not exist or is not a file");
						}
						
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				
			} 
			//decryption and unzip
			else if("unzip".equals(command))
			{
				// sh /home/infadm/monitoring_chk_sh/LogFileZipEncrypt/logFileDecUnzip.sh "/GW_REPO/applog_bak/infinilink/rte_log/eo_cdrm1/eo_cdrm1_svr1" "all"
				// java -cp .:/home/infadm/monitoring_chk_sh/LogFileZipEncrypt:/home/infadm/monitoring_chk_sh/LogFileZipEncrypt/commons-compress-1.11.jar:/home/infadm/monitoring_chk_sh/LogFileZipEncrypt/logFileZipEnc.jar LogFileZipEncrypt "unzip" "$1" "$2"
				
				String filePath = args[1];
				String fileName = args[2];
//				String filePath = "E:\\test\\fileBackupTest\\src";
//				String fileName = "eo_cdrm1_svr1_rte_2016051815.log.zip_enc";
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
			else {
				
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
