/*
 * This file is licensed to the authors stated below
 * Any unauthrised changes are prohibited.
 * and open the template in the editor.
 */

package com.softserve.auxillary.Exceptions;

/**
 *
 * @author SoftServe Group [ Mathys Ellis (12019837) Kgothatso Phatedi Alfred
 * Ngako (12236731) Tokologo Machaba (12078027) ]
 */
public class AutomaticSystemIDGenerationException extends Exception {

    public AutomaticSystemIDGenerationException(String message, Throwable cause) 
    {
        super(message, cause);
    }
    
    public AutomaticSystemIDGenerationException(String message) 
    {
        super(message);
    }  
    
}
