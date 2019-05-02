# 1 "d:\\05.checksum\\01.script\\checksum_esb_loopback\\\\combined_CheckSum_ESB_loopback.c"
# 1 "globals.h" 1
 
 
# 1 "C:\\Program Files\\HP\\LoadRunner\\include/lrun.h" 1
 
 












 











# 103 "C:\\Program Files\\HP\\LoadRunner\\include/lrun.h"








































































	

 


















 
 
 
 
 


 
 
 
 
 
 














int     lr_start_transaction   (char * transaction_name);
int lr_start_sub_transaction          (char * transaction_name, char * trans_parent);
long lr_start_transaction_instance    (char * transaction_name, long parent_handle);



int     lr_end_transaction     (char * transaction_name, int status);
int lr_end_sub_transaction            (char * transaction_name, int status);
int lr_end_transaction_instance       (long transaction, int status);


 
typedef char* lr_uuid_t;
 



lr_uuid_t lr_generate_uuid();

 


int lr_generate_uuid_free(lr_uuid_t uuid);

 



int lr_generate_uuid_on_buf(lr_uuid_t buf);

   
# 263 "C:\\Program Files\\HP\\LoadRunner\\include/lrun.h"
int lr_start_distributed_transaction  (char * transaction_name, lr_uuid_t correlator, long timeout  );

   







int lr_end_distributed_transaction  (lr_uuid_t correlator, int status);


double lr_stop_transaction            (char * transaction_name);
double lr_stop_transaction_instance   (long parent_handle);


void lr_resume_transaction           (char * trans_name);
void lr_resume_transaction_instance  (long trans_handle);


int lr_update_transaction            (const char *trans_name);


 
void lr_wasted_time(long time);


 
int lr_set_transaction(const char *name, double duration, int status);
 
long lr_set_transaction_instance(const char *name, double duration, int status, long parent_handle);


int   lr_user_data_point                      (char *, double);
long lr_user_data_point_instance                   (char *, double, long);
 



int lr_user_data_point_ex(const char *dp_name, double value, int log_flag);
long lr_user_data_point_instance_ex(const char *dp_name, double value, long parent_handle, int log_flag);


int lr_transaction_add_info      (const char *trans_name, char *info);
int lr_transaction_instance_add_info   (long trans_handle, char *info);
int lr_dpoint_add_info           (const char *dpoint_name, char *info);
int lr_dpoint_instance_add_info        (long dpoint_handle, char *info);


double lr_get_transaction_duration       (char * trans_name);
double lr_get_trans_instance_duration    (long trans_handle);
double lr_get_transaction_think_time     (char * trans_name);
double lr_get_trans_instance_think_time  (long trans_handle);
double lr_get_transaction_wasted_time    (char * trans_name);
double lr_get_trans_instance_wasted_time (long trans_handle);
int    lr_get_transaction_status		 (char * trans_name);
int	   lr_get_trans_instance_status		 (long trans_handle);

 



int lr_set_transaction_status(int status);

 



int lr_set_transaction_status_by_name(int status, const char *trans_name);
int lr_set_transaction_instance_status(int status, long trans_handle);


typedef void* merc_timer_handle_t;
 

merc_timer_handle_t lr_start_timer();
double lr_end_timer(merc_timer_handle_t timer_handle);


 
 
 
 
 
 











 



int   lr_rendezvous  (char * rendezvous_name);
 




int   lr_rendezvous_ex (char * rendezvous_name);



 
 
 
 
 
char *lr_get_vuser_ip (void);
void   lr_whoami (int *vuser_id, char ** sgroup, int *scid);
char *	  lr_get_host_name (void);
char *	  lr_get_master_host_name (void);

 
long     lr_get_attrib_long	(char * attr_name);
char *   lr_get_attrib_string	(char * attr_name);
double   lr_get_attrib_double      (char * attr_name);

char * lr_paramarr_idx(const char * paramArrayName, unsigned int index);
char * lr_paramarr_random(const char * paramArrayName);
int    lr_paramarr_len(const char * paramArrayName);

int	lr_param_unique(const char * paramName);
int lr_param_sprintf(const char * paramName, const char * format, ...);


 
 
static void *ci_this_context = 0;






 








void lr_continue_on_error (int lr_continue);
char *   lr_decrypt (const char *EncodedString);


 
 
 
 
 
 



 







 















void   lr_abort (void);
void lr_exit(int exit_option, int exit_status);
void lr_abort_ex (unsigned long flags);

void   lr_peek_events (void);


 
 
 
 
 


void   lr_think_time (double secs);

 


void lr_force_think_time (double secs);


 
 
 
 
 



















int   lr_msg (char * fmt, ...);
int   lr_debug_message (unsigned int msg_class,
									    char * format,
										...);
# 502 "C:\\Program Files\\HP\\LoadRunner\\include/lrun.h"
void   lr_new_prefix (int type,
                                 char * filename,
                                 int line);
# 505 "C:\\Program Files\\HP\\LoadRunner\\include/lrun.h"
int   lr_log_message (char * fmt, ...);
int   lr_message (char * fmt, ...);
int   lr_error_message (char * fmt, ...);
int   lr_output_message (char * fmt, ...);
int   lr_vuser_status_message (char * fmt, ...);
int   lr_error_message_without_fileline (char * fmt, ...);
int   lr_fail_trans_with_error (char * fmt, ...);

 
 
 
 
 
# 528 "C:\\Program Files\\HP\\LoadRunner\\include/lrun.h"

 
 
 
 
 





int   lr_next_row ( char * table);
int lr_advance_param ( char * param);



														  
														  

														  
														  

													      
 


char *   lr_eval_string (char * str);
int   lr_eval_string_ext (const char *in_str,
                                     unsigned long const in_len,
                                     char ** const out_str,
                                     unsigned long * const out_len,
                                     unsigned long const options,
                                     const char *file,
								     long const line);
# 562 "C:\\Program Files\\HP\\LoadRunner\\include/lrun.h"
void   lr_eval_string_ext_free (char * * pstr);

 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
