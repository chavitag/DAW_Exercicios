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
    KBError code;
    private String[] msgs={
        "No es un EMail",
        "No es una Fecha",
        "No es un Número Decimal",
        "No es un número"
    };
    
    KeyboardDataInputException(KBError code) {
        super();
        
        this.code=code;
    }
    
    @Override
    public String getMessage() {
        return msgs[code.ordinal()];
    }
}
