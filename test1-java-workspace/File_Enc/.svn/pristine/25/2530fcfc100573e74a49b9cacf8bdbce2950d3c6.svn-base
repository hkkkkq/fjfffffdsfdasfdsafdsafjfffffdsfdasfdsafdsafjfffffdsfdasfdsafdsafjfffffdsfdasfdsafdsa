

import java.io.*;
import java.util.*; 
import java.text.*;
import java.io.RandomAccessFile;
import java.io.FileInputStream;

public class SLBsUtil
{

	public int[] GetIntArrayToByteArray2( byte[] byteArray, int nStart, int nArraySize )
	{
		int[]	nArraySum = new int[nArraySize];

		for( int i = 0; i < nArraySize ; i++ )
		{			
			nArraySum[i] = GetInttoBytes2( byteArray, i);
		}
		return nArraySum;
	}

	public int GetInttoBytes2(byte[] byteArray, int nStart)
	{
		int	 n1;

		n1 = i2b(byteArray[nStart]);
		return n1;		
	}

	public int[] GetIntArrayToByteArray( byte[] byteArray, int nStart, int nArraySize )
	{
		int[]	nArraySum = new int[nArraySize / 4];

		for( int i = 0; i < ( nArraySize / 4 ); i++ )
		{			
			nArraySum[i] = GetInttoBytes( byteArray, i * 4 );
		}
		return nArraySum;
	}

	public int GetInttoBytes(byte[] byteArray, int nStart)
	{
		int	 nSum, n1, n2, n3, n4;

		n1 = i2b(byteArray[nStart]);
		n2 = (i2b(byteArray[nStart+1]))*256;
		n3 = (i2b(byteArray[nStart+2]))*256*256;
		n4 = (i2b(byteArray[nStart+3]))*256*256*256;

		nSum = n1+n2+n3+n4;
		return nSum;
	}


	public void GetDatatoBytes(byte[] byteArray, byte[] b, int nStart, int nSize)
	{
		for(int i=0; i<nSize; i++)
		{
			b[i] = byteArray[nStart+i];
		}		
	}


	public int GetIdtoBytes(byte[] byteArray, byte[] b, int nStart, int nSize)
	{
		for(int i=0; i<nSize; i++)
		{
			if(byteArray[nStart+i] == '\0')
				return i;
			else
				b[i] = byteArray[nStart+i];
		}	
		
		return nSize;
	}


	public int i2b(byte b)
	{
		int n = (int) b;
		if(n<0)
		{
			b &= 0x7f;
			n = (int)b;
			n += 128;
		}

		return n;
	}


	public byte[] intsToBytes(int nData)
    {
		int ai[] = new int[1];
		ai[0] = nData;
		
        byte abyte0[] = new byte[4 * ai.length];
        for(int i = 0; i < abyte0.length; i++)
            abyte0[i] = (byte)(ai[i / 4] >>> 8 * (i % 4) & 0xff);

        return abyte0;
    }


	public int  EndianChange4(int nval)
	{
		int nRet = 0;
	    nRet = ( (((nval) & 0xff000000) >> 24) | (((nval) & 0x00ff0000) >> 8) |  (((nval) & 0x0000ff00) << 8) | (((nval) & 0x000000ff) << 24) );
		return nRet;
	}


	public int GetStringLength(byte[] byteArray, int nSize)
	{
		for(int i=0; i<nSize; i++)
		{
			if(byteArray[i] == '\0')
			{
				return i;
			}
		}			

		return nSize;
	}


	public void memCopy(byte[] bSrcBuffer, int nSrcStart, int nSize, byte[] bDestBuffer, int nStart)
    {
		int nBound = nSrcStart+nSize;

		for(int i = nSrcStart; i<nBound; i++)	
		{
			bDestBuffer[nStart] = bSrcBuffer[i];
			nStart++;
		}
    }

	public void memCopy(int[] bSrcBuffer, int nSrcStart, int nSize, int[] bDestBuffer, int nStart)
    {
		int nBound = nSrcStart+nSize;

		for(int i = nSrcStart; i<nBound; i++)	
		{
			bDestBuffer[nStart] = bSrcBuffer[i];
			nStart++;
		}
    }



	public void memCopy(byte[] bSrcBuffer, int nSize, byte[] bDestBuffer, int nStart)
    {
		for(int i=0; i<nSize; i++)	
		{
			bDestBuffer[nStart] = bSrcBuffer[i];
			nStart++;
		}
    }
	
	public void memCopy(byte bSrcBuffer, int nSize, byte[] bDestBuffer, int nStart)
	{
		for(int i=0; i<nSize; i++)	
		{
			bDestBuffer[nStart] = bSrcBuffer;
			nStart++;
		}
	}


	public void memSet(byte[] bBuffer, int nSize)
    {
		for(int i=0; i<nSize; i++)	bBuffer[i] = 0x00;
    }

	
	public final void memCpy(byte[] bDestBuffer, byte[] bSrcBuffer, int nSize)
    {
		for(int i=0; i<nSize; i++)
		{
			bDestBuffer[i] = bSrcBuffer[i];
		}
    }

	public final void memCpy(int[] destBuffer, int[] srcBuffer, int nSize)
    {
		for(int i=0; i<nSize; i++)
		{
			destBuffer[i] = srcBuffer[i];
		}
    }

