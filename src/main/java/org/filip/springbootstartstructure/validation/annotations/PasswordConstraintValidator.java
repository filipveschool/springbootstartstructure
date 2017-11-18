package org.filip.springbootstartstructure.validation.annotations;

import com.google.common.base.Joiner;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.NumberRangeRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {
    public void initialize(ValidPassword constraint) {
    }

    public boolean isValid(String password, ConstraintValidatorContext context) {

        final PasswordValidator validator = new PasswordValidator(Arrays.asList(
                // At least 8 characters
                new LengthRule(8,30),
                // at least one upper-case character
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                // at least one lower-case character
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                // at least one digit character
                new CharacterRule(EnglishCharacterData.Digit, 1),
                // at least one symbol (special character)
                new CharacterRule(EnglishCharacterData.Special, 1),
                //new NumberRangeRule(3,4),
                // no whitespace
                new WhitespaceRule()
        ));

        final RuleResult result = validator.validate(new PasswordData(password));
        if(result.isValid()) {
            return true;
        }

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(Joiner.on(",").join(validator.getMessages(result))).addConstraintViolation();
        return false;
    }
}