int lr_param_increment (char * dst_name,
                              char * src_name);
# 585 "C:\\Program Files\\HP\\LoadRunner\\include/lrun.h"













											  
											  

											  
											  
											  

int	  lr_save_var (char *              param_val,
							  unsigned long const param_val_len,
							  unsigned long const options,
							  char *			  param_name);
# 609 "C:\\Program Files\\HP\\LoadRunner\\include/lrun.h"
int   lr_save_string (const char * param_val, const char * param_name);
int   lr_free_parameter (const char * param_name);
int   lr_save_int (const int param_val, const char * param_name);


 
 
 
 
 
 
# 676 "C:\\Program Files\\HP\\LoadRunner\\include/lrun.h"
void   lr_save_datetime (const char *format, int offset, const char *name);









 











 
 
 
 
 






 



char * lr_error_context_get_entry (char * key);

 



long   lr_error_context_get_error_id (void);


 
 
 

int lr_table_get_rows_num (char * param_name);

int lr_table_get_cols_num (char * param_name);

char * lr_table_get_cell_by_col_index (char * param_name, int row, int col);

char * lr_table_get_cell_by_col_name (char * param_name, int row, const char* col_name);

int lr_table_get_column_name_by_index (char * param_name, int col, 
											char * * const col_name,
											int * col_name_len);
# 737 "C:\\Program Files\\HP\\LoadRunner\\include/lrun.h"

int lr_table_get_column_name_by_index_free (char * col_name);


 
 
 
 
 
 
 
 

 
 
 
 
 
 
int   lr_param_substit (char * file,
                                   int const line,
                                   char * in_str,
                                   int const in_len,
                                   char * * const out_str,
                                   int * const out_len);
# 762 "C:\\Program Files\\HP\\LoadRunner\\include/lrun.h"
void   lr_param_substit_free (char * * pstr);


 
# 774 "C:\\Program Files\\HP\\LoadRunner\\include/lrun.h"





char *   lrfnc_eval_string (char * str,
                                      char * file_name,
                                      long const line_num);
# 782 "C:\\Program Files\\HP\\LoadRunner\\include/lrun.h"


int   lrfnc_save_string ( const char * param_val,
                                     const char * param_name,
                                     const char * file_name,
                                     long const line_num);
# 788 "C:\\Program Files\\HP\\LoadRunner\\include/lrun.h"

int   lrfnc_free_parameter (const char * param_name );

int lr_save_searched_string(char *buffer, long buf_size, unsigned int occurrence,
			    char *search_string, int offset, unsigned int param_val_len, 
			    char *param_name);

 
char *   lr_string (char * str);

 
# 859 "C:\\Program Files\\HP\\LoadRunner\\include/lrun.h"

int   lr_save_value (char * param_val,
                                unsigned long const param_val_len,
                                unsigned long const options,
                                char * param_name,
                                char * file_name,
                                long const line_num);
# 866 "C:\\Program Files\\HP\\LoadRunner\\include/lrun.h"


 
 
 
 
 











int   lr_printf (char * fmt, ...);
 
int   lr_set_debug_message (unsigned int msg_class,
                                       unsigned int swtch);
# 888 "C:\\Program Files\\HP\\LoadRunner\\include/lrun.h"
unsigned int   lr_get_debug_message (void);


 
 
 
 
 

void   lr_double_think_time ( double secs);
void   lr_usleep (long);


 
 
 
 
 
 




int *   lr_localtime (long offset);


int   lr_send_port (long port);


# 964 "C:\\Program Files\\HP\\LoadRunner\\include/lrun.h"



struct _lr_declare_identifier{
	char signature[24];
	char value[128];
};

int   lr_pt_abort (void);

void vuser_declaration (void);






# 993 "C:\\Program Files\\HP\\LoadRunner\\include/lrun.h"


# 1005 "C:\\Program Files\\HP\\LoadRunner\\include/lrun.h"
















 
 
 
 
 







int    _lr_declare_transaction   (char * transaction_name);


 
 
 
 
 







int   _lr_declare_rendezvous  (char * rendezvous_name);

 
 
 
 
 

 
int lr_enable_ip_spoofing();
int lr_disable_ip_spoofing();


 




int lr_convert_string_encoding(char *sourceString, char *fromEncoding, char *toEncoding, char *paramName);





 
 

















# 3 "globals.h" 2

# 1 "C:\\Program Files\\HP\\LoadRunner\\include/web_api.h" 1
 







