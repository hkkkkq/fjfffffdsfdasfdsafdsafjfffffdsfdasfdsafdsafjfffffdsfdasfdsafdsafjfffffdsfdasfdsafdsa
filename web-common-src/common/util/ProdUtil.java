package com.kt.kkos.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProdUtil {

	private static final Logger logger = LoggerFactory.getLogger(ProdUtil.class);
	
	/**
	 * VAT 계산처리
	 * @param mapProdPrice(상품-가격 맵)
	 * @param listNoTaxDcPriceProdId(부가세 미포함 할인 상품 리스트)
	 * @return mapCalcResult(계산 결과 맵) 
	 * */
	public static Map<String, String> calcValueAddedTax(Map<String,String> mapProdPrice, List<String> listNoTaxDcPriceProdId)
	{
		Map<String, String> mapCalcResult = new HashMap<String, String>();
		Set<String> setProdId = mapProdPrice.keySet();
		
		for(String strProdId : setProdId)
		{
			String strPrice = mapProdPrice.get(strProdId);
			
			if(!listNoTaxDcPriceProdId.contains(strProdId))
			{
				Double dProdSaleAmt = Double.parseDouble(strPrice);
				strPrice = (int)Math.round(dProdSaleAmt * 1.1d)+"";
			}

			mapCalcResult.put(strProdId, strPrice);
		}
		
		return mapCalcResult;
		
	}
	
	/**
	 * VAT 계산처리
	 * @param mapProdPrice(상품-가격 맵)
	 * @return mapCalcResult(계산 결과 맵) 
	 * */
	public static Map<String, String> calcValueAddedTax(Map<String,String> mapProdPrice)
	{
		return ProdUtil.calcValueAddedTax(mapProdPrice, new ArrayList<String>());
	}
}