	public static final void memSet(byte[] bBuffer, int n, int nSize)
	{
		for(int i=0; i<nSize; i++)	
			bBuffer[i] = (byte)n;
	}


	public void generateKey(byte[] docuKey)
	{
		double	ranDouble;
		int		ranInt;
		
		for(int i=0; i<16; i++)
		{
			ranDouble = java.lang.Math.random();
			ranInt = (int)(ranDouble*100)%100;
			docuKey[i] = (byte)ranInt;
		}
	}

	public void generateKey(int[] docuKey)
	{
		double	ranDouble;
		int		ranInt;
		
		for(int i=0; i<32; i++)
		{
			ranDouble = java.lang.Math.random();
			ranInt = (int)(ranDouble*100)%100;
			docuKey[i] = ranInt;
		}
//		for(int i=0; i<32; i++)
//		{
//			docuKey[i] = i;
//		}
	}

	public void timeOfServer(byte[] svrTime)
	{
		byte[] arrTemp = new byte[16];

		try
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			Date currentTime_1 = new Date(); 
			String dateString = formatter.format(currentTime_1);
			arrTemp = dateString.getBytes();			
			memCpy(svrTime, arrTemp, 16);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static final byte[] intsToBytes(int ai[])
    {
        byte abyte0[] = new byte[4 * ai.length];
        for(int i = 0; i < abyte0.length; i++)
            abyte0[i] = (byte)(ai[i / 4] >>> 8 * (i % 4) & 0xff);

        return abyte0;
    }

	public static final byte[] intsToBytes2(int ai[])
    {
		byte abyte0[] = new byte[ai.length];
		for(int i = 0; i < abyte0.length; i++)
			abyte0[i] = (byte)(ai[i] & 0xff) ;

        return abyte0;
    }

	public String getDateAfterNum(int nNum)
	{
			String strDate = new String();
			Calendar calendar = Calendar.getInstance();

			calendar.add(Calendar.DATE, nNum); 
			String year = String.valueOf(calendar.get(Calendar.YEAR)); 
		    String month = String.valueOf(calendar.get(Calendar.MONTH)+1); 
			String day = String.valueOf(calendar.get(Calendar.DATE)); 
			
			if(month.compareTo("1")==0 | month.compareTo("2")==0 | month.compareTo("3")==0 |
				month.compareTo("4")==0 | month.compareTo("5")==0 | month.compareTo("6")==0 |
				month.compareTo("7")==0 |month.compareTo("8")==0 | month.compareTo("9")== 0)
			{
				month = "0"+month;
			}

			if(day.compareTo("1")==0 | day.compareTo("2")==0 | day.compareTo("3")==0 |
				day.compareTo("4")==0 | day.compareTo("5")==0 | day.compareTo("6")==0 |
				day.compareTo("7")==0 | day.compareTo("8")==0 | day.compareTo("9")== 0)
			{
				day = "0" + day;
			}
			
			strDate = year+month+day;
			return strDate;
	}

    public  short swap(short x) {
		return (short)((x << 8) |
					   ((x >> 8) & 0xff));
    }

    public  char swap(char x) {
		return (char)((x << 8) |
					  ((x >> 8) & 0xff));
    }

    public  int swap(int x) {
		return (int)((swap((short)x) << 16) |
					 (swap((short)(x >> 16)) & 0xffff));
	}

   public long swap(long x) {
		return (long)(((long)swap((int)(x)) << 32) |
					  ((long)swap((int)(x >> 32)) & 0xffffffffL));
    }

   public float swap(float x) {
		return Float.intBitsToFloat(swap(Float.floatToRawIntBits(x)));
    }

    public double swap(double x) {
		return Double.longBitsToDouble(swap(Double.doubleToRawLongBits(x)));
    }
	
	public int CopyFile(String srcPath, String destPath)
	{
		try
		{
			File srcFile = new File(srcPath); 
			RandomAccessFile srcRandomStream = new RandomAccessFile(srcPath, "rw");

			File destFile = new File(destPath);
			FileOutputStream outDestStream = new FileOutputStream(destFile);
			
			int nFileSize=0;
			nFileSize = (int)srcFile.length();
			byte[] arBuf = new byte[nFileSize+1];
			
			srcRandomStream.read(arBuf, 0, nFileSize);
			outDestStream.write(arBuf, 0, nFileSize);

			srcRandomStream.close();
			outDestStream.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return -1;
		}

		return 1;
	}

	public String currentTime()
	{
		String strTime = new String();

		SimpleDateFormat formatter = new SimpleDateFormat("a h:mm:ss");
		Date currentTime_1 = new Date();
		strTime = formatter.format(currentTime_1);
		
		return strTime;		
	}

	public int isEncryptFile(String strFile)
	{
		byte[] inData = new byte[128];
		try
		{			
			int nSize=0;
			File inFile = new File(strFile);
			FileInputStream inStream = new FileInputStream(inFile);
			nSize = inStream.read(inData, 0, 10);
			if(nSize == 10)
			{
				String CheckBuf = new String(inData, 0, 10);
				if (CheckBuf.compareTo("FIRST002\0\0") == 0)
				{
					inStream.close();
					return 1;
				}
				else
				{
					inStream.close();
					return 0;
				}
			}
			else
			{
				inStream.close();
				return -1;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return -1;
		}
	}




}