# 1 "C:\\Program Files\\HP\\LoadRunner\\include/as_web.h" 1
 






















































 




 








 
 
 

  int
	web_add_filter(
		const char *		mpszArg,
		...
	);									 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 

  int
	web_add_auto_filter(
		const char *		mpszArg,
		...
	);									 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
	
  int
	web_add_auto_header(
		const char *		mpszHeader,
		const char *		mpszValue);

  int
	web_add_header(
		const char *		mpszHeader,
		const char *		mpszValue);
  int
	web_add_cookie(
		const char *		mpszCookie);
  int
	web_cleanup_auto_headers(void);
  int
	web_cleanup_cookies(void);
  int
	web_concurrent_end(
		const char * const	mpszReserved,
										 
		...								 
	);
  int
	web_concurrent_start(
		const char * const	mpszConcurrentGroupName,
										 
										 
		...								 
										 
	);
  int
	web_create_html_param(
		const char *		mpszParamName,
		const char *		mpszLeftDelim,
		const char *		mpszRightDelim);
  int
	web_create_html_param_ex(
		const char *		mpszParamName,
		const char *		mpszLeftDelim,
		const char *		mpszRightDelim,
		const char *		mpszNum);
  int
	web_custom_request(
		const char *		mpszReqestName,
		...);							 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
  int
	web_disable_keep_alive(void);
  int
	web_enable_keep_alive(void);
  int
	web_find(
		const char *		mpszStepName,
		...);							 
										 
										 
										 
										 
										 
										 
										 
										 
										 
  int
	web_get_int_property(
		const int			miHttpInfoType);
  int
	web_image(
		const char *		mpszStepName,
		...);							 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
  int
	web_image_check(
		const char *		mpszName,
		...);
  int
	web_java_check(
		const char *		mpszName,
		...);
  int
	web_link(
		const char *		mpszStepName,
		...);							 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 

	
  int
	web_global_verification(
		const char *		mpszArg1,
		...);							 
										 
										 
										 
										 
										 

  int
	web_reg_find(
		const char *		mpszArg1,
		...);							 
										 
										 
										 
										 
										 
										 
										 
				
  int
	web_reg_save_param(
		const char *		mpszParamName,
		...);							 
										 
										 
										 
										 
										 
										 

  int
	web_convert_param(
		const char * 		mpszParamName, 
										 
		...);							 
										 
										 


										 

										 
  int
	web_remove_auto_filter(
		const char *		mpszArg,
		...
	);									 
										 
				
  int
	web_remove_auto_header(
		const char *		mpszHeaderName,
		...);							 
										 



  int
	web_remove_cookie(
		const char *		mpszCookie);

  int
	web_save_header(
		const char *		mpszType,	 
		const char *		mpszName);	 
  int
	web_set_certificate(
		const char *		mpszIndex);
  int
	web_set_certificate_ex(
		const char *		mpszArg1,
		...);							 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
  int
	web_set_connections_limit(
		const char *		mpszLimit);
  int
	web_set_max_html_param_len(
		const char *		mpszLen);
  int
	web_set_max_retries(
		const char *		mpszMaxRetries);
  int
	web_set_proxy(
		const char *		mpszProxyHost);
  int
	web_set_proxy_bypass(
		const char *		mpszBypass);
  int
	web_set_secure_proxy(
		const char *		mpszProxyHost);
  int
	web_set_sockets_option(
		const char *		mpszOptionID,
		const char *		mpszOptionValue
	);
  int
	web_set_option(
		const char *		mpszOptionID,
		const char *		mpszOptionValue,
		...								 
	);
  int
	web_set_timeout(
		const char *		mpszWhat,
		const char *		mpszTimeout);
  int
	web_set_user(
		const char *		mpszUserName,
		const char *		mpszPwd,
		const char *		mpszHost);

  int
	web_sjis_to_euc_param(
		const char *		mpszParamName,
										 
		const char *		mpszParamValSjis);
										 

  int
	web_submit_data(
		const char *		mpszStepName,
		...);							 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
  int
	web_submit_form(
		const char *		mpszStepName,
		...);							 
										 
										 
										 
										 
										 
										 
										 
										 
										  
										 
										 
										 
										 
										 
										  
										 
										 
										 
										 
										 
										 
										 
										  
										 
										 
										 
										 
										 
										  
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
  int
	web_url(
		const char *		mpszUrlName,
		...);							 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 

  int 
	web_set_proxy_bypass_local(
		const char * mpszNoLocal
		);

  int 
	web_cache_cleanup(void);

  int
	web_create_html_query(
		const char* mpszStartQuery,
		...);							 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 

  int 
	web_create_radio_button_param(
		const char *NameFiled,
		const char *NameAndVal,
		const char *ParamName
		);

  int
	web_convert_from_formatted(
		const char * mpszArg1,
		...);							 
										 
										 
										 
										 
										 
										
  int
	web_convert_to_formatted(
		const char * mpszArg1,
		...);							 
										 
										 
										 
										 
										 

  int
	web_reg_save_param_ex(
		const char * mpszParamName,
		...);							 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 

  int
	web_reg_save_param_xpath(
		const char * mpszParamName,
		...);							
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 

  int
	web_reg_save_param_regexp(
		 const char * mpszParamName,
		 ...);							
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 
										 










# 612 "C:\\Program Files\\HP\\LoadRunner\\include/as_web.h"


# 625 "C:\\Program Files\\HP\\LoadRunner\\include/as_web.h"



























# 663 "C:\\Program Files\\HP\\LoadRunner\\include/as_web.h"

 
 
 


  int
	FormSubmit(
		const char *		mpszFormName,
		...);
  int
	InitWebVuser(void);
  int
	SetUser(
		const char *		mpszUserName,
		const char *		mpszPwd,
		const char *		mpszHost);
  int
	TerminateWebVuser(void);
  int
	URL(
		const char *		mpszUrlName);
























# 731 "C:\\Program Files\\HP\\LoadRunner\\include/as_web.h"



 
 
 






# 10 "C:\\Program Files\\HP\\LoadRunner\\include/web_api.h" 2












 






 











  int
	web_reg_add_cookie(
		const char *		mpszCookie,
		...);							 
										 

  int
	web_report_data_point(
		const char *		mpszEventType,
		const char *		mpszEventName,
		const char *		mpszDataPointName,
		const char *		mpszLAST);	 
										 
										 
										 

  int
	web_text_link(
		const char *		mpszStepName,
		...);

  int
	web_element(
		const char *		mpszStepName,
		...);

  int
	web_image_link(
		const char *		mpszStepName,
		...);

  int
	web_static_image(
		const char *		mpszStepName,
		...);

  int
	web_image_submit(
		const char *		mpszStepName,
		...);

  int
	web_button(
		const char *		mpszStepName,
		...);

  int
	web_edit_field(
		const char *		mpszStepName,
		...);

  int
	web_radio_group(
		const char *		mpszStepName,
		...);

  int
	web_check_box(
		const char *		mpszStepName,
		...);

  int
	web_list(
		const char *		mpszStepName,
		...);

  int
	web_text_area(
		const char *		mpszStepName,
		...);

  int
	web_map_area(
		const char *		mpszStepName,
		...);

  int
	web_eval_java_script(
		const char *		mpszStepName,
		...);

  int
	web_reg_dialog(
		const char *		mpszArg1,
		...);

  int
	web_reg_cross_step_download(
		const char *		mpszArg1,
		...);

  int
	web_browser(
		const char *		mpszStepName,
		...);

  int
	web_control(
		const char *		mpszStepName,
		...);

  int
	web_set_rts_key(
		const char *		mpszArg1,
		...);

  int
	web_save_param_length(
		const char * 		mpszParamName,
		...);

  int
	web_save_timestamp_param(
		const char * 		mpszParamName,
		...);

  int
	web_load_cache(
		const char *		mpszStepName,
		...);							 
										 

  int
	web_dump_cache(
		const char *		mpszStepName,
		...);							 
										 
										 

  int
	web_reg_find_in_log(
		const char *		mpszArg1,
		...);							 
										 
										 

  int
	web_get_sockets_info(
		const char *		mpszArg1,
		...);							 
										 
										 
										 
										 

  int
	web_add_cookie_ex(
		const char *		mpszArg1,
		...);							 
										 
										 
										 

  int
	web_hook_java_script(
		const char *		mpszArg1,
		...);							 
										 
										 
										 





 
 
 


