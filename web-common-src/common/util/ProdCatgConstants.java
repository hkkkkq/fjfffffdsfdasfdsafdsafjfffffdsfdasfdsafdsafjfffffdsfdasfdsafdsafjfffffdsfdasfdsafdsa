package com.kt.kkos.common.util;

public class ProdCatgConstants {

	/**
	 * 최초 부모 카테고리
	*/
	public static final String FIRST_PARENT_CATG = "00"; 
	
	/**
	 * LTE 여부 (단말 스펙)
	*/
	public static final String LTE_YN = "077";
	
	/**
	 * 세대구분 : 상품 카테고리 조회 속성 (3G, LTE, ...)
	 * 키오스크 1차 카테고리 활성화 여부에 사용
	*/
	public static final String GEN_TYPE = "GEN_TYPE"; // 세대구분 : 상품 카테고리 조회 속성 (3G, LTE, ...)

	/**
	 * 가입가능 연령대 : 상품 카테고리 조회 속성 (최소나이,최대나이)
	 * 현재 고객의 연령대에 가입 불가한 카테고리 제외
	*/
	public static final String DISPLAY_AGE = "DISPLAY_AGE"; // 가입가능 연령대 : 상품 카테고리 조회 속성 (최소나이,최대나이)

	/**
	 * 대표 카테고리 연령대 : 상품 카테고리 조회 속성 (최소나이,최대나이)
	 * 현재 고객의 연령대에 추천하는 카테고리 세팅
	*/
	public static final String REP_CATG_AGE = "REP_CATG_AGE"; // 대표 카테고리 연령대 : 상품 카테고리 조회 속성 (최소나이,최대나이)

	/**
	 * 전체 상품 카테고리
	 * 전체상품 카테고리 노드 처리
	*/
	public static final String ALL_PROD = "ALL_PROD";
}
