package com.intdict.interactivedictionary.form;

import com.intdict.interactivedictionary.service.CategoryRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class CategoryExistsValidator implements ConstraintValidator<CategoryExists, Object> {

	private String category;
    private String message;
    
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void initialize(final CategoryExists constraintAnnotation) {
        category = constraintAnnotation.category();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        boolean valid = true;
        System.out.println("isValid called!!!!");
        
        try
        {
            final Object categoryObj = BeanUtils.getProperty(value, category);
            int size = categoryRepository.findByName((String)categoryObj).size();
            //System.out.println("Size: " + size);
            
            if (size > 0) {
            	valid = false;
            }
            
        }
        catch (final Exception ignore)
        {
            // ignore
        }

        if (!valid){
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(category)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return valid;
    }
	
}