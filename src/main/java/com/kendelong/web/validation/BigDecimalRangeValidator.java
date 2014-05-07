package com.kendelong.web.validation;

import java.math.BigDecimal;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Ken
 * WARNING: this is not a generic BigDecimal validator.  It's possible for BigDecimals to have values 
 * beyond Double.MAX.  For a better implemenation, you should use 
 * http://stackoverflow.com/questions/15488990/validating-double-and-float-values-using-hibernate-validator-bean-validation
 * which is where I took this code from.
 * 
 * I'm only using this because the generic Min and Max annotations only take Longs and I need decimal capability.
 *
 */
public final class BigDecimalRangeValidator implements ConstraintValidator<BigDecimalRange, Object>
{
    private double max;
    private double min;

    @Override
    public void initialize(final BigDecimalRange bigDecimalRange) 
    {
        max = bigDecimalRange.max();
        min = bigDecimalRange.min();
    }

    @Override
    public boolean isValid(final Object object, final ConstraintValidatorContext cvc) 
    {
        boolean isValid=false;

        if(object == null) //This should be validated by the not null validator.
        {
            isValid=true;
        }
        else if(object instanceof BigDecimal)
        {
            BigDecimal bigDecimal=new BigDecimal(object.toString());
            double dval = bigDecimal.doubleValue();
            isValid = (dval >= min) && (dval <= max);

            if(!isValid)
            {
                cvc.disableDefaultConstraintViolation();
                cvc.buildConstraintViolationWithTemplate("Value should be between [" + min + "] and [" + max + "] but was [" + dval + "]").addConstraintViolation();
            }
        }
        return isValid;
    }
}