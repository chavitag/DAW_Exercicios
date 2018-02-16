/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myscanner;

/**
 *
 * @author xavi
 */
class KeyboardDataInputException extends Exception {
    private KBError code;
    private String info;
    private final String[] msgs={
        "No es un EMail",
        "No es una Fecha",
        "No es un Número Decimal",
        "No es un número"
    };
    
    KeyboardDataInputException(KBError code) {
        super();
        
        this.code=code;
    }
    
    KeyboardDataInputException(KBError code,String errinfo) {
        super();
        this.info=errinfo;
        this.code=code;
    }
    
    @Override
    public String getMessage() {
        String msg=msgs[code.ordinal()];
        if (info!=null) msg=msg+" ("+info+")";
        return msg;
    }
}
