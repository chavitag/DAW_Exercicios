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
class URL {
    private static String[] IANADomains= {
        "org",
        "com",
        "es",
        "gz",
        "fr",
        "edu",
    };
    
    public static boolean verifyDomain(String url) {
        boolean ret=false;
        String[] m=url.toLowerCase().split("\\.");
        if (m.length>1) {
           return isIANADomain(m[m.length-1]);
        }
        return ret;
    }

    public static boolean isIANADomain(String string) {
        String str=string.toLowerCase();
        for(String dom: IANADomains) {
            if (dom.equals(str)) return true;
        }
        return false;
    }
    
}