# 4 "globals.h" 2

# 1 "C:\\Program Files\\HP\\LoadRunner\\include/lrw_custom_body.h" 1
 





# 5 "globals.h" 2


 
 
 
 
# 1 "C:\\Program Files\\HP\\LoadRunner\\include/wssoap.h" 1
 










 
# 77 "C:\\Program Files\\HP\\LoadRunner\\include/wssoap.h"


  int
soap_request(
				char * pFirstArg,
				...
			);


 




























 






 
# 238 "C:\\Program Files\\HP\\LoadRunner\\include/wssoap.h"


  int
web_service_call(
					char * pFirstArg,	
					...
				);



 
# 272 "C:\\Program Files\\HP\\LoadRunner\\include/wssoap.h"


  int
web_service_set_security(
					char * pFirstArg,	
					...
				);

 
# 305 "C:\\Program Files\\HP\\LoadRunner\\include/wssoap.h"


  char*
web_service_wait_for_event(
					char * pFirstArg,	
					...
 
				);

 






  int
web_service_cancel_security();

 
# 334 "C:\\Program Files\\HP\\LoadRunner\\include/wssoap.h"


  int
wsdl_wsi_validation (
					 char * pFirstArg,	
					...
				);

 
# 380 "C:\\Program Files\\HP\\LoadRunner\\include/wssoap.h"


  int
web_service_set_security_saml(
					char * pFirstArg,	
					...
				);


 






  int
web_service_cancel_security_saml();

 
# 412 "C:\\Program Files\\HP\\LoadRunner\\include/wssoap.h"

  int
jms_send_message_queue(
					    const char * StepName,
						const char * SentMessage,
						const char * SendQueueName);



  int
jms_receive_message_queue(
						  const char * StepName,
						  const char * ReceiveQueueName);


  int
jms_send_receive_message_queue(
					const char * StepName,
					const char * SentMessage,
					const char * SendQueueName,
					const char * ReceiveQueueName);


  int
jms_publish_message_topic(
					   const char * StepName,
					   const char * SentMessage,
					   const char * SendTopicName);


  int
jms_receive_message_topic(
						  const char * StepName,
						  const char * SubscriptionName,
						  const char * ReceiveTopicName);

  int
jms_subscribe_topic(
					const char * StepName,
					const char * SubscriptionName,
					const char * SendTopicName);

  int
jms_set_general_property(
						 const char * StepName,
					const char * Name,
					const char * Value);

  int
jms_set_message_property(
						 const char * StepName,
						const char * name,
						const char * value);

						
 











  int
soa_xml_validate (
					char * pFirstArg,	
					...
				  );

  int
lr_db_connect (
					char * pFirstArg,	
					...
				  );
  int
lr_db_disconnect (
					char * pFirstArg,	
					...
				  );
  int
lr_db_executeSQLStatement (
					char * pFirstArg,	
					...
				  );


  int
lr_db_dataset_action (
					char * pFirstArg,	
					...
				  );

  int
lr_checkpoint (
					char * pFirstArg,	
					...
				  );

  int
lr_db_getvalue (
					char * pFirstArg,	
					...
				  );


# 11 "globals.h" 2








# 1 "d:\\05.checksum\\01.script\\checksum_esb_loopback\\\\combined_CheckSum_ESB_loopback.c" 2


# 1 "globals.h" 1
 
 


# 1 "C:\\Program Files\\HP\\LoadRunner\\include/lrw_custom_body.h" 1
 





# 5 "globals.h" 2


 
 
 
 








# 3 "d:\\05.checksum\\01.script\\checksum_esb_loopback\\\\combined_CheckSum_ESB_loopback.c" 2

# 1 "vuser_init.c" 1
int handle_id;
int Enc_size;

char* cert_RequestXml_1;
char* cert_ResquextXmlEnc;
char* policy_RequestXml_1;
char* policy_RequestXmlEnc;
char* keyhandshake_RequestXml_1;
char* keyhandshake_RequestXmlEnc;

char* certReq;
char* certURL;
char* policyReq;
char* policyURL;
char* handshakeURL;
char* handshakeReq;

char* uuid;

char search[1000+1];

int j=0;
int checksum_len = 5000;
int checksum_pos = -150;
int EncYN = 0;	         

unsigned char secretKey1[512] = "d0ff169dddad20cadfe0d018"; 
unsigned char secretKey2[512] = "efdfe6f93682de29222289d2"; 


