

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;

public class SLEncDecFile
{
	static final int DBG_FILE_MODE	= 0; 
	static final int WINDOWS		= 1; 
	static final int BYPASS_MODE	= 0; 

	SLBsUtil slUtil				= new SLBsUtil();
	AriaImpl aria				= new AriaImpl();
	SLReturnValue retValueClass	= new SLReturnValue();


	public int SLIsEncFile( String srcPath ) 
	{
		try{
			File attSrcFile	= new File(srcPath);	
						
			if(!attSrcFile.exists()){
				return retValueClass.ERROR_FILE_EXIST;
			}
		}catch(Exception eCheck){
			return retValueClass.ERROR_EXCEPTION;
		}

		return slUtil.isEncryptFile(srcPath);
	}


	public int SLEncryptFile(String srcPath, String destPath) 
	{	

		try{					
			File attSrcFile	= new File(srcPath);	
						
			if(!attSrcFile.exists()){
				return retValueClass.ERROR_FILE_EXIST;
			}
	
			if(attSrcFile.length() == 0){
				slUtil.CopyFile(srcPath, destPath);
				return retValueClass.ERROR_FILESIZE_ZERO;
			}
		}catch(Exception eCheck){
			return retValueClass.ERROR_EXCEPTION;
		}

		try{
			File srcFile = new File( srcPath );
			RandomAccessFile srcRandomStream = new RandomAccessFile( srcPath, "r" );

			File destFile = new File( destPath );
			FileOutputStream outDestStream = new FileOutputStream( destFile );

			byte[] head 		= new byte[10];			
			
			byte[] in 			= new byte[1024];		
			byte[] arEncIn 		= new byte[1024];			
			int[] arIntIn 		= new int[1024];
			int[] arEncIntIn 	= new int[1024];

			byte[] arByteHeaderData		= new byte[32];			
			int[] arIntHeaderData 		= new int[32];			
			int[] arKeyMakeData 		= new int[32];			

			int[] arDocuKey = new int[32];		
			
			int nRead=0;
					
			if(srcFile.length() == 0){
				srcRandomStream.close();
				outDestStream.close();
				return retValueClass.ERROR_FILESIZE_ZERO;
			}

			srcRandomStream.seek((long)0);			
			
			slUtil.generateKey( arDocuKey );
	
			keyMake( arKeyMakeData );
			aria.blockEncrypt( arDocuKey, 32, arIntHeaderData, arKeyMakeData );		
			arByteHeaderData = SLBsUtil.intsToBytes2(arIntHeaderData);

			String strHead = new String("FIRST002\0\0");
			head = strHead.getBytes();						
			outDestStream.write(head, 0, 10);
			
			outDestStream.write(arByteHeaderData, 0, 32);

			while((nRead = srcRandomStream.read(in, 0, 1024)) == 1024){
				arIntIn = slUtil.GetIntArrayToByteArray2( in, 0, 1024 );	
				aria.blockEncrypt( arIntIn, 1024, arEncIntIn, arDocuKey );
				
				arEncIn = SLBsUtil.intsToBytes2( arEncIntIn );	
				outDestStream.write( arEncIn, 0, 1024 );	
				SLBsUtil.memSet( in, 0x00, 1024 );
				SLBsUtil.memSet( arEncIn, 0x00, 1024 );	
			}
			
			if(nRead > 0){
				arIntIn = slUtil.GetIntArrayToByteArray2( in, 0, nRead );
				int nSizePad = aria.padBlockEncrypt( arIntIn, nRead, arEncIntIn, arDocuKey );
				arEncIn = SLBsUtil.intsToBytes2( arEncIntIn );	
				outDestStream.write( arEncIn, 0, nSizePad );
			}
		
			srcRandomStream.close();
			outDestStream.close();

		}catch(Exception e){
			e.printStackTrace();
			return retValueClass.ERROR_EXCEPTION;
		}

		return retValueClass.RETVAL_SUCCESS;
	}

