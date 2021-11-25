package com.ndl.erp.controller;

import com.ndl.erp.dto.BalanceDTO;
import com.ndl.erp.services.BalanceService;
import com.ndl.erp.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

//import java.sql.Date;

@RestController
@RequestMapping("/api/balance")
public class BalanceController {

    @Autowired
    private BalanceService service;;

    @GetMapping(path = "/generate")
    public @ResponseBody
    BalanceDTO generateBalance(

            @RequestParam (value="startDate",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy")
                    Date startDate,
            @RequestParam (value="endDate",required=false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate
            ) {
        java.sql.Date sDate = DateUtil.convertDateToSqlDate(startDate);
        java.sql.Date eDate = DateUtil.convertDateToSqlDate(endDate);
        return service.generate(sDate,eDate);
    }
}
