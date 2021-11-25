package com.ndl.erp.controller;

import com.ndl.erp.domain.Provider;
import com.ndl.erp.dto.ProviderDTO;
import com.ndl.erp.dto.ProvidersDTO;
import com.ndl.erp.services.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/providers")
public class ProviderController {

    @Autowired
    private ProviderService providerService;


    @PostMapping(path="/save")
    public @ResponseBody
    Provider create(@RequestBody Provider c) {
        return providerService.save(c);
    }


    @GetMapping(path = "/data/list-page")
    public @ResponseBody
    ProvidersDTO getProvidereList(@RequestParam(value = "filter", required = false) String filter,
                              @RequestParam Integer pageNumber,
                              @RequestParam Integer pageSize,
                              @RequestParam String sortDirection,
                              @RequestParam String sortField) {
        return providerService.getProviders(filter, pageNumber, pageSize, sortDirection, sortField);
    }

    @GetMapping(path = "/get")
    public @ResponseBody
    ProviderDTO getCentroCostos(
            @RequestParam(value = "id", required = false) Integer id
    ) {
        return this.providerService.getProvider(id);
    }


@GetMapping(path = "/new-form")
    public @ResponseBody
    ProviderDTO getNewCentroCostos(


    ) {
        return this.providerService.getProvider();
    }


}