	public int SLDecryptFile(String srcPath, String destPath)
	{
		
		try{
			File attSrcFile		= new File(srcPath);	
			if(!attSrcFile.exists()){
				return retValueClass.ERROR_FILE_EXIST;
			}
	
			if(attSrcFile.length() == 0){
				slUtil.CopyFile(srcPath, destPath);
				return retValueClass.ERROR_FILESIZE_ZERO;
			}
		}catch(Exception eCheck){
			return retValueClass.ERROR_EXCEPTION;
		}

		try{			
			File srcFile					= new File(srcPath); //���� ���� üũ�� ���� ����.
			FileInputStream srcFileStream	= new FileInputStream(srcFile);
			DataInputStream	srcDataStream	= new DataInputStream(srcFileStream);

			byte[] arReadHeader		= new byte[32];
			int[] arIntReadHeader	= new int[32];

			int[] arIntDecHeaderData	= new int[32];			

			byte[] szHead		= new byte[10];
			int[] arKeyMakeData	= new int[32];			
			int nDocuStartPoint	= 0;
			int	nResult			= 0;

			if(!srcFile.exists()){

				srcFileStream.close();
				srcDataStream.close();			

				return retValueClass.ERROR_FILE_EXIST;
			}
				
			srcDataStream.read( szHead, 0, 10 );
			
			String compareHead = new String(szHead, 0, 10);
			if(compareHead.compareTo( "FIRST002\0\0" ) != 0)
			{

				slUtil.CopyFile( srcPath, destPath );
				srcFileStream.close();
				srcDataStream.close();				
				return retValueClass.ERROR_FILE_NOTENCRYPTED;
			}
			
			nDocuStartPoint += 10;


			srcDataStream.read( arReadHeader, 0, 32 );			
			arIntReadHeader = slUtil.GetIntArrayToByteArray2( arReadHeader, 0, 32 ); 


			keyMake( arKeyMakeData );

			aria.blockDecrypt( arIntReadHeader, 32, arIntDecHeaderData, arKeyMakeData );		

					
			nDocuStartPoint += 32;

			if ( ( nResult = DecryptFile( srcPath, destPath, nDocuStartPoint, arIntDecHeaderData ) ) != retValueClass.RETVAL_SUCCESS )
			{

				srcFileStream.close();
				srcDataStream.close();			

				return nResult;		
			}


			srcFileStream.close();
			srcDataStream.close();			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return retValueClass.ERROR_EXCEPTION;
		}


		return retValueClass.RETVAL_SUCCESS;
	}

	private int DecryptFile(String srcPath,
							String destPath,
							int nDocuStartPoint, 
							int[] pEncKey )
	{

		try
		{
			
			RandomAccessFile srcRandomStream		= new RandomAccessFile(srcPath, "r");

			File				destFile			= new File(destPath);
			FileOutputStream	outDestStream		= new FileOutputStream(destFile);

			byte[]				arIn				= new byte[1024]; 
			int[]				arIntIn				= new int[1024]; 
			int[]				arDecIntIn			= new int[1024];  
			byte[]				arDecByteIn			= new byte[1024]; 

			int					nRead = 0;
			int					nPadSize = 0;
						
			srcRandomStream.seek( nDocuStartPoint );

			while( ( nRead = srcRandomStream.read( arIn, 0, 1024)) == 1024 )
			{				
				arIntIn = slUtil.GetIntArrayToByteArray2( arIn, 0, 1024 ); 			
				aria.blockDecrypt( arIntIn , 1024, arDecIntIn, pEncKey );

				arDecByteIn = SLBsUtil.intsToBytes2( arDecIntIn );				
				outDestStream.write( arDecByteIn, 0, 1024 );

				SLBsUtil.memSet( arIn, 0x00, 1024 );
				SLBsUtil.memSet( arDecByteIn, 0x00, 1024 );
			}

			if( nRead > 0 )
			{				
				arIntIn = slUtil.GetIntArrayToByteArray2( arIn, 0, nRead ); 				
				nPadSize = aria.padBlockDecrypt( arIntIn , nRead, arDecIntIn, pEncKey );
				
				arDecByteIn = SLBsUtil.intsToBytes2( arDecIntIn ); 
				outDestStream.write( arDecByteIn, 0, nPadSize );
			}

			srcRandomStream.close();
			outDestStream.close();	
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return retValueClass.ERROR_EXCEPTION;
		}

		return	retValueClass.RETVAL_SUCCESS;							
	}

	private static void keyMake(int[] eHeadKey)
	{
		eHeadKey[15] = 120; eHeadKey[0] = 11; eHeadKey[1] = 2;	eHeadKey[2] = 55; 
		eHeadKey[14] = 43;	eHeadKey[3] = 22; eHeadKey[5] = 99; eHeadKey[4] = 104;
		eHeadKey[13] = 114; eHeadKey[12] = 87; eHeadKey[6] = 27; eHeadKey[7] = 77;
		eHeadKey[9] = 119; eHeadKey[8] = 80; eHeadKey[11] = 9; eHeadKey[10] = 111;		
		eHeadKey[15+16] = 120; eHeadKey[0+16] = 11; eHeadKey[1+16] = 2;	eHeadKey[2+16] = 55; 
		eHeadKey[14+16] = 43;	eHeadKey[3+16] = 22; eHeadKey[5+16] = 99; eHeadKey[4+16] = 104;
		eHeadKey[13+16] = 114; eHeadKey[12+16] = 87; eHeadKey[6+16] = 27; eHeadKey[7+16] = 77;
		eHeadKey[9+16] = 119; eHeadKey[8+16] = 80; eHeadKey[11+16] = 9; eHeadKey[10+16] = 111;

	}
}



