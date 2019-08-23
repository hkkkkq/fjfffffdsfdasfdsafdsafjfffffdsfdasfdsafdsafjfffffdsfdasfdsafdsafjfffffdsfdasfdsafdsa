package com.henc.cdrs.sysm.pgm.web;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.henc.cdrs.sysm.pgm.model.SearchNmspc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.henc.dream.util.CamelCaseMap;

import com.henc.cdrs.common.code.service.CodeService;
import com.henc.cdrs.common.web.BaseController;
import com.henc.cdrs.sysm.pgm.model.Pgm;
import com.henc.cdrs.sysm.pgm.model.PgmBtn;
import com.henc.cdrs.sysm.pgm.service.PgmService;

@Controller 
@RequestMapping("/sysm/pgm")
public class PgmController extends BaseController{

	@Autowired
	PgmService pgmService;
	
	@Autowired
	CodeService codeService;
		
    @Autowired
    HttpServletRequest request;

	@GetMapping("/pgmList")
	public String pgmList(Model model){
		return "views/sysm/pgm/pgmList";
	}
	
    @GetMapping({"/pgmForm","/pgmForm/{pgmId}"})
    public String pgmForm(@PathVariable Optional<String> pgmId, Model model){
        model.addAttribute("pgmId", pgmId.orElse(null));
        return "views/sysm/pgm/pgmForm";
    }
        
    @GetMapping(value = "/pgmForm/getPgm")
    public @ResponseBody Pgm getPgm(String pgmId) {
        return pgmService.getPgm(pgmId);
    }    
    
    /**
     * @param pgm
     * @return 프로그램목록
     */    
	@GetMapping("/pgmGridList")
	public @ResponseBody List<CamelCaseMap> getPgmGridList(Pgm pgm){
        return pgmService.getComPgmList(pgm);    
	}
	
    /**
     * @param pgmBtn
     * @return 프로그램버튼목록
     */ 	
	@GetMapping("/btnGridList")
    public @ResponseBody List<CamelCaseMap> btnGridList(PgmBtn pgmBtn){         
        return pgmService.getComPgmBtnList(pgmBtn); 
    }	
    
    /**
     * 프로그램 신규입력
     * @param pgm
     * @return Pgm
     */	
    @PostMapping("/createPgmData")
    public @ResponseBody Pgm createPgmData(@Valid Pgm pgm){
        pgmService.createComPgm(pgm);
        return pgm;
    } 
    
    @PostMapping("/modifyPgmData")
    public @ResponseBody Pgm modifyPgmData(Pgm pgm){
        pgmService.modifyComPgm(pgm);
        return pgm;
    }    
    
    @PostMapping("/removePgmData")
    public @ResponseBody Pgm removePgmData(Pgm pgm){
        return pgmService.removeComPgm(pgm);
    }

    @GetMapping("/nmspcModal")
    public String nmspcModal(Model model) {
        return "views/sysm/pgm/nmspcModal";
    }

    @GetMapping("/nmspcGridList")
    public @ResponseBody
    List<CamelCaseMap> getNmspcGridList(SearchNmspc searchNmspc) {
        return pgmService.getNmspcList(searchNmspc);
    }
}
