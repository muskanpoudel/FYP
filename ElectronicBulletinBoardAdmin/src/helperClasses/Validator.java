package helperClasses;

import java.util.ArrayList;
import java.util.List;

public class Validator 
{
	List<BaseValidator> lstValidator=new ArrayList<BaseValidator>();

	public void addValidator(BaseValidator validator)
	{
		lstValidator.add(validator);
	}
	
	public boolean validate()
	{
		boolean validateStatus=true;
		for(BaseValidator bv:lstValidator){
			if(!bv.validate()){
			  validateStatus= false;
			}
		}
		return validateStatus;
	}
}
