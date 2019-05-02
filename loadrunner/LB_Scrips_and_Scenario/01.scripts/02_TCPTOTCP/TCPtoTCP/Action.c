#include "lrs.h"


Action()
{

	int rc=-9;

	
	lr_start_transaction("02_TCPTOTCP");

	lrs_create_socket("socket1", "TCP", "LocalHost=0", "RemoteHost={IP}:9001",  LrsLastArg); //EGI AP 10.220.212.164~167   
	//lrs_create_socket("socket7", "TCP", "LocalHost=0", "RemoteHost=10.220.212.251:8001",  LrsLastArg);  // SIT L4 10.217.213.98:9116


	lrs_send("socket1", "buf1", LrsLastArg);

//	lrs_receive("socket7", "buf2", LrsLastArg);



	rc = lrs_receive_ex("socket1", "buf2", "NumberOfBytesToRecv=48", LrsLastArg); //buf2 에서 300 bite 받아서

if (rc==0)
	{

     //lr_output_message ("@@lrs_receive_ex error code: %d", rc);

     lrs_save_searched_string("socket1", NULL, "success_flag", NULL, NULL, -1,0,48); //118번째에서 5 length를 success_flag 에 저장
	  lrs_save_searched_string("socket1", NULL, "Rec_Data", NULL, NULL, -1,0,48); //73byte
		//lr_output_message ("success_flag: %s", lr_eval_string("{success_flag}"));

           if (strcmp(lr_eval_string("{success_flag}"), "012345678901234567890123456789012345678901234567") ==0){

	    	  lrs_close_socket("socket1");
		      lr_end_transaction("02_TCPTOTCP", LR_PASS);
	        }
           else 
	       {
		       lrs_close_socket("socket1");
		       lr_end_transaction("02_TCPTOTCP", LR_FAIL);
	           lr_error_message ("success_flag: %s", lr_eval_string("{success_flag}"));
			   lr_error_message ("Fail Receive All Data:: %s",  lr_eval_string("{Rec_Data}"));
	       }
      }
else  {
	   lrs_close_socket("socket1");
	   lr_end_transaction("02_TCPTOTCP", LR_FAIL);
       lr_error_message ("rc Fail");
	   lr_error_message ("Fail Receive All Data:: %s",  lr_eval_string("{Rec_Data}"));
      }


    return 0;
}

