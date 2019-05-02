#!/bin/bash


echo "---------------------------- 대외 SIT Outbound 방화벽 점검 시작 ----------------------------"
echo "1. LCRI_TCP_OUT_001_IP.LCRI_TCP_OUT_001_PORT (10.217.213.158:40000)" `nc -z 10.217.213.158 40000`
echo "2. EKAI_TCP_OUT_001_IP.EKAI_TCP_OUT_001_PORT (211.55.53.51:60017)" `nc -z 211.55.53.51 60017`
echo "3. EKAI_TCP_OUT_002_IP.EKAI_TCP_OUT_002_PORT (10.221.16.27:51060)" `nc -z 10.221.16.27 51060`
echo "4. EKAI_TCP_OUT_002_IP.EKAI_TCP_OUT_003_PORT (10.221.16.27:55002)" `nc -z 10.221.16.27 55002`
echo "5. EKAI_TCP_OUT_003_IP.EKAI_TCP_OUT_004_PORT (10.221.16.27:52001)" `nc -z 10.221.16.27 52001`
echo "6. EKCP_TCP_OUT_001_IP.EKCP_TCP_OUT_001_PORT (192.168.73.158:8090)" `nc -z 192.168.73.158 8090`
echo "7. EKCP_TCP_OUT_002_IP.EKCP_TCP_OUT_002_PORT (192.168.73.158:8090)" `nc -z 192.168.73.158 8090`
echo "8. EKIS_TCP_OUT_001_IP.EKIS_TCP_OUT_001_PORT (192.168.72.50:5240)" `nc -z 192.168.72.50 5240`
echo "9. EMHW_TCP_OUT_001_IP.EMHW_TCP_OUT_001_PORT (10.226.14.93:17212)" `nc -z 10.226.14.93 17212`
echo "10. ENIC_TCP_OUT_001_IP.ENIC_TCP_OUT_001_PORT (10.95.50.53:20061)" `nc -z 10.95.50.53 20061`
echo "11. LCIN_TCP_OUT_001_IP.LCIN_TCP_OUT_002_PORT (147.6.184.103:27721)" `nc -z 147.6.184.103 27721`
echo "12. LCIR_TCP_OUT_003_IP.LCIR_TCP_OUT_002_PORT (147.6.184.103:27723)" `nc -z 147.6.184.103 27723`
echo "13. LCIR_TCP_OUT_003_IP.LCIR_TCP_OUT_003_PORT (147.6.184.103:27724)" `nc -z 147.6.184.103 27724`
echo "14. LCNS_TCP_OUT_001_IP.LCNS_TCP_OUT_001_PORT (147.6.184.89:60017)" `nc -z 147.6.184.89 60017`
echo "15. LCRS_TCP_OUT_001_IP.LCRS_TCP_OUT_001_PORT (147.6.184.100:34060)" `nc -z 147.6.184.100 34060`
echo "16. LIOT_TCP_OUT_001_IP.LIOT_TCP_OUT_001_PORT (192.168.91.216:10011)" `nc -z 192.168.91.216 10011`
echo "17. LNOM_TCP_OUT_001_IP.LNOM_TCP_OUT_002_PORT (192.168.91.216:27714)" `nc -z 192.168.91.216 27714`
echo "18. EKAI_FTP_OUT_001_IP.EKAI_FTP_OUT_001_PORT (61.36.66.22:21)" `nc -z 61.36.66.22 21`
echo "19. ENIC_FTP_OUT_001_IP.ENIC_FTP_OUT_001_PORT (203.234.219.30:21)" `nc -z 203.234.219.30 21`
echo "20. ESAT_FTP_OUT_001_IP.ESAT_FTP_OUT_001_PORT (14.49.27.69:22)" `nc -z 14.49.27.69 22`
echo "21. ESKY_FTP_OUT_001_IP.ESKY_FTP_OUT_001_PORT (10.221.16.254:22)" `nc -z 10.221.16.254 22`
echo "22. LCBN_FTP_OUT_001_IP.LCBN_FTP_OUT_001_PORT (147.6.184.101:22)" `nc -z 147.6.184.101 22`
echo "23. NCRB_FTP_OUT_001_IP.NCRB_FTP_OUT_001_PORT (10.217.157.13:22)" `nc -z 10.217.157.13 22`
echo "24. NCRC_FTP_OUT_001_IP.NCRC_FTP_OUT_001_PORT (10.217.213.69:22)" `nc -z 10.217.213.69 22`
echo "25. NPRM_FTP_OUT_001_IP.NPRM_FTP_OUT_001_PORT (10.217.214.11:22)" `nc -z 10.217.214.11 22`
echo "26. EKCM_HTTP_OUT_001_IP.EKCM_HTTP_OUT_001_PORT (dev.ktcommerce.co.kr:80)" `nc -z dev.ktcommerce.co.kr 80`
echo "27. EMHW_HTTP_OUT_001_IP.EMHW_HTTP_OUT_001_PORT (10.226.14.94:80)" `nc -z 10.226.14.94 80`
echo "28. NCDM_WS_OUT_001_IP.NCDM_WS_OUT_001_PORT (10.217.157.95:5000)" `nc -z 10.217.157.95 5000`
echo "29. NMKT_WS_OUT_001_IP.NMKT_WS_OUT_001_PORT (10.217.157.140:5000)" `nc -z 10.217.157.140 5000`
echo "30. NPRM_WS_OUT_001_IP.NPRM_WS_OUT_001_PORT (10.217.214.198:5001)" `nc -z 10.217.214.198 5001`
echo "31. NASTEC_IP.NASTEC_Port (133.9.10.202:20001)" `nc -z 133.9.10.202 20001`
echo "---------------------------- 대외 SIT Outbound 방화벽 점검 완료 ----------------------------"