vuser_init()
{

	int offset1; 
	int offset2; 
    char * position1; 
	char * position2; 
    
    char * search_str1 = "<Parameter id=\"ErrorMsg\" type=\"string\">"; 
	char * search_str2 = "</Parameter>"; 

	char searched_str[8192];




	char out[8192];

	char* out1; 
    int len1;


	char* handshake_out;


   int result;
   int i;
   char host_name[21][20]={"loadrunner01","loadrunner02","loadrunner03","loadrunner04","loadrunner05","loadrunner06","WIN-DEIMWP6RXRA","WIN-GYLWUXTMDNE","bloadgen02","WIN-RFANNWK6HOZ","bloadcon01","WIN-XMWMB2EK2R8","WIN-1BJ6FAQ5IN8","cldrap08","cldrap07","cldrap06","WIN-KW4CULPDHOA","WIN-OCY5RJXQMEA","WIN-2YN3ZKAH5IX","WIN-1KRKSMJDU0M","WIN-ZZU2XD8GBD2"};
   char host_ip[21][15]={"10.219.1.184","10.219.1.185","10.219.1.186","10.219.1.187","10.219.1.188","10.219.1.189","10.217.233.11","10.217.233.12","10.217.233.13","10.217.233.14","10.217.233.15","10.217.233.16","10.217.233.17","10.217.233.18","10.217.233.19","10.217.233.20","10.217.233.21","10.217.233.22","10.217.233.23","10.217.233.25","10.217.233.26"};


   char * my_host;
 my_host = lr_get_host_name(  );
 lr_output_message("%s", my_host);


  for(i=0; i<21;i++)
  {
 result = strcmp( host_name [i],
  my_host );

    if(result == 0)
    {
break;
    }
  }

  if(i>=21)
  {
 lr_error_message("Check for host name");
  }
  else
  {
 
 lr_save_string( lr_eval_string(host_ip [ i]),
  "IP_Param" );
  }


   web_set_max_html_param_len("2048");

   web_reg_save_param("aaa", 
    "LB=CSRFToken\" value=\"", "RB=\">", "LAST" ); 

	web_url("main.csr", 
		"URL=http://{배포IP}/main.csr", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=", 
		"Snapshot=t1.inf", 
		"Mode=HTML", 
		"LAST");

 


 



   
   


	web_submit_data("login.csr", 
		"Action=http://{배포IP}/login/login.csr", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer=http://{배포IP}/login/formLogin.csr", 
		"Snapshot=t2.inf", 
		"Mode=HTML", 
		"ITEMDATA", 
		"Name=plainUserId", "Value={login_ID}", "ENDITEM", 
		"Name=plainScrtNo", "Value=New1234!", "ENDITEM", 
		"Name=userLocalIp", "Value={IP_Param}", "ENDITEM", 
		"Name=macAdrsNm", "Value=FF-FF-FF-FF-FF-FF", "ENDITEM", 
		"Name=CSRFToken", "Value={aaa}", "ENDITEM", 
		"LAST");

 

 

  


     
    web_reg_save_param("bbb", "LB=TOKEN_ID\":\"", "RB=\",\"PAYLOAD_YN", "LAST" ); 		 
	

	web_submit_data("systemLinkAuth.csr", 
		"Action=http://{배포IP}/main/systemLinkAuth.csr", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=application/json", 
		"Referer=http://{배포IP}/main.csr", 
		"Snapshot=t3.inf", 
		"Mode=HTML", 
		"ITEMDATA", 
		"Name=systId", "Value=NSTU", "ENDITEM", 
		"Name=CSRFToken", "Value={aaa}", "ENDITEM", 
		"LAST");
 

 

	ci_load_dll(ci_this_context,("C:\\sadmod.dll")); 
	ci_load_dll(ci_this_context,("C:\\nexaNbssAdp.dll"));


	return 0;
}

# 4 "d:\\05.checksum\\01.script\\checksum_esb_loopback\\\\combined_CheckSum_ESB_loopback.c" 2

