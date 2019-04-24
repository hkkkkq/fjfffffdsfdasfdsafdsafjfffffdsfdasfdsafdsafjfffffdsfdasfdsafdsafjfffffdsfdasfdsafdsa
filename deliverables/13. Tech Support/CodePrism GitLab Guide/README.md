# CodePrism GitLab 소스 진단 가이드

> Gitlab 연동방법과 CodeEyes, CodePrism 적용 기준 가이드



## Gitlab 연동방법에 대한 안내

GitLab 의 실제주소(IP 등), user id/pw , ssh 옵션 등에 대한 정보만 저희쪽에 전달주시면 되며, kt ds ITO 시스템 대상으로만 CodePrism 시스템 수용이 가능합니다. 

## CodeEyes/CodePrism 적용기준

| 구분              | 설명                                                         |
| ----------------- | ------------------------------------------------------------ |
| KT SI 프로젝트    | It’s Portal 에 소스코드 산출물 등록할 경우에 CodeEyes가 자동 점검 수행 |
| KT DS SI 프로젝트 | PMD or CodePrism 하여 점검 ( 프로젝트 판단 )                 |
| KT DS ITO         | CodePrism 내 수용                                            |

두 시스템 모두 자가점검용 .net 기반 Client / Eclipse Plug-in 이용이 가능합니다. 
 

> CodeEyes의 경우 KT/KT DS 직원의 경우 LDAP 연동이 되어있어 이용이 가능하고
>
> CodePrism의 경우에는 계정을 저희가 신청받아서 생성 ,권한&점검환경을 셋팅해드려야 이용이 가능합니다. 

