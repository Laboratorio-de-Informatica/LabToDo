package edu.eci.labinfo.labtodo.service;

import org.springframework.stereotype.Service;
import org.primefaces.PrimeFaces;
import org.primefaces.context.PrimeRequestContext;

@Service
public class PrimeFacesWrapper {

    public PrimeFaces current() {
        return PrimeFaces.current();
    }

    public PrimeRequestContext gRequestContext() {
        return PrimeRequestContext.getCurrentInstance();
    }
    
}