# 1 "Action1.c" 1
Action1()
{
	  
	char* data_RequestXml_1;
	char* data_RequestXmlEnc_1;

	char* data_ResponseXml_1 = 0;
	char* data_ResponseXmlENC_1 = 0;

	int x;

	
 
 
# 27 "Action1.c"
	char checkSum[100];


	 if ((data_RequestXml_1 = (char *)calloc(20000, sizeof(char))) == 0) { 			 
		 lr_output_message ("Insufficient memory available"); 
		 return -1; 
	 } 
	
     if ((data_RequestXmlEnc_1 = (char *)calloc(20000, sizeof(char))) == 0) { 		 
        lr_output_message ("Insufficient memory available"); 
        return 0; 
     }

	 if ((data_ResponseXml_1 = (char *)calloc(20000, sizeof(char))) == 0) { 		 
		lr_output_message ("Insufficient memory available"); 
		return 0; 
	 }

	 if ((data_ResponseXmlENC_1 = (char *)calloc(20000, sizeof(char))) == 0) { 		 
		lr_output_message ("Insufficient memory available"); 
		return 0; 
	 }


	 

	  







	 
# 75 "Action1.c"

	 
    strcpy( data_RequestXml_1,"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				"<Root xmlns=\"http://www.nexacroplatform.com/platform/dataset\">\n"
				"\t<Parameters>\n"
                "<Parameter id=\"PHAROSVISITOR\">00004f3f0154e03263c62cdd0adb0756</Parameter>"
			    "\t</Parameters>\n"
				"\t<Dataset id=\"commonHeader\">\n"
				"\t\t<ColumnInfo>\n"
				"\t\t\t<Column id=\"appName\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"svcName\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"fnName\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"fnCd\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"globalNo\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"chnlType\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"envrFlag\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"trFlag\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"trDate\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"trTime\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"clntIp\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"responseType\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"responseCode\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"responseLogcd\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"responseTitle\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"responseBasc\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"responseDtal\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"responseSystem\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"userId\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"realUserId\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"filler\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"langCode\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"orgId\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"srcId\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"curHostId\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"lgDateTime\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"tokenId\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"cmpnCd\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"lockType\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"lockId\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"lockTimeSt\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"businessKey\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"arbitraryKey\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"resendFlag\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"phase\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t</ColumnInfo>\n"
				"\t\t<Rows>\n"
				"\t\t\t<Row type=\"insert\">\n"
				"\t\t\t\t<Col id=\"appName\">NBSS_CST</Col>\n"
				"\t\t\t\t<Col id=\"svcName\">/ESBFW/SATest/ETC/PL_LoopBack1</Col>\n"
				"\t\t\t\t<Col id=\"fnName\">service</Col>\n"
			  	"\t\t\t\t<Col id=\"fnCd\" />\n"
				"\t\t\t\t<Col id=\"globalNo\">00000");
				strcat(data_RequestXml_1, lr_eval_string("{login_ID}"));
				strcat(data_RequestXml_1, lr_eval_string("{DATE}"));
				strcat(data_RequestXml_1, lr_eval_string("{TIME}"));
				strcat(data_RequestXml_1, lr_eval_string("{G_Random}"));
				strcat(data_RequestXml_1, "</Col>\n"
				"\t\t\t\t<Col id=\"chnlType\">UI</Col>\n"
				"\t\t\t\t<Col id=\"envrFlag\" />\n"
				"\t\t\t\t<Col id=\"trFlag\">T</Col>\n"
				"\t\t\t\t<Col id=\"trDate\">");
				strcat (data_RequestXml_1, lr_eval_string("{DATE}"));
				strcat (data_RequestXml_1, "</Col>\n"
				"\t\t\t\t<Col id=\"trTime\">");
				strcat (data_RequestXml_1, lr_eval_string("{TIME}"));
				strcat (data_RequestXml_1, "</Col>\n"
				"\t\t\t\t<Col id=\"clntIp\">");
				strcat( data_RequestXml_1,lr_eval_string("{IP}"));
				strcat (data_RequestXml_1, "</Col>\n"
				"\t\t\t\t<Col id=\"responseType\" />\n"
				"\t\t\t\t<Col id=\"responseCode\" />\n"
				"\t\t\t\t<Col id=\"responseLogcd\" />\n"
				"\t\t\t\t<Col id=\"responseTitle\" />\n"
				"\t\t\t\t<Col id=\"responseBasc\" />\n"
				"\t\t\t\t<Col id=\"responseDtal\" />\n"
				"\t\t\t\t<Col id=\"responseSystem\" />\n"
				"\t\t\t\t<Col id=\"userId\">");
				strcat (data_RequestXml_1, lr_eval_string("{login_ID}"));
				strcat (data_RequestXml_1, "</Col>\n"
				"\t\t\t\t<Col id=\"realUserId\">");
				strcat (data_RequestXml_1, lr_eval_string("{login_ID}"));
				strcat (data_RequestXml_1, "</Col>\n"
				"\t\t\t\t<Col id=\"filler\">E</Col>\n"
				"\t\t\t\t<Col id=\"langCode\" />\n"
				"\t\t\t\t<Col id=\"orgId\">SPT8050</Col>\n"
				"\t\t\t\t<Col id=\"srcId\">DNIUI1000LY</Col>\n"
				"\t\t\t\t<Col id=\"curHostId\" />\n"
				"\t\t\t\t<Col id=\"lgDateTime\">");
				strcat (data_RequestXml_1, lr_eval_string("{DATE}"));
				strcat (data_RequestXml_1, lr_eval_string("{TIME}"));
				strcat (data_RequestXml_1, "</Col>\n"
				"\t\t\t\t<Col id=\"tokenId\">");
				strcat (data_RequestXml_1, lr_eval_string("{bbb}"));
				strcat (data_RequestXml_1, "</Col>\n"
				"\t\t\t\t<Col id=\"cmpnCd\">KT</Col>\n"
				"\t\t\t\t<Col id=\"lockType\" />\n"
				"\t\t\t\t<Col id=\"lockId\" />\n"
				"\t\t\t\t<Col id=\"lockTimeSt\" />\n"
				"\t\t\t\t<Col id=\"businessKey\" />\n"
				"\t\t\t\t<Col id=\"arbitraryKey\">{}</Col>\n"
				"\t\t\t\t<Col id=\"resendFlag\" />\n"
				"\t\t\t\t<Col id=\"phase\">PA3</Col>\n"
				"\t\t\t</Row>\n"
				"\t\t</Rows>\n"
				"\t</Dataset>\n"
				"\t<Dataset id=\"bizHeader\">\n"
				"\t\t<ColumnInfo>\n"
				"\t\t\t<Column id=\"orderId\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"cbSvcName\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"cbFnName\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t</ColumnInfo>\n"
				"\t\t<Rows>\n"
				"\t\t\t<Row type=\"insert\">\n"
				"\t\t\t\t<Col id=\"orderId\">orderId</Col>\n"
				"\t\t\t\t<Col id=\"cbSvcName\">/ESBFW/SATest/ETC/PL_LoopBack1</Col>\n"
				"\t\t\t\t<Col id=\"cbFnName\">service</Col>\n"
				"\t\t\t</Row>\n"
				"\t\t</Rows>\n"
				"\t</Dataset>\n"
				"<Dataset id=\"EchoInDTO\">"
						"<ColumnInfo>"
							"<Column id=\"loopCnt\" type=\"STRING\" size=\"32\"  />"
						"</ColumnInfo>"
						"<Rows>"
							"<Row>"
								"<Col id=\"loopCnt\">10</Col>"
							"</Row>"
						"</Rows>"
					"</Dataset>"
				"</Root>",
		"LAST");

     
	 web_set_max_html_param_len("800000");

	 lr_start_transaction("ESBFW_SATest_ETC_PL_LoopBack1"); 

	  if (EncYN == 0) {	

          lr_save_string( data_RequestXml_1, "P_data_RequestXml_1");

		  web_reg_find("Text=<Parameter id=\"ErrorCode\" type=\"string\">0</Parameter>","LAST");

		  
		  
	
		  
		  

		   lr_output_message("%s",data_RequestXml_1);
		  
		 web_custom_request("UiGateway",
			  "URL=http://{ESB_IP}/UiGateway", 
			  "Method=POST",
			  "Resource=0",
			  "RecContentType=text/html",
			  "Referer=http://10.219.6.130:5000/NBSS/FRM/NBSS_LOADING.xfdl.js",
			  "Snapshot=t11.inf",
			  "Mode=HTML",
			  "Body={P_data_RequestXml_1}",
			  "LAST");
	

	  } else if (EncYN == 1) {
		   memset(data_RequestXmlEnc_1, 0, sizeof(data_RequestXmlEnc_1));	

		  	memset (checkSum, 0x00, sizeof (checkSum));


			HmacSha256 (secretKey2, strlen(secretKey2), 
						 data_RequestXml_1 , strlen(data_RequestXml_1) ,
						 checksum_pos, checksum_len,  
						  
						 checkSum, 32);


			 sprintf (data_RequestXmlEnc_1, "%s%s", checkSum, data_RequestXml_1);

			 lr_save_string( data_RequestXmlEnc_1, "Data_encrypt_out_1");
		
		
			 web_reg_find("Text=<Parameter id=\"ErrorCode\" type=\"string\">0</Parameter>","LAST");
		
			  
			  
		
			  
			  

			 lr_output_message("%s",data_RequestXmlEnc_1);

			 web_custom_request("UiGateway",
				  "URL=http://{ESB_IP}/UiGateway", 
				  "Method=POST",
				  "Resource=0",
				  "RecContentType=text/html",
				  "Referer=http://10.219.6.130:5000/NBSS/FRM/NBSS_LOADING.xfdl.js",
				  "Snapshot=t11.inf",
				  "Mode=HTML",
				  "Body={Data_encrypt_out_1}",
				  "LAST");
		
	}


     
	lr_end_transaction("ESBFW_SATest_ETC_PL_LoopBack1", 2);

	free(data_RequestXml_1);
	free(data_RequestXmlEnc_1);
	free(data_ResponseXml_1);
	free(data_ResponseXmlENC_1);

	return 0;
}

# 5 "d:\\05.checksum\\01.script\\checksum_esb_loopback\\\\combined_CheckSum_ESB_loopback.c" 2

# 1 "Action2.c" 1
Action2()
{
	  
	char* data_RequestXml_2;
	char* data_RequestXmlEnc_2;

	char* data_ResponseXml_2 = 0;
	char* data_ResponseXmlENC_2 = 0;

	int x;

	
 
 
# 27 "Action2.c"
	char checkSum[100];


	 if ((data_RequestXml_2 = (char *)calloc(20000, sizeof(char))) == 0) { 			 
		 lr_output_message ("Insufficient memory available"); 
		 return -1; 
	 } 
	
     if ((data_RequestXmlEnc_2 = (char *)calloc(20000, sizeof(char))) == 0) { 		 
        lr_output_message ("Insufficient memory available"); 
        return 0; 
     }

	 if ((data_ResponseXml_2 = (char *)calloc(20000, sizeof(char))) == 0) { 		 
		lr_output_message ("Insufficient memory available"); 
		return 0; 
	 }

	 if ((data_ResponseXmlENC_2 = (char *)calloc(20000, sizeof(char))) == 0) { 		 
		lr_output_message ("Insufficient memory available"); 
		return 0; 
	 }


	 

	  







	 
# 75 "Action2.c"

	 
    strcpy( data_RequestXml_2,"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				"<Root xmlns=\"http://www.nexacroplatform.com/platform/dataset\">\n"
				"\t<Parameters>\n"
                "<Parameter id=\"PHAROSVISITOR\">00004f3f0154e03263c62cdd0adb0756</Parameter>"
			    "\t</Parameters>\n"
				"\t<Dataset id=\"commonHeader\">\n"
				"\t\t<ColumnInfo>\n"
				"\t\t\t<Column id=\"appName\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"svcName\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"fnName\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"fnCd\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"globalNo\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"chnlType\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"envrFlag\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"trFlag\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"trDate\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"trTime\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"clntIp\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"responseType\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"responseCode\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"responseLogcd\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"responseTitle\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"responseBasc\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"responseDtal\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"responseSystem\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"userId\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"realUserId\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"filler\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"langCode\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"orgId\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"srcId\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"curHostId\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"lgDateTime\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"tokenId\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"cmpnCd\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"lockType\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"lockId\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"lockTimeSt\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"businessKey\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"arbitraryKey\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"resendFlag\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"phase\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t</ColumnInfo>\n"
				"\t\t<Rows>\n"
				"\t\t\t<Row type=\"insert\">\n"
				"\t\t\t\t<Col id=\"appName\">NBSS_CST</Col>\n"
				"\t\t\t\t<Col id=\"svcName\">/ESBFW/SATest/ETC/PL_LoopBack2</Col>\n"
				"\t\t\t\t<Col id=\"fnName\">service</Col>\n"
			  	"\t\t\t\t<Col id=\"fnCd\" />\n"
				"\t\t\t\t<Col id=\"globalNo\">00000");
				strcat(data_RequestXml_2, lr_eval_string("{login_ID}"));
				strcat(data_RequestXml_2, lr_eval_string("{DATE}"));
				strcat(data_RequestXml_2, lr_eval_string("{TIME}"));
				strcat(data_RequestXml_2, lr_eval_string("{G_Random}"));
				strcat(data_RequestXml_2, "</Col>\n"
				"\t\t\t\t<Col id=\"chnlType\">UI</Col>\n"
				"\t\t\t\t<Col id=\"envrFlag\" />\n"
				"\t\t\t\t<Col id=\"trFlag\">T</Col>\n"
				"\t\t\t\t<Col id=\"trDate\">");
				strcat (data_RequestXml_2, lr_eval_string("{DATE}"));
				strcat (data_RequestXml_2, "</Col>\n"
				"\t\t\t\t<Col id=\"trTime\">");
				strcat (data_RequestXml_2, lr_eval_string("{TIME}"));
				strcat (data_RequestXml_2, "</Col>\n"
				"\t\t\t\t<Col id=\"clntIp\">");
				strcat( data_RequestXml_2,lr_eval_string("{IP}"));
				strcat (data_RequestXml_2, "</Col>\n"
				"\t\t\t\t<Col id=\"responseType\" />\n"
				"\t\t\t\t<Col id=\"responseCode\" />\n"
				"\t\t\t\t<Col id=\"responseLogcd\" />\n"
				"\t\t\t\t<Col id=\"responseTitle\" />\n"
				"\t\t\t\t<Col id=\"responseBasc\" />\n"
				"\t\t\t\t<Col id=\"responseDtal\" />\n"
				"\t\t\t\t<Col id=\"responseSystem\" />\n"
				"\t\t\t\t<Col id=\"userId\">");
				strcat (data_RequestXml_2, lr_eval_string("{login_ID}"));
				strcat (data_RequestXml_2, "</Col>\n"
				"\t\t\t\t<Col id=\"realUserId\">");
				strcat (data_RequestXml_2, lr_eval_string("{login_ID}"));
				strcat (data_RequestXml_2, "</Col>\n"
				"\t\t\t\t<Col id=\"filler\">E</Col>\n"
				"\t\t\t\t<Col id=\"langCode\" />\n"
				"\t\t\t\t<Col id=\"orgId\">SPT8050</Col>\n"
				"\t\t\t\t<Col id=\"srcId\">DNIUI1000LY</Col>\n"
				"\t\t\t\t<Col id=\"curHostId\" />\n"
				"\t\t\t\t<Col id=\"lgDateTime\">");
				strcat (data_RequestXml_2, lr_eval_string("{DATE}"));
				strcat (data_RequestXml_2, lr_eval_string("{TIME}"));
				strcat (data_RequestXml_2, "</Col>\n"
				"\t\t\t\t<Col id=\"tokenId\">");
				strcat (data_RequestXml_2, lr_eval_string("{bbb}"));
				strcat (data_RequestXml_2, "</Col>\n"
				"\t\t\t\t<Col id=\"cmpnCd\">KT</Col>\n"
				"\t\t\t\t<Col id=\"lockType\" />\n"
				"\t\t\t\t<Col id=\"lockId\" />\n"
				"\t\t\t\t<Col id=\"lockTimeSt\" />\n"
				"\t\t\t\t<Col id=\"businessKey\" />\n"
				"\t\t\t\t<Col id=\"arbitraryKey\">{}</Col>\n"
				"\t\t\t\t<Col id=\"resendFlag\" />\n"
				"\t\t\t\t<Col id=\"phase\">PA3</Col>\n"
				"\t\t\t</Row>\n"
				"\t\t</Rows>\n"
				"\t</Dataset>\n"
				"\t<Dataset id=\"bizHeader\">\n"
				"\t\t<ColumnInfo>\n"
				"\t\t\t<Column id=\"orderId\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"cbSvcName\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t\t<Column id=\"cbFnName\" type=\"STRING\" size=\"256\"  />\n"
				"\t\t</ColumnInfo>\n"
				"\t\t<Rows>\n"
				"\t\t\t<Row type=\"insert\">\n"
				"\t\t\t\t<Col id=\"orderId\">orderId</Col>\n"
				"\t\t\t\t<Col id=\"cbSvcName\">/ESBFW/SATest/ETC/PL_LoopBack2</Col>\n"
				"\t\t\t\t<Col id=\"cbFnName\">service</Col>\n"
				"\t\t\t</Row>\n"
				"\t\t</Rows>\n"
				"\t</Dataset>\n"
				"<Dataset id=\"EchoInDTO\">"
						"<ColumnInfo>"
							"<Column id=\"loopCnt\" type=\"STRING\" size=\"32\"  />"
						"</ColumnInfo>"
						"<Rows>"
							"<Row>"
								"<Col id=\"loopCnt\">10</Col>"
							"</Row>"
						"</Rows>"
					"</Dataset>"
				"</Root>",
		"LAST");

     
	 web_set_max_html_param_len("800000");

	 lr_start_transaction("ESBFW_SATest_ETC_PL_LoopBack2"); 

	  if (EncYN == 0) {	

          lr_save_string( data_RequestXml_2, "P_data_RequestXml_1");

		  web_reg_find("Text=<Parameter id=\"ErrorCode\" type=\"string\">0</Parameter>","LAST");

		  
		  
	
		  
		  

		   lr_output_message("%s",data_RequestXml_2);
		  
		 web_custom_request("UiGateway",
			  "URL=http://{ESB_IP}/UiGateway", 
			  "Method=POST",
			  "Resource=0",
			  "RecContentType=text/html",
			  "Referer=http://10.219.6.130:5000/NBSS/FRM/NBSS_LOADING.xfdl.js",
			  "Snapshot=t11.inf",
			  "Mode=HTML",
			  "Body={P_data_RequestXml_1}",
			  "LAST");
	

	  } else if (EncYN == 1) {
		   memset(data_RequestXmlEnc_2, 0, sizeof(data_RequestXmlEnc_2));	

		  	memset (checkSum, 0x00, sizeof (checkSum));


			HmacSha256 (secretKey2, strlen(secretKey2), 
						 data_RequestXml_2 , strlen(data_RequestXml_2) ,
						 checksum_pos, checksum_len,  
						  
						 checkSum, 32);


			 sprintf (data_RequestXmlEnc_2, "%s%s", checkSum, data_RequestXml_2);

			 lr_save_string( data_RequestXmlEnc_2, "Data_encrypt_out_1");
		
		
			 web_reg_find("Text=<Parameter id=\"ErrorCode\" type=\"string\">0</Parameter>","LAST");
		
			  
			  
		
			  
			  

			 lr_output_message("%s",data_RequestXmlEnc_2);

			 web_custom_request("UiGateway",
				  "URL=http://{ESB_IP}/UiGateway", 
				  "Method=POST",
				  "Resource=0",
				  "RecContentType=text/html",
				  "Referer=http://10.219.6.130:5000/NBSS/FRM/NBSS_LOADING.xfdl.js",
				  "Snapshot=t11.inf",
				  "Mode=HTML",
				  "Body={Data_encrypt_out_1}",
				  "LAST");
		
	}


     
	lr_end_transaction("ESBFW_SATest_ETC_PL_LoopBack2", 2);

	free(data_RequestXml_2);
	free(data_RequestXmlEnc_2);
	free(data_ResponseXml_2);
	free(data_ResponseXmlENC_2);

	return 0;
}

# 6 "d:\\05.checksum\\01.script\\checksum_esb_loopback\\\\combined_CheckSum_ESB_loopback.c" 2

# 1 "vuser_end.c" 1
vuser_end()
{
	return 0;
}
# 7 "d:\\05.checksum\\01.script\\checksum_esb_loopback\\\\combined_CheckSum_ESB_loopback.c" 2

